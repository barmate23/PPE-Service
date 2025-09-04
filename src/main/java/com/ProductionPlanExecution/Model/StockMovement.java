package com.ProductionPlanExecution.Model;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "tbl_StockMovement")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MovementId", nullable = false)
    private Integer movementId;

    @Column(name = "organizationId")
    private Integer organizationId;

    @Column(name = "subOrganizationId")
    private Integer subOrganizationId;

    @Column(name = "GroupId")
    private Integer groupId;

    @ManyToOne
    @JoinColumn(name = "ItemId", referencedColumnName = "id")
    private Item item;

    private  Integer received;

    @ManyToOne
    @JoinColumn(name = "SourceLocationId", referencedColumnName = "id")
    private Location sourceLocation;

    @ManyToOne
    @JoinColumn(name = "DestinationLocation", referencedColumnName = "id")
    private Location destinationLocation;

    @ManyToOne
    @JoinColumn(name = "TransationTypeId", referencedColumnName = "id")
    private TransactionStatus transactionType;

    @Column(name = "BatchSize")
    private Integer batchSize;

    @Column(name = "SerialBatchNo")
    private Integer serialBatchNo;

    @Column(name = "Mode", length = 100)
    private String mode;

    @Column(name = "RejectedReason", length = 20)
    private String rejectedReason;

    @Column(name = "TimeStamp")
    private Timestamp timeStamp;

    @Column(name = "GRNNumber")
    private Integer grnNumber;

    @Column(name = "ItemPrice")
    private Integer itemPrice;

    @Column(name = "DiscrepancyDetail", length = 100)
    private String discrepancyDetail;

    @Column(name = "ASNId")
    private Integer asnId;

    @Column(name = "AcceptedQty")
    private Integer acceptedQty;

    @Column(name = "RejectedQty")
    private Integer rejectedQty;

    @ManyToOne
    @JoinColumn(name = "transactionStatusId", referencedColumnName = "id")
    private TransactionStatus transactionStatus;

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
