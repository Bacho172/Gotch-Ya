package com.example.adars.gotchya.Core.Threading.Services;

import android.content.Context;

/**
 * Created by Adam Bachorz on 09.11.2018.
 */
public class GhostCounter extends GhostThreadHelper {

    private  int counter = 0;

    public GhostCounter() {
        super();
    }

    public GhostCounter(Context context, long interval, boolean immortal, boolean uninterrupted) {
        super(context, interval, immortal, uninterrupted);
    }

    public GhostCounter(Context context, long interval, boolean immortal, boolean uninterrupted, boolean infinite) {
        super(context, interval, immortal, uninterrupted, infinite);
    }

    @Override
    protected void onRun() {
        System.out.println(counter++);
    }

    @Override
    protected String reviveSpell() {
        return "ReviveMe";
    }

    public void start() {startThread();}

    public void stop() {stopThread();}
}
