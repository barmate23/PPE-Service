package com.ProductionPlanExecution.Repository;
import com.ProductionPlanExecution.Model.PpeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PpeStatusRepository extends JpaRepository<PpeStatus,Integer> {

  PpeStatus findByStatusName(String ppeStatus);

}
