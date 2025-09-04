package com.ProductionPlanExecution.Repository;
import com.ProductionPlanExecution.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Integer> {
  Optional<Users> findByOrganizationIdAndSubOrganizationIdAndIsDeletedAndIsActiveAndId(Integer orgId, Integer subOrgId, boolean b1, boolean b, Integer id);
    Optional<Users> findByOrganizationIdAndSubOrganizationIdAndIsDeletedAndIsActiveAndUserId(Integer orgId, Integer subOrgId,boolean b1,boolean b,String userId);
    Users findByOrganizationIdAndSubOrganizationIdAndIsActiveAndIsDeletedAndUsername(Integer orgId, Integer subOrgId, boolean b, boolean b1, String dockSupervisor);

    List<Users> findByIsDeletedAndSubOrganizationIdAndIsActiveAndModuleUserLicenceKeyLicenceLineSubModuleSubModuleCode(boolean b,Integer subOrgId, boolean b1, String ppeo);
}
