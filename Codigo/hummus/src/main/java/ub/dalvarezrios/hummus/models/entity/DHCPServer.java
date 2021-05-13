package ub.dalvarezrios.hummus.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="dhcp_server")
public class DHCPServer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique=true)
    private String netname;

    @NotEmpty
    private String ip;

    @NotEmpty
    private String netMask;

    @NotEmpty
    private String lower_ip;

    @NotEmpty
    private String upper_ip;

    private boolean enabled;

    private boolean used;

    // GETTER AND SETTER
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNetname() {
        return netname;
    }

    public void setNetname(String netname) {
        this.netname = netname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLower_ip() {
        return lower_ip;
    }

    public void setLower_ip(String lower_ip) {
        this.lower_ip = lower_ip;
    }

    public String getUpper_ip() {
        return upper_ip;
    }

    public void setUpper_ip(String upper_ip) {
        this.upper_ip = upper_ip;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getNetMask() {
        return netMask;
    }

    public void setNetMask(String netMask) {
        this.netMask = netMask;
    }

    public void autoconfigure(String name){
        this.setNetname(name);
        this.setUsed(true);
        this.setIp("10.0.0.1");
        this.setNetMask("255.255.255.0");
        this.setLower_ip("10.0.0.2");
        this.setUpper_ip("10.0.0.12");
    }
}
