package com.ProductionPlanExecution.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_Configuration")
@Data
public class SysConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "SysConfigurationId")
    private Integer sysConfigurationId;

    @Column(name = "OrganizationId")
    private Integer organizationId;

    @Column(name = "SubOrganizationId")
    private Integer subOrganizationId;

    @Column(name = "configName")
    private String configName;

    @Column(name = "configValue")
    private String configValue;

    @Column(name = "module")
    private String module;

    @Column(name = "IsDeleted")
    private Boolean isDeleted;

    @Column(name = "CreatedBy")
    private Integer createdBy;

    @Column(name = "CreatedOn")
    private Date createdOn;

    @Column(name = "ModifiedBy")
    private Integer modifiedBy;

    @Column(name = "ModifiedOn")
    private Date modifiedOn;


}
