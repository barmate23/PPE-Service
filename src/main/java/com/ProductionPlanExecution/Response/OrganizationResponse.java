package com.ProductionPlanExecution.Response;

import com.ProductionPlanExecution.Model.Organization;
import lombok.Data;

import java.util.List;
@Data
public class OrganizationResponse {
    List<Organization> organizations;
    private Integer pageCount;
    private Long recordCount;
}
