package com.ProductionPlanExecution.Model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tbl_address")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Pincode")
    private Integer pincode;

    @Column(name = "StateName", length = 20)
    private String stateName;

    @Column(name = "DistrictName", length = 20)
    private String districtName;

    @Column(name = "SubDistrictName", length = 20)
    private String subDistrictName;

    @Column(name = "TownName", length = 20)
    private String townName;

    @Column(name = "VillageName", length = 20)
    private String villageName;

}

