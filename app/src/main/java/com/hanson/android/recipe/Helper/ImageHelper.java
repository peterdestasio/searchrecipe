package com.hanson.android.recipe.Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

/**
 * Created by lily on 2017-03-15.
 */

public class ImageHelper
{

    public byte[] getByteArrayFromDrawable(Drawable d)
    {
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();

        return data;
    }

    public byte[] getByteArrayFromBitmap(Bitmap d)
    {

        // convert bitmap to byte

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        d.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byte imageInByte[] = stream.toByteArray();
        return imageInByte;
    }

    public Bitmap getBitmapFromByteArray(byte[] b)
    {
        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        return bitmap;
    }

    public Bitmap resizeImage(Bitmap pBitmap){
        int height = pBitmap.getHeight();
        int width = pBitmap.getWidth();

        Bitmap resizedImg = Bitmap.createScaledBitmap(pBitmap, 350, 200, true);
        return resizedImg;
    }

    public Bitmap getThubmail(Bitmap pBitmap){
        int height = pBitmap.getHeight();
        int width = pBitmap.getWidth();

        Bitmap resizedImg = Bitmap.createScaledBitmap(pBitmap, (width * 90) / 90, 90, true);
        return resizedImg;
    }
}
