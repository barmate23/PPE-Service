package com.ProductionPlanExecution.Repository;

import com.ProductionPlanExecution.Model.SubModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PpeSubmoduleRepository extends JpaRepository<SubModule,Integer> {


    Optional<SubModule> findByIsDeletedAndId(boolean b, Integer subModuleId);
}
