package ub.dalvarezrios.hummus.models.dao;

import org.springframework.data.repository.CrudRepository;
import ub.dalvarezrios.hummus.models.entity.User;

import java.util.List;


public interface IUserDao extends CrudRepository<User, Long> {

    List<User> findByUsername(String username);
    List<User> findByEmail(String email);

}
