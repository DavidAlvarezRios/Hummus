package ub.dalvarezrios.hummus.models.service;

import ub.dalvarezrios.hummus.models.entity.Exercise;
import ub.dalvarezrios.hummus.models.entity.VirtualMachine;

import java.util.List;

public interface IVmService {
    public List<VirtualMachine> findAll();
    public void save(VirtualMachine vm);
    public VirtualMachine findOne(Long id);
    public void delete(Long id);
    public List<VirtualMachine> findAllVmByUserID(Long user_id);
    public VirtualMachine findByMachineName(String name);
    public List<VirtualMachine> findVMsByExercise(Long exerciseId);
    public List<VirtualMachine> findVMsByExercise(Exercise exercise);


}
