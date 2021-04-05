package ub.dalvarezrios.hummus.models.dao;


import org.springframework.data.repository.CrudRepository;
import ub.dalvarezrios.hummus.models.entity.DHCPServer;

public interface IDHCPServerDao extends CrudRepository<DHCPServer, Long> {
}
