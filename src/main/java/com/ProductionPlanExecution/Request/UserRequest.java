package com.ProductionPlanExecution.Request;

import lombok.Data;

@Data
public class UserRequest {
    private Integer groupId;
    private Integer organizationId;
    private Integer subModuleId;
    private String userName;
    private String employeeID;
    private String firstName;
    private String lastName;
    private String emailID;
    private String countryCode;
    private String mobileNo;
    private Boolean isEmailVerified;
}
