package com.ProductionPlanExecution.Request;

import lombok.Data;

import java.util.Date;


@Data
public class OrganizationRequest {
    private Boolean isSubOrg;
    private Boolean isAdminPresent;
    private Integer organizationId;
    private String organizationName;
    private String orgRegNumber;
    private String panCardNumber;
    private String gstinNumber;
    private Integer postCode;
    private String district;
    private String subDistrict;
    private String state;
    private String country;
    private Integer countryCode;
    private String officeNumber;
    private String city;
    private String town;
    private String village;
    private String address;
    private String address1;
    private String address2;
    private String building;
    private String street;
    private String landMark;
    private String locality;
    private String subLocality;
    private String areaCode;
    private Integer licenseType;
    private Date startDate;
    private Date endDate;
    private String email;
    private String mobileNumber;
    private String landlineNumber;
    private String adminMobileNumber;
    private String adminLandlineNumber;
    private String firstName;
    private String lastName;
    private String username;

}
