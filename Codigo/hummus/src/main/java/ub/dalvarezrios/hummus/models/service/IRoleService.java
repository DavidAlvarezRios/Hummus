package ub.dalvarezrios.hummus.models.service;

import ub.dalvarezrios.hummus.models.entity.Role;

import java.util.List;

public interface IRoleService {

    public List<Role> findAll();
    public void save(Role role);
    public Role findOne(Long id);
    public void delete(Long id);
}
