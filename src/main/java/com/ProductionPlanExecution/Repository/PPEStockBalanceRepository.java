package com.ProductionPlanExecution.Repository;

import com.ProductionPlanExecution.Model.StockBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PPEStockBalanceRepository extends JpaRepository<StockBalance,Integer> {

    List<StockBalance> findByIsDeletedAndSubOrganizationId(boolean b, Integer subOrgId);

    StockBalance findByIsDeletedAndSubOrganizationIdAndItemIdId(boolean b, Integer subOrgId, Integer id);

    StockBalance findByIsDeletedAndItemIdId(boolean b, Integer id);

}
