package com.example.adars.gotchya.Core.Threading.GhostThreads;

import com.example.adars.gotchya.Core.Threading.ThreadHelper;

/**
 * Created by Adam Bachorz on 28.12.2018.
 */
public class GhostTracker extends ThreadHelper {

    @Override
    protected void onRun() {

    }

    @Override
    protected String reviveSpell() {
        return "keepTracking";
    }
}
