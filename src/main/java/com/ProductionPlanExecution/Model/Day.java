package com.ProductionPlanExecution.Model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "tbl_Day")
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "OrganizationId")
    private Integer organizationId;

    @Column(name = "SubOrganizationId")
    private Integer subOrganizationId;

    @Column(name = "Day", length = 255)
    private String day;

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
