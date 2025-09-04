package com.ProductionPlanExecution.Model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="tbl_PpeStatus")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String statusName;

}
