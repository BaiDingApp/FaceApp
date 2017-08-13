package com.baidingapp.faceapp.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.avos.avoscloud.AVUser;

public class MyInfoPreference {

    private static final String BASIC_INFO_OBJECT_ID = "basicInfoObjectId";
    private static final String RATE_FACE_PHOTO_ID = "rateFacePhotoId";

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


    // BasicInfoObjectId
    public static String getStoredBasicInfoObjectId(Context context) {
        return GeneralGetString(context, BASIC_INFO_OBJECT_ID);
        /*
        String username = AVUser.getCurrentUser().getUsername();
        SharedPreferences BasicInfoObjectId = context.getSharedPreferences(username, Context.MODE_PRIVATE);
        return BasicInfoObjectId.getString(BASIC_INFO_OBJECT_ID, null);
        */
    }
    public static void setStoredBasicInfoObjectId(Context context, String basicInfoObjectId) {
        GeneralSetString(context, BASIC_INFO_OBJECT_ID, basicInfoObjectId);
        /*
        String username = AVUser.getCurrentUser().getUsername();
        SharedPreferences BasicInfoObjectId = context.getSharedPreferences(username, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = BasicInfoObjectId.edit();
        editor.putString(BASIC_INFO_OBJECT_ID, basicInfoObjectId)
                .apply();
        */
    }


    // RateFacePhotoId
    public static String  getStoredRateFacePhotoId(Context context) {
        return GeneralGetString(context, RATE_FACE_PHOTO_ID);
        /*
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(RATE_FACE_PHOTO_ID, null);
        */
    }
    public static void setStoredRateFacePhotoId(Context context, String rateFacePhotoId) {
        GeneralSetString(context, RATE_FACE_PHOTO_ID, rateFacePhotoId);
        /*
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(RATE_FACE_PHOTO_ID, rateFacePhotoId)
                .apply();
        */
    }


    // Education
    public static int getStoredSingle(Context context) {
        return GeneralGetInt(context, PREF_SINGLE);
    }
    public static void setStoredSingle(Context context, int isSingle) {
        GeneralSetInt(context, PREF_SINGLE, isSingle);
    }


    // Religion


    // Pet


    // General method - setStoredString & getStoredString
    private static String GeneralGetString(Context context, String keyString) {
        String username = AVUser.getCurrentUser().getUsername();
        SharedPreferences sp = context.getSharedPreferences(username, Context.MODE_PRIVATE);
        return sp.getString(keyString, null);
    }
    private static void GeneralSetString(Context context, String stringKey, String stringValue) {
        String username = AVUser.getCurrentUser().getUsername();
        SharedPreferences sp = context.getSharedPreferences(username, Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.putString(stringKey, stringValue)
                .apply();
    }


    // General method - setStoredInt & getStoredInt
    private static int GeneralGetInt(Context context, String keyString) {
        String username = AVUser.getCurrentUser().getUsername();
        SharedPreferences sp = context.getSharedPreferences(username, Context.MODE_PRIVATE);
        return sp.getInt(keyString, 0);
    }
    private static void GeneralSetInt(Context context, String stringKey, int intValue) {
        String username = AVUser.getCurrentUser().getUsername();
        SharedPreferences sp = context.getSharedPreferences(username, Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.putInt(stringKey, intValue)
                .apply();
    }

}

