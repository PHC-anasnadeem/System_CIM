package com.phc.cim.Activities.Notification;

/**
 * Model class for notifications fetched from C# API
 */
public class NotificationModel {

    // Common fields
    private int NotificationId;
    private String Type; // REVISIT or COMPLAINT
    private String Message;
    private String InsertedDate;
    private boolean isRead;

    // Revisit Fields
    private String CaseFileID;
    private int FinalID;
    private String CategoryType;
    private String SealType;
    private String SealedBy;
    private String SummonIssueDate;
    private String Quack_ContactNumber;
    private String Quack_CNIC;
    private String OutletName;
    private String OutletAddress;
    private String DistrictName;
    private String Comments;

    // Complaint Fields
    private String PHC_RegistrationNo;
    private String Title;
    private String ComplaintDetail;
    private String ComplainantName;
    private String ComplainantContactNo;
    private String DiaryNo;
    private String ComplainantAddress;
    private String District;

    public NotificationModel() {
    }


    public NotificationModel(int notificationId, String type, String message, String insertedDate, boolean isRead, String caseFileID, int finalID, String categoryType, String sealType, String sealedBy, String summonIssueDate, String quack_ContactNumber, String quack_CNIC, String outletName, String outletAddress, String districtName, String comments, String PHC_RegistrationNo, String title, String complaintDetail, String complainantName, String complainantContactNo, String diaryNo, String complainantAddress, String district) {
        NotificationId = notificationId;
        Type = type;
        Message = message;
        InsertedDate = insertedDate;
        this.isRead = isRead;
        CaseFileID = caseFileID;
        FinalID = finalID;
        CategoryType = categoryType;
        SealType = sealType;
        SealedBy = sealedBy;
        SummonIssueDate = summonIssueDate;
        Quack_ContactNumber = quack_ContactNumber;
        Quack_CNIC = quack_CNIC;
        OutletName = outletName;
        OutletAddress = outletAddress;
        DistrictName = districtName;
        Comments = comments;
        this.PHC_RegistrationNo = PHC_RegistrationNo;
        Title = title;
        ComplaintDetail = complaintDetail;
        ComplainantName = complainantName;
        ComplainantContactNo = complainantContactNo;
        DiaryNo = diaryNo;
        ComplainantAddress = complainantAddress;
        District = district;
    }

    // Getters and Setters for Common Fields
    public int getNotificationId() { return NotificationId; }
    public void setNotificationId(int notificationId) { NotificationId = notificationId; }

    public String getType() { return Type; }
    public void setType(String type) { Type = type; }

    public String getMessage() { return Message; }
    public void setMessage(String message) { Message = message; }

    public String getInsertedDate() { return InsertedDate; }
    public void setInsertedDate(String insertedDate) { InsertedDate = insertedDate; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    // Revisit Fields
    public String getCaseFileID() { return CaseFileID; }
    public void setCaseFileID(String caseFileID) { CaseFileID = caseFileID; }

    public int getFinalID() { return FinalID; }
    public void setFinalID(int finalID) { FinalID = finalID; }

    public String getCategoryType() { return CategoryType; }
    public void setCategoryType(String categoryType) { CategoryType = categoryType; }

    public String getSealType() { return SealType; }
    public void setSealType(String sealType) { SealType = sealType; }

    public String getSealedBy() { return SealedBy; }
    public void setSealedBy(String sealedBy) { SealedBy = sealedBy; }

    public String getSummonIssueDate() { return SummonIssueDate; }
    public void setSummonIssueDate(String summonIssueDate) { SummonIssueDate = summonIssueDate; }

    public String getQuack_ContactNumber() { return Quack_ContactNumber; }
    public void setQuack_ContactNumber(String quack_ContactNumber) { Quack_ContactNumber = quack_ContactNumber; }

    public String getQuack_CNIC() { return Quack_CNIC; }
    public void setQuack_CNIC(String quack_CNIC) { Quack_CNIC = quack_CNIC; }

    public String getOutletName() { return OutletName; }
    public void setOutletName(String outletName) { OutletName = outletName; }

    public String getOutletAddress() { return OutletAddress; }
    public void setOutletAddress(String outletAddress) { OutletAddress = outletAddress; }

    public String getDistrictName() { return DistrictName; }
    public void setDistrictName(String districtName) { DistrictName = districtName; }

    public String getComments() { return Comments; }
    public void setComments(String comments) { Comments = comments; }

    // Complaint Fields
    public String getPHC_RegistrationNo() { return PHC_RegistrationNo; }
    public void setPHC_RegistrationNo(String PHC_RegistrationNo) { this.PHC_RegistrationNo = PHC_RegistrationNo; }

    public String getTitle() { return Title; }
    public void setTitle(String title) { Title = title; }

    public String getComplaintDetail() { return ComplaintDetail; }
    public void setComplaintDetail(String complaintDetail) { ComplaintDetail = complaintDetail; }

    public String getComplainantName() { return ComplainantName; }
    public void setComplainantName(String complainantName) { ComplainantName = complainantName; }

    public String getComplainantContactNo() { return ComplainantContactNo; }
    public void setComplainantContactNo(String complainantContactNo) { ComplainantContactNo = complainantContactNo; }

    public String getDiaryNo() { return DiaryNo; }
    public void setDiaryNo(String diaryNo) { DiaryNo = diaryNo; }

    public String getComplainantAddress() { return ComplainantAddress; }
    public void setComplainantAddress(String complainantAddress) { ComplainantAddress = complainantAddress; }

    public String getDistrict() { return District; }
    public void setDistrict(String district) { District = district; }
}
