package com.ProductionPlanExecution.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_ReasonMaster")
@Data
public class ReasonMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Item_Name", length = 255)
    private String itemName;

    @Column(name = "Reason_ID")
    private Integer reasonID;

    @Column(name = "Rejection_Reason", length = 255)
    private String rejectionReason;

    @Column(name = "Reason_Category", length = 255)
    private String reasonCategory;

    @Column(name = "Is_Deleted")
    private Boolean isDeleted;

    @Column(name = "Created_By")
    private Integer createdBy;


    @Column(name = "Created_On")
    private Date createdOn;


    @Column(name = "Modified_By")
    private Integer modifiedBy;

    @Column(name = "Modified_On")
    private Date modifiedOn;


}

