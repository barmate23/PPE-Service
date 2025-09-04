package com.ProductionPlanExecution.Response;

import lombok.Data;

import java.util.List;

@Data
public class ModulePartNumberResponse {
    private Integer moduleId;
    private String moduleName;
    private List<PartNumberResponse> partNumber;
}
