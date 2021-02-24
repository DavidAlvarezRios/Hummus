package ub.dalvarezrios.hummus.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="virtual_machines")
public class VirtualMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique=true, name="vm_name")
    private String vmName;

    @NotEmpty
    @Column(unique=true)
    private String port;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public VirtualMachine(){

    }

    public VirtualMachine(String vm_name, String port, User user){
        this.vmName = vm_name;
        this.port = port;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVm_name() {
        return vmName;
    }

    public void setVm_name(String vm_name) {
        this.vmName = vm_name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
