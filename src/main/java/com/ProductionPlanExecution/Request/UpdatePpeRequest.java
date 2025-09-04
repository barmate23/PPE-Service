package com.ProductionPlanExecution.Request;

import lombok.Data;

@Data
public class UpdatePpeRequest {

    private Integer planId;

    private Integer officerId;

    private String startTime;

    private String endDate;

    private String endTime;

    private String requiredBy;
}
