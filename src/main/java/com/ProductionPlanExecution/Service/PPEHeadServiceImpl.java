package com.ProductionPlanExecution.Service;


import com.ProductionPlanExecution.Model.*;
import com.ProductionPlanExecution.Repository.*;
import com.ProductionPlanExecution.Request.PPEHeadRequest;
import com.ProductionPlanExecution.Request.UpdatePpeRequest;
import com.ProductionPlanExecution.Response.BaseResponse;
import com.ProductionPlanExecution.Response.ItemsResponse;
import com.ProductionPlanExecution.Response.PPEHeadResponse;
import com.ProductionPlanExecution.Response.PPEResponse;
import com.ProductionPlanExecution.Utils.Const;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PPEHeadServiceImpl implements PPEHeadService{

    @Autowired
    PPEHeadRepository ppeHeadRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    LoginUser loginUser;

    @Autowired
    private PPEAllFilterRepository ppeAllFilterRepository;

    @Autowired
    private PPEStockBalanceRepository ppeStockBalanceRepository;

    @Autowired
    private PPELineRepository ppeLineRepository;

    @Autowired
    private PpeStatusRepository ppeStatusRepository;

    @Autowired
    private StockBalanceRepository stockBalanceRepository;

    @Autowired
    private AlternateItemMapperRepository alternateItemMapperRepository;

    @Autowired
    private PPEBomLineRepository ppeBomLineRepository;

    @Autowired
    private PpeSubmoduleRepository ppeSubmoduleRepository;

    @Autowired
    private ReasonRepository reasonRepository;

    @Autowired
    private ASNLineRepository asnLineRepository;

    public BaseResponse<PPEHead> getRolePlans( int page, int size){
        long startTime = System.currentTimeMillis();
        BaseResponse<PPEHead> baseResponse=new BaseResponse<>();
        final Pageable pageable = (Pageable) PageRequest.of((int) page, (int) size);
        Page<PPEHead> pageResult;

        List<PPEHead> ppeHeads;
        log.info("LogId:{} - ProductionPlanService - getRolePlans - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," GET ROLE PLANS Started ");

        try{

            if(loginUser.getSubModuleCode().equalsIgnoreCase("PPEM")){
                log.info("LogId:{} - ProductionPlanService - getRolePlans - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(), "SubOrganisation Id :: " + loginUser.getSubOrgId());
                pageResult= ppeHeadRepository.findByIsDeletedAndSubOrganizationId(false, loginUser.getSubOrgId(),pageable);
            }else if(loginUser.getSubModuleCode().equalsIgnoreCase("PPEO")){
                log.info("LogId:{} - ProductionPlanService - getRolePlans - UserId:{} - {}",loginUser.getLogId(), "PPEOfficer:: ",loginUser.getUserId()+ "SubOrganisation Id :: " + loginUser.getSubOrgId());
                pageResult= ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeOfficerId(false, loginUser.getSubOrgId(),loginUser.getUserId(),pageable);
            }else {
                baseResponse.setCode(0);
                baseResponse.setStatus(500);
                baseResponse.setData(new ArrayList<>());
                baseResponse.setLogId(loginUser.getLogId());
                baseResponse.setMessage(Const.USERNOTBELONGS);
                return baseResponse;
            }

            baseResponse.setData(pageResult.getContent());
            baseResponse.setTotalPageCount(pageResult.getTotalPages());
            baseResponse.setTotalRecordCount(pageResult.getTotalElements());
            baseResponse.setCode(1);
            baseResponse.setStatus(200);
            baseResponse.setLogId(loginUser.getLogId());
            baseResponse.setMessage(" All shortage items fetch successfully ");
        }catch (Exception e){
            long endTime = System.currentTimeMillis();
            log.error("LogId:{} - ProductionPlanService - getRolePlans - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(),  " ERROR OCCURS AT WHILE FETCHING Plans  EXECUTED TIME " + (endTime - startTime), e);
        }
        long endTime = System.currentTimeMillis();
        log.error("LogId:{} - ProductionPlanService - getRolePlans - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(),  " GET ROLE PLANS METHOD EXECUTED END EXECUTED TIME " + (endTime - startTime));
        return baseResponse;
    }

    @Override
    public BaseResponse<PPEHead> getPpePlans(String planId) {
        long startTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - getPpePlans - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," GET PPE PLANS METHOD START");
        BaseResponse<PPEHead> baseResponse=new BaseResponse<>();
        List<PPEHead> plan= null;
        try {
            if(planId!=null) {
                log.info("LogId:{} - ProductionPlanService - getPpePlans - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(), "SubOrganisation Id :: " + loginUser.getSubOrgId()+" Plan Id ::"+planId);
                plan = ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPlanOrderId(false, loginUser.getSubOrgId(), planId);
            }
        }catch (Exception e){
            long endTime = System.currentTimeMillis();
            log.error("LogId:{} - ProductionPlanService - getPpePlans - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(), " ERROR OCCURS AT WHILE FETCHING PLANS  EXECUTED TIME " + (endTime - startTime), e);
        }
        baseResponse.setCode(1);
        baseResponse.setStatus(200);
        baseResponse.setData(plan);
        baseResponse.setLogId(loginUser.getLogId());
        baseResponse.setMessage(Const.PLANLISTFETCHSUCCESSFULLY);
        long endTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - getPpePlans - UserId:{} - {}",loginUser.getLogId() , " GET PPE PLANS METHOD EXECUTED END  EXECUTED TIME " , (endTime - startTime));
        return baseResponse;
    }

    @Override
    public List<PPEHead> filterDataByDateRange(Date startDate, Date endDate) {
        long startTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - filterDataByDateRange - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," FILTER DATA BY DATE RANGE METHOD START");
        List<PPEHead>ppeHeads=ppeHeadRepository.findByIsDeleted(false);

        List<PPEHead>ppeHeads1=ppeHeads.stream().filter(ppeHead -> (ppeHead.getStartDate().equals(startDate) || ppeHead.getStartDate().after(startDate) && (ppeHead.getStartDate().before(endDate) || ppeHead.getStartDate().equals(endDate)))).collect(Collectors.toList());
        long endTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - filterDataByDateRange - UserId:{} - {}",loginUser.getLogId() , " FILTER DATA BY DATE RANGE METHOD EXECUTED END  EXECUTED TIME " , (endTime - startTime));
        return ppeHeads1;
    }

    @Override
    public BaseResponse<Item> getShortageItemsList1() {
        long startTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - getShortageItemsList1 - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," GET SHORTAGE ITEM METHOD START");
        BaseResponse<Item> baseResponse=new BaseResponse<>();
        List<PPELine> shortageItems= new ArrayList<>();
        List<PPELine> noShortageItem= new ArrayList<>();
        List<Item> items=null;

        try {
            log.info("LogId:{} - ProductionPlanService - getShortageItemsList1 - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(), "SubOrganisation Id :: " + loginUser.getSubOrgId());
            List<StockBalance> stckbData = ppeStockBalanceRepository.findByIsDeletedAndSubOrganizationId(false, loginUser.getSubOrgId());
            List<PPELine> lineData = ppeLineRepository.findByIsDeletedAndSubOrganizationId(false, loginUser.getSubOrgId());

            for (PPELine ppeLine : lineData) {
                Integer requiredQty = ppeLine.getRequiredQuantity();
                List<StockBalance> filteredStockBalance = stckbData.stream()
                        .filter(stockBalance -> stockBalance.getItemId().equals(ppeLine.getItem()))
                        .collect(Collectors.toList());
                int totalBalanceQty = filteredStockBalance.stream()
                        .mapToInt(StockBalance::getBalanceQuantity)
                        .sum();
                if (totalBalanceQty<requiredQty) {
                    shortageItems.add(ppeLine);
                } else {
                    noShortageItem.add(ppeLine);
                }

            }
            items = shortageItems.stream()
                    .map(PPELine::getItem)
                    .collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());

            baseResponse.setCode(1);
            baseResponse.setStatus(200);
            baseResponse.setData(items);
            baseResponse.setLogId(loginUser.getLogId());
            baseResponse.setMessage(Const.PLANLISTFETCHSUCCESSFULLY);

        }catch (Exception e){
            long endTime = System.currentTimeMillis();
            log.error("LogId:{} - ProductionPlanService - getShortageItemsList1 - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," ERROR OCCURS AT WHILE FETCHING SHORTAGE ITEMS  EXECUTED TIME "+ (endTime - startTime), e);
        }
        long endTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - getShortageItemsList1 - UserId:{} - {}",loginUser.getLogId() , " GET SHORTAGE ITEM METHOD EXECUTED END  EXECUTED TIME " , (endTime - startTime));
        return baseResponse;
    }

    @Override
    public BaseResponse<PPEHead> assignPlans(UpdatePpeRequest updatePpeRequest) {
        BaseResponse<PPEHead> baseResponse= new BaseResponse<>();
        long  startTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - assignPlans - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," ASSIGN PLANS METHOD START");

        List<PPEHead>  ppeHead=new ArrayList<>();

        try {
            log.info("LogId:{} - ProductionPlanService - assignPlans - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(),"SubOrganisation Id :: " + loginUser.getSubOrgId() + " Plan Id ::" + updatePpeRequest.getPlanId()+"Officer Id ::"+updatePpeRequest.getOfficerId());
            PPEHead plan=ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndId(false,loginUser.getSubOrgId(),updatePpeRequest.getPlanId());
            Optional<Users> users =userRepository.findById(updatePpeRequest.getOfficerId());
            if(users.get().getUserId() != null && loginUser.getSubModuleCode().equals("PPEM") && !String.valueOf(users.get().getUserId()).isEmpty() ){

                plan.setPpeOfficer(users.get());
                plan.setAssignedDate(new java.sql.Date(System.currentTimeMillis()));

                try{
                    String startTimeStr = updatePpeRequest.getStartTime();
                    if (startTimeStr != null && !startTimeStr.isEmpty()) {
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                        Date parsedStartTime = timeFormat.parse(startTimeStr);
                        plan.setStartTime(new Time(parsedStartTime.getTime()));
                    }

                    String endDateStr = updatePpeRequest.getEndDate();
                    if (endDateStr != null && !endDateStr.isEmpty()) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date parsedEndDate = dateFormat.parse(endDateStr);
                        plan.setEndDate(new java.sql.Date(parsedEndDate.getTime()));
                    }

                    String endTimeStr = updatePpeRequest.getEndTime();
                    if (endTimeStr != null && !endTimeStr.isEmpty()) {
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                        Date parsedEndTime = timeFormat.parse(endTimeStr);
                        plan.setEndTime(new Time(parsedEndTime.getTime()));
                    }

                    String requiredByStr = updatePpeRequest.getRequiredBy();
                    if (requiredByStr != null && !requiredByStr.isEmpty()) {
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                        Date parsedRequiredBy = timeFormat.parse(requiredByStr);
                        plan.setRequiredBy(new Time(parsedRequiredBy.getTime()));
                    }

                    ppeHead.add(plan);

                }catch(Exception e){
                    e.printStackTrace();
                }

                ppeHeadRepository.save(plan);

                baseResponse.setCode(1);
                baseResponse.setData(ppeHead);
                baseResponse.setStatus(200);
                baseResponse.setLogId(loginUser.getLogId());
                baseResponse.setMessage(Const.OFFICERASSIGNSUCCESSFULLY);
            }else{
                baseResponse.setCode(0);
                baseResponse.setStatus(500);
                baseResponse.setMessage(" Only managers are authorized to assign plans to officers. ");
            }
        }catch (Exception e){
            long endTime = System.currentTimeMillis();
            log.error("LogId:{} - ProductionPlanService - assignPlans - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," ERROR OCCURS AT WHILE Saving PPEHead  EXECUTED TIME " + (endTime - startTime), e);
        }
        long endTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - assignPlans - UserId:{} - {}",loginUser.getLogId() , " ASSIGN PLANS METHOD EXECUTED END EXECUTED TIME " , (endTime - startTime));

        return baseResponse;
    }

    @Override
    public BaseResponse<PPEHead> updatePpeStatus(PPEHeadRequest ppeHeadRequest) {
        BaseResponse<PPEHead> baseResponse=new BaseResponse<>();
        long startTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - updatePpeStatus - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," UPDATE PPE STATUS METHOD START");
        PPEHead ppeHeadDbData=null;

        try {
            log.info("LogId:{} - ProductionPlanService - updatePpeStatus - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(),"SubOrganisation Id :: " + loginUser.getSubOrgId());
            ppeHeadDbData = ppeHeadRepository.findByIdAndIsDeletedAndSubOrganizationId(ppeHeadRequest.getPpeId(),false,loginUser.getSubOrgId());
            PpeStatus ppeStatus = ppeStatusRepository.findByStatusName(ppeHeadRequest.getStatus());
            ppeHeadDbData.setPpeStatus(ppeStatus);
            if(ppeStatus.getStatusName().equals(Const.HOLD) || ppeStatus.getStatusName().equals(Const.CANCEL)){
                ppeHeadDbData.setReason(reasonRepository.findByIsDeletedAndId(false, ppeHeadRequest.getReasonId()));
            }
            ppeHeadDbData.setModifiedBy(loginUser.getUserId());
            ppeHeadDbData.setModifiedOn(new Date());

            List<PPELine> ppeLineList = ppeLineRepository.findByIsDeletedAndSubOrganizationIdAndPPEHeadId(false, loginUser.getSubOrgId(),ppeHeadDbData.getId());


            if(ppeHeadDbData.getPpeStatus().getStatusName().equals(Const.CONFIRM)){

                for (PPELine ppeline: ppeLineList){
                    log.info("LogId:{} - ProductionPlanService - updatePpeStatus - UserId:{} - {}"+loginUser.getLogId()+loginUser.getUserId(),"SubOrganisation Id :: " , loginUser.getSubOrgId() , " Ppe Line Id ::" , ppeline.getItem().getId());
                    StockBalance stckBalance = ppeStockBalanceRepository.findByIsDeletedAndSubOrganizationIdAndItemIdId(false, loginUser.getSubOrgId(), ppeline.getItem().getId());

//                  BOMLine bomline = ppeBomLineRepository.findByIsDeletedAndSubOrganizationIdAndItemIdAndBomHeadIdId(false, loginUser.getSubOrgId(), ppeline.getItem().getId(),ppeHeadDbData.getBomHead().getId());
//                    BOMLine bomline = ppeBomLineRepository.findByIsDeletedAndSubOrganizationIdAndItemIdAndBomHeadIdId(false, loginUser.getSubOrgId(), ppeline.getItem().getId(),ppeline.getBomLine().getId());

                    if(ppeline.getBomLine().getDependency().equals(Const.CRITICAL) && stckBalance.getBalanceQuantity()<ppeline.getRequiredQuantity()){
                        baseResponse.setCode(0);
                        baseResponse.setMessage(Const.CANNOTPOSTPLANMESSAGE);
                        baseResponse.setData(null);
                        baseResponse.setLogId(loginUser.getLogId());
                        baseResponse.setStatus(500);
                        return baseResponse;
                    }else {
                        if (stckBalance.getBalanceQuantity() >= ppeline.getRequiredQuantity()) {
                            Integer newBalancedQty = stckBalance.getBalanceQuantity() - ppeline.getRequiredQuantity();
                            stckBalance.setBalanceQuantity(newBalancedQty);
                            ppeline.setAllocatedQty(ppeline.getRequiredQuantity());
                        } else {

                            ppeline.setAllocatedQty(stckBalance.getBalanceQuantity());
                            stckBalance.setBalanceQuantity(0);

                        }
                    }
                    ppeStockBalanceRepository.save(stckBalance);
                    ppeLineRepository.save(ppeline);
                }

            }else if(ppeHeadDbData.getPpeStatus().getStatusName().equals(Const.CANCEL)){

                for(PPELine ppeline: ppeLineList){
                    log.info("LogId:{} - ProductionPlanService - updatePpeStatus - UserId:{} - {}"+loginUser.getLogId()+loginUser.getUserId(),"SubOrganisation Id :: " , loginUser.getSubOrgId() , " Ppe Line Id ::" , ppeline.getItem().getId());
                    StockBalance stckBalance = ppeStockBalanceRepository.findByIsDeletedAndSubOrganizationIdAndItemIdId(false, loginUser.getSubOrgId(), ppeline.getItem().getId());
                    stckBalance.setBalanceQuantity(ppeline.getAllocatedQty()+stckBalance.getBalanceQuantity());
                    ppeline.setAllocatedQty(0);

                    ppeStockBalanceRepository.save(stckBalance);
                    ppeLineRepository.save(ppeline);

                }

            }
            ppeHeadRepository.save(ppeHeadDbData);

            baseResponse.setStatus(200);
            baseResponse.setCode(1);
            baseResponse.setLogId(loginUser.getLogId());
            baseResponse.setMessage(Const.PLANSTATUSUPDATEDSUCCESSFULLY);
            baseResponse.setData(Arrays.asList(ppeHeadDbData));

        }catch (Exception e){
            long endTime = System.currentTimeMillis();
            log.error("LogId:{} - ProductionPlanService - updatePpeStatus - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," ERROR OCCURS AT WHILE UPDATING PPE STATUS MODULE  EXECUTED TIME " + (endTime - startTime), e);
        }

        long endTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - updatePpeStatus - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," UPDATE PPE STATUS METHOD EXECUTED END  EXECUTED TIME " + (endTime - startTime));
        return  baseResponse;
    }

    @Override
    public List<PPEHead> getPpeFilter(String ppeFilterName) {
        long startTime = System.currentTimeMillis();
        log.info(loginUser.getLogId() + " GET PPE FILTER METHOD");
        log.info("LogId:{} - ProductionPlanService - getPpeFilter - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," GET PPE FILTER METHOD START");
        List<PPEHead> filterData= new ArrayList<>();

        try {
            List<PPELine> shoratgeItem= new ArrayList<>();
            List<PPELine> noShortageItem= new ArrayList<>();
            List<PPEHead> ppeHeadShortageList= new ArrayList<>();
            List<PPEHead> ppeHeadNoShortageList= new ArrayList<>();
            List<StockBalance> stckbData = ppeStockBalanceRepository.findByIsDeletedAndSubOrganizationId(false, loginUser.getSubOrgId());
//           List<PPELine> lineData = ppeLineRepository.findAll();
            List<PPELine> lineData = ppeLineRepository.findByIsDeletedAndSubOrganizationId(false, loginUser.getSubOrgId());

            List<PPEHead> headData = null;
            if(loginUser.getSubModuleCode().equalsIgnoreCase("PPEM")){
                log.info("LogId:{} - ProductionPlanService - getPpeFilter - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(),"SubOrganisation Id :: " + loginUser.getSubOrgId());
                headData = ppeHeadRepository.findByIsDeletedAndSubOrganizationId(false, loginUser.getSubOrgId());
            }else if(loginUser.getSubModuleCode().equalsIgnoreCase("PPEO")){
                log.info("LogId:{} - ProductionPlanService - getPpeFilter - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(),"SubOrganisation Id :: " + loginUser.getSubOrgId());
                headData = ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeOfficerId(false, loginUser.getSubOrgId(),loginUser.getUserId());
            }
            for(PPEHead ppeHead:headData) {
                boolean hasShortage=false;
                boolean ppelineprocessed=false;

                for (PPELine ppeLine : lineData) {
                    if (ppeLine.getPPEHead().equals(ppeHead)){
                        ppelineprocessed=true;
                        Integer requiredQty = ppeLine.getRequiredQuantity();
                        List<StockBalance> filteredStockBalance = stckbData.stream()
                                .filter(stockBalance -> stockBalance.getItemId().equals(ppeLine.getItem()))
                                .collect(Collectors.toList());
                        int totalBalanceQty = filteredStockBalance.stream()
                                .mapToInt(StockBalance::getBalanceQuantity)
                                .sum();
                        if (totalBalanceQty<requiredQty) {
                            shoratgeItem.add(ppeLine);
                            hasShortage = true;

                        } else {
                            noShortageItem.add(ppeLine);
                        }
                    }
                }
                if(ppelineprocessed) {
                    if (hasShortage) {
                        ppeHeadShortageList.add(ppeHead);
                    } else {
                        ppeHeadNoShortageList.add(ppeHead);
                    }
                }
            }
            if(ppeFilterName!=null && ppeFilterName.equals(Const.PRODUCTIONPALNORORDER)){
                filterData = getAllPlans().getData();
            }else if(ppeFilterName!=null && ppeFilterName.equals(Const.NOSHORTAGEPLANORORDER)){
                filterData.addAll(ppeHeadNoShortageList);
            }else if(ppeFilterName!=null && ppeFilterName.equals(Const.SHORTAGEALLPLANSORORDER)) {
                filterData.addAll(ppeHeadShortageList);
            }else if(ppeFilterName!=null && ppeFilterName.equals(Const.CONFIRM_PLANS)){
                List<PPEHead> confirmPlans = ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeStatusStatusName(false, loginUser.getSubOrgId(), Const.CONFIRM);
                filterData.addAll(confirmPlans);
            }else if(ppeFilterName!=null && ppeFilterName.equals(Const.HOLD_PLANS)){
                List<PPEHead> holdPlans=ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeStatusStatusName(false, loginUser.getSubOrgId(),Const.HOLD);
                filterData.addAll(holdPlans);
            }else if(ppeFilterName!=null && ppeFilterName.equals(Const.CANCEL_PLANS)){
                List<PPEHead> cancelPlans=ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeStatusStatusName(false, loginUser.getSubOrgId(),Const.CANCEL);
                filterData.addAll(cancelPlans);
            }else if(ppeFilterName!=null && ppeFilterName.equals(Const.POSTED_PLANS)){
                List<PPEHead> postedPlans=ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeStatusStatusName(false, loginUser.getSubOrgId(),Const.POST);
                filterData.addAll(postedPlans);
            }
            log.info("LogId:{} - ProductionPlanService - getPpeFilter - UserId:{} - {}",loginUser.getLogId(), " GET -> GET PPE Module SubOrganisation Id :: " , loginUser.getSubOrgId()+ "OfficerId:: "+ loginUser.getUserId());
        }catch (Exception e){
            e.printStackTrace();
            long endTime = System.currentTimeMillis();
            log.error("LogId:{} - ProductionPlanService - getPpeFilter - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," ERROR OCCURS AT WHILE FETCHING PPE MODULE   EXECUTED TIME " + (endTime - startTime), e);
        }
        long endTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - getPpeFilter - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," GET PPE FILTER METHOD EXECUTED END EXECUTED TIME " + (endTime - startTime));
        return filterData;
    }

    @Override
    public BaseResponse<PpeFilter> getAllPpeFilerData() {
        long startTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - getAllPpeFilerData - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," GET ALL PPE FILTER DATA METHOD START");
        BaseResponse<PpeFilter> baseResponse= new BaseResponse<>();
        List<PpeFilter> allFieldData=null;
        try {
            log.info("LogId:{} - ProductionPlanService - getAllPpeFilerData - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(), " SubOrganisation Id :: " + loginUser.getSubOrgId());
            allFieldData = ppeAllFilterRepository.findByIsDeletedOrderBySequenceAsc(false);
            baseResponse.setCode(1);
            baseResponse.setStatus(200);
            baseResponse.setLogId(loginUser.getLogId());
            baseResponse.setData(allFieldData);
            baseResponse.setMessage(Const.GETALLFILTERSUCCESSFULLY);
        }catch(Exception  e){
            long endTime = System.currentTimeMillis();
            log.error("LogId:{} - ProductionPlanService - getAllPpeFilerData - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," ERROR OCCURS AT WHILE FETCHING FILTER DROPDAWN DATA   EXECUTED TIME " + (endTime - startTime), e);
        }
        long endTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - getAllPpeFilerData - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(), " GET ALL PPE FILTER DATA METHOD EXECUTED END EXECUTED TIME " + (endTime - startTime));
        return baseResponse;
    }

    @Override
    public BaseResponse<PPEHeadResponse> getCommonFilter(String ppeFilterName, String planId, Date startDate, Date endDate,Integer itemId, int page, int size) {
        long startTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - getCommonFilter - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," GET COMMON FILTER METHOD START");
        BaseResponse<PPEHeadResponse> baseResponse=new BaseResponse();
        PageImpl<PPEHead> ppeHeads=null;

        PPEHeadResponse ppeHeadResponse= new PPEHeadResponse();
        List<PPEHeadResponse> dataList = new ArrayList<>();

        try {
            Pageable pageable = PageRequest.of(page, size);
            List<PPEHead> filterData = new ArrayList<>();
            int totalShortage=0;
            int totalAvailable=0;

            if(!StringUtils.isEmpty(ppeFilterName) && ppeFilterName.equals(Const.SHORTAGEBYITEM) && itemId != null && startDate != null && endDate != null && !StringUtils.isEmpty(planId)){
                List<PPEHead> ppePlans1 = new ArrayList<>();
                List<PPEHead> ppePlans2 = new ArrayList<>();

                List<PPELine> shortItems = getShortageItemsList();
                List<PPEHead> plans=new ArrayList<>();
                List<StockBalance> stckbData = ppeStockBalanceRepository.findByIsDeletedAndSubOrganizationId(false, loginUser.getSubOrgId());

                for (PPELine shortItem : shortItems) {
                    if (shortItem.getItem().getId().equals(itemId)) {
                        Integer reqQty = shortItem.getRequiredQuantity();

                        List<StockBalance> filteredStockBalance = stckbData.stream()
                                .filter(stockBalance -> stockBalance.getItemId().equals(shortItem.getItem()))
                                .collect(Collectors.toList());
                        int balQty = filteredStockBalance.stream()
                                .mapToInt(StockBalance::getBalanceQuantity)
                                .sum();

                        PPEHead ppeHead = shortItem.getPPEHead();
                        if(reqQty>balQty) {
                            Integer shortage = reqQty - balQty;
                            ppeHead.setTotalShortage(shortage);
                        }
                        ppeHead.setTotalAvailable(balQty);

                        plans.add(ppeHead);
                    }
                }

                totalShortage = plans.stream()
                        .mapToInt(PPEHead::getTotalShortage)
                        .sum();

                totalAvailable = plans.stream()
                        .mapToInt(PPEHead::getTotalAvailable)
                        .sum();

                ppePlans1.addAll(plans);
                ppePlans2.addAll(filterDataByDateRange(startDate, endDate));

                List<PPEHead> ppePlans3 = ppePlans1.stream()
                        .filter(ppePlans2::contains)
                        .collect(Collectors.toList());

                List<PPEHead> matchingPlans = ppePlans3.stream()
                        .filter(plan -> plan.getPlanOrderId().equals(planId))
                        .collect(Collectors.toList());

                filterData.addAll(matchingPlans);

            }

            else if(!StringUtils.isEmpty(ppeFilterName) && ppeFilterName.equals(Const.SHORTAGEBYITEM) && itemId != null && startDate != null && endDate != null){
                List<PPEHead> ppePlans1 = new ArrayList<>();
                List<PPEHead> ppePlans2 = new ArrayList<>();

                List<PPELine> shortItems = getShortageItemsList();
                List<PPEHead> plans=new ArrayList<>();
                List<StockBalance> stckbData = ppeStockBalanceRepository.findByIsDeletedAndSubOrganizationId(false, loginUser.getSubOrgId());

                for (PPELine shortItem : shortItems) {
                    if (shortItem.getItem().getId().equals(itemId)) {
                        Integer reqQty = shortItem.getRequiredQuantity();

                        List<StockBalance> filteredStockBalance = stckbData.stream()
                                .filter(stockBalance -> stockBalance.getItemId().equals(shortItem.getItem()))
                                .collect(Collectors.toList());
                        int balQty = filteredStockBalance.stream()
                                .mapToInt(StockBalance::getBalanceQuantity)
                                .sum();

                        PPEHead ppeHead = shortItem.getPPEHead();
                        if(reqQty>balQty) {
                            Integer shortage = reqQty - balQty;
                            ppeHead.setTotalShortage(shortage);
                        }
                        ppeHead.setTotalAvailable(balQty);

                        plans.add(ppeHead);
                    }
                }

                totalShortage = plans.stream()
                        .mapToInt(PPEHead::getTotalShortage)
                        .sum();

                totalAvailable = plans.stream()
                        .mapToInt(PPEHead::getTotalAvailable)
                        .sum();

                ppePlans1.addAll(plans);
                ppePlans2.addAll(filterDataByDateRange(startDate, endDate));

                List<PPEHead> matchingPlans = ppePlans1.stream()
                        .filter(ppePlans2::contains)
                        .collect(Collectors.toList());

                filterData.addAll(matchingPlans);

            }
            else if(!StringUtils.isEmpty(ppeFilterName) && ppeFilterName.equals(Const.SHORTAGEBYITEM) && itemId != null && !StringUtils.isEmpty(planId)){
                List<PPEHead> ppePlans = new ArrayList<>();

                List<PPELine> shortItems = getShortageItemsList();
                List<PPEHead> plans=new ArrayList<>();
                List<StockBalance> stckbData = ppeStockBalanceRepository.findByIsDeletedAndSubOrganizationId(false, loginUser.getSubOrgId());

                for (PPELine shortItem : shortItems) {
                    if (shortItem.getItem().getId().equals(itemId)) {
                        Integer reqQty = shortItem.getRequiredQuantity();

                        List<StockBalance> filteredStockBalance = stckbData.stream()
                                .filter(stockBalance -> stockBalance.getItemId().equals(shortItem.getItem()))
                                .collect(Collectors.toList());
                        int balQty = filteredStockBalance.stream()
                                .mapToInt(StockBalance::getBalanceQuantity)
                                .sum();

                        PPEHead ppeHead = shortItem.getPPEHead();
                        if(reqQty>balQty) {
                            Integer shortage = reqQty - balQty;
                            ppeHead.setTotalShortage(shortage);
                        }
                        ppeHead.setTotalAvailable(balQty);

                        plans.add(ppeHead);
                    }
                }

                totalShortage = plans.stream()
                        .mapToInt(PPEHead::getTotalShortage)
                        .sum();

                totalAvailable = plans.stream()
                        .mapToInt(PPEHead::getTotalAvailable)
                        .sum();

                ppePlans.addAll(plans);

                List<PPEHead> matchingPlans = ppePlans.stream()
                        .filter(plan -> plan.getPlanOrderId().equals(planId))
                        .collect(Collectors.toList());

                filterData.addAll(matchingPlans);

            }

            else if(!StringUtils.isEmpty(ppeFilterName) && ppeFilterName.equals(Const.SHORTAGEBYITEM) && itemId != null){

                List<PPELine> shortItems = getShortageItemsList();
                List<PPEHead> plans=new ArrayList<>();
                List<StockBalance> stckbData = ppeStockBalanceRepository.findByIsDeletedAndSubOrganizationId(false, loginUser.getSubOrgId());

                for (PPELine shortItem : shortItems) {
                    if (shortItem.getItem().getId().equals(itemId)) {
                        Integer reqQty = shortItem.getRequiredQuantity();

                        List<StockBalance> filteredStockBalance = stckbData.stream()
                                .filter(stockBalance -> stockBalance.getItemId().equals(shortItem.getItem()))
                                .collect(Collectors.toList());
                        int balQty = filteredStockBalance.stream()
                                .mapToInt(StockBalance::getBalanceQuantity)
                                .sum();

                        PPEHead ppeHead = shortItem.getPPEHead();
                        if(reqQty>balQty) {
                            Integer shortage = reqQty - balQty;
                            ppeHead.setTotalShortage(shortage);
                        }
                        ppeHead.setTotalAvailable(balQty);

                        plans.add(ppeHead);
                    }
                }

                totalShortage = plans.stream()
                        .mapToInt(PPEHead::getTotalShortage)
                        .sum();

                totalAvailable = plans.stream()
                        .mapToInt(PPEHead::getTotalAvailable)
                        .sum();

                filterData.addAll(plans);

            }else if (!StringUtils.isEmpty(ppeFilterName) && !StringUtils.isEmpty(planId) && startDate != null && endDate != null) {
                List<PPEHead> filteredPlans = getPpeFilter(ppeFilterName);
                List<PPEHead> filteredByDateRange = filterDataByDateRange(startDate, endDate);
                filteredPlans.retainAll(filteredByDateRange);
                filteredPlans.sort(Comparator.comparing(PPEHead::getStartDate)
                        .thenComparing(PPEHead::getEndDate));

                List<PPEHead> matchingPlans = filteredPlans.stream()
                        .filter(plan -> plan.getPlanOrderId().equals(planId))
                        .collect(Collectors.toList());

                filterData.addAll(matchingPlans);

            } else if (!StringUtils.isEmpty(ppeFilterName) && !StringUtils.isEmpty(planId)) {
                List<PPEHead> filteredPlans = getPpeFilter(ppeFilterName);
                List<PPEHead> matchingPlans = filteredPlans.stream()
                        .filter(plan -> plan.getPlanOrderId().equals(planId))
                        .collect(Collectors.toList());

                filterData.addAll(matchingPlans);

            } else if (!StringUtils.isEmpty(ppeFilterName) && startDate != null && endDate != null) {
                List<PPEHead> filteredPlans = getPpeFilter(ppeFilterName);
                List<PPEHead> filteredByDateRange = filterDataByDateRange(startDate, endDate);
                filteredPlans.retainAll(filteredByDateRange);
                filteredPlans.sort(Comparator.comparing(PPEHead::getStartDate)
                        .thenComparing(PPEHead::getEndDate));
                filterData.addAll(filteredPlans);

            }else if(!StringUtils.isEmpty(ppeFilterName) && itemId != null){

                List<PPELine> shortItems = getShortageItemsList();
                List<PPEHead> plans=new ArrayList<>();
                List<StockBalance> stckbData = ppeStockBalanceRepository.findByIsDeletedAndSubOrganizationId(false, loginUser.getSubOrgId());

                for (PPELine shortItem : shortItems) {
                    if (shortItem.getItem().getId().equals(itemId)) {
                        Integer reqQty = shortItem.getRequiredQuantity();

                        List<StockBalance> filteredStockBalance = stckbData.stream()
                                .filter(stockBalance -> stockBalance.getItemId().equals(shortItem.getItem()))
                                .collect(Collectors.toList());
                        int balQty = filteredStockBalance.stream()
                                .mapToInt(StockBalance::getBalanceQuantity)
                                .sum();

                        PPEHead ppeHead = shortItem.getPPEHead();
                        if(reqQty>balQty) {
                            Integer shoratge = reqQty - balQty;
                        ppeHead.setTotalShortage(shoratge);
                        }
                        ppeHead.setTotalAvailable(balQty);

                        plans.add(ppeHead);
                    }
                }

                totalShortage = plans.stream()
                        .mapToInt(PPEHead::getTotalShortage)
                        .sum();

                totalAvailable = plans.stream()
                        .mapToInt(PPEHead::getTotalAvailable)
                        .sum();

                filterData.addAll(plans);
            }
            else if (startDate != null && endDate != null) {
                filterData.addAll(filterDataByDateRange(startDate, endDate));
            } else if (!StringUtils.isEmpty(planId)) {
                filterData.addAll(getPpePlans(planId).getData());
            } else if (!StringUtils.isEmpty(ppeFilterName) ) {
                filterData.addAll(getPpeFilter(ppeFilterName));
            }

            filterData = filterData.stream()
                    .sorted(Comparator.comparing(PPEHead::getStartDate))
                    .collect(Collectors.toList());

            for(PPEHead ppeHead:filterData) {
                Integer planRequiredQty=0;
                Integer planAvailableQty=0;
                List<PPELine> ppeline=ppeLineRepository.findByIsDeletedAndSubOrganizationIdAndPPEHeadId(false, loginUser.getSubOrgId(), ppeHead.getId());
                for(PPELine line:ppeline){

                    StockBalance stock=ppeStockBalanceRepository.findByIsDeletedAndSubOrganizationIdAndItemIdId(false, loginUser.getSubOrgId(), line.getItem().getId());
                    Integer reqQty=line.getRequiredQuantity();
                    Integer balQty=stock.getBalanceQuantity();

                    planRequiredQty+=reqQty;
                    planAvailableQty+=balQty;
                }
//              ppeHead.setShortQty(shortQty1);
                ppeHead.setRequiredQty(planRequiredQty);
                ppeHead.setAvailableQty(planAvailableQty);

                Integer producedQty=0;
                if(ppeHead.getRequiredQty()>ppeHead.getAvailableQty()){
                     producedQty=(ppeHead.getPlanQuantity()*ppeHead.getAvailableQty())/ppeHead.getRequiredQty();
                }else{
                     producedQty=ppeHead.getPlanQuantity();
                }

                Integer shortQty1= ppeHead.getPlanQuantity()-producedQty;

                ppeHead.setProduceQuantity(producedQty);
                ppeHead.setShortQty(shortQty1);


            }


              //Manual pagination
            int totalRecords = filterData.size();
            int totalPages = (int) Math.ceil((double) totalRecords / 10);
            int offset = Math.max(0, page * 10);

            int endIndex = Math.min(offset + 10, totalRecords);

            List<PPEHead> pageData;
            if (offset >= totalRecords) {
                pageData = Collections.emptyList();
            } else {
                pageData = filterData.subList(offset, endIndex);
            }


            ppeHeadResponse.setPpeHeadData(pageData);
            ppeHeadResponse.setTotalAvailable(totalAvailable);
            ppeHeadResponse.setTotalShortage(totalShortage);
            dataList.add(ppeHeadResponse);


            baseResponse.setData(dataList);
            baseResponse.setTotalPageCount(totalPages);
            baseResponse.setTotalRecordCount((long) totalRecords);
            baseResponse.setStatus(200);
            baseResponse.setLogId(loginUser.getLogId());
            baseResponse.setCode(1);
            baseResponse.setMessage(Const.PLANLISTFETCHSUCCESSFULLY);

        }catch (Exception e){
            long endTime = System.currentTimeMillis();
            log.error("LogId:{} - ProductionPlanService - getCommonFilter - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(), " ERROR OCCURS AT WHILE SAVING PPEHEAD   EXECUTED TIME " + (endTime - startTime), e);
        }
        long endTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - getCommonFilter - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," GET COMMON FILTER METHOD EXECUTED END  EXECUTED TIME " + (endTime - startTime));
        return baseResponse;
    }

    @Override
    public BaseResponse<PPEResponse> getPPEDetails(Integer PPEId, Boolean isAlternateItem) {
        long startTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - getPPEDetails - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(), " GET PPE DETAILS METHOD START");
        PPEHead ppeHead=null;
        PPEResponse ppeResponse = new PPEResponse();
        BaseResponse<PPEResponse> baseResponse =new BaseResponse<>();
        try {
            if(PPEId != null){

                ppeHead = ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndId(false, loginUser.getSubOrgId(), PPEId);

                Integer planRequiredQty=0;
                Integer planAvailableQty=0;
                List<PPELine> ppeline=ppeLineRepository.findByIsDeletedAndSubOrganizationIdAndPPEHeadId(false, loginUser.getSubOrgId(), ppeHead.getId());
                for(PPELine line:ppeline){

                    StockBalance stock=ppeStockBalanceRepository.findByIsDeletedAndSubOrganizationIdAndItemIdId(false, loginUser.getSubOrgId(), line.getItem().getId());
                    Integer reqQty=line.getRequiredQuantity();
                    Integer balQty=stock.getBalanceQuantity();

                    planRequiredQty+=reqQty;
                    planAvailableQty+=balQty;
                }
//              ppeHead.setShortQty(shortQty1);
                ppeHead.setRequiredQty(planRequiredQty);
                ppeHead.setAvailableQty(planAvailableQty);

                Integer producedQty=0;
                if(ppeHead.getRequiredQty()>ppeHead.getAvailableQty()){
                    producedQty=(ppeHead.getPlanQuantity()*ppeHead.getAvailableQty())/ppeHead.getRequiredQty();
                }else{
                    producedQty=ppeHead.getPlanQuantity();
                }

                Integer shortQty1= ppeHead.getPlanQuantity()-producedQty;

                ppeHead.setProduceQuantity(producedQty);
                ppeHead.setShortQty(shortQty1);


                if(ppeHead!= null) {
                    ppeResponse.setPlanOrderId(ppeHead.getPlanOrderId());
                    ppeResponse.setPpeId(ppeHead.getPpeId());
                    ppeResponse.setStatus(ppeHead.getPpeStatus().getStatusName());
                    ppeResponse.setBomId(ppeHead.getBomHead().getBomId());
                    ppeResponse.setProduct(ppeHead.getProduct());
                    ppeResponse.setBrand(ppeHead.getBrand());
                    ppeResponse.setModel(ppeHead.getModel());
                    ppeResponse.setVariant(ppeHead.getVariant());
                    ppeResponse.setColor(ppeHead.getColor());
                    ppeResponse.setUom(ppeHead.getUom());
                    ppeResponse.setPlanQuantity(ppeHead.getPlanQuantity());
                    ppeResponse.setProduceQuantity(ppeHead.getProduceQuantity());
                    ppeResponse.setShortQuantity(ppeHead.getShortQty());
                    ppeResponse.setProductionShop(ppeHead.getProductionShop());
                    ppeResponse.setShopId(ppeHead.getShopId());
                    ppeResponse.setLine(ppeHead.getLine());
                    ppeResponse.setLineId(ppeHead.getAssemblyLine().getAssemblyLineId());
                    ppeResponse.setStartDate(ppeHead.getStartDate());
                    ppeResponse.setStartTime(ppeHead.getStartTime());
                    ppeResponse.setEndDate(ppeHead.getEndDate());
                    ppeResponse.setEndTime(ppeHead.getEndTime());
                    List<PPELine> ppeLines = null;
                    log.info("LogId:{} - ProductionPlanService - getPPEDetails - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(), "SubOrganisation Id :: " + loginUser.getSubOrgId());
                    ppeLines = ppeLineRepository.findByIsDeletedAndSubOrganizationIdAndPPEHeadId(false, loginUser.getSubOrgId(), ppeHead.getId());

                    if (ppeLines != null && ppeLines.size() != 0) {
                        List<ItemsResponse> itemsResponseList = convertPPELinesToItemsResponse(ppeLines,isAlternateItem);
                        ppeResponse.setItems(itemsResponseList);
                    }
                    List<PPEResponse> ppeResponses = new ArrayList<>();
                    ppeResponses.add(ppeResponse);
                    baseResponse.setCode(1);
                    baseResponse.setMessage(Const.PPEDATAFETCHSUCCESSFULLY);
                    baseResponse.setLogId(loginUser.getLogId());
                    baseResponse.setData(ppeResponses);
                    baseResponse.setStatus(200);

                    long endTime = System.currentTimeMillis();
                    log.info("LogId:{} - ProductionPlanService - getCommonFilter - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," GET COMMON FILTER METHOD EXECUTED END  EXECUTED TIME " + (endTime - startTime));
                    return baseResponse;
                }
                else{
                    List<PPEResponse> ppeResponses = new ArrayList<>();
                    ppeResponses.add(ppeResponse);
                    baseResponse.setCode(0);
                    baseResponse.setMessage(Const.PAGENOTFOUND);

                    baseResponse.setData(ppeResponses);
                    baseResponse.setStatus(200);

                    long endTime = System.currentTimeMillis();
                    log.info("LogId:{} - ProductionPlanService - getCommonFilter - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," GET COMMON FILTER METHOD EXECUTED END  EXECUTED TIME " + (endTime - startTime));

                    return baseResponse;
                }



            }
            List<PPEResponse> ppeResponses = new ArrayList<>();
            ppeResponses.add(ppeResponse);
            baseResponse.setCode(0);
            baseResponse.setMessage(Const.PAGENOTFOUND);

            baseResponse.setData(ppeResponses);
            baseResponse.setStatus(200);


        }catch (Exception e){
            long endTime = System.currentTimeMillis();
            log.error("LogId:{} - ProductionPlanService - getCommonFilter - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(), " ERROR OCCURS AT WHILE FETCHING PPE MODULE   EXECUTED TIME " + (endTime - startTime), e);
        }
        long endTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - getCommonFilter - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(), "GET COMMON FILTER METHOD EXECUTED END  EXECUTED TIME " + (endTime - startTime));
        return baseResponse;
    }

    private List<ItemsResponse> convertPPELinesToItemsResponse(List<PPELine> ppeLines, Boolean isAlternateItem) {
        long startTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - convertPPELinesToItemsResponse - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," CONVERT PPE LINES TO ITEMS RESPONSE METHOD START");
        List<ItemsResponse> itemsResponseList = new ArrayList<>();
        for (PPELine ppeLine : ppeLines) {
            ItemsResponse itemsResponse = new ItemsResponse();
            itemsResponse.setId(ppeLine.getItem().getId());
            itemsResponse.setCode(ppeLine.getItem().getItemId());
            itemsResponse.setItemName(ppeLine.getItem().getName());
            itemsResponse.setType(ppeLine.getItem().getTypeDirectIndirect());
            itemsResponse.setClas(ppeLine.getItem().getClassABC());
            itemsResponse.setItemUnitWeight(ppeLine.getItem().getItemUnitWeight());
            itemsResponse.setAttribute(ppeLine.getBomLine().getDependency());
            itemsResponse.setUom(ppeLine.getItem().getUom());
            itemsResponse.setRequiredQuantity(ppeLine.getRequiredQuantity());
            itemsResponse.setRequiredBy(ppeLine.getRequiredBy());
            itemsResponse.setStore(ppeLine.getStore());
            itemsResponse.setOrgOrAlt("Original");

            ASNOrderLine asnLineResult = asnLineRepository.findByIsDeletedAndSubOrganizationIdAndItemIdAndAsnHeadIdPurchaseStatusStatusNameAndAsnHeadIdRequiredOnDate(false, loginUser.getSubOrgId(), ppeLine.getItem().getId(), "InPipeline", ppeLine.getPPEHead().getStartDate());
            if(asnLineResult!=null){
                itemsResponse.setPipeline(asnLineResult.getAllocatedQuantity());
                itemsResponse.setEta(asnLineResult.getAsnHeadId().getDeliveryDate());
            }

            log.info("LogId:{} - ProductionPlanService - convertPPELinesToItemsResponse - UserId:{} - {}"+loginUser.getLogId()+loginUser.getUserId(),"SubOrganisation Id :: " , loginUser.getSubOrgId()," Item Id ::" , ppeLine.getItem().getId());
            StockBalance stockBalance = stockBalanceRepository.findByIsDeletedAndSubOrganizationIdAndItemIdId(false,loginUser.getSubOrgId(),ppeLine.getItem().getId());


            if (stockBalance != null) {

                if (stockBalance.getBalanceQuantity() >= ppeLine.getRequiredQuantity()) {
                    itemsResponse.setShortage(0);
                    itemsResponse.setStatus(Const.GREEN);
                } else if (stockBalance.getBalanceQuantity()<=ppeLine.getRequiredQuantity()  ) {
                    if (isAlternateItem) {
                        log.info("LogId:{} - ProductionPlanService - convertPPELinesToItemsResponse - UserId:{} - {}",loginUser.getLogId()+loginUser.getUserId(), "SubOrganisation Id :: " , loginUser.getSubOrgId());
                        List<AlternateItemMapper> alternateItemMappers = alternateItemMapperRepository.findByIsDeletedAndSubOrganizationIdAndItemId(false,loginUser.getSubOrgId(),ppeLine.getItem().getId());
                        if (alternateItemMappers != null && alternateItemMappers.size()>0) {
                            itemsResponseList.add(convertAlternateItemToItemsResponse(ppeLine,alternateItemMappers.get(0).getAlternateItemId()));
                            continue;
                        }
                    }

                    Integer stock  = stockBalance.getBalanceQuantity() - ppeLine.getRequiredQuantity();
                    itemsResponse.setShortage(Math.abs(stock));
                    if (ppeLine.getBomLine() != null && ppeLine.getBomLine().getDependency().equalsIgnoreCase(Const.CRITICAL)) {
                        itemsResponse.setStatus(Const.RED);
                    } else if (ppeLine.getBomLine() != null && ppeLine.getBomLine().getDependency().equalsIgnoreCase(Const.NONCRITICAL)) {
                        itemsResponse.setStatus(Const.YELLOW);
                    }
                }
            }
            itemsResponseList.add(itemsResponse);

        }
        long endTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - convertPPELinesToItemsResponse - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," CONVERT PPE LINES TO ITEMS RESPONSE METHOD EXECUTED END  EXECUTED TIME " + (endTime - startTime));
        return itemsResponseList;
    }

    private ItemsResponse convertAlternateItemToItemsResponse(PPELine ppeLine, Item alternateItemId) {
        long startTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - convertAlternateItemToItemsResponse - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," CONVERT ALTERNATE ITEM TO ITEMS RESPONSE METHOD START");
        ItemsResponse itemsResponse = new ItemsResponse();
        itemsResponse.setId(alternateItemId.getId());
        itemsResponse.setCode(alternateItemId.getItemId());
        itemsResponse.setItemName(alternateItemId.getName());
        itemsResponse.setType(alternateItemId.getTypeDirectIndirect());
        itemsResponse.setClas(alternateItemId.getClassABC());
        itemsResponse.setItemUnitWeight(alternateItemId.getItemUnitWeight());
        itemsResponse.setAttribute(ppeLine.getBomLine().getDependency());
        itemsResponse.setUom(alternateItemId.getUom());
        itemsResponse.setRequiredQuantity(ppeLine.getRequiredQuantity());
        itemsResponse.setRequiredBy(ppeLine.getRequiredBy());
        itemsResponse.setStore(ppeLine.getStore());
        itemsResponse.setOrgOrAlt("Alternative");
        log.info("LogId:{} - ProductionPlanService - convertAlternateItemToItemsResponse - UserId:{} - {}" + loginUser.getLogId(),loginUser.getUserId(),"SubOrganisation Id :: " , loginUser.getSubOrgId(), alternateItemId.getId() + " Alternate Item ID :: ");
        StockBalance stockBalance = stockBalanceRepository.findByIsDeletedAndSubOrganizationIdAndItemIdId(false,loginUser.getSubOrgId(),alternateItemId.getId());


        if (stockBalance != null) {

            if (stockBalance.getBalanceQuantity() >= ppeLine.getRequiredQuantity()) {
                itemsResponse.setShortage(0);
                itemsResponse.setStatus(Const.GREEN);
            } else if (stockBalance.getBalanceQuantity()<=ppeLine.getRequiredQuantity()  ) {
                Integer stock  = stockBalance.getBalanceQuantity() - ppeLine.getRequiredQuantity();
                itemsResponse.setShortage(Math.abs(stock));
                if (ppeLine.getBomLine() != null && ppeLine.getBomLine().getDependency().equalsIgnoreCase(Const.CRITICAL)) {
                    itemsResponse.setStatus(Const.RED);
                } else if (ppeLine.getBomLine() != null && ppeLine.getBomLine().getDependency().equalsIgnoreCase(Const.NONCRITICAL)) {
                    itemsResponse.setStatus(Const.YELLOW);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - convertAlternateItemToItemsResponse - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," CONVERT ALTERNATE ITEM TO ITEMS RESPONSE METHOD EXECUTED END  EXECUTED TIME " + (endTime - startTime));
        return itemsResponse;
    }

    private List<PPELine> getShortageItemsList() {
        long startTime = System.currentTimeMillis();
        log.info(loginUser.getLogId() + " GET SHORTAGE ITEM METHOD");
        log.info("LogId:{} - ProductionPlanService - getShortageItemsList - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," GET SHORTAGE ITEMS LIST METHOD START");
        List<PPELine> shortageItems= new ArrayList<>();
        List<PPELine> noShortageItem= new ArrayList<>();

        try {
            log.info("LogId:{} - ProductionPlanService - getShortageItemsList - UserId:{} - {}"+loginUser.getLogId(),loginUser.getUserId(),"SubOrganisation Id :: " , loginUser.getSubOrgId());
            List<StockBalance> stckbData = ppeStockBalanceRepository.findByIsDeletedAndSubOrganizationId(false, loginUser.getSubOrgId());
            List<PPELine> lineData = ppeLineRepository.findByIsDeletedAndSubOrganizationId(false, loginUser.getSubOrgId());

            for (PPELine ppeLine : lineData) {
                Integer requiredQty = ppeLine.getRequiredQuantity();
                List<StockBalance> filteredStockBalance = stckbData.stream()
                        .filter(stockBalance -> stockBalance.getItemId().equals(ppeLine.getItem()))
                        .collect(Collectors.toList());
                int totalBalanceQty = filteredStockBalance.stream()
                        .mapToInt(StockBalance::getBalanceQuantity)
                        .sum();
                if (totalBalanceQty<requiredQty) {
                    shortageItems.add(ppeLine);
                } else {
                    noShortageItem.add(ppeLine);
                }

            }

        }catch (Exception e){
            long endTime = System.currentTimeMillis();
            log.error("LogId:{} - ProductionPlanService - getShortageItemsList - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(), " ERROR OCCURS AT WHILE FETCHING SHORTAGE ITEMS  EXECUTED TIME " + (endTime - startTime), e);
        }
        long endTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - getShortageItemsList - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(), " GET SHORTAGE ITEMS LIST METHOD EXECUTED END  EXECUTED TIME " + (endTime - startTime));
        return shortageItems;
    }

    @Override
    public BaseResponse<Users> getAllOfficers(){
        long startTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - getAllOfficers - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(),"  GET ALL OFFICERS METHOD START");
        BaseResponse baseResponse=new BaseResponse<>();
        try{
            log.info("LogId:{} - ProductionPlanService - getAllOfficers - UserId:{} - {}"+loginUser.getLogId(),loginUser.getUserId(),"SubOrganisation Id :: " , loginUser.getSubOrgId());
            List<Users> users = this.userRepository.findByIsDeletedAndSubOrganizationIdAndIsActiveAndModuleUserLicenceKeyLicenceLineSubModuleSubModuleCode(false, loginUser.getSubOrgId(), true,"PPEO");
            baseResponse.setCode(1);
            baseResponse.setData(users);
            baseResponse.setLogId(loginUser.getLogId());
            baseResponse.setStatus(200);
            baseResponse.setMessage("SUCCESSFULLY FETCHED OFFICER LIST");
            log.info("LogId:{} - ProductionPlanService - getAllOfficers - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," SUCCESSFULLY FETCHED OFFICER LIST ");

        }catch(Exception e){
            baseResponse.setCode(0);
            baseResponse.setData(null);
            baseResponse.setLogId(loginUser.getLogId());
            baseResponse.setStatus(500);
            baseResponse.setMessage("FAILED TO  FETCHED OFFICERS LISTt");
            long endTime = System.currentTimeMillis();
            log.error("LogId:{} - ProductionPlanService - getAllOfficers - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(), " ERROR OCCURS AT GETTING FETCHED LIST OF USER EXECUTED TIME " + (endTime - startTime), e);
        }
        long endTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - getCommonFilter - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," GET ALL OFFICERS METHOD EXECUTED END  EXECUTED TIME " + (endTime - startTime));
        return baseResponse;
    }

    @Override
    public BaseResponse<Reason> getAllReasons() {
        BaseResponse<Reason> baseResponse=new BaseResponse<>();
        try{
            List<Reason> reasonList=reasonRepository.findByIsDeletedAndSubOrganizationIdAndReasonCategory(false,loginUser.getSubOrgId(),"PPE");

             if (reasonList!=null) {
                baseResponse.setCode(1);
                baseResponse.setStatus(200);
                baseResponse.setData(reasonList);
                baseResponse.setMessage(" REASON LIST FETCHED SUCCESSFULLY ");
                baseResponse.setLogId(loginUser.getLogId());
            }else{
                baseResponse.setCode(0);
                baseResponse.setStatus(500);
                baseResponse.setMessage(" REASON LIST IS EMPTY ");
                baseResponse.setLogId(loginUser.getLogId());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return baseResponse;
    }

    @Override
    public BaseResponse<PPEHead> deletePlans(Integer ppeId) {
        BaseResponse<PPEHead> baseResponse=new BaseResponse<>();
        try{
            PPEHead plan = ppeHeadRepository.findByIsDeletedAndId(false, ppeId);
            plan.setIsDeleted(true);
            List<PPEHead> plans= new ArrayList<>();
            plans.add(plan);
            ppeHeadRepository.save(plan);
            baseResponse.setCode(1);
            baseResponse.setStatus(200);
            baseResponse.setData(plans);
            baseResponse.setMessage("PLANS DELETED SUCCESSFULLY");
            baseResponse.setLogId(loginUser.getLogId());

        }catch(Exception e){
            baseResponse.setCode(0);
            baseResponse.setStatus(500);
            baseResponse.setMessage("ENABLE TO DELETE THE PLAN");
            e.printStackTrace();
        }
        return baseResponse;
    }

    public BaseResponse<PPEHead> unassignPlans(Integer planId) {
        {
            BaseResponse<PPEHead> baseResponse = new BaseResponse<>();
            List<PPEHead> ppeHead = new ArrayList<>();

            try {
                log.info("LogId:{} - ProductionPlanService - unassignPlans - UserId:{} - {}", loginUser.getLogId(), loginUser.getUserId(), "SubOrganisation Id :: " + loginUser.getSubOrgId() + " Plan Id ::" + planId );
                PPEHead plan = ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndId(false, loginUser.getSubOrgId(), planId);
                if ( loginUser.getSubModuleCode().equals("PPEM") && plan.getPpeOfficer()!=null) {

                    plan.setPpeOfficer(null);
                    PPEHead save = ppeHeadRepository.save(plan);
                    ppeHead.add(save);

                    baseResponse.setCode(1);
                    baseResponse.setData(ppeHead);
                    baseResponse.setStatus(200);
                    baseResponse.setLogId(loginUser.getLogId());
                    baseResponse.setMessage(Const.OFFICERUNASSIGNSUCCESSFULLY);
                } else {
                    baseResponse.setCode(0);
                    baseResponse.setStatus(500);
                    baseResponse.setMessage("Only managers are authorized to unassign officers from the selected plan.");
                }
            } catch (Exception e) {
                long endTime = System.currentTimeMillis();
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            return baseResponse;
        }
    }

    @Override
    public BaseResponse<PPEHead> getCommonFilterManager(String ppeFilterName, Integer officerId, Date startDate, Date endDate, int page, int size) {

        long startTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - getCommonFilterManager - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," GET COMMON FILTER FOR MANAGER METHOD START");
        BaseResponse<PPEHead> baseResponse=new BaseResponse();
        PageImpl<PPEHead> ppeHeads=null;

        try {
            Pageable pageable = PageRequest.of(page, size);
            List<PPEHead> filterData = new ArrayList<>();

            if(!StringUtils.isEmpty(ppeFilterName) && officerId !=null && startDate != null && endDate != null){

                List<PPEHead> plans=null;
                if (ppeFilterName.equalsIgnoreCase(Const.ASSIGN)) {
                    plans = ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeOfficerIsNotNull(false, loginUser.getSubOrgId());
                } else {
                    plans = ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeOfficerIsNull(false, loginUser.getSubOrgId());
                }
                List<PPEHead> filteredByDateRange = filterDataByDateRange(startDate, endDate);

                List<PPEHead> plansByOfficer= ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeOfficerId(false, loginUser.getSubOrgId(), officerId);

                plans.retainAll(filteredByDateRange);
                plans.retainAll(plansByOfficer);

                filterData.addAll(plans);

            } else if(!StringUtils.isEmpty(ppeFilterName) && ppeFilterName.equals(Const.ASSIGN) && startDate != null && endDate != null){

               List<PPEHead> plans = ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeOfficerIsNotNull(false, loginUser.getSubOrgId());
               List<PPEHead> filteredByDateRange = filterDataByDateRange(startDate, endDate);

                List<PPEHead> matchingPlans = plans.stream()
                        .filter(filteredByDateRange::contains)
                        .collect(Collectors.toList());

                filterData.addAll(matchingPlans);

            }else if(!StringUtils.isEmpty(ppeFilterName) && ppeFilterName.equalsIgnoreCase(Const.ASSIGN) && officerId !=null){

                List<PPEHead> plans = ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeOfficerIsNotNull(false, loginUser.getSubOrgId());
                List<PPEHead> plansByOfficer= ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeOfficerId(false, loginUser.getSubOrgId(), officerId);

                List<PPEHead> matchingPlans = plans.stream()
                        .filter(plansByOfficer::contains)
                        .collect(Collectors.toList());

                filterData.addAll(matchingPlans);

            }else if(!StringUtils.isEmpty(ppeFilterName) && ppeFilterName.equalsIgnoreCase(Const.UNASSIGN) && officerId !=null){

               List<PPEHead> plans = ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeOfficerIsNull(false, loginUser.getSubOrgId());
               List<PPEHead> plansByOfficer= ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeOfficerId(false, loginUser.getSubOrgId(), officerId);

                List<PPEHead> matchingPlans = plans.stream()
                        .filter(plan -> plansByOfficer.stream().anyMatch(planByOfficer -> Objects.equals(plan.getId(), planByOfficer.getId())))
                        .collect(Collectors.toList());

                filterData.addAll(matchingPlans);

            }else if(!StringUtils.isEmpty(ppeFilterName) && ppeFilterName.equalsIgnoreCase(Const.UNASSIGN) && startDate != null && endDate != null){
                List<PPEHead> plans = ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeOfficerIsNull(false, loginUser.getSubOrgId());
                List<PPEHead> filteredByDateRange = filterDataByDateRange(startDate, endDate);

                List<PPEHead> matchingPlans = plans.stream()
                        .filter(filteredByDateRange::contains)
                        .collect(Collectors.toList());

                filterData.addAll(matchingPlans);
            }
            else if(!StringUtils.isEmpty(ppeFilterName)){

                List<PPEHead> plans=null;
                if (ppeFilterName.equalsIgnoreCase(Const.ASSIGN)) {
                    plans = ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeOfficerIsNotNull(false, loginUser.getSubOrgId());
                } else {
                    plans = ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeOfficerIsNull(false, loginUser.getSubOrgId());
                }

                filterData.addAll(plans);

                filterData = filterData.stream()
                        .sorted(Comparator.comparing(PPEHead::getStartDate))
                        .collect(Collectors.toList());

            }

            // Perform manual pagination
            int totalRecords = filterData.size();
            int totalPages = (int) Math.ceil((double) totalRecords / 10);
            int offset = Math.max(0, page * 10);

            int endIndex = Math.min(offset + 10, totalRecords);

            List<PPEHead> pageData;
            if (offset >= totalRecords) {
                pageData = Collections.emptyList();
            } else {
                pageData = filterData.subList(offset, endIndex);
            }


            baseResponse.setData(pageData);
            baseResponse.setTotalPageCount(totalPages);
            baseResponse.setTotalRecordCount((long) filterData.size());
            baseResponse.setStatus(200);
            baseResponse.setLogId(loginUser.getLogId());
            baseResponse.setCode(1);
            baseResponse.setMessage(Const.PLANLISTFETCHSUCCESSFULLY);

        }catch (Exception e){
            long endTime = System.currentTimeMillis();
            e.printStackTrace();
            log.error("LogId:{} - ProductionPlanService - getCommonFilterManager - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(), " ERROR OCCURS AT WHILE SAVING PPEHEAD   EXECUTED TIME " + (endTime - startTime), e);
        }
        long endTime = System.currentTimeMillis();
        log.info("LogId:{} - ProductionPlanService - getCommonFilterManager - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," GET COMMON FILTER METHOD EXECUTED END  EXECUTED TIME " + (endTime - startTime));
        return baseResponse;
    }

    public BaseResponse<PPEHead> getAllPlans(){

        long startTime = System.currentTimeMillis();
        BaseResponse<PPEHead> baseResponse=new BaseResponse<>();
        log.info("LogId:{} - ProductionPlanService - getRolePlans - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId()," GET ROLE PLANS Started ");

        List<PPEHead> roleData=null;
        try{

            if(loginUser.getSubModuleCode().equalsIgnoreCase("PPEM")){
                log.info("LogId:{} - ProductionPlanService - getRolePlans - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(), "SubOrganisation Id :: " + loginUser.getSubOrgId());
                roleData = ppeHeadRepository.findByIsDeletedAndSubOrganizationId(false, loginUser.getSubOrgId());
            }else if(loginUser.getSubModuleCode().equalsIgnoreCase("PPEO")){
                log.info("LogId:{} - ProductionPlanService - getRolePlans - UserId:{} - {}",loginUser.getLogId(), "PPEOfficer:: ",loginUser.getUserId()+ "SubOrganisation Id :: " + loginUser.getSubOrgId());
                roleData = ppeHeadRepository.findByIsDeletedAndSubOrganizationIdAndPpeOfficerId(false, loginUser.getSubOrgId(),loginUser.getUserId());
            }else {
                baseResponse.setCode(0);
                baseResponse.setStatus(500);
                baseResponse.setData(new ArrayList<>());
                baseResponse.setLogId(loginUser.getLogId());
                baseResponse.setMessage("Provided user not belongs to PPE Officer or PPE Manager");
                return baseResponse;
            }
            baseResponse.setCode(1);
            baseResponse.setStatus(200);
            baseResponse.setData(roleData);
            baseResponse.setLogId(loginUser.getLogId());
            baseResponse.setMessage(Const.PLANLISTFETCHSUCCESSFULLY);
        }catch (Exception e){
            long endTime = System.currentTimeMillis();
            e.printStackTrace();
            log.error("LogId:{} - ProductionPlanService - getRolePlans - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(),  " ERROR OCCURS AT WHILE FETCHING Plans  EXECUTED TIME " + (endTime - startTime), e);
        }
        long endTime = System.currentTimeMillis();
        log.error("LogId:{} - ProductionPlanService - getRolePlans - UserId:{} - {}",loginUser.getLogId(),loginUser.getUserId(),  " GET ROLE PLANS METHOD EXECUTED END EXECUTED TIME " + (endTime - startTime));
        return baseResponse;
    }

}
