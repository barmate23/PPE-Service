package com.ProductionPlanExecution.Request;

import lombok.Data;

@Data
public class LicenceModuleReq {
    private Integer licenceLineId;
    private Integer partNumberId;
    private Integer userLimit;
}
