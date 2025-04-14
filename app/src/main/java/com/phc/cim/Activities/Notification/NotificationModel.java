package com.phc.cim.Activities.Notification;

/**
 * Model class for notifications fetched from C# API
 */
public class NotificationModel {
    private String CaseFileID;
    private String CategoryType;
    private String DistrictName;
    private String EvidenceMaterial;
    private int FinalID;
    private String InsertedDate;
    private String Message;
    private int NotificationId;
    private String OutletAddress;
    private String OutletName;
    private String Revisit_Assign_Date;
    private String SealDateTime;
    private String SealType;
    private String SealedBy;
    private String SummonIssueDate;
    private boolean isRead;
    private String Quack_ContactNumber;
    private String Quack_CNIC;

    public NotificationModel() {
    }

    public NotificationModel(String caseFileID, String categoryType, String districtName, String evidenceMaterial, int finalID, String insertedDate, String message, int notificationId, String outletAddress, String outletName, String revisit_Assign_Date, String sealDateTime, String sealType, String sealedBy, String summonIssueDate, boolean isRead, String quack_ContactNumber, String quack_CNIC) {
        CaseFileID = caseFileID;
        CategoryType = categoryType;
        DistrictName = districtName;
        EvidenceMaterial = evidenceMaterial;
        FinalID = finalID;
        InsertedDate = insertedDate;
        Message = message;
        NotificationId = notificationId;
        OutletAddress = outletAddress;
        OutletName = outletName;
        Revisit_Assign_Date = revisit_Assign_Date;
        SealDateTime = sealDateTime;
        SealType = sealType;
        SealedBy = sealedBy;
        SummonIssueDate = summonIssueDate;
        this.isRead = isRead;
        Quack_ContactNumber = quack_ContactNumber;
        Quack_CNIC = quack_CNIC;
    }

    public String getCaseFileID() {
        return CaseFileID;
    }

    public void setCaseFileID(String caseFileID) {
        CaseFileID = caseFileID;
    }

    public String getCategoryType() {
        return CategoryType;
    }

    public void setCategoryType(String categoryType) {
        CategoryType = categoryType;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public String getEvidenceMaterial() {
        return EvidenceMaterial;
    }

    public void setEvidenceMaterial(String evidenceMaterial) {
        EvidenceMaterial = evidenceMaterial;
    }

    public int getFinalID() {
        return FinalID;
    }

    public void setFinalID(int finalID) {
        FinalID = finalID;
    }

    public String getInsertedDate() {
        return InsertedDate;
    }

    public void setInsertedDate(String insertedDate) {
        InsertedDate = insertedDate;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getNotificationId() {
        return NotificationId;
    }

    public void setNotificationId(int notificationId) {
        NotificationId = notificationId;
    }

    public String getOutletAddress() {
        return OutletAddress;
    }

    public void setOutletAddress(String outletAddress) {
        OutletAddress = outletAddress;
    }

    public String getOutletName() {
        return OutletName;
    }

    public void setOutletName(String outletName) {
        OutletName = outletName;
    }

    public String getRevisit_Assign_Date() {
        return Revisit_Assign_Date;
    }

    public void setRevisit_Assign_Date(String revisit_Assign_Date) {
        Revisit_Assign_Date = revisit_Assign_Date;
    }

    public String getSealDateTime() {
        return SealDateTime;
    }

    public void setSealDateTime(String sealDateTime) {
        SealDateTime = sealDateTime;
    }

    public String getSealType() {
        return SealType;
    }

    public void setSealType(String sealType) {
        SealType = sealType;
    }

    public String getSealedBy() {
        return SealedBy;
    }

    public void setSealedBy(String sealedBy) {
        SealedBy = sealedBy;
    }

    public String getSummonIssueDate() {
        return SummonIssueDate;
    }

    public void setSummonIssueDate(String summonIssueDate) {
        SummonIssueDate = summonIssueDate;
    }
    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getQuack_ContactNumber() {
        return Quack_ContactNumber;
    }

    public void setQuack_ContactNumber(String quack_ContactNumber) {
        Quack_ContactNumber = quack_ContactNumber;
    }

    public String getQuack_CNIC() {
        return Quack_CNIC;
    }

    public void setQuack_CNIC(String quack_CNIC) {
        Quack_CNIC = quack_CNIC;
    }
}