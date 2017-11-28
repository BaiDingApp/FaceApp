package com.baidingapp.faceapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.baidingapp.faceapp.helper.ImageHelper;
import com.baidingapp.faceapp.helper.MyInfoPreference;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileNotFoundException;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class FaceInformationActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_IMAGE = 2;

    private ImageView mFaceImageView;
    private Button mUploadImageButton;
    private String mImagePath;
    private AVFile mImageFile = null;
    private String datingFacePhotoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_information);


        // Show the uploaded face image for dating
        mFaceImageView = (ImageView) findViewById(R.id.show_face_image_dating);

        File internalStorage = FaceInformationActivity.this.getDir(AVUser.getCurrentUser().getUsername(), MODE_PRIVATE);
        File myImage = new File(internalStorage.getPath(), "datingImage");
        if (myImage.exists() && myImage.length() > 0) {
            GlideApp.with(FaceInformationActivity.this)
                    .load(myImage)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.face_image)
                    .error(R.drawable.face_image)
                    .into(mFaceImageView);
        } else {
            GlideApp.with(FaceInformationActivity.this).load(R.drawable.face_image).into(mFaceImageView);
        }


        // the UPLOAD face image Button
        Button mPickImageButton = (Button) findViewById(R.id.action_pick_face_photo_dating);
        mUploadImageButton = (Button) findViewById(R.id.action_upload_face_photo_dating);


        // on Click the PICK Button
        mPickImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FaceInformationActivityPermissionsDispatcher.pickPhotoFromGalleryWithCheck(FaceInformationActivity.this);
                // pickPhotoFromGallery();
                mUploadImageButton.setEnabled(true);
            }
        });


        // The Upload Button is enabled after the Pick Button is clicked
        mUploadImageButton.setEnabled(false);
        // on Click the Upload Button
        mUploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhotoToLeanCloud();
                // Copy the uploaded photo from External Storage to Internal Storage
                ImageHelper.copyPhotoToInternalStorage(mImagePath, FaceInformationActivity.this, "datingImage");
                mUploadImageButton.setEnabled(false);
            }
        });


        // on Click the Upload Info Button
        Button mUploadInfoButton = (Button) findViewById(R.id.action_upload_face_info);
        mUploadInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Toast.makeText(FaceInformationActivity.this, "TODO - Upload face info", Toast.LENGTH_SHORT).show();
            }
        });


        // on Click the Upload Info Button
        Button mMeasureFaceButton = (Button) findViewById(R.id.action_measure_face_info);
        mMeasureFaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Toast.makeText(FaceInformationActivity.this, "TODO - Introduction of Measure", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == REQUEST_CODE_IMAGE) {
            if(data == null) {
                return;
            }

            // Show the Picked image on the imageView
            GlideApp.with(this).load(data.getData()).into(mFaceImageView);

            // Get the real path of the Picked image from its Uri
            mImagePath = ImageHelper.getRealPathFromURI(FaceInformationActivity.this, data.getData());
        }
    }


    // Pick Photo from the Phone's gallery
    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void pickPhotoFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, null);
        photoPickerIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_IMAGE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: Delegate the permission handling to generated method
        FaceInformationActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    // Upload the picked photo to LeanCloud
    private void uploadPhotoToLeanCloud() {
        try {
            mImageFile = AVFile.withAbsoluteLocalPath(AVUser.getCurrentUser().getUsername()+".jpg", mImagePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Upload the face photo to the LeanCloud
        mImageFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // Get the URL of the uploaded photo
                    String mPhotoUrl = mImageFile.getUrl();

                    // Update the last uploaded face photo to the RateFacePhoto table (class in LeanCloud)
                    datingFacePhotoId = MyInfoPreference.getStoredDatingFacePhotoId(FaceInformationActivity.this);

                    if (datingFacePhotoId != null) {
                        final AVObject datingFaceObject = AVObject.createWithoutData("DatingFacePhoto", datingFacePhotoId);
                        datingFaceObject.put("photoUrl", mPhotoUrl);

                        datingFaceObject.saveInBackground();
                    } else {
                        final AVObject datingFaceObject = new AVObject("DatingFacePhoto");
                        datingFaceObject.put("username", AVUser.getCurrentUser().getUsername());
                        datingFaceObject.put("userId", AVUser.getCurrentUser());
                        datingFaceObject.put("photoUrl", mPhotoUrl);

                        datingFaceObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                datingFacePhotoId = datingFaceObject.getObjectId();
                                MyInfoPreference.setStoredDatingFacePhotoId(FaceInformationActivity.this, datingFacePhotoId);
                            }
                        });
                    }


                    mUploadImageButton.setText(R.string.upload_face_photo);
                    Toast.makeText(FaceInformationActivity.this, R.string.uploading_success, Toast.LENGTH_SHORT).show();
                } else {
                    mUploadImageButton.setText(R.string.upload_face_photo);
                    Toast.makeText(FaceInformationActivity.this, R.string.uploading_fail, Toast.LENGTH_SHORT).show();
                }
            }
        }, new ProgressCallback() {
            @Override
            public void done(final Integer integer) {
                FaceInformationActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mUploadImageButton.setText("已经上传: " + integer + "%");
                    }
                });
            }
        });
    }


}
