package com.ProductionPlanExecution.Repository;

import com.ProductionPlanExecution.Model.PPEHead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PPEHeadRepository extends JpaRepository<PPEHead,Integer> {

    List<PPEHead> findByIsDeletedAndSubOrganizationId(boolean b, Integer subOrgId);
    List<PPEHead> findByIsDeletedAndSubOrganizationIdAndPpeFilterId(boolean b,Integer subOrgId,Integer PpeFilterId);

    List<PPEHead> findByIsDeletedAndSubOrganizationIdAndPpeOfficerId(boolean b, Integer subOrgId, Integer userId);
    PPEHead findByIdAndIsDeletedAndSubOrganizationId(Integer PpeId, boolean b,Integer subOrgId);

    PPEHead findByIsDeletedAndSubOrganizationIdAndPpeFilter(boolean b, Integer subOrgId,String FilterId);
    List<PPEHead> findByIsDeletedAndSubOrganizationIdAndPpeId(boolean b, Integer subOrgId,String ppeId);

    List<PPEHead> findByIsDeleted(boolean b);

    List<PPEHead> findByIsDeletedAndSubOrganizationIdAndPpeFilterStatusName(boolean b, Integer subOrgId, String statusName);

    PPEHead findByIsDeletedAndSubOrganizationIdAndId(boolean b, Integer subOrgId, Integer ppeId);

//    List<PPEHead> findByIsDeletedAndPpeStatusStatusName(boolean b, String hold);

    List<PPEHead> findByIsDeletedAndSubOrganizationIdAndIdIn(boolean b, Integer subOrgId, List<Integer> planId);

    List<PPEHead> findByIsDeletedAndPpeStatusStatusNameIn(boolean b, List<String> planStatus);

    PPEHead findByIsDeletedAndId(boolean b, Integer ppeId);

    List<PPEHead> findByIsDeletedAndSubOrganizationIdAndPpeStatusStatusName(boolean b, Integer subOrgId, String confirm);

    List<PPEHead> findByIsDeletedAndSubOrganizationIdAndPpeOfficerIsNotNull(boolean b, Integer subOrgId);

    List<PPEHead> findByIsDeletedAndSubOrganizationIdAndPpeOfficerIsNull(boolean b, Integer subOrgId);


    //for pagable
    Page<PPEHead> findByIsDeletedAndSubOrganizationId(boolean isDeleted, Integer subOrgId, Pageable pageable);

    Page<PPEHead> findByIsDeletedAndSubOrganizationIdAndPpeOfficerId(boolean isDeleted, Integer subOrgId, Integer userId, Pageable pageable);

    List<PPEHead> findByIsDeletedAndSubOrganizationIdAndPlanOrderId(boolean b, Integer subOrgId, String planId);
}
