package ub.dalvarezrios.hummus.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name="virtual_machines")
public class VirtualMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique=true, name="vm_name")
    private String vmName;

    @NotEmpty
    @Column(unique=true)
    private String port;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="exercise_id")
    private Exercise exercise;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="dhcp_id")
    private DHCPServer dhcpServer;

    @Column(name="type")
    private Integer type;

    @Column(name="used")
    private boolean used;

    public VirtualMachine(){

    }

    public VirtualMachine(String vm_name, String port, User user){
        this.vmName = vm_name;
        this.port = port;
        this.user = user;
    }

    @PrePersist
    public void prePersist(){
        this.used = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVm_name() {
        return vmName;
    }

    public void setVm_name(String vm_name) {
        this.vmName = vm_name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DHCPServer getDhcpServer() {
        return dhcpServer;
    }

    public void setDhcpServer(DHCPServer dhcpServer) {
        this.dhcpServer = dhcpServer;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
