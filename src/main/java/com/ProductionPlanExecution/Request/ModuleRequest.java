package com.ProductionPlanExecution.Request;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class ModuleRequest {
    private Integer organizationId;
    private Integer licenceHeadId;
    private Integer purchaseOrder;
    private Date date;
    private String note;
    private Integer docId;
    private List<LicenceModuleReq> licenceModuleReqList;
}
