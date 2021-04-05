package ub.dalvarezrios.hummus.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="dchp_server")
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
    private String lower_ip;

    @NotEmpty
    private String upper_ip;

    @NotEmpty
    private boolean enabled;

    @NotEmpty
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
}
