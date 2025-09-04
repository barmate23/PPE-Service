package com.ProductionPlanExecution.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "tbl_location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "OrganizationId")
    private Integer organizationId;

    @Column(name = "SubOrganizationId")
    private Integer subOrganizationId;

    @ManyToOne
    @JoinColumn(name = "ItemId", referencedColumnName = "id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "ZoneId", referencedColumnName = "id")
    private Zone zone;

    @Column(name = "Level", length = 30)
    private String level;

    @Column(name = "Row", length = 30)
    private String row;

    @Column(name = "RackFloor", length = 30)
    private String rackFloor;

    @Column(name = "RackNo", length = 30)
    private String rackNo;

    @Column(name = "ShelfNo", length = 30)
    private String shelfNo;

    @Column(name = "ErpLocationId", length = 30)
    private String erpLocationId;

    @Column(name = "LocationId", length = 30)
    private String locationId;

    @Column(name = "LocationType", length = 30)
    private String locationType;

    @Column(name = "Length")
    private Float length;

    @Column(name = "Width")
    private Float width;

    @Column(name = "Height")
    private Float height;

    @Column(name = "AreaSqCm")
    private Float areaSqCm;

    @Column(name = "VolumeCuCm")
    private Float volumeCuCm;

    @Column(name = "CarryingCapacity")
    private Integer carryingCapacity;

    @Column(name = "Isfree")
    private Integer isFree;

    @Column(name = "FreeField1")
    private Integer freeField1;

    @Column(name = "FreeField2", length = 30)
    private String freeField2;

    @Column(name = "FreeField3")
    private Integer freeField3;

    @Column(name = "FreeField4", length = 30)
    private String freeField4;

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
