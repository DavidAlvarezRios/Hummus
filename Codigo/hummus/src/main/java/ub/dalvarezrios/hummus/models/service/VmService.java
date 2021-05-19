package ub.dalvarezrios.hummus.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ub.dalvarezrios.hummus.models.dao.IVmDao;
import ub.dalvarezrios.hummus.models.entity.Exercise;
import ub.dalvarezrios.hummus.models.entity.VirtualMachine;

import java.util.List;

@Repository
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

    @Override
    public List<VirtualMachine> findAllVmByUserID(Long user_id) {
        return iVmDao.findByUserId(user_id);
    }

    @Override
    public VirtualMachine findByMachineName(String name){
        List<VirtualMachine> result = iVmDao.findByVmName(name);
        if(result.isEmpty()){
            return null;
        }
        return result.get(0);
    }
    @Override
    public List<VirtualMachine> findVMsByExercise(Long exerciseId) {
        return iVmDao.findByExerciseAndUsedFalse(exerciseId);
    }

    public List<VirtualMachine> findVMsByExercise(Exercise exercise){
        return iVmDao.findByExerciseAndUsedFalse(exercise);
    }
}
