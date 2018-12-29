package com.example.adars.gotchya.Core.Threading;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.adars.gotchya.Core.ExtendedMap;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Adam Bachorz on 09.11.2018.
 */
public abstract class ThreadHelper extends Service {

    private Thread thread;
    private Intent intent;
    protected Activity activity;
    protected long interval = 100;
    protected boolean running, paused = false, infinite = true,
            immortal = false, continueWork = false;
    protected static ExtendedMap<Integer, String, Object> stickyValues;
    protected static String stickyClassName;
    protected static Integer stickyHashCode;

    static {
        stickyValues = new ExtendedMap<>();
    }

    protected abstract class STICKY_LABEL {
        public static final String INTERVAL = "interval";
        public static final String IMMORTAL = "immortal";
        public static final String UNINTERRUPTED = "uninterrupted";
        public static final String CONTINUE_WORK = "continueWork";
    }

    public ThreadHelper() {
        super();
        init();
    }

    public ThreadHelper(Activity activity, long interval) {
        super();
        this.interval = interval;
        this.activity = activity;
        intent = new Intent(activity.getApplicationContext(), getClass());
        init();
    }

    public ThreadHelper(Activity activity, long interval, boolean immortal) {
        super();
        this.interval = interval;
        this.activity = activity;
        this.immortal = immortal;
        intent = new Intent(activity.getApplicationContext(), getClass());
        stickyHashCode = hashCode();
        stickyValues.put(stickyHashCode,
                new String[] {STICKY_LABEL.INTERVAL, STICKY_LABEL.IMMORTAL, STICKY_LABEL.CONTINUE_WORK},
                new Object[] {interval, immortal, continueWork});
        init();
    }

    private void init() {
        stickyClassName = getClass().getSimpleName();
        boolean isImmortal = (boolean) stickyValues.get(stickyHashCode, STICKY_LABEL.IMMORTAL);
        System.out.println(getGhostThreadState(isImmortal));
        thread = new Thread(() -> {
            while (running) {
                if (paused) continue;
                onRun();
                if (!infinite) break;
                long interval = (long) stickyValues.get(stickyHashCode, STICKY_LABEL.INTERVAL);
                delay(interval);
            }
        });
    }

    protected void onStart() {
        System.out.println("KOD OBIEKTU " + (stickyHashCode != null ? stickyHashCode : "BRAK"));
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
        continueWork = (boolean) stickyValues.get(stickyHashCode, STICKY_LABEL.CONTINUE_WORK);
        if (!continueWork) startThread();
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        System.out.println("taskRem");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onExit();

        boolean immortal = (boolean) stickyValues.get(stickyHashCode, STICKY_LABEL.IMMORTAL);

        if (immortal) {

            if (reviveSpell() == null || reviveSpell().trim().equals("")) {
                try {
                    throw new Exception("Zaklęcie wskrzeszające nie zostało zaimplementowane");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            stickyValues.put(stickyHashCode, STICKY_LABEL.CONTINUE_WORK,  true);
            sendBroadcast(new Intent(reviveSpell()));
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
            Logger.getLogger(ThreadHelper.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ThreadHelper.class.getName()).log(Level.SEVERE, null, ex);
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

    public static String getGhostThreadState(boolean immortal) {
        return String.format("Autonomiczny wątek: '%s' został utworzony. \nBędzie on %s",
                stickyClassName,
                immortal ? "NIEŚMIERTELNY" : "ŚMIERTELNY");
    }

    public static String getGhostThreadRevivedInfo(Class <?> mClass) {
        return "Autonomiczny wątek '" + mClass.getSimpleName()
                + "' został wskrzeszony i będzie działał mimo wyłączonej aplikacji";
    }

    public boolean isInfinite() {
        return infinite;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public Intent getIntent() {
        return intent;
    }

    public Activity getActivity() {
        return activity;
    }
}
