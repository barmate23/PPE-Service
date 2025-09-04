package com.ProductionPlanExecution.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_StoreKeeperMapper")
public class StoreKeeperMapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "OrganizationId")
    private Integer organizationId;

    @Column(name = "SubOrganizationId")
    private Integer subOrganizationId;

    @ManyToOne
    @JoinColumn(name = "StoreId")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "StoreKeeperId")
    private Users storeKeeper;

    @ManyToOne
    @JoinColumn(name = "ShiftId")
    private Shift shift;

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

