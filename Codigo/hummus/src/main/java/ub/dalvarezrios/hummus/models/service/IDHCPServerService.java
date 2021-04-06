package ub.dalvarezrios.hummus.models.service;

import ub.dalvarezrios.hummus.models.entity.DHCPServer;

import java.util.List;

public interface IDHCPServerService {

    public List<DHCPServer> findall();
    public void save(DHCPServer dhcp);
    public DHCPServer findOne(Long id);
    public void delete(Long id);

}
