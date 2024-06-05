package com.phc.cim.DataElements;

public class NotificationItem {
    private int ComplaintsDetail_SEQ;
    private String title;
    private String OutletName;
    private String ComplainantAddress;
    private String ComplaintDate;
    private String ComplainantContactNo;
    private String AQC_ComplaintsSource_Desc;
    private String District;
    private String TehsilDesc;
    private String PHC_RegistrationNo;
    private String Comments;
    private String FinalID;
    private String TypeDesc;
    private String isRegWithPHC;
    private String User_Name;
    private String LastUpdatedBy;

    public NotificationItem(int complaintsDetail_SEQ, String title, String outletName, String complainantAddress, String complaintDate, String complainantContactNo, String AQC_ComplaintsSource_Desc, String district, String tehsilDesc, String PHC_RegistrationNo, String comments, String finalID, String typeDesc, String isRegWithPHC, String user_Name, String lastUpdatedBy) {
        ComplaintsDetail_SEQ = complaintsDetail_SEQ;
        this.title = title;
        OutletName = outletName;
        ComplainantAddress = complainantAddress;
        ComplaintDate = complaintDate;
        ComplainantContactNo = complainantContactNo;
        this.AQC_ComplaintsSource_Desc = AQC_ComplaintsSource_Desc;
        District = district;
        TehsilDesc = tehsilDesc;
        this.PHC_RegistrationNo = PHC_RegistrationNo;
        Comments = comments;
        FinalID = finalID;
        TypeDesc = typeDesc;
        this.isRegWithPHC = isRegWithPHC;
        User_Name = user_Name;
        LastUpdatedBy = lastUpdatedBy;
    }

    public int getComplaintsDetail_SEQ() {
        return ComplaintsDetail_SEQ;
    }

    public void setComplaintsDetail_SEQ(int complaintsDetail_SEQ) {
        ComplaintsDetail_SEQ = complaintsDetail_SEQ;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOutletName() {
        return OutletName;
    }

    public void setOutletName(String outletName) {
        OutletName = outletName;
    }

    public String getComplainantAddress() {
        return ComplainantAddress;
    }

    public void setComplainantAddress(String complainantAddress) {
        ComplainantAddress = complainantAddress;
    }

    public String getComplaintDate() {
        return ComplaintDate;
    }

    public void setComplaintDate(String complaintDate) {
        ComplaintDate = complaintDate;
    }

    public String getComplainantContactNo() {
        return ComplainantContactNo;
    }

    public void setComplainantContactNo(String complainantContactNo) {
        ComplainantContactNo = complainantContactNo;
    }

    public String getAQC_ComplaintsSource_Desc() {
        return AQC_ComplaintsSource_Desc;
    }

    public void setAQC_ComplaintsSource_Desc(String AQC_ComplaintsSource_Desc) {
        this.AQC_ComplaintsSource_Desc = AQC_ComplaintsSource_Desc;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getTehsilDesc() {
        return TehsilDesc;
    }

    public void setTehsilDesc(String tehsilDesc) {
        TehsilDesc = tehsilDesc;
    }

    public String getPHC_RegistrationNo() {
        return PHC_RegistrationNo;
    }

    public void setPHC_RegistrationNo(String PHC_RegistrationNo) {
        this.PHC_RegistrationNo = PHC_RegistrationNo;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getFinalID() {
        return FinalID;
    }

    public void setFinalID(String finalID) {
        FinalID = finalID;
    }

    public String getTypeDesc() {
        return TypeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        TypeDesc = typeDesc;
    }

    public String getIsRegWithPHC() {
        return isRegWithPHC;
    }

    public void setIsRegWithPHC(String isRegWithPHC) {
        this.isRegWithPHC = isRegWithPHC;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getLastUpdatedBy() {
        return LastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        LastUpdatedBy = lastUpdatedBy;
    }
}

