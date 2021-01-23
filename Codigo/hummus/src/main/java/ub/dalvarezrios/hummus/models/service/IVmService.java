package ub.dalvarezrios.hummus.models.service;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import ub.dalvarezrios.hummus.models.entity.User;
import ub.dalvarezrios.hummus.models.entity.VirtualMachine;

import java.util.List;

public interface IVmService {
    public List<VirtualMachine> findAll();
    public void save(VirtualMachine vm);
    public VirtualMachine findOne(Long id);
    public void delete(Long id);

}
