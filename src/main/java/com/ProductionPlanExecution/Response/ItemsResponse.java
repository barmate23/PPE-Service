package com.ProductionPlanExecution.Response;

import lombok.Data;

import java.util.Date;

@Data
public  class ItemsResponse {
    private Integer Id;
    private String code;
    private String itemName;
    private String type;
    private String clas;
    private Double itemUnitWeight;
    private String attribute;
    private String uom;
  
    private Integer requiredQuantity;
    private Date requiredBy;
    private Integer shortage;
    private Integer pipeline;
    private String store;
    private Date eta;
    private String status;
    private String orgOrAlt;



    // Getters and setters
  }