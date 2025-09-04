package com.ProductionPlanExecution.Response;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class PPEResponse {

  private String planOrderId;
  private String ppeId;
  private String status;
  private String bomId;
  private String product;
  private String brand;
  private String model;
  private String variant;
  private String color;
  private String uom;
  private Integer planQuantity;
  private Integer produceQuantity;
  private Integer shortQuantity;
  private String productionShop;
  private String shopId;
  private String line;
  private String lineId;
  private Date startDate;
  private Date startTime;
  private Date endDate;
  private Date endTime;
  private List<ItemsResponse> items;
  
  // Getters and setters
}
