package com.ProductionPlanExecution.Request;

import lombok.Data;

@Data
public class PPEHeadRequest {

    private Integer ppeId;
    private Integer reasonId;
    private String status;

}
