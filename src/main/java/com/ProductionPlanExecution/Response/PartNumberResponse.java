package com.ProductionPlanExecution.Response;

import lombok.Data;

@Data
public class PartNumberResponse {
    private Integer licenseLineId;
    private Integer partNumberId;
    private String partNumber;
    private Integer defaultAddition;
    private Integer quantity;
    private String description;
}
