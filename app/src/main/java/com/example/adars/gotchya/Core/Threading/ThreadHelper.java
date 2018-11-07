package com.example.adars.gotchya.Core.Threading;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Adam Bachorz on 07.11.2018.
 */
public abstract class ThreadHelper {
    private Thread thread;
    protected boolean running, paused = false, infinite = true;
    protected long interval = 100;

    public ThreadHelper(long interval) {
        this.interval = interval;
        init();
    }

    public ThreadHelper(long interval, boolean infinite) {
        this.interval = interval;
        this.infinite = infinite;
        init();
    }

    private void init() {
        thread = new Thread(() -> {
            onStart();
            while(running) {
                if (paused) continue;
                onRun();
                if (!infinite) break;
                delay(interval);
            }
            onExit();
        });
    }

    protected void onStart() {
    }
    protected void onExit() {
    }
    protected abstract void onRun();

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
        }
        thread.start();
        System.out.println(getThreadState());
    }

    protected void stopThread() {
        if (running) {
            running = false;
            System.out.println(getThreadState());
        }
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
}
