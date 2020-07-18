package com.example.kulearmatnie;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Guzik {


    private long lastDrawNanoTime =-1;

    private GameSurface gameSurface;
    private Bitmap guzikImage;

    protected final int WIDTH;
    protected final int HEIGHT;


    protected int x;
    protected int y;

    public Guzik(GameSurface gameSurface, Bitmap image, int x, int y) {



        this.gameSurface= gameSurface;
        this.guzikImage = image ;
        this.x=x;
        this.y=y;

        this.WIDTH = image.getWidth();
        this.HEIGHT = image.getHeight();


    }



    public void draw(Canvas canvas)  {
        Bitmap bitmap = guzikImage ; // pobiera odpowiedni obrazek ze funkcji get current MoveBitmap
        canvas.drawBitmap(bitmap,x, y, null);// rysuje obiekt w danej pozycji x i y
        // Last draw time.
        this.lastDrawNanoTime= System.nanoTime();
    }
    int getX()  {
        return this.x;
    }

    int getY()  {
        return this.y;
    }


    int getHeight() {
        return HEIGHT;
    }

    int getWidth() {
        return WIDTH;
    }




}
