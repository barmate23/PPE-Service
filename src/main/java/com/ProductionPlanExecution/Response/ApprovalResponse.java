package com.ProductionPlanExecution.Response;

import com.ProductionPlanExecution.Model.LicenseHead;
import lombok.Data;

import java.util.List;

@Data
public class ApprovalResponse {
    List<LicenseHead> licenseHeadList;
    private Integer pageCount;
    private Long recordCount;
}
