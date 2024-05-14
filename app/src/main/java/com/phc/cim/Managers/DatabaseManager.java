package com.phc.cim.Managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.phc.cim.DataElements.Action;
import com.phc.cim.DataElements.ActionType;
import com.phc.cim.DataElements.CouncilType;
import com.phc.cim.DataElements.Distance;
import com.phc.cim.DataElements.District;
import com.phc.cim.DataElements.Division;
import com.phc.cim.DataElements.OrgType;
import com.phc.cim.DataElements.PWSDistrict;
import com.phc.cim.DataElements.PWSOrgType;
import com.phc.cim.DataElements.RegStatus;
import com.phc.cim.DataElements.Role;
import com.phc.cim.DataElements.SectorType;
import com.phc.cim.DataElements.Tehsil;
import com.phc.cim.DataElements.UpdateStatL1;
import com.phc.cim.DataElements.UpdateStatL2;
import com.phc.cim.DataElements.subActionType;

import java.util.ArrayList;

/**
 * Created by PHC_ICT on 1/27/2018.
 */

public class DatabaseManager {


    private static final int DATABASE_VERSION = 14;
    private static final String DATABASE_NAME = "CIMAllDropDown.db";
    public static final String DISTRICT = "District";
    private static final String TAG_DISTRICT_ID = "Dis_District_id";
    private static final String TAG_DISTRICT_DIV = "Dis_Division";
    private static final String TAG_DISTRICT = "Dis_District";
    public static final String TEHSIL = "Tehsil";
    private static final String TAG_TEHSIL_ID = "Teh_Tehsil_id";
    private static final String TAG_TDISTRICT = "Teh_District";
    private static final String TAG_TEHSIL = "Teh_Tehsil";
    public static final String SECTORTYPE = "SectorType";
    private static final String TAG_SECTORTYPE_ID = "Sec_SectorType_id";
    private static final String TAG_SECTORTYPE = "Sec_SectorType";
    public static final String COUNCILTYPE = "CouncilType";
    private static final String TAG_COUNCILTYPE_ID = "Coun_CouncilType_id";
    private static final String TAG_COUNCILTYPE = "Coun_CouncilType";
    public static final String ORGTYPE = "OrgType";
    private static final String TAG_ORGTYPE_ID = "Org_OrgType_id";
    private static final String TAG_ORGTYPE = "Org_OrgType";
    public static final String UPDATESTATL1 = "Updatestatus";
    private static final String TAG_UpdatedStatus_ID = "Update_Status_id";
    private static final String TAG_UpdatedStatus = "Update_Status";
    public static final String UPDATESTATL2 = "Substatus";
    private static final String TAG_SubStatus_ID = "Sub_Status_id";
    private static final String TAG_SubStatus = "Sub_Status";
    public static final String DISTANCE = "distance";
    private static final String TAG_Distance_ID = "Distance_id";
    private static final String TAG_Distance = "Distance";
    public static final String REGSTATUS = "Regstatus";
    private static final String TAG_RegStatus_ID = "Reg_Status_id";
    private static final String TAG_RegStatus = "Reg_Status";
    public static final String ACTIONS = "Actions";
    private static final String TAG_Action_ID = "Action_id";
    private static final String TAG_Action = "Actioncol";

    public static final String ACTIONSTYPE = "Actionstype";
    private static final String TAG_ActionType_ID = "Actiontype_id";
    private static final String TAG_ActionType = "Actiontype";
    private static final String TAG_FIR = "Fir_id";
    private static final String TAG_Cat_Def = "Cat_def";
    private static final String TAG_CatRole_ID = "Cat_Roleid";
    private static final String TAG_CatStatus_ID = "Cat_Statusid";

    public static final String SUBACTIONSTYPE = "Subactions";
    private static final String TAG_SubActionType_ID = "Subactiontype_id";
    private static final String TAG_Sub_ActionType = "Subactiontype";
    private static final String TAG_Sub_catActionID = "Subcatactiontype";
    private static final String TAG_Sub_Roleid = "Sub_Roleid";
    private static final String TAG_Sub_statusid = "Sub_Statusid";

    public static final String DIVISION = "Division";
    private static final String TAG_Division_ID = "division_id";
    private static final String TAG_Division = "divisions";

    public static final String ROLE = "Role";
    private static final String TAG_Role_ID = "role_id";
    private static final String TAG_Role = "role_name";

    public static final String PWSDISTRICT = "PWSdistrict";
    private static final String TAG_PWSdistrict_ID = "PWSdistrict_id";
    private static final String TAG_PWSdistrict = "PWSdistrictName";

    public static final String PWSORGTYPE = "PWSorg";
    private static final String TAG_PWSorg_ID = "PWSorg_id";
    private static final String TAG_PWSorg = "PWSorgType";

    DatabaseHelper openHelper;
    Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {

        this.context = context;
        openHelper = new DatabaseHelper(context);
        database = openHelper.getWritableDatabase();

    }

    public void saveDistrict(District district) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_DISTRICT_ID, district.getDistrict_id());
        contentValues.put(TAG_DISTRICT_DIV, district.getDistrict_div());
        contentValues.put(TAG_DISTRICT, district.getDistrict());
        // database.update(DISTRICT, contentValues, "Dis_District_id="+ district.getDistrict_id(), null);
        database.insert(DISTRICT, null, contentValues);

    }

    public void saveTehsil(Tehsil tehsil) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_TEHSIL_ID, tehsil.getTehsil_id());
        contentValues.put(TAG_TDISTRICT, tehsil.getTdistrict());
        contentValues.put(TAG_TEHSIL, tehsil.getTehsil());
        //database.update(TEHSIL, contentValues, "Teh_Tehsil_id="+ tehsil.getTehsil_id(), null);
        database.insert(TEHSIL, null, contentValues);

    }

    public void saveSectorType(SectorType sectorType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_SECTORTYPE_ID, sectorType.getSectorType_id());
        contentValues.put(TAG_SECTORTYPE, sectorType.getSectorType());
        database.update(SECTORTYPE, contentValues, "Sec_SectorType_id=" + sectorType.getSectorType_id(), null);
        database.insert(SECTORTYPE, null, contentValues);

    }

    public void saveCouncilType(CouncilType councilType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_COUNCILTYPE_ID, councilType.getSectorType_id());
        contentValues.put(TAG_COUNCILTYPE, councilType.getCouncilType());
        // database.update(COUNCILTYPE, contentValues, "Coun_CouncilType_id="+ councilType.getSectorType_id(), null);
        database.insert(COUNCILTYPE, null, contentValues);

    }

    public void saveOrgType(OrgType orgType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_ORGTYPE_ID, orgType.getOrgType_id());
        contentValues.put(TAG_ORGTYPE, orgType.getOrgType());
        database.update(ORGTYPE, contentValues, "Org_OrgType_id=" + orgType.getOrgType_id(), null);
        database.insert(ORGTYPE, null, contentValues);

    }

    public void saveUpdateStatus(UpdateStatL1 updateStatL1) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_UpdatedStatus_ID, updateStatL1.getUpdatedStatL1_Id());
        contentValues.put(TAG_UpdatedStatus, updateStatL1.getUpdatedStatL1());
        // database.update(UPDATESTATL1, contentValues, "Update_Status_id="+ updateStatL1.getUpdatedStatL1_Id(), null);
        database.insert(UPDATESTATL1, null, contentValues);
    }

    public void saveSubStatus(UpdateStatL2 updateStatL2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_SubStatus_ID, updateStatL2.getUpdatedStatL2_Id());
        contentValues.put(TAG_SubStatus, updateStatL2.getUpdatedStatL2());
        database.update(UPDATESTATL2, contentValues, "Sub_Status_id=" + updateStatL2.getUpdatedStatL2_Id(), null);
        database.insert(UPDATESTATL2, null, contentValues);

    }

    public void saveDistance(Distance distance) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_Distance_ID, distance.getDistance_id());
        contentValues.put(TAG_Distance, distance.getDistance());
        //database.update(UPDATESTATL2, contentValues, "Sub_Status_id="+ updateStatL2.getUpdatedStatL2_Id(), null);
        database.insert(DISTANCE, null, contentValues);

    }

    public void saveRegStatus(RegStatus regStatus) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_RegStatus_ID, regStatus.getRegStatus_id());
        contentValues.put(TAG_RegStatus, regStatus.getRegStatus());
        //  database.update(UPDATESTATL2, contentValues, "Sub_Status_id="+ updateStatL2.getUpdatedStatL2_Id(), null);
        database.insert(REGSTATUS, null, contentValues);

    }

    public void saveAction(Action action) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_Action_ID, action.getAction_id());
        contentValues.put(TAG_Action, action.getAction());
        //database.update(UPDATESTATL2, contentValues, "Sub_Status_id="+ updateStatL2.getUpdatedStatL2_Id(), null);
        database.insert(ACTIONS, null, contentValues);

    }

    public void saveActiontype(ActionType actiontype) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_ActionType_ID, actiontype.getActionType_Id());
        contentValues.put(TAG_ActionType, actiontype.getActionType());
        contentValues.put(TAG_FIR, actiontype.getFIRAssociated());
        contentValues.put(TAG_Cat_Def, actiontype.getCatDefinition());
        contentValues.put(TAG_CatRole_ID, actiontype.getCatRoleID());
        contentValues.put(TAG_CatStatus_ID, actiontype.getCatStatusID());

        //database.update(UPDATESTATL2, contentValues, "Sub_Status_id="+ updateStatL2.getUpdatedStatL2_Id(), null);
        database.insert(ACTIONSTYPE, null, contentValues);

    }

    public void savesubActiontype(subActionType subActionType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_SubActionType_ID, subActionType.getSubActionType_Id());
        contentValues.put(TAG_Sub_catActionID, subActionType.getSubcatActionType_Id());
        contentValues.put(TAG_Sub_ActionType, subActionType.getSubactionType());
        contentValues.put(TAG_Sub_Roleid, subActionType.getSubRoleID());
        contentValues.put(TAG_Sub_statusid, subActionType.getSubStatusID());

        //database.update(UPDATESTATL2, contentValues, "Sub_Status_id="+ updateStatL2.getUpdatedStatL2_Id(), null);
        database.insert(SUBACTIONSTYPE, null, contentValues);

    }

    public void savedivision(Division division) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_Division_ID, division.getDivision_id());
        contentValues.put(TAG_Division, division.getDivision());
        //database.update(UPDATESTATL2, contentValues, "Sub_Status_id="+ updateStatL2.getUpdatedStatL2_Id(), null);
        database.insert(DIVISION, null, contentValues);

    }

    public void saveRole(Role role) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_Role_ID, role.getRole_Id());
        contentValues.put(TAG_Role, role.getRoleName());
        // database.update(UPDATESTATL2, contentValues, "Sub_Status_id="+ updateStatL2.getUpdatedStatL2_Id(), null);
        database.insert(ROLE, null, contentValues);

    }

    public void savePWSdistrict(PWSDistrict pwsDistrict) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_PWSdistrict_ID, pwsDistrict.getDistrict_id());
        contentValues.put(TAG_PWSdistrict, pwsDistrict.getDistrict());
        //database.update(UPDATESTATL2, contentValues, "Sub_Status_id="+ updateStatL2.getUpdatedStatL2_Id(), null);
        database.insert(PWSDISTRICT, null, contentValues);

    }

    public void savePWSOrg(PWSOrgType pwsOrgType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG_PWSorg_ID, pwsOrgType.getOrgType_id());
        contentValues.put(TAG_PWSorg, pwsOrgType.getOrgType());
        //database.update(UPDATESTATL2, contentValues, "Sub_Status_id="+ updateStatL2.getUpdatedStatL2_Id(), null);
        database.insert(PWSORGTYPE, null, contentValues);

    }


    public ArrayList<Division> getDivision() {
        String selectQuery = "SELECT * FROM " + DIVISION;

        database = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        ArrayList<Division> divisions = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                divisions.add(new Division(
                        cursor.getString(cursor.getColumnIndex("division_id")),
                        cursor.getString(cursor.getColumnIndex("divisions"))


                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return divisions;
    }

//   public ArrayList<District> getDistrictList(String division) {
//       String selectQuery=null;
//       if(division.equals("Please Select")) {
//           selectQuery = "SELECT * FROM " + DISTRICT;
//       }
//       else {
//           selectQuery = "SELECT * FROM " + DISTRICT + " WHERE Dis_Division='" + division + "' UNION SELECT * FROM "  + DISTRICT +  " WHERE Dis_District='Please Select'" ;
//
//       }
//
//        database  = openHelper.getReadableDatabase();
//        Cursor cursor = database.rawQuery(selectQuery, null);
//        ArrayList<District> districts =new ArrayList<>();
//        if (cursor.moveToFirst()) {
//            do {
//                districts.add(new District(
//                        cursor.getString(cursor.getColumnIndex("Dis_District_id")),
//                        cursor.getString(cursor.getColumnIndex("Dis_Division")),
//                        cursor.getString(cursor.getColumnIndex("Dis_District"))
//
//
//                ));
//
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return districts;
//    }

    public ArrayList<District> getDistrictList(String division) {
        String selectQuery=null;
        if(division.equals("Please Select")) {
            selectQuery = "SELECT * FROM " + DISTRICT;
        }
        else {
            selectQuery = "SELECT * FROM " + DISTRICT + " WHERE Dis_Division='" + division + "' UNION SELECT * FROM "  + DISTRICT +  " WHERE Dis_District='Please Select'" ;

        }

        database  = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        ArrayList<District> districts =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                districts.add(new District(
                        cursor.getString(cursor.getColumnIndex("Dis_District_id")),
                        cursor.getString(cursor.getColumnIndex("Dis_Division")),
                        cursor.getString(cursor.getColumnIndex("Dis_District"))


                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return districts;
    }

//    public ArrayList<District> getDistrictList(String division) {
//        String selectQuery = null;
//        ArrayList<District> districts = new ArrayList<>();
//
//        districts.add(new District("0", "Division", "Please Select"));
//        if (division.equals("Please Select")) {
//            // Hardcoded list of districts
//            districts.add(new District("1", "Rawalpindi", "Attock"));
//            districts.add(new District("2", "Bahawalpur", "Bahawalnagar"));
//            districts.add(new District("3", "Bahawalpur", "Bahawalpur"));
//            districts.add(new District("4", "Sargodha", "Bhakkar"));
//            districts.add(new District("5", "Rawalpindi", "Chakwal"));
//            districts.add(new District("6", "Faisalabad", "Chiniot"));
//            districts.add(new District("7", "D.G Khan", "Dera Ghazi Khan"));
//            districts.add(new District("8", "Faisalabad", "Faisalabad"));
//            districts.add(new District("9", "Gujranwala", "Gujranwala"));
//            districts.add(new District("10", "Gujranwala", "Gujrat"));
//            districts.add(new District("11", "Gujranwala", "Hafizabad"));
//            districts.add(new District("12", "Faisalabad", "Jhang"));
//            districts.add(new District("13", "Rawalpindi", "Jhelum"));
//            districts.add(new District("14", "Lahore", "Kasur"));
//            districts.add(new District("15", "Multan", "Khanewal"));
//            districts.add(new District("16", "Sargodha", "Khushab"));
//            districts.add(new District("17", "Lahore", "Lahore"));
//            districts.add(new District("18", "D.G Khan", "Layyah"));
//            districts.add(new District("19", "Multan", "Lodhran"));
//            districts.add(new District("20", "Division1", "Mandi Bahauddin"));
//            districts.add(new District("21", "Sargodha", "Mianwali"));
//            districts.add(new District("22", "Multan", "Multan"));
//            districts.add(new District("23", "D.G Khan", "Muzaffargarh"));
//            districts.add(new District("24", "Gujranwala", "Narowal"));
//            districts.add(new District("25", "Lahore", "Nankana Sahib"));
//            districts.add(new District("26", "Sahiwal", "Okara"));
//            districts.add(new District("27", "Sahiwal", "Pakpattan"));
//            districts.add(new District("28", "Bahawalpur", "Rahim Yar Khan"));
//            districts.add(new District("29", "D.G Khan", "Rajanpur"));
//            districts.add(new District("30", "Rawalpindi", "Rawalpindi"));
//            districts.add(new District("31", "Sahiwal", "Sahiwal"));
//            districts.add(new District("32", "Sargodha", "Sargodha"));
//            districts.add(new District("33", "Lahore", "Sheikhupura"));
//            districts.add(new District("34", "Gujranwala", "Sialkot"));
//            districts.add(new District("35", "Faisalabad", "Toba Tek Singh"));
//            districts.add(new District("36", "Multan", "Vehari"));
//            districts.add(new District("37", "Other", "Other"));
//        } else {
//            selectQuery = "SELECT * FROM " + DISTRICT + " WHERE Dis_Division='" + division + "' UNION SELECT * FROM " + DISTRICT + " WHERE Dis_District='Please Select'";
//            database = openHelper.getReadableDatabase();
//            Cursor cursor = database.rawQuery(selectQuery, null);
//
//            if (cursor.moveToFirst()) {
//                do {
//                    districts.add(new District(
//                            cursor.getString(cursor.getColumnIndex("Dis_District_id")),
//                            cursor.getString(cursor.getColumnIndex("Dis_Division")),
//                            cursor.getString(cursor.getColumnIndex("Dis_District"))
//                    ));
//
//                } while (cursor.moveToNext());
//            }
//
//            cursor.close();
//        }
//
//        return districts;
//    }

    public ArrayList<Tehsil> getTehsilList(String district) {
        String selectQuery=null;
        if(district.equals("Please Select")){
            selectQuery = "SELECT * FROM " + TEHSIL;
        }
        else {
            selectQuery = "SELECT * FROM " + TEHSIL + " WHERE Teh_District='" + district + "' UNION SELECT * FROM "  + TEHSIL +  " WHERE Teh_Tehsil='Please Select'" ;
        }

        database  = openHelper.getReadableDatabase();
        Cursor cursor      = database.rawQuery(selectQuery, null);
        ArrayList<Tehsil> tehsils =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                tehsils.add(new Tehsil(
                        cursor.getString(cursor.getColumnIndex("Teh_Tehsil_id")),
                        cursor.getString(cursor.getColumnIndex("Teh_District")),
                        cursor.getString(cursor.getColumnIndex("Teh_Tehsil"))

                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return tehsils;
    }
//
//    public ArrayList<Tehsil> getTehsilList(String district) {
//        String selectQuery = null;
//        ArrayList<Tehsil> tehsils = new ArrayList<>();
//
//
////        tehsils.add(new Tehsil("0", "District", "Please Select District First"));
//        if (district.equals("Please Select District")) {
//
//        }else if (district.equals("Attock")) {
//                tehsils.add(new Tehsil("1", "Attock", "Hazro"));
//                tehsils.add(new Tehsil("2", "Attock", "Pindi Gheb"));
//                tehsils.add(new Tehsil("3", "Attock", "Fateh Jang"));
//                tehsils.add(new Tehsil("4", "Attock", "Jand"));
//                tehsils.add(new Tehsil("5", "Attock", "Taxila"));
//                tehsils.add(new Tehsil("6", "Attock", "Hassan Abdal"));
//                tehsils.add(new Tehsil("7", "Attock", "Attock"));
//                // Add more Tehsils for Attock district as needed
//            } else if (district.equals("Bahawalnagar")) {
//                tehsils.add(new Tehsil("8", "Bahawalnagar", "Bahawalnagar"));
//                tehsils.add(new Tehsil("9", "Bahawalnagar", "Chishtian"));
//                tehsils.add(new Tehsil("10", "Bahawalnagar", "Fort Abbas"));
//                tehsils.add(new Tehsil("11", "Bahawalnagar", "Haroonabad"));
//                tehsils.add(new Tehsil("12", "Bahawalnagar", "Minchanabad"));
//                // Add more Tehsils for Bahawalnagar district as needed
//            } else if (district.equals("Bahawalpur")) {
//            tehsils.add(new Tehsil("13", "Bahawalpur", "Ahmedpur Sharqia"));
//            tehsils.add(new Tehsil("14", "Bahawalpur", "Bahawalpur City"));
//            tehsils.add(new Tehsil("15", "Bahawalpur", "Hasilpur"));
//            tehsils.add(new Tehsil("16", "Bahawalpur", "Khairpur"));
//            tehsils.add(new Tehsil("17", "Bahawalpur", "Tamewali"));
//            tehsils.add(new Tehsil("18", "Bahawalpur", "Yazman"));
//        }else if (district.equals("Bhakkar")) {
//            tehsils.add(new Tehsil("19", "Bhakkar", "Bhakkar"));
//            tehsils.add(new Tehsil("20", "Bhakkar", "Darya Khan"));
//            tehsils.add(new Tehsil("21", "Bhakkar", "Kaloorkot"));
//            tehsils.add(new Tehsil("22", "Bhakkar", "Bugallon"));
//            tehsils.add(new Tehsil("23", "Bhakkar", "Mankera"));
//            // Add more Tehsils for Bahawalnagar district as needed
//        }else if (district.equals("Chakwal")) {
//            tehsils.add(new Tehsil("24", "Chakwal", "Choa Saidan Shah"));
//            tehsils.add(new Tehsil("25", "Chakwal", "Talagang"));
//            tehsils.add(new Tehsil("26", "Chakwal", "Kallar Kahar"));
//            tehsils.add(new Tehsil("27", "Chakwal", "Chakwal"));
//        }else if (district.equals("Chiniot")) {
//            tehsils.add(new Tehsil("28", "Chiniot", "Lalian"));
//            tehsils.add(new Tehsil("29", "Chiniot", "Chiniot"));
//            tehsils.add(new Tehsil("30", "Chiniot", "Bhowana"));
//        }else if (district.equals("Dera Ghazi Khan")) {
//            tehsils.add(new Tehsil("31", "Dera Ghazi Khan", "Dera Ghazi Khan"));
//            tehsils.add(new Tehsil("32", "Dera Ghazi Khan", "Taunsa Sharif"));
//        }else if (district.equals("Faisalabad")) {
//            tehsils.add(new Tehsil("33", "Faisalabad", "Faisalabad Saddar"));
//            tehsils.add(new Tehsil("34", "Faisalabad", "Chak Jhumra"));
//            tehsils.add(new Tehsil("35", "Faisalabad", "Samundri"));
//            tehsils.add(new Tehsil("36", "Faisalabad", "Jaranwala"));
//            tehsils.add(new Tehsil("37", "Faisalabad", "Tandlianwala"));
//        }else if (district.equals("Gujranwala")) {
//            tehsils.add(new Tehsil("38", "Gujranwala", "Gujranwala"));
//            tehsils.add(new Tehsil("39", "Gujranwala", "Kamoki"));
//            tehsils.add(new Tehsil("40", "Gujranwala", "Nowshera"));
//            tehsils.add(new Tehsil("41", "Gujranwala", "Virkan"));
//            tehsils.add(new Tehsil("42", "Gujranwala", "Wazirabad"));
//        }else if (district.equals("Gujrat")) {
//            tehsils.add(new Tehsil("43", "Gujrat", "Gujrat"));
//            tehsils.add(new Tehsil("44", "Gujrat", "Kharian"));
//            tehsils.add(new Tehsil("45", "Gujrat", "Sarai Alamgir"));
//        }else if (district.equals("Hafizabad")) {
//            tehsils.add(new Tehsil("46", "Hafizabad", "Hafizabad"));
//            tehsils.add(new Tehsil("47", "Hafizabad", "Pindi Bhattian"));
//        }else if (district.equals("Jhang")) {
//            tehsils.add(new Tehsil("48", "Jhang", "18 Hazari"));
//            tehsils.add(new Tehsil("49", "Jhang", "Jhang"));
//            tehsils.add(new Tehsil("50", "Jhang", "Ahmad Pur Sial"));
//            tehsils.add(new Tehsil("51", "Jhang", "Shorkot"));
//        }else if (district.equals("Jhelum")) {
//            tehsils.add(new Tehsil("52", "Jhelum", "Sohawa"));
//            tehsils.add(new Tehsil("53", "Jhelum", "Pind Dadan Khan"));
//            tehsils.add(new Tehsil("54", "Jhelum", "Jhelum"));
//            tehsils.add(new Tehsil("55", "Jhelum", "Dina"));
//        }else if (district.equals("Kasur")) {
//            tehsils.add(new Tehsil("56", "Kasur", "Chunian"));
//            tehsils.add(new Tehsil("57", "Kasur", "Kasur"));
//            tehsils.add(new Tehsil("58", "Kasur", "Pattoki"));
//        }else if (district.equals("Khanewal")) {
//            tehsils.add(new Tehsil("59", "Kasur", "Chunian"));
//            tehsils.add(new Tehsil("60", "Kasur", "Kasur"));
//            tehsils.add(new Tehsil("61", "Kasur", "Pattoki"));
//        }else if (district.equals("Khushab")) {
//            tehsils.add(new Tehsil("62", "Khushab", "Khushab"));
//            tehsils.add(new Tehsil("63", "Khushab", "Noorpur"));
//            tehsils.add(new Tehsil("64", "Khushab", "Thal"));
//        }else if (district.equals("Lahore")) {
//            tehsils.add(new Tehsil("65", "Lahore", "Lahore City"));
//            tehsils.add(new Tehsil("66", "Lahore", "Lahore Cantt"));
//            tehsils.add(new Tehsil("67", "Lahore", "Lahore Sadar"));
//            tehsils.add(new Tehsil("68", "Lahore", "Model Town"));
//            tehsils.add(new Tehsil("69", "Lahore", "Shalamar"));
//        }else if (district.equals("Layyah")) {
//            tehsils.add(new Tehsil("70", "Layyah", "Layyah"));
//            tehsils.add(new Tehsil("71", "Layyah", "Chaubara"));
//            tehsils.add(new Tehsil("72", "Layyah", "Karor"));
//            tehsils.add(new Tehsil("73", "Layyah", "Chowk Azam"));
//            tehsils.add(new Tehsil("74", "Layyah", "Fatehpur"));
//        }else if (district.equals("Lodhran")) {
//            tehsils.add(new Tehsil("75", "Lodhran", "Dunyapur"));
//            tehsils.add(new Tehsil("76", "Lodhran", "Karor Pacca"));
//            tehsils.add(new Tehsil("77", "Lodhran", "Lodhran"));
//        }else if (district.equals("Mandi Bahauddin")) {
//            tehsils.add(new Tehsil("78", "Mandi Bahauddin", "Malakwal"));
//            tehsils.add(new Tehsil("79", "Mandi Bahauddin", "Mandi Bahauddin"));
//            tehsils.add(new Tehsil("80", "Mandi Bahauddin", "Westfalia"));
//        }else if (district.equals("Mianwali")) {
//            tehsils.add(new Tehsil("81", "Mianwali", "Isakhel"));
//            tehsils.add(new Tehsil("82", "Mianwali", "Mianwali"));
//            tehsils.add(new Tehsil("83", "Mianwali", "Piplan"));
//        }else if (district.equals("Multan")) {
//            tehsils.add(new Tehsil("84", "Multan", "Multan Sadar"));
//            tehsils.add(new Tehsil("85", "Multan", "Dunyapur"));
//            tehsils.add(new Tehsil("86", "Multan", "Shuja Abad"));
//            tehsils.add(new Tehsil("87", "Multan", "Jalal Pur Pirwala"));
//            tehsils.add(new Tehsil("88", "Multan", "Multan City"));
//            tehsils.add(new Tehsil("89", "Multan", "Lodhran"));
//        }else if (district.equals("Muzaffargarh")) {
//            tehsils.add(new Tehsil("90", "Muzaffargarh", "Alipur"));
//            tehsils.add(new Tehsil("91", "Muzaffargarh", "Jatoi"));
//            tehsils.add(new Tehsil("92", "Muzaffargarh", "Kot Addu"));
//            tehsils.add(new Tehsil("93", "Muzaffargarh", "Muzaffargarh"));
//        }else if (district.equals("Narowal")) {
//            tehsils.add(new Tehsil("94", "Narowal", "Narowal"));
//            tehsils.add(new Tehsil("95", "Narowal", "Shakargarh"));
//            tehsils.add(new Tehsil("96", "Narowal", "Zafarwal"));
//        }else if (district.equals("Nankana Sahib")) {
//            tehsils.add(new Tehsil("97", "Nankana Sahib", "Shahkot"));
//            tehsils.add(new Tehsil("98", "Nankana Sahib", "Sangla Hill"));
//            tehsils.add(new Tehsil("99", "Nankana Sahib", "Nankana"));
//        }else if (district.equals("Okara")) {
//            tehsils.add(new Tehsil("100", "Okara", "Okara"));
//            tehsils.add(new Tehsil("101", "Okara", "Renala Khurd"));
//            tehsils.add(new Tehsil("102", "Okara", "Depalpur"));
//        }else if (district.equals("Pakpattan")) {
//            tehsils.add(new Tehsil("103", "Pakpattan", "Pakpattan"));
//            tehsils.add(new Tehsil("104", "Pakpattan", "Arifwala"));
//        }else if (district.equals("Rahim Yar Khan")) {
//            tehsils.add(new Tehsil("105", "Rahim Yar Khan", "Khanpur"));
//            tehsils.add(new Tehsil("106", "Rahim Yar Khan", "Liaquatpur"));
//            tehsils.add(new Tehsil("107", "Rahim Yar Khan", "Rahim Yar Khan"));
//            tehsils.add(new Tehsil("108", "Rahim Yar Khan", "Sadiqabad"));
//        }else if (district.equals("Rajanpur")) {
//            tehsils.add(new Tehsil("109", "Rajanpur", "Jampur"));
//            tehsils.add(new Tehsil("110", "Rajanpur", "Rajanpur"));
//            tehsils.add(new Tehsil("111", "Rajanpur", "Rojhan"));
//        }else if (district.equals("Rawalpindi")) {
//            tehsils.add(new Tehsil("112", "Rawalpindi", "Rawalpindi"));
//            tehsils.add(new Tehsil("113", "Rawalpindi", "Muree"));
//            tehsils.add(new Tehsil("114", "Rawalpindi", "NULL"));
//            tehsils.add(new Tehsil("115", "Rawalpindi", "Taxila"));
//            tehsils.add(new Tehsil("116", "Rawalpindi", "Kotli Sattian"));
//            tehsils.add(new Tehsil("117", "Rawalpindi", "Gujar Khan"));
//            tehsils.add(new Tehsil("118", "Rawalpindi", "Kallar Syedan"));
//            tehsils.add(new Tehsil("119", "Rawalpindi", "Kahuta"));
//        }else if (district.equals("Sahiwal")) {
//            tehsils.add(new Tehsil("120", "Sahiwal", "Chichawatni"));
//            tehsils.add(new Tehsil("121", "Sahiwal", "Sahiwal"));
//        }else if (district.equals("Sargodha")) {
//            tehsils.add(new Tehsil("122", "Sargodha", "Bhalwal"));
//            tehsils.add(new Tehsil("123", "Sargodha", "Sahiwal"));
//            tehsils.add(new Tehsil("124", "Sargodha", "Sargodha"));
//            tehsils.add(new Tehsil("125", "Sargodha", "Bhera"));
//            tehsils.add(new Tehsil("126", "Sargodha", "Kot momin"));
//            tehsils.add(new Tehsil("127", "Sargodha", "Shahpur"));
//            tehsils.add(new Tehsil("128", "Sargodha", "Silanwali"));
//        }else if (district.equals("Sheikhupura")) {
//            tehsils.add(new Tehsil("129", "Sheikhupura", "Sharaqpur"));
//            tehsils.add(new Tehsil("130", "Sheikhupura", "Muridke"));
//            tehsils.add(new Tehsil("131", "Sheikhupura", "Sheikhupura"));
//            tehsils.add(new Tehsil("132", "Sheikhupura", "Ferozwala"));
//            tehsils.add(new Tehsil("133", "Sheikhupura", "Safdarabad"));
//        }else if (district.equals("Sialkot")) {
//            tehsils.add(new Tehsil("134", "Sialkot", "Daska"));
//            tehsils.add(new Tehsil("135", "Sialkot", "Pasrur"));
//            tehsils.add(new Tehsil("136", "Sialkot", "Sambrial"));
//            tehsils.add(new Tehsil("137", "Sialkot", "Sialkot"));
//        }else if (district.equals("Toba Tek Singh")) {
//            tehsils.add(new Tehsil("138", "Toba Tek Singh", "Gojra"));
//            tehsils.add(new Tehsil("139", "Toba Tek Singh", "Kamalia"));
//            tehsils.add(new Tehsil("140", "Toba Tek Singh", "Toba Tek Singh"));
//        }else if (district.equals("Vehari")) {
//            tehsils.add(new Tehsil("141", "Vehari", "Mailsi"));
//            tehsils.add(new Tehsil("142", "Vehari", "Vehari"));
//            tehsils.add(new Tehsil("143", "Vehari", "Burewala"));
//        }else if (district.equals("Other")) {
//            tehsils.add(new Tehsil("144", "Other", "Other Tehsil"));
//        }
//        else {
//            selectQuery = "SELECT * FROM " + TEHSIL + " WHERE Teh_District='" + district + "' UNION SELECT * FROM " + TEHSIL + " WHERE Teh_Tehsil='Please Select'";
//
//
//            database = openHelper.getReadableDatabase();
//            try (Cursor cursor = database.rawQuery(selectQuery, null)) {
//
//                if (cursor.moveToFirst()) {
//                    do {
//                        tehsils.add(new Tehsil(
//                                cursor.getString(cursor.getColumnIndex("Teh_Tehsil_id")),
//                                cursor.getString(cursor.getColumnIndex("Teh_District")),
//                                cursor.getString(cursor.getColumnIndex("Teh_Tehsil"))
//
//                        ));
//
//                    } while (cursor.moveToNext());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }
//        }
//        return tehsils;
//    }


    public String getActionID(String action) {
        String selectQuery=null;


            selectQuery = "SELECT * FROM " + ACTIONS + " WHERE Actioncol='" + action+"'";


        database  = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        String action_id=null;
        if(cursor.moveToFirst() || cursor.getCount() >= -1) {
            do {
                action_id = cursor.getString(cursor.getColumnIndex("Action_id"));

            } while (cursor.moveToNext());
        }
        cursor.close();

        return action_id;
    }

    public ArrayList<SectorType> getSectorTypeList() {
        String selectQuery = null;
        ArrayList<SectorType> sectorTypes = new ArrayList<>();

        sectorTypes.add(new SectorType("0", "Please Select"));

        if (sectorTypes.get(0).getSectorType().equals("Please Select")) {
            // Populate sectorTypes for the "Please Select" case
            sectorTypes.add(new SectorType("1", "Private"));
            sectorTypes.add(new SectorType("2", "Public"));
        } else {
            selectQuery = "SELECT * FROM " + SECTORTYPE;

            database = openHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    sectorTypes.add(new SectorType(
                            cursor.getString(cursor.getColumnIndex("Sec_SectorType_id")),
                            cursor.getString(cursor.getColumnIndex("Sec_SectorType"))
                    ));

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return sectorTypes;
    }

    public ArrayList<CouncilType> getCouncilTypeList() {
        String selectQuery = "SELECT * FROM " + COUNCILTYPE;
        database  = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        ArrayList<CouncilType> councilTypes =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                councilTypes.add(new CouncilType(
                        cursor.getString(cursor.getColumnIndex("Coun_CouncilType_id")),
                        cursor.getString(cursor.getColumnIndex("Coun_CouncilType"))


                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return councilTypes;
    }

    public ArrayList<OrgType> getOrgTypeList() {
        String selectQuery = "SELECT * FROM " + ORGTYPE;
        database  = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        ArrayList<OrgType> orgTypes =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                orgTypes.add(new OrgType(
                        cursor.getString(cursor.getColumnIndex("Org_OrgType_id")),
                        cursor.getString(cursor.getColumnIndex("Org_OrgType"))


                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return orgTypes;
    }

//    public ArrayList<OrgType> getOrgTypeList() {
//        ArrayList<OrgType> orgTypes = new ArrayList<>();
//
//        // Add "Please Select" option
//        orgTypes.add(new OrgType("0", "Please Select"));
//
//        if (orgTypes.get(0).getOrgType().equals("Please Select")) {
//            orgTypes.add(new OrgType("1", "Acupuncturist"));
//            orgTypes.add(new OrgType("2", "BHU"));
//            orgTypes.add(new OrgType("3", "Collection Center"));
//            orgTypes.add(new OrgType("4", "Cosmetic Surgery Clinic"));
//            orgTypes.add(new OrgType("5", "Dental Hygienist"));
//            orgTypes.add(new OrgType("6", "Dentist"));
//            orgTypes.add(new OrgType("7", "DHQ"));
//            orgTypes.add(new OrgType("8", "DHQ/Teaching"));
//            orgTypes.add(new OrgType("9", "Dispensary"));
//            orgTypes.add(new OrgType("10", "FHC"));
//            orgTypes.add(new OrgType("11", "FP Clinic"));
//            orgTypes.add(new OrgType("12", "GP"));
//            orgTypes.add(new OrgType("13", "GRD"));
//            orgTypes.add(new OrgType("14", "Gyne Clinic"));
//            orgTypes.add(new OrgType("15", "Hakim"));
//            orgTypes.add(new OrgType("16", "Homeopath"));
//            orgTypes.add(new OrgType("17", "Hospital"));
//            orgTypes.add(new OrgType("18", "Imaging Lab"));
//            orgTypes.add(new OrgType("19", "Lab"));
//            orgTypes.add(new OrgType("20", "Maternity Home"));
//            orgTypes.add(new OrgType("21", "MCHC"));
//            orgTypes.add(new OrgType("22", "Mobile Health Clinic"));
//            orgTypes.add(new OrgType("23", "Multiple Specialty"));
//            orgTypes.add(new OrgType("24", "Non-Teaching"));
//            orgTypes.add(new OrgType("25", "Nursing Home"));
//            orgTypes.add(new OrgType("26", "Others"));
//            orgTypes.add(new OrgType("27", "PESSI"));
//            orgTypes.add(new OrgType("28", "Physiotherapist"));
//            orgTypes.add(new OrgType("29", "Poly Clinic"));
//            orgTypes.add(new OrgType("30", "PWD"));
//            orgTypes.add(new OrgType("31", "RHC"));
//            orgTypes.add(new OrgType("32", "Single Speciality"));
//            orgTypes.add(new OrgType("33", "SSD"));
//            orgTypes.add(new OrgType("34", "SSEC"));
//            orgTypes.add(new OrgType("35", "SSMC"));
//            orgTypes.add(new OrgType("36", "Teaching"));
//            orgTypes.add(new OrgType("37", "THQ"));
//            orgTypes.add(new OrgType("38", "Wapda"));
//
//        } else {
//            // Fetch data from the database
//            String selectQuery = "SELECT * FROM " + ORGTYPE;
//            database = openHelper.getReadableDatabase();
//            Cursor cursor = database.rawQuery(selectQuery, null);
//
//            if (cursor.moveToFirst()) {
//                do {
//                    orgTypes.add(new OrgType(
//                            cursor.getString(cursor.getColumnIndex("Org_OrgType_id")),
//                            cursor.getString(cursor.getColumnIndex("Org_OrgType"))
//                    ));
//                } while (cursor.moveToNext());
//            }
//
//            cursor.close();
//        }
//
//        return orgTypes;
//    }

    public ArrayList<UpdateStatL1> getUpdateStatus() {
        String selectQuery = "SELECT * FROM " + UPDATESTATL1;
        database  = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        ArrayList<UpdateStatL1> updateStatL1s =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                updateStatL1s.add(new UpdateStatL1(
                        cursor.getString(cursor.getColumnIndex("Update_Status_id")),
                        cursor.getString(cursor.getColumnIndex("Update_Status"))


                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return updateStatL1s;
    }
    public ArrayList<UpdateStatL2> getSubStatus() {
        String selectQuery = "SELECT * FROM " + UPDATESTATL2;
        database  = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        ArrayList<UpdateStatL2> updateStatL2s =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                updateStatL2s.add(new UpdateStatL2(
                        cursor.getString(cursor.getColumnIndex("Sub_Status_id")),
                        cursor.getString(cursor.getColumnIndex("Sub_Status"))


                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return updateStatL2s;
    }
    public ArrayList<Distance> getdistance() {
        String selectQuery = "SELECT * FROM " + DISTANCE;
        database  = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        ArrayList<Distance> distances =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                distances.add(new Distance(
                        cursor.getString(cursor.getColumnIndex("Distance_id")),
                        cursor.getString(cursor.getColumnIndex("Distance"))


                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return distances;
    }





    public ArrayList<RegStatus> getRegStatus() {
        String selectQuery = "SELECT * FROM " + REGSTATUS;
        database  = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        ArrayList<RegStatus> regStatuses =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                regStatuses.add(new RegStatus(
                        cursor.getString(cursor.getColumnIndex("Reg_Status_id")),
                        cursor.getString(cursor.getColumnIndex("Reg_Status"))


                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return regStatuses;
    }
    public ArrayList<Action> getActions() {
        String selectQuery = "SELECT * FROM " + ACTIONS;
        database  = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        ArrayList<Action> actions =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                actions.add(new Action(
                        cursor.getString(cursor.getColumnIndex("Action_id")),
                        cursor.getString(cursor.getColumnIndex("Actioncol"))


                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return actions;
    }

    public ArrayList<ActionType> getActionType(String Roleid) {

        String selectQuery = "SELECT * FROM " + ACTIONSTYPE + " WHERE Cat_Roleid='" + Roleid + "' UNION SELECT * FROM "  + ACTIONSTYPE +  " WHERE Actiontype_id='0'" ;
        database  = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        ArrayList<ActionType> actions =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                actions.add(new ActionType(
                        cursor.getString(cursor.getColumnIndex("Actiontype_id")),
                        cursor.getString(cursor.getColumnIndex("Actiontype")),
                        cursor.getString(cursor.getColumnIndex("Fir_id")),
                        cursor.getString(cursor.getColumnIndex("Cat_def")),
                        cursor.getString(cursor.getColumnIndex("Cat_Roleid")),
                        cursor.getString(cursor.getColumnIndex("Cat_Statusid"))


                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return actions;
    }
    public ArrayList<Role> getRoles() {

        String selectQuery = "SELECT * FROM " + ROLE;
        database  = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        ArrayList<Role> roles =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                roles.add(new Role(
                        cursor.getString(cursor.getColumnIndex("role_id")),
                        cursor.getString(cursor.getColumnIndex("role_name"))
                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return roles;
    }
    public ArrayList<subActionType> getsubActiontype(String actiontypeid , String Roleid) {

        String selectQuery=null;

            selectQuery = "SELECT * FROM " + SUBACTIONSTYPE + " WHERE Subcatactiontype='" + actiontypeid +  "' and Sub_Roleid='"+ Roleid +"'  UNION SELECT * FROM "  + SUBACTIONSTYPE +  " WHERE Subactiontype_id='0'" ;


        database  = openHelper.getReadableDatabase();
        Cursor cursor      = database.rawQuery(selectQuery, null);
        ArrayList<subActionType> tehsils =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                tehsils.add(new subActionType(
                        cursor.getString(cursor.getColumnIndex("Subactiontype_id")),
                        cursor.getString(cursor.getColumnIndex("Subcatactiontype")),
                        cursor.getString(cursor.getColumnIndex("Subactiontype")),
                        cursor.getString(cursor.getColumnIndex("Sub_Roleid")),
                        cursor.getString(cursor.getColumnIndex("Sub_Statusid"))



                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return tehsils;
    }
    public ArrayList<subActionType> getsubActionselected(String actiontypeid) {

        String selectQuery=null;

        selectQuery = "SELECT * FROM " + SUBACTIONSTYPE + " WHERE Subactiontype_id='" + actiontypeid+"'";


        database  = openHelper.getReadableDatabase();
        Cursor cursor      = database.rawQuery(selectQuery, null);
        ArrayList<subActionType> tehsils =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                tehsils.add(new subActionType(
                        cursor.getString(cursor.getColumnIndex("Subactiontype_id")),
                        cursor.getString(cursor.getColumnIndex("Subcatactiontype")),
                        cursor.getString(cursor.getColumnIndex("Subactiontype")),
                        cursor.getString(cursor.getColumnIndex("Sub_Roleid")),
                        cursor.getString(cursor.getColumnIndex("Sub_Statusid"))

                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return tehsils;
    }
    public ArrayList<ActionType> getActionselected(String actiontypeid) {

        String selectQuery = "SELECT * FROM " + ACTIONSTYPE + " WHERE Actiontype_id='" + actiontypeid+"'";
        database  = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        ArrayList<ActionType> actions =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                actions.add(new ActionType(
                        cursor.getString(cursor.getColumnIndex("Actiontype_id")),
                        cursor.getString(cursor.getColumnIndex("Actiontype")),
                        cursor.getString(cursor.getColumnIndex("Fir_id")),
                        cursor.getString(cursor.getColumnIndex("Cat_def")),
                        cursor.getString(cursor.getColumnIndex("Cat_Roleid")),
                        cursor.getString(cursor.getColumnIndex("Cat_Statusid"))

                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return actions;
    }
    public ArrayList<PWSDistrict> getPWSDistrict() {

        String selectQuery = "SELECT * FROM " + PWSDISTRICT;
        database  = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        ArrayList<PWSDistrict> pwsdistrictss =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                pwsdistrictss.add(new PWSDistrict(
                        cursor.getString(cursor.getColumnIndex("PWSdistrict_id")),
                        cursor.getString(cursor.getColumnIndex("PWSdistrictName"))
                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return pwsdistrictss;
    }
    public ArrayList<PWSOrgType> getPWSorgType() {

        String selectQuery = "SELECT * FROM " + PWSORGTYPE;
        database  = openHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        ArrayList<PWSOrgType> pwsOrgTypes =new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                pwsOrgTypes.add(new PWSOrgType(
                        cursor.getString(cursor.getColumnIndex("PWSorg_id")),
                        cursor.getString(cursor.getColumnIndex("PWSorgType"))
                ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return pwsOrgTypes;
    }

    public void delete(int count){

       // openHelper.onUpgrade(database,DATABASE_VERSION,DATABASE_VERSION);
      if(count==0){
            database.execSQL("delete from "+ DISTRICT);
        }
        if(count==1){
            database.execSQL("delete from "+ TEHSIL);
        }
        if(count==2){
            database.execSQL("delete from "+ SECTORTYPE);
        }
        if(count==3){
            database.execSQL("delete from "+ COUNCILTYPE);
        }
        if(count==4){
            database.execSQL("delete from "+ ORGTYPE);
        }

        if(count==5){
            database.execSQL("delete from "+ DISTANCE);
        }
        if(count==6){
            database.execSQL("delete from "+ REGSTATUS);
        }
        if(count==7){
            database.execSQL("delete from "+ ACTIONSTYPE);
        }
        if(count==8){
            database.execSQL("delete from "+ SUBACTIONSTYPE);
        }
        if(count==9){
            database.execSQL("delete from "+ DIVISION);
        }
        if(count==10){
            database.execSQL("delete from "+ UPDATESTATL1);
        }
        if(count==11){
            database.execSQL("delete from "+ ROLE);
        }
        if(count==12){
            database.execSQL("delete from "+ UPDATESTATL2);
        }
        if(count==13){
            database.execSQL("delete from "+ ACTIONS);
        }
        if(count==14){
            database.execSQL("delete from "+ PWSDISTRICT);
        }
        if(count==15){
            database.execSQL("delete from "+ PWSORGTYPE);
        }
    }

    public DatabaseManager open() throws SQLException {
        openHelper = new DatabaseHelper(context);
        database = openHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (openHelper != null) {
            openHelper.close();
        }
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            // TODO Auto-generated constructor stub
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {

            // TODO Auto-generated method stub
            database.execSQL(" CREATE TABLE " + DIVISION + " ( "
                    + TAG_Division_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TAG_Division + " TEXT ) "


            );
            database.execSQL(" CREATE TABLE " + DISTRICT + " ( "
                    + TAG_DISTRICT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TAG_DISTRICT_DIV + " TEXT, "
                    + TAG_DISTRICT + " TEXT ) "


            );
            database.execSQL(" CREATE TABLE " + TEHSIL + " ( "
                    + TAG_TEHSIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TAG_TDISTRICT + " TEXT, "
                    + TAG_TEHSIL + " TEXT ) "
                   // + "FOREIGN KEY(category_id) REFERENCES Category(category_id) )"
            );
            database.execSQL(" CREATE TABLE " + SECTORTYPE + " ( "
                    + TAG_SECTORTYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TAG_SECTORTYPE + " TEXT ) "


            );
            database.execSQL(" CREATE TABLE " + COUNCILTYPE + " ( "
                    + TAG_COUNCILTYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TAG_COUNCILTYPE + " TEXT ) "


            );
            database.execSQL(" CREATE TABLE " + ORGTYPE + " ( "
                    + TAG_ORGTYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TAG_ORGTYPE + " TEXT ) "


            );
            database.execSQL(" CREATE TABLE " + UPDATESTATL1 + " ( "
                    + TAG_UpdatedStatus_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TAG_UpdatedStatus + " TEXT ) "


            );
            database.execSQL(" CREATE TABLE " + UPDATESTATL2 + " ( "
                    + TAG_SubStatus_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TAG_SubStatus + " TEXT ) "


            );
            database.execSQL(" CREATE TABLE " + DISTANCE + " ( "
                    + TAG_Distance_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TAG_Distance + " TEXT ) "


            );
            database.execSQL(" CREATE TABLE " + REGSTATUS + " ( "
                    + TAG_RegStatus_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TAG_RegStatus + " TEXT ) "


            );
            database.execSQL(" CREATE TABLE " + ACTIONS + " ( "
                    + TAG_Action_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TAG_Action + " TEXT ) "


            );
            database.execSQL(" CREATE TABLE " + ROLE + " ( "
                    + TAG_Role_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TAG_Role + " TEXT ) "


            );
            database.execSQL(" CREATE TABLE " + ACTIONSTYPE + " ( "
                    + TAG_ActionType_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TAG_ActionType + " TEXT , "
                    + TAG_FIR + " INTEGER , "
                    + TAG_Cat_Def + " TEXT , "
                    + TAG_CatRole_ID + " INTEGER , "
                    + TAG_CatStatus_ID + " INTEGER ) "



            );
            database.execSQL(" CREATE TABLE " + SUBACTIONSTYPE + " ( "
                    + TAG_SubActionType_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TAG_Sub_catActionID + " INTEGER, "
                    + TAG_Sub_ActionType + " TEXT,  "
                    + TAG_Sub_Roleid + " INTEGER, "
                    + TAG_Sub_statusid + " INTEGER ) "



            );
            database.execSQL(" CREATE TABLE " + PWSDISTRICT + " ( "
                    + TAG_PWSdistrict_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TAG_PWSdistrict + " TEXT ) "


            );
            database.execSQL(" CREATE TABLE " + PWSORGTYPE + " ( "
                    + TAG_PWSorg_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TAG_PWSorg + " TEXT ) "


            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            database.execSQL(" DROP TABLE IF EXISTS "+  DISTRICT);
            database.execSQL(" DROP TABLE IF EXISTS "+  TEHSIL);
            database.execSQL(" DROP TABLE IF EXISTS "+  SECTORTYPE);
            database.execSQL(" DROP TABLE IF EXISTS "+  COUNCILTYPE);
            database.execSQL(" DROP TABLE IF EXISTS "+  ORGTYPE);
            database.execSQL(" DROP TABLE IF EXISTS "+  UPDATESTATL1);
            database.execSQL(" DROP TABLE IF EXISTS "+  UPDATESTATL2);
            database.execSQL(" DROP TABLE IF EXISTS "+  DISTANCE);
            database.execSQL(" DROP TABLE IF EXISTS "+  REGSTATUS);
            database.execSQL(" DROP TABLE IF EXISTS "+  ACTIONS);
            database.execSQL(" DROP TABLE IF EXISTS "+  ACTIONSTYPE);
            database.execSQL(" DROP TABLE IF EXISTS "+  SUBACTIONSTYPE);
            database.execSQL(" DROP TABLE IF EXISTS "+  ROLE);
            database.execSQL(" DROP TABLE IF EXISTS "+  PWSDISTRICT);
            database.execSQL(" DROP TABLE IF EXISTS "+  PWSORGTYPE);
            database.execSQL(" DROP TABLE IF EXISTS "+  DIVISION);
            onCreate(database);
        }
    }

}