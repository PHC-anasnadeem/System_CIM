package com.phc.cim.DataElements;


public class DiaryEntry {
    private int finalID;
    private int aqcFileNo;
    private String comments;
    private String diaryNo;
    private String district;
    private String outletName;
    private String desealDate;

    public DiaryEntry(int finalID, int aqcFileNo, String comments, String diaryNo, String district, String outletName, String desealDate) {
        this.finalID = finalID;
        this.aqcFileNo = aqcFileNo;
        this.comments = comments;
        this.diaryNo = diaryNo;
        this.district = district;
        this.outletName = outletName;
        this.desealDate = desealDate;
    }

    public int getFinalID() {
        return finalID;
    }

    public void setFinalID(int finalID) {
        this.finalID = finalID;
    }

    public int getAqcFileNo() {
        return aqcFileNo;
    }

    public void setAqcFileNo(int aqcFileNo) {
        this.aqcFileNo = aqcFileNo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDiaryNo() {
        return diaryNo;
    }

    public void setDiaryNo(String diaryNo) {
        this.diaryNo = diaryNo;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getDesealDate() {
        return desealDate;
    }

    public void setDesealDate(String desealDate) {
        this.desealDate = desealDate;
    }
}
