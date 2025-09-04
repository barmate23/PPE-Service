package com.ProductionPlanExecution.Request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String username;
    private Integer otp;
    private String newPassword;


}
