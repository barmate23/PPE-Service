package com.ProductionPlanExecution.Response;

import lombok.Data;

import java.util.List;

@Data
public class SubModuleResponse {
    private Integer licenceLineId;
    private String partNumber;
    private Integer defaultAddition;
    private String discription;
    private String reason;
    private Integer status;
    private List<License> licenses;
}
