package com.ProductionPlanExecution.Repository;

import com.ProductionPlanExecution.Model.BOMLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PPEBomLineRepository extends JpaRepository<BOMLine,Integer> {

    BOMLine findByIsDeletedAndSubOrganizationIdAndItemIdAndBomHeadIdId(boolean b, Integer subOrgId, Integer id,Integer bomId );
}
