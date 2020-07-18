package com.example.kulearmatnie;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Iterator;

public class KuleArmatnie extends GameObject {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int ROW_TOP_TO_BOTTOM = 0;
    private static final int ROW_RIGHT_TO_LEFT = 1;
    private static final int ROW_LEFT_TO_RIGHT = 2;
    private static final int ROW_BOTTOM_TO_TOP = 3;

    // Row index of Image are being used.
    private int rowUsing = ROW_LEFT_TO_RIGHT;

    private int colUsing;

    private Bitmap[] leftToRights;
    private Bitmap[] rightToLefts;
    private Bitmap[] topToBottoms;
    private Bitmap[] bottomToTops;


    // Velocity of game character (pixel/millisecond)
    public static final float VELOCITY = 0.5f;

    public int movingVectorX = 20;
    public int movingVectorY = 2;



    private long lastDrawNanoTime = -1;

    private GameSurface gameSurface;

    public KuleArmatnie(GameSurface gameSurface, Bitmap image, int x, int y,int statekX,int statekY, int currentStatekImage,
                        int enymyGetX, int enymyGetY) {
        super(image, 4, 3, x, y,statekX,statekY,currentStatekImage, enymyGetX, enymyGetY);


        this.gameSurface = gameSurface;

        this.topToBottoms = new Bitmap[colCount]; // 3  setting that the variable on the left side naw is a list with the same amount of elements as number of columns in bit map image
        this.rightToLefts = new Bitmap[colCount]; // 3
        this.leftToRights = new Bitmap[colCount]; // 3
        this.bottomToTops = new Bitmap[colCount]; // 3


        for (int col = 0; col < this.colCount; col++) {
            this.topToBottoms[col] = this.createSubImageAt(ROW_TOP_TO_BOTTOM, col); //creating and adding an imaga from bitma plik to every element of list
            this.rightToLefts[col] = this.createSubImageAt(ROW_RIGHT_TO_LEFT, col);
            this.leftToRights[col] = this.createSubImageAt(ROW_LEFT_TO_RIGHT, col);
            this.bottomToTops[col] = this.createSubImageAt(ROW_BOTTOM_TO_TOP, col);


        }
    }


    public Bitmap[] getMoveBitmaps() {
        switch (rowUsing) {
            case ROW_BOTTOM_TO_TOP:
                return this.bottomToTops;
            case ROW_LEFT_TO_RIGHT:
                return this.leftToRights;
            case ROW_RIGHT_TO_LEFT:
                return this.rightToLefts;
            case ROW_TOP_TO_BOTTOM:
                return this.topToBottoms;
            default:
                return null;
        }
    }

    public Bitmap getCurrentMoveBitmap() {
        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.colUsing];
    }


    public void update() {
        this.colUsing++;
        if (colUsing >= this.colCount) {
            this.colUsing = 0;
        }
        // Current time in nanoseconds
        long now = System.nanoTime();

        // Never once did draw.
        if (lastDrawNanoTime == -1) {
            lastDrawNanoTime = now;
        }
        // Change nanoseconds to milliseconds (1 nanosecond = 1000000 milliseconds).
        int deltaTime = (int) ((now - lastDrawNanoTime) / 1000000);

        // Distance moves
        float distance = VELOCITY * deltaTime;

        double movingVectorLength = Math.sqrt(movingVectorX * movingVectorX + movingVectorY * movingVectorY);


        // making that animation for kule depending on statek movement;

        int localStatekImage = this.currentStatekImage;
        int szybkośćKuli=30;


        //Tu ustalamy ruch kul w zalezności od ruchu statku i położenia przeciwnika


        if (localStatekImage == ROW_TOP_TO_BOTTOM || localStatekImage ==ROW_BOTTOM_TO_TOP) {

            if (statekX < enymyGetX) {

                this.rowUsing = ROW_TOP_TO_BOTTOM;
                this.x = x + szybkośćKuli;

                //Log.d(TAG, "localStatekImage wysłane z 2=  " + localStatekImage);
                //Log.d(TAG, "currentStatekImage wysłane z 2=  " + this.currentStatekImage);
                ///Log.d(TAG, "statekX wysłane z 2=  " + statekX);
                //Log.d(TAG, "enymyGetX wysłane z 2=  " + enymyGetX);

            } else {
                this.rowUsing = ROW_TOP_TO_BOTTOM;
                this.x = x -szybkośćKuli;

                //Log.d(TAG, "localStatekImage wysłane z 3=  " + localStatekImage);
               /// Log.d(TAG, "currentStatekImage wysłane z 3=  " + this.currentStatekImage);
               // Log.d(TAG, "statekX wysłane z 3=  " + statekX);
               // Log.d(TAG, "enymyGetX wysłane z 3=  " + enymyGetX);

            }

        }

        else {

            if (statekY < enymyGetY) {

                this.rowUsing = ROW_RIGHT_TO_LEFT;
                this.y = y +szybkośćKuli;
               // Log.d(TAG, "localStatekImage wysłane z 2=  " + localStatekImage);
               // Log.d(TAG, "currentStatekImage wysłane z 2=  " + this.currentStatekImage);
               // Log.d(TAG, "statekX wysłane z 2=  " + statekX);
               // Log.d(TAG, "enymyGetX wysłane z 2=  " + enymyGetX);

            } else {

                this.rowUsing = ROW_RIGHT_TO_LEFT;
                this.y = y -szybkośćKuli;
                //this.y = y + (int) (distance * movingVectorY / movingVectorLength);
               // Log.d(TAG, "localStatekImage wysłane z 3=  " + localStatekImage);
               // Log.d(TAG, "currentStatekImage wysłane z 3=  " + this.currentStatekImage);
               // Log.d(TAG, "statekX wysłane z 3=  " + statekX);
               // Log.d(TAG, "enymyGetX wysłane z 3=  " + enymyGetX);

            }

        }



    }




    public void draw(Canvas canvas) {
        Bitmap bitmap = this.getCurrentMoveBitmap(); // pobiera odpowiedni obrazek ze funkcji get current MoveBitmap
        canvas.drawBitmap(bitmap, x, y, null);// rysuje obiekt w danej pozycji x i y
        // Last draw time.
        this.lastDrawNanoTime = System.nanoTime();
    }


}

