package ub.dalvarezrios.hummus.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ub.dalvarezrios.hummus.models.dao.IRoleDao;
import ub.dalvarezrios.hummus.models.entity.Role;

import java.util.List;

@Repository
public class RoleService implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    @Override
    public List<Role> findAll() {
        return (List<Role>) roleDao.findAll();
    }

    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public Role findOne(Long id) {
        return roleDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        roleDao.deleteById(id);
    }
}
