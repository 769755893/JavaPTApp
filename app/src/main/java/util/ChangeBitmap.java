package util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class ChangeBitmap {
    public Bitmap changeBitmap(Bitmap bitmap, int newWidth, int newHeight){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        newWidth = 100;
        newHeight = 100;

        float scaleWidth = ((float) newWidth)/width;
        float scaleHeight = ((float) newHeight)/height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        bitmap = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
        return bitmap;
    }
}
