package ub.dalvarezrios.hummus.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import ub.dalvarezrios.hummus.models.dao.IVmDao;
import ub.dalvarezrios.hummus.models.entity.User;
import ub.dalvarezrios.hummus.models.entity.VirtualMachine;

import java.util.List;

public class VmService implements IVmService{

    @Autowired
    private IVmDao iVmDao;

    @Override
    public List<VirtualMachine> findAll() {
        return (List<VirtualMachine>) iVmDao.findAll();
    }

    @Override
    public void save(VirtualMachine vm) {
        iVmDao.save(vm);
    }

    @Override
    public VirtualMachine findOne(Long id) {
        return (VirtualMachine) iVmDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        iVmDao.deleteById(id);
    }
}
