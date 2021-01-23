package ub.dalvarezrios.hummus.models.service;

import ub.dalvarezrios.hummus.models.entity.User;

import java.util.List;

public interface IUserService {

    public List<User> findAll();
    public void save(User user);
    public User findOne(Long id);
    public void delete(Long id);
    public boolean existsUsername(String username);
    public boolean existsEmail(String email);

}
