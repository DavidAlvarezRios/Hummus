package ub.dalvarezrios.hummus.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ub.dalvarezrios.hummus.models.dao.IUserDao;
import ub.dalvarezrios.hummus.models.entity.User;

import java.util.List;

@Repository
public class UserService implements IUserService{

    @Autowired
    private IUserDao userDao;

    @Override
    public List<User> findAll() {
        return (List<User>) userDao.findAll();
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public User findOne(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        userDao.deleteById(id);
    }
}
