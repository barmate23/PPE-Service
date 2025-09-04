package com.ProductionPlanExecution.Utils;

//import org.springframework.security.core.parameters.P;

public class ConstantsForAPIs {
    public static final String PREFIX = "/api/ProductionPlanService/v1";
    public static final String GET_ADDRESS_BY_PINCODE="/getAddressByPincode/{pincode}";
    public static final String JWT_AUTHENTICATION_CONTROLLER = "authenticate";
    public static final String RESET_PASSWORD="resetPassword";
    public static final String GET_ALL_SUB_ORGANIZATION="/getAllSubOrganization/{orgId}";

    public static final String GENERATE_OTP="generateOTP";
    public static final String IS_FIRST_LOGIN="isFirstLogin";
    public static final String GET_ALL_LICENCE_TYPE="/getAllLicenceType";

    //ORGANIZATION
    public static final String GET_ORGANIZATION_BY_ID="getOrganizationById/{organizationId}";
    public static final String SAVE_ORGANIZATION="/saveOrganization";
    public static final String UPDATE_ORGANIZATION="/updateOrganization/{organizationId}";
    public static final String GET_ALL_ORGANIZATION="/getAllOrganization";
    public static final String GET_ALL_ORGANIZATION_WITH_PAGINATION="/getAllOrganizationWithPagination";
    public static final String DELETE_ORGANIZATION_BY_ID="/deleteOrganizationById/{organizationId}";
    public static final String ACTIVE_ORGANIZATION_BY_ID="/activeOrganizationById";

    public static final String SAVE_LICENCE_MODULE="/saveLicenceModule";

    public static final String UPDATE_LICENCE_MODULE="/updateLicenceModule";
    public static final String DELETE_LICENCE_MODULE="/deleteLicenceModuleById/{licenceModuleId}";
    public static final String GET_ALL_LICENCE_MODULE="/getAllLicenceLine/{licHeadId}";
    public static final String GET_MODULE = "/getLicModules";
    public static final String GET_APPROVER_LIST = "/getApproverList";
    public static final String GET_APPROVER_DET_LIST = "/getApproverDetList";
    public static final String APPROVE_LICENCE_STATUS = "/saveApproveRequest";
    public static final String SAVE_DOCUMENT = "/savePoDoc";
    public static final String GET_DOCUMENT = "/getDocument/{docId}";
    public static final String GET_USER_LICENCE_KEYS = "/getUserLicKeys/{orgId}";
    public static final String GET_LICENCE_BY_MODULE = "/getLicenceByMod/{subModId}";
    public static final String GET_ALL_MODULE = "/getAllModule";
    public static final String GET_COUNTRY = "/getCountry";
    public static final String GET_CITIES = "/getCity/{stateId}";
    public static final String GET_STATE = "/getState/{countryId}";
    public static final String GET_ALL_LICENCE_HEAD_MODULE = "/getLicenceHead/{orgId}";
    public static final String UPDATE_USER_LIC_STATUS = "/updateUserLicStatus";
    public static final String GET_REG_NO = "/getRegNo";
    public static final String DELETE_USER_LICENCE = "/deleteUserLicence/{userLicenceId}";
    public static final String GET_SUB_MODULE = "/getSubModule/{moduleId}";
    public static final String SEND_FOR_APPROVAL = "/sendForApproval";
    public static final String GET_MODULE_AND_LICENSE_PURCHASE_ORDER_LIST = "/getLicenceModulePurchaseOrderList";
    public static final String GET_MODULE_AND_LICENSE_DETAILS = "/getLicenceModuleDetails/{licenceHeadId}";
    public static final String GET_USER_LICENSE_KEY_DETAILS = "/getUserLicenseKeyDetails";
    public static final String GET_REASON_MASTER = "/getReasonMaster";
    public static final String GET_LICENSE_ALL_MODULE_DETAILS = "/getModuleDetails";
    public static final String SAVE_REASON_FILE = "/saveReasonFile";

    //For PPE Service
    public static final String GET_PPE_FILTER="/GetPpeFilter/{ppeFilterName}";
    public static final String GET_ALL_PPE_ROLE="/getAllPPE";
    public static final String ASSIGN_PPE_PLAN="/ppePlanAssign";
    public static final String UPDATE_STATUS="/updateStatus";
    public static final String FILTER_DATA="/FilterData";
    public static final String FILTER_BY_PLAN="/ppePlan/{planId}";
    public static final String DATE_FILTER="/dateFilter";
    public static final String COMMON_FILTER="/commonFilterPpe";
    public static final String ITEM_LIST="/itemsList";
    public static final String PPE_DETAILS="/PPEDetailsById";
    public static final String PPE_OFFICERS="/getAllOfficers";
    public static final String ALL_REASON="/getAllReasons";
    public static final String DELETE_CANCEL_PLAN="/deleteCancelPlans/{ppeId}";
    public static final String UNASSIGN_PPE_PLAN="/ppePlanUnAssign/{planId}";
    public static final String COMMON_FILTER_MANAGER="/commonFilterManager";


}