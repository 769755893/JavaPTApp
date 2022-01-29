package myapp.example.myfinalproject.myapp.game.project;

import android.graphics.Bitmap;

public class ImagePiece {//储存图像和与之对应的id数值
    private int index;
    private int currentX;
    private int currentY;
    private Bitmap bitmap;

    public ImagePiece() {
    }
    public ImagePiece(int index, int x,int y,Bitmap bitmap) {
        this.index = index;
        this.currentX=x;
        this.currentY=y;
        this.bitmap = bitmap;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    @Override
    public String toString() {
        return "ImagePiece{" +
                "index=" + index +
                ", currentX=" + currentX +
                ", currentY=" + currentY +
                ", bitmap=" + bitmap +
                '}';
    }
}
