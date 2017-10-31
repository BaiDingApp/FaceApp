package com.baidingapp.faceapp.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.avos.avoscloud.AVUser;

public class MyInfoPreference {

    private static final String BASIC_INFO_OBJECT_ID = "basicInfoObjectId";
    private static final String RATE_FACE_PHOTO_ID = "rateFacePhotoId";
    private static final String PREDICT_FACE_PHOTO_ID = "predictFacePhotoId";

    private static final String PREF_BIRTHDAY = "birthday";
    private static final String PREF_HEIGHT = "height";
    private static final String PREF_WEIGHT = "weight";
    private static final String PREF_GENDER = "gender";
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
    }
    public static void setStoredBasicInfoObjectId(Context context, String basicInfoObjectId) {
        GeneralSetString(context, BASIC_INFO_OBJECT_ID, basicInfoObjectId);
    }


    // RateFacePhotoId
    public static String getStoredRateFacePhotoId(Context context) {
        return GeneralGetString(context, RATE_FACE_PHOTO_ID);
    }
    public static void setStoredRateFacePhotoId(Context context, String rateFacePhotoId) {
        GeneralSetString(context, RATE_FACE_PHOTO_ID, rateFacePhotoId);
    }


    // PredictFacePhotoId
    public static String getStoredPredictFacePhotoId(Context context) {
        return GeneralGetString(context, PREDICT_FACE_PHOTO_ID);
    }
    public static void setStoredPredictFacePhotoId(Context context, String predictFacePhotoId) {
        GeneralSetString(context, PREDICT_FACE_PHOTO_ID, predictFacePhotoId);
    }


    // Height
    public static int getStoredHeight(Context context) {
        return GeneralGetInt(context, PREF_HEIGHT);
    }
    public static void setStoredHeight(Context context, int mHeight) {
        GeneralSetInt(context, PREF_HEIGHT, mHeight);
    }


    // Weight
    public static int getStoredWeight(Context context) {
        return GeneralGetInt(context, PREF_WEIGHT);
    }
    public static void setStoredWeight(Context context, int mWeight) {
        GeneralSetInt(context, PREF_WEIGHT, mWeight);
    }


    // Gender
    public static int getStoredGender(Context context) {
        return GeneralGetInt(context, PREF_GENDER);
    }
    public static void setStoredGender(Context context, int mGender) {
        GeneralSetInt(context, PREF_GENDER, mGender);
    }


    // Education Level
    public static int getStoredEducation(Context context) {
        return GeneralGetInt(context, PREF_EDUCATION);
    }
    public static void setStoredEducation(Context context, int mEducation) {
        GeneralSetInt(context, PREF_EDUCATION, mEducation);
    }


    // Occupation
    public static int getStoredOccupation(Context context) {
        return GeneralGetInt(context, PREF_OCCUPATION);
    }
    public static void setStoredOccupation(Context context, int mOccupation) {
        GeneralSetInt(context, PREF_OCCUPATION, mOccupation);
    }


    // Birth Place
    public static int getStoredBirthPlace(Context context) {
        return GeneralGetInt(context, PREF_BIRTH_PLACE);
    }
    public static void setStoredBirthPlace(Context context, int mBirthPlace) {
        GeneralSetInt(context, PREF_BIRTH_PLACE, mBirthPlace);
    }


    // Work Place
    public static int getStoredWorkPlace(Context context) {
        return GeneralGetInt(context, PREF_WORK_PLACE);
    }
    public static void setStoredWorkPlace(Context context, int mWorkPlace) {
        GeneralSetInt(context, PREF_WORK_PLACE, mWorkPlace);
    }


    // Oversea
    public static int getStoredOversea(Context context) {
        return GeneralGetInt(context, PREF_OVERSEA);
    }
    public static void setStoredOversea(Context context, int mOversea) {
        GeneralSetInt(context, PREF_OVERSEA, mOversea);
    }


    // Single
    public static int getStoredSingle(Context context) {
        return GeneralGetInt(context, PREF_SINGLE);
    }
    public static void setStoredSingle(Context context, int isSingle) {
        GeneralSetInt(context, PREF_SINGLE, isSingle);
    }


    // Religion
    public static int getStoredReligion(Context context) {
        return GeneralGetInt(context, PREF_RELIGION);
    }
    public static void setStoredReligion(Context context, int mReligion) {
        GeneralSetInt(context, PREF_RELIGION, mReligion);
    }


    // Pet
    public static int getStoredPet(Context context) {
        return GeneralGetInt(context, PREF_PET);
    }
    public static void setStoredPet(Context context, int mPet) {
        GeneralSetInt(context, PREF_PET, mPet);
    }


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

