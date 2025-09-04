package com.ProductionPlanExecution.Response;

import com.ProductionPlanExecution.Model.PPEHead;
import lombok.Data;

import java.util.List;

@Data
public class PPEHeadResponse {

    private List<PPEHead> ppeHeadData;

    private Integer totalAvailable;

    private Integer totalShortage;
}
