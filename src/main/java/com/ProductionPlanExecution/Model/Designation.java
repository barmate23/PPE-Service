//package com.SuperAdminManagement.Model;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@Table(name = "tbl_Designation")
//@Data
//public class Designation {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID", length = 20)
//    private Integer id;
//    @Column(name = "DesignationName")
//    private String designationName;
//    @Column(name = "GroupId")
//    private Integer groupId;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "OrganizationId")
//    private Organization organizationId;
//
//    @Column(name = "IsActive")
//    private Boolean isActive;
//
//    @Column(name = "IsDeleted")
//    private Boolean isDeleted;
//
//    @Column(name = "CreatedBy")
//    private Integer createdBy;
//
//    @Column(name = "CreatedOn")
//    private Date createdOn;
//
//    @Column(name = "ModifiedBy")
//    private Integer modifiedBy;
//
//    @Column(name = "ModifiedOn")
//    private Date modifiedOn;
//}
