package com.ProductionPlanExecution.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    private String roleName;
    private String displayName;
    private Integer organizationId;

}