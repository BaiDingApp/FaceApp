package com.baidingapp.faceapp.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.avos.avoscloud.AVUser;

public class MyInfoPreference {

    private static final String BASIC_INFO_OBJECT_ID = "basicInfoObjectId";
    private static final String MORE_TEXT_INFO_OBJECT_ID = "moreTextInfoObjectId";
    private static final String RATE_FACE_PHOTO_ID = "rateFacePhotoId";
    private static final String PREDICT_FACE_PHOTO_ID = "predictFacePhotoId";
    private static final String DATING_FACE_PHOTO_ID = "datingFacePhotoId";

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

    private static final String PREF_INCOME = "income";
    private static final String PREF_RELIGION_MORE = "religion_more";
    private static final String PREF_MARITAL_HISTORY = "marital_history";
    private static final String PREF_HAS_KIDS = "has_kids";
    private static final String PREF_WANT_KIDS = "want_kids";
    private static final String PREF_HOUSE = "house";
    private static final String PREF_CAR = "car";
    private static final String PREF_LIVE_WITH_PARENTS_AFTER_MARRIAGE = "live_with_parents";
    private static final String PREF_FACE_LIFT = "face_lift";
    private static final String PREF_ANGRY = "angry";
    private static final String PREF_FRIENDS_OPPOSITE_SEX = "friends_opposite_sex";
    private static final String PREF_SMOKING = "smoking";
    private static final String PREF_DRINKING = "drinking";
    private static final String PREF_GAMBLING = "gambling";

    // BasicInfoObjectId
    public static String getStoredBasicInfoObjectId(Context context) {
        return GeneralGetString(context, BASIC_INFO_OBJECT_ID);
    }
    public static void setStoredBasicInfoObjectId(Context context, String basicInfoObjectId) {
        GeneralSetString(context, BASIC_INFO_OBJECT_ID, basicInfoObjectId);
    }

    // MoreTextInfoObjectId
    public static String getStoredMoreTextInfoObjectId(Context context) {
        return GeneralGetString(context, MORE_TEXT_INFO_OBJECT_ID);
    }
    public static void setStoredMoreTextInfoObjectId(Context context, String moreTextInfoObjectId) {
        GeneralSetString(context, MORE_TEXT_INFO_OBJECT_ID, moreTextInfoObjectId);
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

    // DatingFacePhotoId
    public static String getStoredDatingFacePhotoId(Context context) {
        return GeneralGetString(context, DATING_FACE_PHOTO_ID);
    }
    public static void setStoredDatingFacePhotoId(Context context, String datingFacePhotoId) {
        GeneralSetString(context, DATING_FACE_PHOTO_ID, datingFacePhotoId);
    }



    // BASIC INFORMATION
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



    // MORE TEXT INFORMATION
    // Income
    public static int getStoredIncome(Context context) {
        return GeneralGetInt(context, PREF_INCOME);
    }
    public static void setStoredIncome(Context context, int mIncome) {
        GeneralSetInt(context, PREF_INCOME, mIncome);
    }

    // Religion_More
    public static int getStoredReligion_More(Context context) {
        return GeneralGetInt(context, PREF_RELIGION_MORE);
    }
    public static void setStoredReligion_More(Context context, int mReligionMore) {
        GeneralSetInt(context, PREF_RELIGION_MORE, mReligionMore);
    }

    // MaritalHistory
    public static int getStoredMaritalHistory(Context context) {
        return GeneralGetInt(context, PREF_MARITAL_HISTORY);
    }
    public static void setStoredMaritalHistory(Context context, int mMaritalHistory) {
        GeneralSetInt(context, PREF_MARITAL_HISTORY, mMaritalHistory);
    }

    // HasKids
    public static int getStoredHasKids(Context context) {
        return GeneralGetInt(context, PREF_HAS_KIDS);
    }
    public static void setStoredHasKids(Context context, int mHasKids) {
        GeneralSetInt(context, PREF_HAS_KIDS, mHasKids);
    }

    // WantKids
    public static int getStoredWantKids(Context context) {
        return GeneralGetInt(context, PREF_WANT_KIDS);
    }
    public static void setStoredWantKids(Context context, int mWantKids) {
        GeneralSetInt(context, PREF_WANT_KIDS, mWantKids);
    }

    // House
    public static int getStoredHouse(Context context) {
        return GeneralGetInt(context, PREF_HOUSE);
    }
    public static void setStoredHouse(Context context, int mHouse) {
        GeneralSetInt(context, PREF_HOUSE, mHouse);
    }

    // Car
    public static int getStoredCar(Context context) {
        return GeneralGetInt(context, PREF_CAR);
    }
    public static void setStoredCar(Context context, int mCar) {
        GeneralSetInt(context, PREF_CAR, mCar);
    }

    // LiveWithParentsAfterMarriage
    public static int getStoredLiveWithParentsAfterMarriage(Context context) {
        return GeneralGetInt(context, PREF_LIVE_WITH_PARENTS_AFTER_MARRIAGE);
    }
    public static void setStoredLiveWithParentsAfterMarriage(Context context, int mLiveWithParents) {
        GeneralSetInt(context, PREF_LIVE_WITH_PARENTS_AFTER_MARRIAGE, mLiveWithParents);
    }

    // FaceLift
    public static int getStoredFaceLift(Context context) {
        return GeneralGetInt(context, PREF_FACE_LIFT);
    }
    public static void setStoredFaceLift(Context context, int mFaceLift) {
        GeneralSetInt(context, PREF_FACE_LIFT, mFaceLift);
    }

    // Angry
    public static int getStoredAngry(Context context) {
        return GeneralGetInt(context, PREF_ANGRY);
    }
    public static void setStoredAngry(Context context, int mAngry) {
        GeneralSetInt(context, PREF_ANGRY, mAngry);
    }

    // FriendsOppositeSex
    public static int getStoredFriendsOppositeSex(Context context) {
        return GeneralGetInt(context, PREF_FRIENDS_OPPOSITE_SEX);
    }
    public static void setStoredFriendsOppositeSex(Context context, int mFriendsOppositeSex) {
        GeneralSetInt(context, PREF_FRIENDS_OPPOSITE_SEX, mFriendsOppositeSex);
    }

    // Smoking
    public static int getStoredSmoking(Context context) {
        return GeneralGetInt(context, PREF_SMOKING);
    }
    public static void setStoredSmoking(Context context, int mSmoking) {
        GeneralSetInt(context, PREF_SMOKING, mSmoking);
    }

    // Drinking
    public static int getStoredDrinking(Context context) {
        return GeneralGetInt(context, PREF_DRINKING);
    }
    public static void setStoredDrinking(Context context, int mDrinking) {
        GeneralSetInt(context, PREF_DRINKING, mDrinking);
    }

    // Gambling
    public static int getStoredGambling(Context context) {
        return GeneralGetInt(context, PREF_GAMBLING);
    }
    public static void setStoredGambling(Context context, int mGambling) {
        GeneralSetInt(context, PREF_GAMBLING, mGambling);
    }



    // GENERAL METHOD
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

