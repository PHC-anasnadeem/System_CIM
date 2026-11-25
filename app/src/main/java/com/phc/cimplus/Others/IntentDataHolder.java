package com.phc.cimplus.Others;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Static data holder to avoid TransactionTooLargeException when passing
 * large ArrayLists through Intent extras or Fragment arguments.
 * Data is stored temporarily in memory instead of being serialized.
 */
public class IntentDataHolder {

    private static ArrayList<HashMap<String, String>> data;
    private static ArrayList<HashMap<String, String>> fragmentData;

    // For Intent extras (Activity to Activity)
    public static void setData(ArrayList<HashMap<String, String>> list) {
        data = list;
    }

    public static ArrayList<HashMap<String, String>> getData() {
        ArrayList<HashMap<String, String>> temp = data;
        data = null;
        return temp;
    }

    // For Fragment arguments (Activity to Fragments) - NOT cleared after get
    // because multiple fragments need to read the same data
    public static void setFragmentData(ArrayList<HashMap<String, String>> list) {
        fragmentData = list;
    }

    public static ArrayList<HashMap<String, String>> getFragmentData() {
        return fragmentData;
    }

    public static void clearFragmentData() {
        fragmentData = null;
    }
}
