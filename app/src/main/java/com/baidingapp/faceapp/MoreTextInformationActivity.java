package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.baidingapp.faceapp.helper.MyInfoPreference;

public class MoreTextInformationActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private String moreTextInfoObjectId;

    private int mIncomePosition;
    private int mReligionPosition;
    private int mMaritalPosition;
    private int mKidsPosition;
    private int mKidsAfterMarriagePosition;
    private int mHousePosition;
    private int mCarPosition;
    private int mLiveWithParentsAfterMarriagePosition;
    private int mFaceLiftPosition;
    private int mAngryPosition;
    private int mFriendOppositeSexPosition;
    private int mSmokingPosition;
    private int mDrinkingPosition;
    private int mGamblingPosition;

    private Spinner mIncomeSpinner;
    private Spinner mReligionSpinner;
    private Spinner mMaritalSpinner;
    private Spinner mKidsSpinner;
    private Spinner mKidsAfterMarriageSpinner;
    private Spinner mHouseSpinner;
    private Spinner mCarSpinner;
    private Spinner mLiveWithParentsAfterMarriageSpinner;
    private Spinner mFaceLiftSpinner;
    private Spinner mAngrySpinner;
    private Spinner mFriendOppositeSexSpinner;
    private Spinner mSmokingSpinner;
    private Spinner mDrinkingSpinner;
    private Spinner mGamblingSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_text_info);


        // onClick Income spinner
        mIncomeSpinner = (Spinner) findViewById(R.id.action_more_income_levels);
        ArrayAdapter mIncomeAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_income_levels, android.R.layout.simple_spinner_item);
        mIncomeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mIncomeSpinner.setAdapter(mIncomeAdapter);
        mIncomeSpinner.setOnItemSelectedListener(this);

        // onClick Religion spinner
        mReligionSpinner = (Spinner) findViewById(R.id.action_more_all_religion);
        ArrayAdapter mReligionAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_all_religion, android.R.layout.simple_spinner_item);
        mReligionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mReligionSpinner.setAdapter(mReligionAdapter);
        mReligionSpinner.setOnItemSelectedListener(this);

        // onClick Marital spinner
        mMaritalSpinner = (Spinner) findViewById(R.id.action_more_marital_history);
        ArrayAdapter mMaritalAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mMaritalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMaritalSpinner.setAdapter(mMaritalAdapter);
        mMaritalSpinner.setOnItemSelectedListener(this);

        // onClick Kids spinner
        mKidsSpinner = (Spinner) findViewById(R.id.action_more_has_kids);
        ArrayAdapter mKidsAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mKidsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mKidsSpinner.setAdapter(mKidsAdapter);
        mKidsSpinner.setOnItemSelectedListener(this);

        // onClick KidsAfterMarriage spinner
        mKidsAfterMarriageSpinner = (Spinner) findViewById(R.id.action_more_has_kids_after_marriage);
        ArrayAdapter mKidsAfterMarriageAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mKidsAfterMarriageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mKidsAfterMarriageSpinner.setAdapter(mKidsAfterMarriageAdapter);
        mKidsAfterMarriageSpinner.setOnItemSelectedListener(this);

        // onClick House spinner
        mHouseSpinner = (Spinner) findViewById(R.id.action_more_has_house);
        ArrayAdapter mHouseAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mHouseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHouseSpinner.setAdapter(mHouseAdapter);
        mHouseSpinner.setOnItemSelectedListener(this);

        // onClick Car spinner
        mCarSpinner = (Spinner) findViewById(R.id.action_more_has_car);
        ArrayAdapter mCarAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mCarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCarSpinner.setAdapter(mCarAdapter);
        mCarSpinner.setOnItemSelectedListener(this);

        // onClick LiveWithParentsAfterMarriage spinner
        mLiveWithParentsAfterMarriageSpinner = (Spinner) findViewById(R.id.action_more_live_with_parents_after_marriage);
        ArrayAdapter mLiveWithParentsAfterMarriageAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mLiveWithParentsAfterMarriageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLiveWithParentsAfterMarriageSpinner.setAdapter(mLiveWithParentsAfterMarriageAdapter);
        mLiveWithParentsAfterMarriageSpinner.setOnItemSelectedListener(this);

        // onClick FaceLift spinner
        mFaceLiftSpinner = (Spinner) findViewById(R.id.action_more_face_lift);
        ArrayAdapter mFaceLiftAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mFaceLiftAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFaceLiftSpinner.setAdapter(mFaceLiftAdapter);
        mFaceLiftSpinner.setOnItemSelectedListener(this);

        // onClick Angry spinner
        mAngrySpinner = (Spinner) findViewById(R.id.action_more_angry);
        ArrayAdapter mAngryAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mAngryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAngrySpinner.setAdapter(mAngryAdapter);
        mAngrySpinner.setOnItemSelectedListener(this);

        // onClick FriendOppositeSex spinner
        mFriendOppositeSexSpinner = (Spinner) findViewById(R.id.action_more_friends_with_opposite_sex);
        ArrayAdapter mFriendOppositeSexAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mFriendOppositeSexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFriendOppositeSexSpinner.setAdapter(mFriendOppositeSexAdapter);
        mFriendOppositeSexSpinner.setOnItemSelectedListener(this);

        // onClick Smoking spinner
        mSmokingSpinner = (Spinner) findViewById(R.id.action_more_smoking);
        ArrayAdapter mSmokingAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_smoking, android.R.layout.simple_spinner_item);
        mSmokingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSmokingSpinner.setAdapter(mSmokingAdapter);
        mSmokingSpinner.setOnItemSelectedListener(this);

        // onClick Drinking spinner
        mDrinkingSpinner = (Spinner) findViewById(R.id.action_more_drinking);
        ArrayAdapter mDrinkingAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_drinking, android.R.layout.simple_spinner_item);
        mDrinkingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDrinkingSpinner.setAdapter(mDrinkingAdapter);
        mDrinkingSpinner.setOnItemSelectedListener(this);

        // onClick Gambling spinner
        mGamblingSpinner = (Spinner) findViewById(R.id.action_more_gambling);
        ArrayAdapter mGamblingAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_gambling, android.R.layout.simple_spinner_item);
        mGamblingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGamblingSpinner.setAdapter(mGamblingAdapter);
        mGamblingSpinner.setOnItemSelectedListener(this);


        // onClick SAVE button
        Button mSaveButton = (Button) findViewById(R.id.action_submit_more_text_info);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save data to SharedPreference
                saveDataToSharedPreference();

                // Save data to LeanCloud
                saveDataToLeanCloud();
            }
        });

    }


    // Identify the Spinners
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.action_more_income_levels:
                mIncomePosition = position;
                break;

            case R.id.action_more_all_religion:
                mReligionPosition = position;
                break;

            case R.id.action_more_marital_history:
                mMaritalPosition = position;
                break;

            case R.id.action_more_has_kids:
                mKidsPosition = position;
                break;

            case R.id.action_more_has_kids_after_marriage:
                mKidsAfterMarriagePosition = position;
                break;

            case R.id.action_more_has_house:
                mHousePosition = position;
                break;

            case R.id.action_more_has_car:
                mCarPosition = position;
                break;

            case R.id.action_more_live_with_parents_after_marriage:
                mLiveWithParentsAfterMarriagePosition = position;
                break;

            case R.id.action_more_face_lift:
                mFaceLiftPosition = position;
                break;

            case R.id.action_more_angry:
                mAngryPosition = position;
                break;

            case R.id.action_more_friends_with_opposite_sex:
                mFriendOppositeSexPosition = position;
                break;

            case R.id.action_more_smoking:
                mSmokingPosition = position;
                break;

            case R.id.action_more_drinking:
                mDrinkingPosition = position;
                break;

            case R.id.action_more_gambling:
                mGamblingPosition = position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    // Create the profile by using info saved in SharedPreference when onCreate
    private void createProfileUsingSavedData() {
        // Income
        mIncomePosition = MyInfoPreference.getStoredIncome(MoreTextInformationActivity.this);
        mIncomeSpinner.setSelection(mIncomePosition);

        // Religion
        mReligionPosition = MyInfoPreference.getStoredReligion_More(MoreTextInformationActivity.this);
        mReligionSpinner.setSelection(mReligionPosition);

        // MaritalHistory
        mMaritalPosition = MyInfoPreference.getStoredMaritalHistory(MoreTextInformationActivity.this);
        mMaritalSpinner.setSelection(mMaritalPosition);

        // Has Kids
        mKidsPosition = MyInfoPreference.getStoredHasKids(MoreTextInformationActivity.this);
        mKidsSpinner.setSelection(mKidsPosition);

        // Want Kids
        mKidsAfterMarriagePosition = MyInfoPreference.getStoredWantKids(MoreTextInformationActivity.this);
        mKidsAfterMarriageSpinner.setSelection(mKidsAfterMarriagePosition);

        // House
        mHousePosition = MyInfoPreference.getStoredHouse(MoreTextInformationActivity.this);
        mHouseSpinner.setSelection(mHousePosition);

        // Car
        mCarPosition = MyInfoPreference.getStoredCar(MoreTextInformationActivity.this);
        mCarSpinner.setSelection(mCarPosition);

        // LiveWithParentsAfterMarriage
        mLiveWithParentsAfterMarriagePosition = MyInfoPreference.getStoredLiveWithParentsAfterMarriage(MoreTextInformationActivity.this);
        mLiveWithParentsAfterMarriageSpinner.setSelection(mLiveWithParentsAfterMarriagePosition);

        // FaceLift
        mFaceLiftPosition = MyInfoPreference.getStoredFaceLift(MoreTextInformationActivity.this);
        mFaceLiftSpinner.setSelection(mFaceLiftPosition);

        // Angry
        mAngryPosition = MyInfoPreference.getStoredAngry(MoreTextInformationActivity.this);
        mAngrySpinner.setSelection(mAngryPosition);

        // Angry
        mFriendOppositeSexPosition = MyInfoPreference.getStoredFriendsOppositeSex(MoreTextInformationActivity.this);
        mFriendOppositeSexSpinner.setSelection(mFriendOppositeSexPosition);

        // Smoking
        mSmokingPosition = MyInfoPreference.getStoredSmoking(MoreTextInformationActivity.this);
        mSmokingSpinner.setSelection(mSmokingPosition);

        // Drinking
        mDrinkingPosition = MyInfoPreference.getStoredDrinking(MoreTextInformationActivity.this);
        mDrinkingSpinner.setSelection(mDrinkingPosition);

        // Gambling
        mGamblingPosition = MyInfoPreference.getStoredGambling(MoreTextInformationActivity.this);
        mGamblingSpinner.setSelection(mGamblingPosition);

    }


    // Save data to SharedPreference
    private void saveDataToSharedPreference() {
        MyInfoPreference.setStoredIncome(MoreTextInformationActivity.this, mIncomePosition);
        MyInfoPreference.setStoredReligion_More(MoreTextInformationActivity.this, mReligionPosition);
        MyInfoPreference.setStoredMaritalHistory(MoreTextInformationActivity.this, mMaritalPosition);
        MyInfoPreference.setStoredHasKids(MoreTextInformationActivity.this, mKidsPosition);
        MyInfoPreference.setStoredWantKids(MoreTextInformationActivity.this, mKidsAfterMarriagePosition);
        MyInfoPreference.setStoredHouse(MoreTextInformationActivity.this, mHousePosition);
        MyInfoPreference.setStoredCar(MoreTextInformationActivity.this, mCarPosition);
        MyInfoPreference.setStoredLiveWithParentsAfterMarriage(MoreTextInformationActivity.this, mLiveWithParentsAfterMarriagePosition);
        MyInfoPreference.setStoredFaceLift(MoreTextInformationActivity.this, mFaceLiftPosition);
        MyInfoPreference.setStoredAngry(MoreTextInformationActivity.this, mAngryPosition);
        MyInfoPreference.setStoredFriendsOppositeSex(MoreTextInformationActivity.this, mFriendOppositeSexPosition);
        MyInfoPreference.setStoredSmoking(MoreTextInformationActivity.this, mSmokingPosition);
        MyInfoPreference.setStoredDrinking(MoreTextInformationActivity.this, mDrinkingPosition);
        MyInfoPreference.setStoredGambling(MoreTextInformationActivity.this, mGamblingPosition);
    }


    // Save data to LeanCloud
    private void saveDataToLeanCloud() {
        moreTextInfoObjectId = MyInfoPreference.getStoredMoreTextInfoObjectId(MoreTextInformationActivity.this);

        if (moreTextInfoObjectId != null) {
            final AVObject mMoreTextInfo = AVObject.createWithoutData("MoreTextInfo", moreTextInfoObjectId);

            mMoreTextInfo.put("income", mIncomePosition);
            mMoreTextInfo.put("religion", mReligionPosition);
            mMoreTextInfo.put("maritalHistory", mMaritalPosition);
            mMoreTextInfo.put("hasKids", mKidsPosition);
            mMoreTextInfo.put("wantKids", mKidsAfterMarriagePosition);
            mMoreTextInfo.put("house", mHousePosition);
            mMoreTextInfo.put("car", mCarPosition);
            mMoreTextInfo.put("liveWithParents", mLiveWithParentsAfterMarriagePosition);
            mMoreTextInfo.put("faceLift", mFaceLiftPosition);
            mMoreTextInfo.put("angry", mAngryPosition);
            mMoreTextInfo.put("friendsOppositeSex", mFriendOppositeSexPosition);
            mMoreTextInfo.put("smoking", mSmokingPosition);
            mMoreTextInfo.put("drinking", mDrinkingPosition);
            mMoreTextInfo.put("gambling", mGamblingPosition);

            mMoreTextInfo.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        Toast.makeText(MoreTextInformationActivity.this, R.string.save_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MoreTextInformationActivity.this, R.string.save_fail, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            final AVObject mMoreTextInfo = new AVObject("MoreTextInfo");

            // Create the pointer that points to _User
            mMoreTextInfo.put("username", AVUser.getCurrentUser().getUsername());
            mMoreTextInfo.put("userId", AVUser.getCurrentUser());

            mMoreTextInfo.put("income", mIncomePosition);
            mMoreTextInfo.put("religion", mReligionPosition);
            mMoreTextInfo.put("maritalHistory", mMaritalPosition);
            mMoreTextInfo.put("hasKids", mKidsPosition);
            mMoreTextInfo.put("wantKids", mKidsAfterMarriagePosition);
            mMoreTextInfo.put("house", mHousePosition);
            mMoreTextInfo.put("car", mCarPosition);
            mMoreTextInfo.put("liveWithParents", mLiveWithParentsAfterMarriagePosition);
            mMoreTextInfo.put("faceLift", mFaceLiftPosition);
            mMoreTextInfo.put("angry", mAngryPosition);
            mMoreTextInfo.put("friendsOppositeSex", mFriendOppositeSexPosition);
            mMoreTextInfo.put("smoking", mSmokingPosition);
            mMoreTextInfo.put("drinking", mDrinkingPosition);
            mMoreTextInfo.put("gambling", mGamblingPosition);

            mMoreTextInfo.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        // Save the objectId of BasicInfo to SharedPreference
                        moreTextInfoObjectId = mMoreTextInfo.getObjectId();
                        MyInfoPreference.setStoredMoreTextInfoObjectId(MoreTextInformationActivity.this, moreTextInfoObjectId);

                        Toast.makeText(MoreTextInformationActivity.this, R.string.save_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MoreTextInformationActivity.this, R.string.save_fail, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }  // End of "Save data to LeanCloud"

}
