package com.phc.cimplus.Others;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCompressor {
    private static final String TAG = "ImageCompressor";
    private static final long MAX_SIZE = 5 * 1024 * 1024; // 5 MB

    public static File getCompressedImageIfNeeded(Context context, String filePath) {
        if (filePath == null) {
            return null;
        }
        File originalFile = new File(filePath);
        if (!originalFile.exists()) {
            return originalFile;
        }

        long size = originalFile.length();
        if (size <= MAX_SIZE) {
            Log.d(TAG, "File size (" + size + " bytes) is <= 5MB. No compression needed.");
            return originalFile;
        }

        Log.d(TAG, "File size (" + size + " bytes) exceeds 5MB. Compressing...");
        File compressedFile = null;
        try {
            // Get original extension
            String extension = "";
            int lastDot = filePath.lastIndexOf('.');
            if (lastDot >= 0) {
                extension = filePath.substring(lastDot);
            }
            if (extension.isEmpty() || extension.length() > 5) {
                extension = ".jpg";
            }

            // Create a temp file in cache directory
            compressedFile = File.createTempFile("comp_", extension, context.getCacheDir());

            // Decode bitmap with inSampleSize set to downsample
            Bitmap bitmap = decodeSampledBitmapFromFile(filePath, 2048, 2048);
            if (bitmap == null) {
                Log.e(TAG, "Failed to decode bitmap from: " + filePath);
                return originalFile; // fallback to original on failure
            }

            // Compress to JPEG
            FileOutputStream fos = new FileOutputStream(compressedFile);
            boolean compressed = bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos); // 80% quality
            fos.flush();
            fos.close();
            bitmap.recycle();

            if (compressed && compressedFile.exists()) {
                Log.d(TAG, "Compression successful. New size: " + compressedFile.length() + " bytes");
                // Double check if compressed size is still > 5MB, if so, compress even further
                if (compressedFile.length() > MAX_SIZE) {
                    Log.d(TAG, "Compressed file still exceeds 5MB. Re-compressing with lower quality...");
                    Bitmap bmp = decodeSampledBitmapFromFile(compressedFile.getAbsolutePath(), 1600, 1600);
                    if (bmp != null) {
                        FileOutputStream fos2 = new FileOutputStream(compressedFile);
                        bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos2);
                        fos2.flush();
                        fos2.close();
                        bmp.recycle();
                        Log.d(TAG, "Second compression size: " + compressedFile.length() + " bytes");
                    }
                }
                return compressedFile;
            } else {
                Log.e(TAG, "Failed to compress bitmap");
                return originalFile;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error compressing image: " + e.getMessage(), e);
            if (compressedFile != null && compressedFile.exists()) {
                compressedFile.delete();
            }
            return originalFile; // fallback
        }
    }

    public static Bitmap decodeSampledBitmapFromFile(String filePath, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565; // Efficient memory config
        return BitmapFactory.decodeFile(filePath, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
