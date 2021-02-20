package ub.dalvarezrios.hummus.models.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ub.dalvarezrios.hummus.models.dao.IUserDao;
import ub.dalvarezrios.hummus.models.entity.User;

import java.util.List;

@Repository
public class UserService implements IUserService{

    protected final Log _logger = LogFactory.getLog(this.getClass());

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

    @Override
    public boolean existsUsername(String username) {
        return !userDao.findByUsername(username).isEmpty();
    }

    @Override
    public boolean existsEmail(String email) {
        return !userDao.findByEmail(email).isEmpty();
    }

    public User findByUsername(String username){
        return userDao.findByUsername(username).get(0);
    }
}
