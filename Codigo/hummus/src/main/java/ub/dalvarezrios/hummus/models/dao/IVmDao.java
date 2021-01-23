package ub.dalvarezrios.hummus.models.dao;

import org.springframework.data.repository.CrudRepository;
import ub.dalvarezrios.hummus.models.entity.VirtualMachine;

public interface IVmDao extends CrudRepository<VirtualMachine, Long> {
}
