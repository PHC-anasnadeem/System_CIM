package com.phc.cim.Managers;


import android.content.Context;

import com.phc.cim.DataElements.Action;
import com.phc.cim.DataElements.ActionType;
import com.phc.cim.DataElements.CouncilType;
import com.phc.cim.DataElements.Distance;
import com.phc.cim.DataElements.District;
import com.phc.cim.DataElements.Division;
import com.phc.cim.DataElements.OrgType;
import com.phc.cim.DataElements.RegStatus;
import com.phc.cim.DataElements.Role;
import com.phc.cim.DataElements.SectorType;
import com.phc.cim.DataElements.Tehsil;
import com.phc.cim.DataElements.UpdateStatL1;
import com.phc.cim.DataElements.UpdateStatL2;
import com.phc.cim.DataElements.subActionType;

import java.util.ArrayList;

/**
 * Created by TeespireHP on 7/19/2017.
 */

public class DataManager {

    DatabaseManager databaseManagerHelper;
    ArrayList<District> districts = null;
    ArrayList<Division> divisons = null;
    ArrayList<Tehsil> tehsils = null;
    ArrayList<SectorType> sectorTypes = null;
    ArrayList<CouncilType> councilTypes = null;
    ArrayList<OrgType> orgTypes = null;
    ArrayList<UpdateStatL1> updateStatL1s = null;
    ArrayList<Role> roles = null;
    ArrayList<UpdateStatL2> updateStatL2s = null;
    ArrayList<Distance> distances = null;
    ArrayList<RegStatus> regStatuses = null;
    ArrayList<Action> actions = null;
    ArrayList<ActionType> actionTypes = null;
    ArrayList<subActionType> subActionTypes = null;

    private WebApiManager webApiManager;
    Context context;
    int count=0;

    public DataManager(Context context) {


        databaseManagerHelper = new DatabaseManager(context);
        this.context=context;

    }

    public ArrayList<District> getDistrictList(String division) {

        districts = databaseManagerHelper.getDistrictList(division);
        if (districts.isEmpty())
        {
            webApiManager = new WebApiManager(context,count);
            districts = databaseManagerHelper.getDistrictList(division);
        }

        return districts;
    }



    public ArrayList<Tehsil> getTehsilList(String district) {

        tehsils = databaseManagerHelper.getTehsilList(district);
        if (tehsils.isEmpty())
        {
            count=1;
            webApiManager = new WebApiManager(context,count);
            tehsils = databaseManagerHelper.getTehsilList(district);
        }

        return tehsils;
    }

    public ArrayList<SectorType> getSectorTypes() {

        sectorTypes = databaseManagerHelper.getSectorTypeList();
        if (sectorTypes.isEmpty())
        {
            count=2;
            webApiManager = new WebApiManager(context,count);
            sectorTypes = databaseManagerHelper.getSectorTypeList();
        }

        return sectorTypes;
    }


    public ArrayList<CouncilType> getCouncilTypes() {

        councilTypes = databaseManagerHelper.getCouncilTypeList();
        if (councilTypes.isEmpty())
        {
            count=3;
            webApiManager = new WebApiManager(context,count);
            councilTypes = databaseManagerHelper.getCouncilTypeList();
        }

        return councilTypes;
    }
    public ArrayList<OrgType> getOrgTypesList() {

        orgTypes = databaseManagerHelper.getOrgTypeList();
        if (orgTypes.isEmpty())
        {
            count=4;
            webApiManager = new WebApiManager(context,count);
            orgTypes = databaseManagerHelper.getOrgTypeList();
        }

        return orgTypes;
    }

    public ArrayList<Distance> getdistance() {

        distances = databaseManagerHelper.getdistance();
        if (distances.isEmpty())
        {
            count=5;
            webApiManager = new WebApiManager(context,count);
            distances = databaseManagerHelper.getdistance();
        }

        return distances;
    }
    public ArrayList<RegStatus> getRegStatus() {

        regStatuses = databaseManagerHelper.getRegStatus();
        if (regStatuses.isEmpty())
        {
            count=6;
            webApiManager = new WebApiManager(context,count);
            regStatuses = databaseManagerHelper.getRegStatus();
        }

        return regStatuses;
    }

    public ArrayList<ActionType> getActionstype(String  roleid) {

        actionTypes = databaseManagerHelper.getActionType(roleid);
        if (actionTypes.isEmpty())
        {
            count=7;
            webApiManager = new WebApiManager(context,count);
            actionTypes = databaseManagerHelper.getActionType(roleid);
        }

        return actionTypes;
    }
    public ArrayList<subActionType> getsubActionstype(String id, String roleid) {

        subActionTypes = databaseManagerHelper.getsubActiontype(id,roleid);
        if (subActionTypes.isEmpty())
        {
            count=8;
            webApiManager = new WebApiManager(context,count);
            subActionTypes = databaseManagerHelper.getsubActiontype(id,roleid);
        }

        return subActionTypes;
    }
    public ArrayList<Division> getDivision() {

        divisons = databaseManagerHelper.getDivision();
        if (divisons.isEmpty())
        {
            count=9;
            webApiManager = new WebApiManager(context,count);
            divisons = databaseManagerHelper.getDivision();
        }

        return divisons;
    }

   public ArrayList<UpdateStatL1> getUpdateStatus() {

        updateStatL1s = databaseManagerHelper.getUpdateStatus();
        if (updateStatL1s.isEmpty())
        {
            count=10;
            webApiManager = new WebApiManager(context,count);
            updateStatL1s = databaseManagerHelper.getUpdateStatus();
        }

        return updateStatL1s;
    }

    public ArrayList<Role> getRoles() {

        roles = databaseManagerHelper.getRoles();
        if (roles.isEmpty())
        {
            count=11;
            webApiManager = new WebApiManager(context,count);
            roles = databaseManagerHelper.getRoles();
        }

        return roles;
    }
 /*    public ArrayList<UpdateStatL2> getSubStatus() {

        updateStatL2s = databaseManagerHelper.getSubStatus();
        if (updateStatL2s.isEmpty())
        {
            count=11;
            webApiManager = new WebApiManager(context,count);
            updateStatL2s = databaseManagerHelper.getSubStatus();
        }

        return updateStatL2s;
    }
    public ArrayList<Action> getActions() {

        actions = databaseManagerHelper.getActions();
        if (actions.isEmpty())
        {
            count=12;
            webApiManager = new WebApiManager(context,count);
            actions = databaseManagerHelper.getActions();
        }

        return actions;
    }*/
}
