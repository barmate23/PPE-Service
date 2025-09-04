package com.ProductionPlanExecution.Response;

import com.ProductionPlanExecution.Model.Organization;
import com.ProductionPlanExecution.Model.Users;
import lombok.Data;

@Data
public class OrgResponse {
    private Organization organization;
    private Users orgUser;
}
