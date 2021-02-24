package ub.dalvarezrios.hummus.models.dao;

import org.springframework.data.repository.CrudRepository;
import ub.dalvarezrios.hummus.models.entity.VirtualMachine;

import java.util.List;

public interface IVmDao extends CrudRepository<VirtualMachine, Long> {

    List<VirtualMachine> findByUserId(Long user_id);
    List<VirtualMachine> findByVmName(String vmName);

}
