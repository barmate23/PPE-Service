package com.ProductionPlanExecution.Model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;


@Entity
@Data
@Table(name = "tbl_PPEHead")
public class PPEHead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "OrganizationId")
    private Integer organizationId;

    @Column(name = "SubOrganizationId")
    private Integer subOrganizationId;

    //Added new column
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PPEOfficer")
    private Users ppeOfficer;

    //Added new column
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="ppePlanStatus")
    private PpeStatus ppeStatus;

    //Added new column
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="ppeFilterId")
    private PpeFilter ppeFilter;

    //Added new column
    @ManyToOne
    private Reason reason;

    @Column(name = "planOrderId", length = 30)
    private String planOrderId;

    @Column(name = "ppeId", length = 30)
    private String ppeId;

    @ManyToOne
    @JoinColumn(name="bomHeadId")
    private BoMHead bomHead;

    @Column(name = "product", length = 30)
    private String product;

    @Column(name = "brand", length = 30)
    private String brand;

    @Column(name = "model", length = 30)
    private String model;

    @Column(name = "variant", length = 30)
    private String variant;

    @Column(name = "color", length = 30)
    private String color;

    @Column(name = "uom", length = 30)
    private String uom;

    @Column(name = "planQuantity")
    private Integer planQuantity;

    @Column(name = "produceQuantity")
    private Integer produceQuantity;

    @Column(name = "shortQuantity")
    private Integer shortQuantity;

    @Column(name = "productionShop", length = 30)
    private String productionShop;

    @Column(name = "shopId", length = 30)
    private String shopId;

    @Column(name = "line")
    private String line;

//    @Column(name = "lineId")
//    private String lineId;

    @ManyToOne
    @JoinColumn(name="assemblyLineId")
    private AssemblyLine assemblyLine;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "startTime")
    private Time startTime;

    @Column(name = "endDate")
    private Date endDate;

    @Column(name = "endTime")
    private Time endTime;

    @Column(name = "status")
    private Integer status;

    @Column(name = "isDeleted")
    private Boolean isDeleted;

    @Column(name = "createdBy")
    private Integer createdBy;

    @Column(name = "createdOn")
    private Date createdOn;

    @Column(name = "modifiedBy")
    private Integer modifiedBy;

    @Column(name = "modifiedOn")
    private Date modifiedOn;

    @Column(name= "sapId")
    private String sapId;

    //added new column
    @Column(name = "AssignedDate")
    private Date assignedDate;

    //added new column
    @Column(name = "requiredBy")
    private Time requiredBy;

    @Transient
    private Integer totalAvailable;

    @Transient
    private Integer totalShortage;

    @Transient
    private Integer shortQty;

    @Transient
    private Integer availableQty;

    @Transient
    private Integer requiredQty;

}