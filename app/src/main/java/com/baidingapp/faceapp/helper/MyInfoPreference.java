package com.baidingapp.faceapp.helper;

import android.content.Context;
import android.preference.PreferenceManager;

public class MyInfoPreference {

    private static final String BASIC_INFO_OBJECT_ID = "basicInfoObjectId";

    private static final String PREF_BIRTHDAY = "birthday";
    private static final String PREF_HEIGHT = "height";
    private static final String PREF_WEIGHT = "weight";
    private static final String PREF_EDUCATION = "education";
    private static final String PREF_OCCUPATION = "occupation";
    private static final String PREF_BIRTH_PLACE = "birth_place";
    private static final String PREF_WORK_PLACE = "work_place";
    private static final String PREF_OVERSEA = "oversea";
    private static final String PREF_SINGLE = "single";
    private static final String PREF_RELIGION = "religion";
    private static final String PREF_PET = "pet";


    // Education
    public static String  getStoredBasicInfoObjectId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(BASIC_INFO_OBJECT_ID, null);
    }
    public static void setStoredBasicInfoObjectId(Context context, String basicInfoObjectId) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(BASIC_INFO_OBJECT_ID, basicInfoObjectId)
                .apply();
    }


    // Education
    public static int getStoredSingle(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_SINGLE, 0);
    }
    public static void setStoredSingle(Context context, int single) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_SINGLE, single)
                .apply();
    }

    // Religion


    // Pet


}

