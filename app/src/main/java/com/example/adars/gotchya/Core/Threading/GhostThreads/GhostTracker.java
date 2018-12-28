package com.example.adars.gotchya.Core.Threading.GhostThreads;

import android.content.Context;

import com.example.adars.gotchya.Core.Threading.ThreadHelper;

/**
 * Created by Adam Bachorz on 28.12.2018.
 */
public class GhostTracker extends ThreadHelper {

    public GhostTracker() {
        super();
    }

    public GhostTracker(Context context, long interval) {
        super(context, interval, true);
    }

    @Override
    protected void onRun() {

    }

    @Override
    protected String reviveSpell() {
        return "KeepTracking";
    }

    public void start() {startThread();}

    public void stop() {stopThread();}
}
