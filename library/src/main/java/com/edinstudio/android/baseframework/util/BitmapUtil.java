package com.edinstudio.android.baseframework.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by albert on 15-5-20.
 */
public class BitmapUtil {
    public static double calScaleRatio(int originalWidth, int originalHeight, int targetWidth, int targetHeight) {
        if (originalWidth == 0 || originalHeight == 0) {
            return 1;
        } else {
            double ratio = Math.sqrt((targetWidth * targetHeight * 1.0) / (originalWidth * originalHeight));
            if (ratio > 1) {
                return 1;
            } else {
                return ratio;
            }
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int targetWidth, int targetHeight, boolean needPortrait) {
        if (bitmap == null) {
            return null;
        }

        float ratio = (float) calScaleRatio(bitmap.getWidth(), bitmap.getHeight(), targetWidth, targetHeight);
        Matrix matrix = new Matrix();
        matrix.postScale(ratio, ratio);
        if (needPortrait && bitmap.getWidth() > bitmap.getHeight()) {
            matrix.postRotate(90);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap scaleBitmap(String filename, int targetWidth, int targetHeight, boolean needPortrait) {
        if (TextUtils.isEmpty(filename)) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);
        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);

        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filename, options);
        return scaleBitmap(bitmap, targetWidth, targetHeight, needPortrait);
    }

    public static File bitmapToFile(Bitmap bitmap, File dir, String filename) {
        if (dir == null || TextUtils.isEmpty(filename)) {
            return null;
        }

        File file = new File(dir, filename);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArray = baos.toByteArray();

            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(byteArray);

            return file;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
}
