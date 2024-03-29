package ub.dalvarezrios.hummus.models;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.virtualbox_6_1.*;
import ub.dalvarezrios.hummus.models.entity.DHCPServer;

import java.util.ArrayList;
import java.util.List;

public class VBoxManager {

    private final VirtualBoxManager boxManager;
    private final IVirtualBox vbox;
    private IProgress progress;
    protected final Log _logger = LogFactory.getLog(this.getClass());

    public VBoxManager() {
        boxManager = VirtualBoxManager.createInstance(null);
        boxManager.connect("http://localhost:18083", null, null);
        vbox = boxManager.getVBox();
    }

    public IMachine findMachine(String name){
        return vbox.findMachine(name);
    }

    public List<IMachine> getMachines(MachineState state) {
        List<IMachine> iMachines = new ArrayList<>();
        for (IMachine machine : vbox.getMachines()) {
            if (machine.getState() == state) {
                iMachines.add(machine);
            }
        }
        return iMachines;
    }

    public boolean launchMachine(String machineName, LaunchMode mode) {
        if (!machineExists(machineName)) {
            return false;
        }
        IMachine machine = vbox.findMachine(machineName);
        ISession session = boxManager.getSessionObject();
        //System.out.println(session.getState().name());
        try {
            IProgress progress = machine.launchVMProcess(session, mode.name(), null);
            wait(progress);
        } finally {
            session.unlockMachine();
        }

        try {
            String ipv4 = null;
            do {
                Thread.sleep(3000);
                ipv4 = getMachineIPv4(machineName);
            } while (ipv4 == null);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return true;
    }

    public String getMachineIPv4(String machineName) {
        if (!machineExists(machineName)) {
            return null;
        }
        IMachine machine = vbox.findMachine(machineName);

        //scan the machine properties looking for its ip, once
        //we get it, we can assemble the command to add the new rule
        Holder<List<String>> keys = new Holder<>();
        Holder<List<String>> values = new Holder<>();
        Holder<List<Long>> timestamps = new Holder<>();
        Holder<List<String>> flags = new Holder<>();
        machine.enumerateGuestProperties(null, keys, values, timestamps, flags);
        String ipv4 = null;
        for (int i = 0; i < keys.value.size(); i++) {
            String key = keys.value.get(i);
            String val = values.value.get(i);
            if (key.contains("GuestInfo/Net/0/V4/IP") && val.startsWith("10.0")) {
                ipv4 = val;
                break;
            }
        }
        //if this property was not found, we can't continue
        return ipv4;
    }

    public IProgress getProgress() {
        return progress;
    }

    private void waitToUnlock(ISession session, IMachine machine) {
        session.unlockMachine();
        SessionState sessionState = machine.getSessionState();
        while (!SessionState.Unlocked.equals(sessionState)) {
            sessionState = machine.getSessionState();
            try {
                System.err.println("Waiting for session unlock...[" + sessionState.name() + "][" + machine.getName() + "]");
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                System.err.println("Interrupted while waiting for session to be unlocked");
            }
        }
    }

    private void wait(IProgress progress) {
        this.progress = progress;
        progress.waitForCompletion(-1);
        if (progress.getResultCode() != 0) {
            System.err.println("Operation failed: " + progress.getErrorInfo().getText());
        }
    }

    public boolean machineExists(String machineName) {
        if (machineName == null) {
            return false;
        }
        List<IMachine> machines = vbox.getMachines();
        for (IMachine machine : machines) {
            if (machine.getName().equals(machineName)) {
                return true;
            }
        }
        return false;
    }


    public void setPortVRDE(String nameMachine, String port){
        IMachine machine = findMachine(nameMachine);
        ISession session = boxManager.getSessionObject();
        machine.lockMachine(session, LockType.Write);
        IMachine mutable = session.getMachine();
        mutable.getVRDEServer().setVRDEProperty("TCP/Ports", port);
        mutable.getVRDEServer().setEnabled(true);
        mutable.saveSettings();
        session.unlockMachine();

    }

    public boolean existsDHCPServer(String name){

        boolean exists = false;
        for(IDHCPServer dhcp : vbox.getDHCPServers()){
            if(dhcp.getNetworkName().equals(name)){
                exists = true;
                break;
            }
        }
        return exists;
    }

    public IDHCPServer findDHCPServerByName(String name){
        for(IDHCPServer dhcp : vbox.getDHCPServers()){
            if(dhcp.getNetworkName().equals(name)){
                return dhcp;
            }
        }
        return null;
    }

    public void createDHCPServer(String name, DHCPServer conf){
        IDHCPServer dhcpServer = vbox.createDHCPServer(name);
        // IpAddress, networkMask, lowerIp, upperIp
        dhcpServer.setConfiguration(conf.getIp(), conf.getNetMask(), conf.getLower_ip(), conf.getUpper_ip());
        dhcpServer.setEnabled(true);
        dhcpServer.start(name, String.valueOf(NetworkAttachmentType.Internal));
    }

    public void deleteDHCPServer(String name){
        IDHCPServer toRemove = findDHCPServerByName(name);
        if(toRemove != null){
            vbox.removeDHCPServer(toRemove);
        }
    }

    public boolean assignInternalNetworkFromMachineNames(String networkName, List<String> machineNames, DHCPServer conf){

        boolean fail = false;

        if(machineNames.size() < 2){
            _logger.info("createInternalNetworkFromMachineNames: Not enough machines to create a network");
            return fail;
        }

        if(existsDHCPServer(networkName)){
            deleteDHCPServer(networkName);
        }

        createDHCPServer(networkName, conf);


        for(String machineName: machineNames){
            if(machineExists(machineName)) {
                IMachine machine = findMachine(machineName);
                ISession session = boxManager.getSessionObject();
                machine.lockMachine(session, LockType.Write);
                IMachine mutable = session.getMachine();
                INetworkAdapter networkAdapter = mutable.getNetworkAdapter(0L);
                networkAdapter.setAttachmentType(NetworkAttachmentType.Internal);
                networkAdapter.setInternalNetwork(networkName);
                networkAdapter.setPromiscModePolicy(NetworkAdapterPromiscModePolicy.AllowNetwork); // Same as Allow VMs
                mutable.saveSettings();
                session.unlockMachine();
            }else {
                fail = true;
                break;
            }
        }

        return fail;
    }

    public IMachine cloneMachine(String nameOldMachine, String nameNewMachine){
        IMachine oldMachine = findMachine(nameOldMachine);
        IMachine newMachine = vbox.createMachine(null, nameNewMachine, null, oldMachine.getOSTypeId(), null);

        newMachine.saveSettings();
        vbox.registerMachine(newMachine);

        ISession session = boxManager.getSessionObject();
        newMachine.lockMachine(session, LockType.Write);
        IMachine mutable = session.getMachine();

        List<CloneOptions> options = new ArrayList<CloneOptions>(); //clone options
        //options.add(CloneOptions.KeepDiskNames, CloneOptions.); //just keep the disk name
        IProgress clone_progress = oldMachine.cloneTo(mutable, CloneMode.MachineState, options); //start the clone process
        //progressBar(clone_progress); //this function keeps track of the clone percent

        mutable.saveSettings();
        session.unlockMachine();

        return newMachine;
    }

    private boolean progressBar(IProgress p)
    {
        while (!p.getCompleted())
        {
            System.out.println(p.getPercent() + " . ");
        }
        if(p.getCompleted()){
            System.out.println(p.getResultCode());
            IVirtualBoxErrorInfo info = p.getErrorInfo();
            if(info!=null)
                System.out.println(info.getText());
        }
        return true;
    }

}
