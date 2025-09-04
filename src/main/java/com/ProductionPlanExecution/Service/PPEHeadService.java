package com.ProductionPlanExecution.Service;

import com.ProductionPlanExecution.Model.*;
import com.ProductionPlanExecution.Request.PPEHeadRequest;
import com.ProductionPlanExecution.Request.UpdatePpeRequest;
import com.ProductionPlanExecution.Response.BaseResponse;
import com.ProductionPlanExecution.Response.PPEHeadResponse;
import com.ProductionPlanExecution.Response.PPEResponse;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface PPEHeadService {

     List<PPEHead> getPpeFilter(String ppeFilterName);

     BaseResponse<PPEHead> getRolePlans( int page, int size);

     BaseResponse<PPEHead> assignPlans(UpdatePpeRequest updatePpeRequest);

     BaseResponse<PPEHead> updatePpeStatus(PPEHeadRequest ppeHeadRequest);

     BaseResponse<PpeFilter> getAllPpeFilerData();

     BaseResponse<PPEHead> getPpePlans(String planId);

     List<PPEHead> filterDataByDateRange(Date startDate, Date endDate);

     BaseResponse<Item> getShortageItemsList1();

     BaseResponse<PPEResponse> getPPEDetails(Integer PPEId, Boolean isAlternateItem);

     BaseResponse<PPEHeadResponse> getCommonFilter(String ppeFilterName, String planId, Date startDate, Date endDate, Integer itemId, int page, int size);

     BaseResponse<Users> getAllOfficers();

     BaseResponse<Reason> getAllReasons();

     BaseResponse<PPEHead> deletePlans(Integer planId);

     BaseResponse<PPEHead> unassignPlans(Integer planId);

     BaseResponse<PPEHead> getCommonFilterManager(String ppeFilterName,Integer officerId,Date startDate, Date endDate, int page, int size);
}
