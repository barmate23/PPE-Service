package com.ProductionPlanExecution.Response;

import lombok.Data;

import java.util.List;

@Data
public class ModuleResponse {
    Integer id;
    String moduleName;
    Integer sequence;
    List<SubModuleResp> subModuleRespList;
}
