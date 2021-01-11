package ub.dalvarezrios.hummus.models.dao;

import org.springframework.data.repository.CrudRepository;
import ub.dalvarezrios.hummus.models.entity.User;


public interface IUserDao extends CrudRepository<User, Long> {

}
