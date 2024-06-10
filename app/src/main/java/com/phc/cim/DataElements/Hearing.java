package com.phc.cim.DataElements;

public class Hearing {
    private String district;
    private String activeStatus;
    private String caseFileId;
    private String comments;
    private String committees;
    private String createDate;
    private int finalId;
    private String fineImposed;
    private String hearingScheduleDate;
    private String hearingStatusDesc;
    private Integer isFineImposed;
    private String outletAddress;
    private String outletName;

    public Hearing(String district,  String activeStatus, String caseFileId, String comments, String committees, String createDate, int finalId, String fineImposed, String hearingScheduleDate, String hearingStatusDesc, Integer isFineImposed, String outletAddress, String outletName) {
        this.district = district;
        this.activeStatus = activeStatus;
        this.caseFileId = caseFileId;
        this.comments = comments;
        this.committees = committees;
        this.createDate = createDate;
        this.finalId = finalId;
        this.fineImposed = fineImposed;
        this.hearingScheduleDate = hearingScheduleDate;
        this.hearingStatusDesc = hearingStatusDesc;
        this.isFineImposed = isFineImposed;
        this.outletAddress = outletAddress;
        this.outletName = outletName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getHearingScheduleDate() {
        return hearingScheduleDate;
    }

    public void setHearingScheduleDate(String hearingScheduleDate) {
        this.hearingScheduleDate = hearingScheduleDate;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getCaseFileId() {
        return caseFileId;
    }

    public void setCaseFileId(String caseFileId) {
        this.caseFileId = caseFileId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCommittees() {
        return committees;
    }

    public void setCommittees(String committees) {
        this.committees = committees;
    }



    public int getFinalId() {
        return finalId;
    }

    public void setFinalId(int finalId) {
        this.finalId = finalId;
    }

    public String getFineImposed() {
        return fineImposed;
    }

    public void setFineImposed(String fineImposed) {
        this.fineImposed = fineImposed;
    }


    public String getHearingStatusDesc() {
        return hearingStatusDesc;
    }

    public void setHearingStatusDesc(String hearingStatusDesc) {
        this.hearingStatusDesc = hearingStatusDesc;
    }

    public Integer getIsFineImposed() {
        return isFineImposed;
    }

    public void setIsFineImposed(Integer isFineImposed) {
        this.isFineImposed = isFineImposed;
    }

    public String getOutletAddress() {
        return outletAddress;
    }

    public void setOutletAddress(String outletAddress) {
        this.outletAddress = outletAddress;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }
}
