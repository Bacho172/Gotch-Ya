package com.example.adars.gotchya.Core.Threading.Services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Adam Bachorz on 09.11.2018.
 */
public abstract class GhostThreadHelper extends Service {

    private Thread thread;
    private Context context;
    protected boolean running, paused = false, infinite = true;
    protected static boolean stickyUninterrupted, stickyImmortal = true, continueWork = true;
    protected static long stickyInterval = 100;
    protected static String stickyClassName;




    public GhostThreadHelper() {
        super();
        init();
    }

    public GhostThreadHelper(Context context, long interval, boolean immortal, boolean uninterrupted) {
        super();
        stickyInterval = interval;
        this.context = context;
        stickyImmortal = immortal;
        stickyUninterrupted = uninterrupted;
        init();
    }

    public GhostThreadHelper(Context context, long interval, boolean immortal, boolean uninterrupted, boolean infinite) {
        super();
        stickyInterval = interval;
        stickyImmortal = immortal;
        stickyUninterrupted = uninterrupted;
        this.infinite = infinite;
        this.context =  context;
        init();
    }

    private void init() {
        stickyClassName = getClass().getSimpleName();
        System.out.println(getGhostThreadState());
        thread = new Thread(() -> {
            while(running) {
                if (paused) continue;
                onRun();
                if (!infinite) break;
                delay(stickyInterval);
            }
            onExit();
        });
    }

    protected void onStart() {
    }
    protected void onExit() {
    }
    protected abstract void onRun();
    protected abstract String reviveSpell();


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStart();
        if (continueWork) {
            startThread();
            continueWork = false;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onExit();

        if (stickyImmortal) {

            if (reviveSpell() == null) {
                try {
                    throw new Exception("Zaklęcie wskrzeszające nie zostało zaimplementowane");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            sendBroadcast(new Intent(reviveSpell()));

            if (stickyUninterrupted) {
                continueWork = true;
            }
            else {
                stopThread();
                thread = null;
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static boolean threadIsRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                System.out.println("WĄTEK DZIAŁA");
                return true;
            }
        }
        System.out.println("WĄTEK NIE DZIAŁA");
        return false;
    }

    public static void delay(long interval) {
        try {
            Thread.sleep(interval);
        } catch (InterruptedException ex) {
            Logger.getLogger(GhostThreadHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static long convertToMillis(float interval, TimeUnit timeUnit) {
        if (timeUnit == TimeUnit.MICROSECONDS) {
            throw new RuntimeException("Niedozwolona jednostka !");
        }
        if (timeUnit == TimeUnit.MILLISECONDS) {
            return (long) interval;
        }
        long prefix = 1;
        switch(timeUnit) {
            case MINUTES:
                prefix = 60;
                break;
            case HOURS:
                prefix = 3600;
                break;
        }
        return (long) (prefix * (interval * 1000));
    }

    public static void runAsync(Runnable toDo) {
        new Thread(toDo).start();
    }

    public static void runAsync(Runnable[] tasks) {
        for (Runnable task : tasks) {
            runAsync(task);
        }
    }

    protected void startThread() {
        if (!running) {
            running = true;
        } else return;
        thread.start();
    }

    protected void stopThread() {
        if (running) {
            running = false;
        } else return;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(GhostThreadHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void pauseThread() {
        if (!paused) paused = true;
        System.out.println(getThreadState());
    }

    protected void resumeThread() {
        if (paused) paused = false;
        System.out.println(getThreadState());
    }

    public String getThreadState() {
        return "Wątek: '" + getClass().getSimpleName() + "' został " + (running ? "URUCHOMIONY" : "ZATRZYMANY");
    }

    public static String getGhostThreadState() {
        return String.format("Autonomiczny wątek: '%s' został utworzony. \nBędzie on %s oraz %s %s",
                stickyClassName,
                stickyImmortal ? "NIEŚMIERTELNY" : "ŚMIERTELNY",
                stickyUninterrupted ? "BĘDZIE KONTUNUOWAŁ SWOJE DZIAŁANIE" : "ROZPOCZNIE SWOJE DZIAŁANIE OD NOWA",
                stickyImmortal ? "po wskrzeszeniu" : "");
    }

    public static String getGhostThreadRevivedInfo(Class <?> mClass) {
        return "Autonomiczny wątek '" + mClass.getSimpleName()
                + "' został wskrzeszony i będzie działał mimo wyłączonej aplikacji";
    }

    public boolean isInfinite() {
        return infinite;
    }

    public long getInterval() {
        return stickyInterval;
    }

    public void setInterval(long interval) {
        stickyInterval = interval;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isUninterrupted() {
        return stickyUninterrupted;
    }

    public boolean isImmortal() {
        return stickyImmortal;
    }
}
