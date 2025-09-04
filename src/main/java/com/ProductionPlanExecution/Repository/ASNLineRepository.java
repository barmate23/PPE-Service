package com.ProductionPlanExecution.Repository;

import com.ProductionPlanExecution.Model.ASNOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface ASNLineRepository extends JpaRepository<ASNOrderLine,Integer> {

    ASNOrderLine findByIsDeletedAndSubOrganizationIdAndItemIdAndAsnHeadIdPurchaseStatusStatusNameAndAsnHeadIdRequiredOnDate(boolean b, Integer subOrgId, Integer itemId, String status, Date requiredOnDate);

}
