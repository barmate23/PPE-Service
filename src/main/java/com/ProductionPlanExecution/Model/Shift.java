package com.ProductionPlanExecution.Model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "tbl_shift")
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "OrganizationId")
    private Integer organizationId;

    @Column(name = "SubOrganizationId")
    private Integer subOrganizationId;

    @Column(name = "ShiftName")
    private String shiftName;

    @Column(name = "ShiftStart")
    private Timestamp shiftStart;

    @Column(name = "ShiftEnd")
    private Timestamp shiftEnd;

    @Column(name = "IsDeleted")
    private Boolean isDeleted;

    @Column(name = "CreatedOn")
    private Timestamp createdOn;

    @Column(name = "CreatedBy")
    private Integer createdBy;

    @Column(name = "ModifiedOn")
    private Timestamp modifiedOn;

    @Column(name = "ModifiedBy")
    private Integer modifiedBy;
}
