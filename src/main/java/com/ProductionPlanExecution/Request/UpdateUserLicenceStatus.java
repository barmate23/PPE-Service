package com.ProductionPlanExecution.Request;

import lombok.Data;

@Data
public class UpdateUserLicenceStatus {
    Integer userLicId;
    Boolean status;
}
