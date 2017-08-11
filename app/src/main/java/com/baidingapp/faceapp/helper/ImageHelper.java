package com.baidingapp.faceapp.helper;

import android.content.Context;
import android.widget.ImageView;

import com.baidingapp.faceapp.GlideApp;
import com.baidingapp.faceapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class ImageHelper {

    // ImageLoad Method
    public static void ImageLoad(Context context, String imageUrl, ImageView imageView) {
        GlideApp.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.face_image)
                .error(R.drawable.face_image)
                .into(imageView);
    }


    // Copy the UPLOADED image from the external storage to the internal storage of the App
    public static void copyImage(File externalImage, File internalImage) throws IOException {
        FileChannel externalChannel = new FileInputStream(externalImage).getChannel();
        FileChannel internalChannel = new FileOutputStream(internalImage).getChannel();

        if (externalChannel != null) {
            try {
                externalChannel.transferTo(0, externalChannel.size(), internalChannel);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                externalChannel.close();
                internalChannel.close();
            }
        }
    }
}














