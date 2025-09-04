package com.ProductionPlanExecution.Repository;

import com.ProductionPlanExecution.Model.Reason;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReasonRepository extends JpaRepository<Reason,Integer> {

    Reason findByIsDeletedAndId(boolean b, Integer reasonId);

    List<Reason> findByIsDeletedAndSubOrganizationIdAndReasonCategory(boolean b, Integer subOrgId, String ppe);
}
