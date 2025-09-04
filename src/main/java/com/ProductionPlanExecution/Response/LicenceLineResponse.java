package com.ProductionPlanExecution.Response;

import lombok.Data;

import java.util.List;

@Data
public class LicenceLineResponse {
    private String moduleName;
    private Integer moduleId;
    private List<SubModuleResponse> subModule;
}
