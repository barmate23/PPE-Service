package com.ProductionPlanExecution.Model;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Data
@Component
@RequestScope
public class LoginUser {
    private Integer userId;
    private String userName;
    private String logId;
    private Integer subOrgId;
    private String subOrgName;
    private Integer orgId;
    private String orgName;
    private String subModuleCode;

}
