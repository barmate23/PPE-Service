package com.ProductionPlanExecution.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "tbl_POAsnInvoiceGrnMapper")
public class POAsnInvoiceGrnMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "OrganizationId")
    private Integer organizationId;

    @Column(name = "SubOrganizationId")
    private Integer subOrganizationId;

    @Column(name = "PoNumber")
    private Integer poNumber;

    @Column(name = "ASNNumber")
    private Integer asnNumber;

    @Column(name = "InvoiceNo")
    private Integer invoiceNumber;

    @Column(name = "GRNNumber")
    private Integer grnNumber;

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
