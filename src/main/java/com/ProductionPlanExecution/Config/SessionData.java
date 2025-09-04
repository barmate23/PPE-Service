package com.ProductionPlanExecution.Config;

import lombok.Data;

@Data
public class SessionData {
    private Integer userId;
    private String username;
    private String firstName;
    private String lastName;
    private Integer groupId;
    private Integer organization;
    private String permissionModule;
    private String permissionCode;
    private String designation;
    private String department;
}
