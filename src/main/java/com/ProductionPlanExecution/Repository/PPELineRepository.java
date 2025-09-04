package com.ProductionPlanExecution.Repository;

import com.ProductionPlanExecution.Model.PPELine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PPELineRepository extends JpaRepository<PPELine,Integer> {

    List<PPELine> findByIsDeletedAndSubOrganizationId(boolean b, Integer subOrgId);

    List<PPELine> findByIsDeletedAndSubOrganizationIdAndPPEHeadId(boolean b, Integer subOrgId, Integer id);

    List<PPELine> findByIsDeletedAndPPEHeadId(boolean b, Integer id);
}
