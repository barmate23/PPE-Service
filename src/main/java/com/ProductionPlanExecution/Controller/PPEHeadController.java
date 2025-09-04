package com.ProductionPlanExecution.Controller;

import com.ProductionPlanExecution.Model.*;
import com.ProductionPlanExecution.Repository.UserRepository;
import com.ProductionPlanExecution.Request.PPEHeadRequest;
import com.ProductionPlanExecution.Request.UpdatePpeRequest;
import com.ProductionPlanExecution.Response.BaseResponse;
import com.ProductionPlanExecution.Response.PPEHeadResponse;
import com.ProductionPlanExecution.Response.PPEResponse;
import com.ProductionPlanExecution.Service.PPEHeadService;
import com.ProductionPlanExecution.Utils.ConstantsForAPIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(ConstantsForAPIs.PREFIX)
public class PPEHeadController {

    @Autowired
    private PPEHeadService ppeHeadService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(ConstantsForAPIs.GET_ALL_PPE_ROLE)
    public  BaseResponse<PPEHead> getALlPpeRolePlans(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size){
        return ppeHeadService.getRolePlans(page,size);
    }

    @PutMapping(ConstantsForAPIs.ASSIGN_PPE_PLAN)
    public BaseResponse<PPEHead> assignOfficer(@RequestBody UpdatePpeRequest updatePpeRequest){
        return ppeHeadService.assignPlans(updatePpeRequest);
    }

    @PutMapping(ConstantsForAPIs.UPDATE_STATUS)
    public BaseResponse<PPEHead>updateStatus(@RequestBody PPEHeadRequest ppeHeadRequest){
       return ppeHeadService.updatePpeStatus(ppeHeadRequest);
    }

    @GetMapping(ConstantsForAPIs.FILTER_DATA)
    public BaseResponse<PpeFilter> getAllFilterData(){
        BaseResponse<PpeFilter> allPpeFilerData = ppeHeadService.getAllPpeFilerData();
        return allPpeFilerData;
    }

    @GetMapping(ConstantsForAPIs.FILTER_BY_PLAN)
    public BaseResponse<PPEHead> getPPEPlans(@PathVariable String planId){
        BaseResponse<PPEHead> ppePlans = ppeHeadService.getPpePlans(planId);
        return ppePlans;
    }

    @GetMapping(ConstantsForAPIs.ITEM_LIST)
    public BaseResponse<Item> getAllItems(){
       BaseResponse<Item> items= ppeHeadService.getShortageItemsList1();
        return items;
    }

    @GetMapping(ConstantsForAPIs.COMMON_FILTER)
    public BaseResponse<PPEHeadResponse> commonFilter(@RequestParam(required = false) String ppeFilterName, @RequestParam(required = false)  String planId,
                                                      @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                      @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd")  Date endDate,
                                                      @RequestParam(required = false) Integer itemId,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size)
    {
        return ppeHeadService.getCommonFilter(ppeFilterName, planId, startDate, endDate,itemId,page,size);

    }

    @GetMapping(ConstantsForAPIs.PPE_DETAILS)
    public BaseResponse<PPEResponse> getPPEDetails(@RequestParam(name = "PPEId") Integer PPEId , @RequestParam(name = "isAlternateItem") Boolean isAlternateItem){
        BaseResponse<PPEResponse> baseResponse = ppeHeadService.getPPEDetails(PPEId,isAlternateItem);
        return baseResponse;
    }

    @GetMapping(ConstantsForAPIs.PPE_OFFICERS)
    public BaseResponse<Users> getOfficers(){
        BaseResponse<Users> baseResponse = ppeHeadService.getAllOfficers();
        return baseResponse;
    }

    @GetMapping(ConstantsForAPIs.ALL_REASON)
    public BaseResponse<Reason> getAllSuppliers(){
        return ppeHeadService.getAllReasons();
    }

    @DeleteMapping(ConstantsForAPIs.DELETE_CANCEL_PLAN)
    public BaseResponse<PPEHead> deleteCancelPlanById(@PathVariable Integer ppeId){
        return ppeHeadService.deletePlans(ppeId);
    }

    @PutMapping(ConstantsForAPIs.UNASSIGN_PPE_PLAN)
    public BaseResponse<PPEHead> unassignOfficer(@PathVariable Integer planId){
        return ppeHeadService.unassignPlans(planId);
    }

    @GetMapping(ConstantsForAPIs.COMMON_FILTER_MANAGER)
    public BaseResponse<PPEHead> commonFilterMangerView(@RequestParam(required = false) String ppeFilterName,@RequestParam(required = false)  Integer officerId,
                                              @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                              @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd")  Date endDate,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size)
    {

        return ppeHeadService.getCommonFilterManager(ppeFilterName,officerId, startDate, endDate,page,size);

    }

}
