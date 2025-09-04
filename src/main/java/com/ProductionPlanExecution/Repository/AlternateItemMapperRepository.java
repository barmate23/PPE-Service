package com.ProductionPlanExecution.Repository;

import com.ProductionPlanExecution.Model.AlternateItemMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlternateItemMapperRepository extends JpaRepository<AlternateItemMapper,Integer> {

    List<AlternateItemMapper> findByIsDeletedAndSubOrganizationIdAndItemId(boolean b, Integer subOrgId, Integer itemId);

//    @Query("select p from PPELine p where p.isDeleted = ?1 and p.subOrganizationId = ?2 and p.PPEHead.id = ?3")
//    List<PPELine> findByIsDeletedAndSubOrganizationIdAndPPEHead_Id(Boolean isDeleted, Integer subOrganizationId, Integer id);

}
