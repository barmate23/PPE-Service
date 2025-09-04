package com.ProductionPlanExecution.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "tbl_AcceptedRejectedItem")
public class AcceptedRejectedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "OrganizationId")
    private Integer organizationId;

    @Column(name = "SubOrganizationId")
    private Integer subOrganizationId;

    @ManyToOne
    @JoinColumn(name = "acceptedRejectedContainerId", referencedColumnName = "id")
    private AcceptedRejectedContainer acceptedRejectedContainer;

    @ManyToOne
    @JoinColumn(name = "serialBatchNumberId", referencedColumnName = "id")
    private SerialBatchNumbers serialBatchNumbers;

    @ManyToOne
    @JoinColumn(name = "reason", referencedColumnName = "id")
    private ReasonMaster reason;

    @Column(name = "acceptedRejectedBarcode", length = 30)
    private String acceptedRejectedBarcode;

    @Column(name = "isAccepted")
    private Boolean isAccepted;

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
