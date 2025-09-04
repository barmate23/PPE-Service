package com.ProductionPlanExecution.Model;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "tbl_AcceptedRejectedContainerBarcode")
public class AcceptedRejectedContainerBarcode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "acceptedRejectedContainerId", referencedColumnName = "id")
    private AcceptedRejectedContainer acceptedRejectedContainer;

    @Column(name = "barcode")
    private Integer barcode;

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
