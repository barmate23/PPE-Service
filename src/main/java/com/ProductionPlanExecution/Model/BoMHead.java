package com.ProductionPlanExecution.Model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Table(name = "tbl_BoMHead")
public class BoMHead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "Model", length = 255)
    private String model;

    @Column(name = "Variant", length = 255)
    private String variant;

    @Column(name = "Colour", length = 255)
    private String colour;

    @Column(name = "bomId", nullable = false)
    private String bomId;

    @Column(name = "Date")
    private Date date;

    @Column(name = "Version", length = 10)
    private String version;

    @Column(name = "LifecyclePhase", length = 50)
    private String lifecyclePhase;

    @Column(name = "IsActive")
    private Boolean isActive;

    @Column(name = "IsDeleted")
    private Boolean isDeleted;

    @Column(name = "CreatedBy")
    private Integer createdBy;

    @Column(name = "CreatedOn")
    private Timestamp createdOn;

    @Column(name = "ModifiedBy")
    private Integer modifiedBy;

    @Column(name = "ModifiedOn")
    private Timestamp modifiedOn;
}
