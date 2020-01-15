package com.example.drtherapist.common.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class AppHelper {
    public static byte[] getFileDataBitmap(Bitmap bitmp) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmp.compress(Bitmap.CompressFormat.WEBP, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public static byte[] getFileDataFromDrawable(Context context, Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public static byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        Log.e("Image_post ", "Image_post" + byteArrayOutputStream.toByteArray());
        return byteArrayOutputStream.toByteArray();
    }
}
