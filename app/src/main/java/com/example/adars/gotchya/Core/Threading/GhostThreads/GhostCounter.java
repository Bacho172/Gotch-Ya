package com.example.adars.gotchya.Core.Threading.GhostThreads;

import android.app.Activity;

import com.example.adars.gotchya.Core.Threading.ThreadHelper;

/**
 * Created by Adam Bachorz on 09.11.2018.
 */
public class GhostCounter extends ThreadHelper {

    private int counter = 0;
    private int jump = 1;

    public GhostCounter() {
        super();
    }

    public GhostCounter(Activity activity, long interval, int jump) {
        super(activity, interval);
        this.jump = jump;
    }

    @Override
    protected void onRun() {
        System.out.println("Licznik ze skokiem " + jump + ". Stan licznika: " + (counter += jump));
    }

    @Override
    protected String reviveSpell() {
        return "ReviveMe";
    }

    public void start() {startThread();}

    public void stop() {stopThread();}
}
