package com.ProductionPlanExecution.Request;

import lombok.Data;

import java.util.List;
@Data
public class SaveLicenceApprover {
    Integer licenceHeadId;
    Integer status;
    List<SaveLicenceLineReq> licenceLineReqList;
}