package com.ProductionPlanExecution.Utils;

import com.ProductionPlanExecution.Model.PPEHead;
import com.ProductionPlanExecution.Model.PPELine;
import com.ProductionPlanExecution.Model.StockBalance;
import com.ProductionPlanExecution.Repository.PPEHeadRepository;
import com.ProductionPlanExecution.Repository.PPELineRepository;
import com.ProductionPlanExecution.Repository.PPEStockBalanceRepository;
import com.ProductionPlanExecution.Repository.PpeStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@EnableAsync
@EnableScheduling
public class PlanScedular {

    @Autowired
    private PPEHeadRepository ppeHeadRepository;

    @Autowired
    private PpeStatusRepository ppeStatusRepository;

    @Autowired
    private PPELineRepository ppeLineRepository;

    @Autowired
    private PPEStockBalanceRepository ppeStockBalanceRepository;


    @Scheduled(cron = "0 5 0 * * ?")       //This cron will run after every day at 12:05 to check hold plan
//  @Scheduled(cron = "0 * * * * ?")
    public void cancelHoldPlansPastStartDate() {
        List<String> planStatus= new ArrayList<>();
        planStatus.add(Const.HOLD);
        planStatus.add(Const.CONFIRM);

        log.info(" CRON JOB STARTED TO CHECK HOLD PLANS ");
        List<PPEHead> holdAndConfirm = ppeHeadRepository.findByIsDeletedAndPpeStatusStatusNameIn(false, planStatus);
          LocalDate currentDate = LocalDate.now();

          List<PPELine> ppeLineList =null;


          try {
            for (PPEHead holdNconfirmPlan : holdAndConfirm) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                LocalDate startDate = LocalDate.parse(holdNconfirmPlan.getStartDate().toString(), formatter);

                ppeLineList= ppeLineRepository.findByIsDeletedAndPPEHeadId(false,holdNconfirmPlan.getId());

                if (currentDate.isAfter(startDate)) {
                    holdNconfirmPlan.setPpeStatus(ppeStatusRepository.findByStatusName(Const.CANCEL));
                    for(PPELine ppeline: ppeLineList){
                        StockBalance stckBalance = ppeStockBalanceRepository.findByIsDeletedAndItemIdId(false, ppeline.getItem().getId());
                        stckBalance.setBalanceQuantity(ppeline.getAllocatedQty()+stckBalance.getBalanceQuantity());
                        ppeline.setAllocatedQty(0);

                        ppeStockBalanceRepository.save(stckBalance);
                        ppeLineRepository.save(ppeline);

                    }

                    ppeHeadRepository.save(holdNconfirmPlan);
                }
            }
        }catch(Exception e){
            log.error(" ERROR IN CRON JOB EXECUTION FOR HOLD PLANS "+e);
        }
        log.info(" CRON JOB COMPLETED TO CHECK HOLD PLANS ");
    }

}
