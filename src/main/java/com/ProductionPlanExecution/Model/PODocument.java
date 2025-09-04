package com.ProductionPlanExecution.Model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tbl_purchase_order_Document")
@Data
public class PODocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private byte[] file;
}
