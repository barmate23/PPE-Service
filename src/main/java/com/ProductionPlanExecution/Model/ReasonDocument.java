package com.ProductionPlanExecution.Model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tbl_ReasonDocument")
@Data
public class ReasonDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private byte[] file;


}

