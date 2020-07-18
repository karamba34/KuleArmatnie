package com.example.kulearmatnie;



import android.graphics.Bitmap;

public  class GameObject {

    protected Bitmap image;



    protected final int rowCount;
    protected final int colCount;

    protected final int WIDTH;
    protected final int HEIGHT;

    protected final int width;


    protected final int height;
    protected int x;
    protected int y;
    protected int statekX;
    protected int statekY;

    protected int currentStatekImage;


    protected int enymyGetX;
    protected int enymyGetY;

    public GameObject(Bitmap image, int rowCount, int colCount, int x, int y,int statekX, int statekY,int currentStatekImage ,int enymyGetX,int enymyGetY)  {

        this.image = image;
        this.rowCount= rowCount;
        this.colCount= colCount;

        this.x= x;
        this.y= y;

        this.statekX=statekX;
        this.statekY=statekY;

        this.WIDTH = image.getWidth();
        this.HEIGHT = image.getHeight();

        this.width = this.WIDTH/ colCount;
        this.height= this.HEIGHT/ rowCount;

        this.currentStatekImage= currentStatekImage;

        this.enymyGetX=enymyGetX;
        this.enymyGetY=enymyGetY;
    }


    protected Bitmap createSubImageAt(int row, int col)  {
        // createBitmap(bitmap, x, y, width, height).
        Bitmap subImage = Bitmap.createBitmap(image, col* width, row* height ,width,height);
        return subImage;
    }

    public int getX()  {
        return this.x;
    }

    public int getY()  {
        return this.y;
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}