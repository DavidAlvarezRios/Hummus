package ub.dalvarezrios.hummus.models.dao;

import org.springframework.data.repository.CrudRepository;
import ub.dalvarezrios.hummus.models.entity.Role;

public interface IRoleDao extends CrudRepository<Role, Long> {
}
