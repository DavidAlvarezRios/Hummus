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
    @Column(unique=true)
    private String vm_name;

    @NotEmpty
    @Column(unique=true)
    private String vm_path;

    @NotEmpty
    @Column(unique=true)
    private String port;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;


}
