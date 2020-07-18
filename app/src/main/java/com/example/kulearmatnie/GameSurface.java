package com.example.kulearmatnie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MainActivity.class.getSimpleName();

    private GameThread gameThread;

    private final List<StatekCharakter> statekCharakterList = new ArrayList<StatekCharakter>();
    private final List<KuleArmatnie> kuleArmatnieList = new ArrayList<KuleArmatnie>();
    private final List<Guzik> guzikList = new ArrayList<Guzik>();
    private final List<EnymyCharakter> enymyCharakterList = new ArrayList<EnymyCharakter>();


    private static final int MAX_STREAMS=100;
    //private int soundIdExplosion;
    //private int soundIdBackground;

    //private boolean soundPoolLoaded;
   // private SoundPool soundPool;



    public GameSurface(Context context)  {
        super(context);

        // Make Game Surface focusable so it can handle events.
        this.setFocusable(true);

        // Sét callback.
        this.getHolder().addCallback(this);

        //this.initSoundPool();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Bitmap guzikBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.guzik);
        Guzik guzik = new Guzik(this,guzikBitmap1,50,700);

        if (event.getAction() == MotionEvent.ACTION_DOWN ) {

            int x=  (int)event.getX();
            int y = (int)event.getY();
            // In this code when hou klik ewery wewre accecpt button ju set another curse for the ship
            if ( guzik.getX() > x || x > guzik.getX() + guzik.getWidth() && guzik.getY() > y || y > guzik.getY()+ guzik.getHeight()) { // nowy kawałek kodu 14.03.2020


                for (StatekCharakter statek : statekCharakterList) {
                    int movingVectorX = x - statek.getX();
                    int movingVectorY = y - statek.getY();

                    statek.setMovingVector(movingVectorX, movingVectorY);

                }
                return true;
//
            }
            // this code is executed when you destroy enymy ship
            if (enymyCharakterList.isEmpty()) {

                    if (guzik.getX() < x && x < guzik.getX() + guzik.getWidth() && guzik.getY() < y && y < guzik.getY() + guzik.getHeight()) {

                        Iterator<StatekCharakter> iterator = this.statekCharakterList.iterator();
                        StatekCharakter statek = iterator.next();
                        int currentStatekImage = statek.getRowUsing();
                        int statekX = statek.getX();
                        int statekY = statek.getY();
                        // Create KuleArmatnie object.
                        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.bullets);
                        KuleArmatnie kule = new KuleArmatnie(this, bitmap, statek.getX(), statek.getY(), statekX, statekY, currentStatekImage, 0, 0);

                        // przypisanie wektora ruchu statku do zmiennej w klasie kule armatnie gdy kliknie się na statek


                        this.kuleArmatnieList.add(kule);
                    }

            }
            // this code is eceutted if enymy ship is still exist.
            else {
                    Iterator<StatekCharakter> iterator = this.statekCharakterList.iterator();
                    StatekCharakter statek = iterator.next();

                    Iterator<EnymyCharakter> iterator2 = this.enymyCharakterList.iterator();
                    EnymyCharakter enymy = iterator2.next();


                    if (guzik.getX() < x && x < guzik.getX() + guzik.getWidth() && guzik.getY() < y && y < guzik.getY() + guzik.getHeight()) {


                        int enymyGetX = enymy.getX();
                        int enymyGetY = enymy.getY();
                        int currentStatekImage = statek.getRowUsing();
                        int statekX = statek.getX();
                        int statekY = statek.getY();

                        // Create KuleArmatnie object.
                        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.bullets);
                        KuleArmatnie kule = new KuleArmatnie(this, bitmap, statek.getX(), statek.getY(), statekX, statekY, currentStatekImage, enymyGetX, enymyGetY);

                        // przypisanie wektora ruchu statku do zmiennej w klasie kule armatnie gdy kliknie się na statek


                        this.kuleArmatnieList.add(kule);
                    }
            }
        }


        return false;
    }

    public void update() {


        for (StatekCharakter statek : statekCharakterList) {
            statek.update();
        }
        for (KuleArmatnie kule : this.kuleArmatnieList){




            //kule.update();
            if(enymyCharakterList.isEmpty()){

                kule.update();
            }
            else {
                Iterator<EnymyCharakter> enymyIterator = this.enymyCharakterList.iterator();
                EnymyCharakter enymy = enymyIterator.next();
                //Iterator<EnymyCharakter> enymyIterator = this.enymyCharakterList.iterator();


                if (enymy.getX() < kule.getX() && kule.getX() < enymy.getX() + enymy.getWidth() &&
                        enymy.getY() < kule.getY() && enymy.getY() + enymy.getHeight() > kule.getY()) {
                    int enymyLifie = enymy.getEnymyStatekLife() - 5;
                    enymy.setEnymyStatekLife(enymyLifie);

                    kule.update();

                    Log.d(TAG, "stan zdrowia Wroga =  " + enymy.getEnymyStatekLife());
                    Log.d(TAG, "pozycja x statku wroga =  " + enymy.getX());
                    //Log.d(TAG, "pozycja y statku wroga =  " + enymy.getY());
                    //Log.d(TAG, "pozycja x  kuli =  " + kule.getX());
                    // Log.d(TAG, "pozycja y  kuli =  " + kule.getY());
                    continue;

                } else {

                    kule.update();
                    continue;
                }



            }




        }
        for (EnymyCharakter enymy : this.enymyCharakterList) {

            enymy.update();

        }
        Iterator<EnymyCharakter> enymyIterator = this.enymyCharakterList.iterator();
        while(enymyIterator.hasNext()) {
            EnymyCharakter enymy = enymyIterator.next();


            if (enymy.getEnymyStatekLife() == 0) {

                enymyIterator.remove();
                continue;
            }
        }





    }




    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);

        for(StatekCharakter statek: statekCharakterList)  {
            statek.draw(canvas);
        }

        for(KuleArmatnie kule: this.kuleArmatnieList)  {
            kule.draw(canvas);
        }
        for(Guzik guzik: this.guzikList)  {
            guzik.draw(canvas);
        }
        for(EnymyCharakter enymy: this.enymyCharakterList)  {
            enymy.draw(canvas);
        }

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Bitmap statekBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.statek);
        StatekCharakter statek = new StatekCharakter(this,statekBitmap1,1000,50);

        Bitmap guzikBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.guzik);
        Guzik guzik = new Guzik(this,guzikBitmap1,50,700);

        Bitmap enymyBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.enymy);
        EnymyCharakter enymy = new EnymyCharakter(this,enymyBitmap1,100,50);


        this.statekCharakterList.add(statek);
        this.guzikList.add(guzik);
        this.enymyCharakterList.add(enymy);

        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }

}