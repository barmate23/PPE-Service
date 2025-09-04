package com.ProductionPlanExecution.Response;

import com.ProductionPlanExecution.Model.LicenseLine;
import com.ProductionPlanExecution.Model.ModuleUserLicenceKey;
import lombok.Data;

import java.util.List;
@Data
public class UserLicenceKeysRes {
    private LicenseLine licenseLine;
    private List<ModuleUserLicenceKey> userLicenceKeys;
}
