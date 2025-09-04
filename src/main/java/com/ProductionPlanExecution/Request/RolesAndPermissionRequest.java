package com.ProductionPlanExecution.Request;

import lombok.Data;

import java.util.List;

@Data
public class RolesAndPermissionRequest {
    Integer roleId;
    List<RolePermission> permissions;
}
