package myapp.example.myfinalproject.myapp.game.project;

import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.List;

public class ImageSplit {
    //传入bitmap,切成piece块，返回图片列。
    public static List<ImagePiece> splitImage(Bitmap bitmap,int piece){
        List<ImagePiece>imagePieces=new ArrayList<ImagePiece>();
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        int pieceWidth=Math.min(width,height)/piece;
        //生产piece个小块
        for(int i=0;i<piece;i++) {
            for (int j = 0; j < piece; j++) {
                if((i==piece-1)&&(j==piece-1))break;//给最右下角的位置空出来！
                ImagePiece imagePiece = new ImagePiece();
                imagePiece.setIndex(j + i * piece);
                int x = j * pieceWidth;
                int y = i * pieceWidth;
                imagePiece.setBitmap(Bitmap.createBitmap(bitmap, x, y, pieceWidth, pieceWidth));
                imagePieces.add(imagePiece);//加入切片；
            }
        }
        return imagePieces;
    }
}