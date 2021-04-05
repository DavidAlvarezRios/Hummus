package ub.dalvarezrios.hummus.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ub.dalvarezrios.hummus.models.dao.IDHCPServerDao;
import ub.dalvarezrios.hummus.models.entity.DHCPServer;

import java.util.List;

@Repository
public class DHCPServerService implements IDHCPServerService {

    @Autowired
    private IDHCPServerDao dhcpDao;

    @Override
    public List<DHCPServer> findall() {
        return (List<DHCPServer>) dhcpDao.findAll();
    }

}
