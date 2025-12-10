package com.phc.cim.DataElements;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notifications")
public class NotificationItem {

    @PrimaryKey
    private int NotificationId;

    private String Type; // NEW → "REVISIT" or "COMPLAINT"

    // COMMON FIELDS
    private String Message;
    private String InsertedDate;
    private boolean isRead;

    // REVISIT FIELDS
    private String CaseFileID;
    private String CategoryType;
    private String DistrictName;
    private String EvidenceMaterial;
    private int FinalID;
    private String OutletAddress;
    private String OutletName;
    private String Revisit_Assign_Date;
    private String SealDateTime;
    private String SealType;
    private String SealedBy;
    private String SummonIssueDate;

    // COMPLAINT FIELDS
    private String PHC_RegistrationNo;
    private String Title;
    private String ComplaintDetail;
    private String ComplainantName;
    private String ComplainantAddress;
    private String ComplainantContactNo;
    private String DiaryNo;
    private String ComplaintDistrict;
    private String District;
    private String Comments;

    public NotificationItem() {}

    // 👉 Create constructor for Room


    public NotificationItem(int notificationId, String type, String message, String insertedDate, boolean isRead, String caseFileID, String categoryType, String districtName, String evidenceMaterial, int finalID, String outletAddress, String outletName, String revisit_Assign_Date, String sealDateTime, String sealType, String sealedBy, String summonIssueDate, String PHC_RegistrationNo, String title, String complaintDetail, String complainantName, String complainantAddress, String complainantContactNo, String diaryNo, String complaintDistrict, String district, String comments) {
        NotificationId = notificationId;
        Type = type;
        Message = message;
        InsertedDate = insertedDate;
        this.isRead = isRead;
        CaseFileID = caseFileID;
        CategoryType = categoryType;
        DistrictName = districtName;
        EvidenceMaterial = evidenceMaterial;
        FinalID = finalID;
        OutletAddress = outletAddress;
        OutletName = outletName;
        Revisit_Assign_Date = revisit_Assign_Date;
        SealDateTime = sealDateTime;
        SealType = sealType;
        SealedBy = sealedBy;
        SummonIssueDate = summonIssueDate;
        this.PHC_RegistrationNo = PHC_RegistrationNo;
        Title = title;
        ComplaintDetail = complaintDetail;
        ComplainantName = complainantName;
        ComplainantAddress = complainantAddress;
        ComplainantContactNo = complainantContactNo;
        DiaryNo = diaryNo;
        ComplaintDistrict = complaintDistrict;
        District = district;
        Comments = comments;
    }

    // GETTERS + SETTERS (Keep all existing ones)
    // Add new ones:
    public String getType() { return Type; }
    public void setType(String type) { Type = type; }

    public String getPHC_RegistrationNo() { return PHC_RegistrationNo; }
    public void setPHC_RegistrationNo(String p) { PHC_RegistrationNo = p; }

    public String getTitle() { return Title; }
    public void setTitle(String t) { Title = t; }

    public String getComplaintDetail() { return ComplaintDetail; }
    public void setComplaintDetail(String c) { ComplaintDetail = c; }

    public String getComplainantName() { return ComplainantName; }
    public void setComplainantName(String c) { ComplainantName = c; }

    public String getComplainantContactNo() { return ComplainantContactNo; }
    public void setComplainantContactNo(String c) { ComplainantContactNo = c; }

    public String getDiaryNo() { return DiaryNo; }
    public void setDiaryNo(String d) { DiaryNo = d; }

    public int getNotificationId() {
        return NotificationId;
    }

    public void setNotificationId(int notificationId) {
        NotificationId = notificationId;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getInsertedDate() {
        return InsertedDate;
    }

    public void setInsertedDate(String insertedDate) {
        InsertedDate = insertedDate;
    }

    public boolean isRead() {
        return isRead;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public void setRead(boolean read) {
        isRead = read;
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

    public String getComplainantAddress() {
        return ComplainantAddress;
    }

    public void setComplainantAddress(String complainantAddress) {
        ComplainantAddress = complainantAddress;
    }

    public String getComplaintDistrict() { return ComplaintDistrict; }
    public void setComplaintDistrict(String d) { ComplaintDistrict = d; }
}

