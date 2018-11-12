package com.example.adars.gotchya.Core.Threading.GhostThreads;

import android.content.Context;

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

    public GhostCounter(Context context, long interval, boolean immortal) {
        super(context, interval, immortal);
    }

    public GhostCounter(int jump, Context context, long interval, boolean immortal) {
        super(context, interval, immortal);
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
