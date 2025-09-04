package com.ProductionPlanExecution.Model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "tbl_PurchaseOrderLine")
public class PurchaseOrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "PurchaseOrderLineID")
    private String purchaseOrderLineId;

    @Column(name = "OrganizationId")
    private Integer organizationId;

    @Column(name = "SubOrganizationId")
    private Integer subOrganizationId;

    @ManyToOne
    @JoinColumn(name = "PurchaseOrderHeadID", referencedColumnName = "Id")
    private PurchaseOrderHead purchaseOrderHead;

    @Column(name = "LineNumber")
    private Integer lineNumber;

    @ManyToOne
    @JoinColumn(name = "ItemID", referencedColumnName = "id")
    private Item item;

    @Column(name = "UoM", length = 255)
    private String uom;

    @Column(name = "Currency", length = 255)
    private String currency;

    @Column(name = "UnitPrice")
    private Integer unitPrice;

    @Column(name = "numberOfContainer")
    private Integer numberOfContainer;

    @Column(name = "PurchaseOrderQuantity")
    private Integer purchaseOrderQuantity;

    @Column(name = "SubTotalRs")
    private Integer subTotalRs;

    @Column(name = "StateGSTPercentage")
    private Integer stateGSTPercentage;

    @Column(name = "StateGSTAmount")
    private Integer stateGSTAmount;

    @Column(name = "CentralGSTPercentage")
    private Integer centralGSTPercentage;

    @Column(name = "InterStateGSTPercentage")
    private Integer interStateGSTPercentage;

    @Column(name = "InterStateGSTAmount")
    private Integer interStateGSTAmount;

    @Column(name = "TotalAmountRs")
    private Integer totalAmountRs;

    @ManyToOne
    @JoinColumn(name = "StatusId", referencedColumnName = "id")
    private PurchaseStatus status;

    @Column(name = "RemainingQuantity")
    private Integer remainingQuantity;

    @Column(name = "RemainingAmount")
    private Integer remainingAmount;

    @Column(name = "IsDeleted")
    private Boolean isDeleted;

    @Column(name = "CreatedBy")
    private Long createdBy;

    @Column(name = "CreatedOn")
    private Timestamp createdOn;

    @Column(name = "ModifiedBy")
    private Integer modifiedBy;

    @Column(name = "ModifiedOn")
    private Timestamp modifiedOn;
}
