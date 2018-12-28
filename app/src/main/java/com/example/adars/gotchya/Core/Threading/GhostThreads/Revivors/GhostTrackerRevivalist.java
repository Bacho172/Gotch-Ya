package com.example.adars.gotchya.Core.Threading.GhostThreads.Revivors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.adars.gotchya.Core.Threading.GhostThreads.GhostTracker;
import com.example.adars.gotchya.Core.Threading.ThreadHelper;

/**
 * Created by Adam Bachorz on 28.12.2018.
 */
public class GhostTrackerRevivalist extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Class<?> ghostThreadClass = GhostTracker.class;
        System.out.println(ThreadHelper.getGhostThreadRevivedInfo(ghostThreadClass));
        context.startService(new Intent(context, ghostThreadClass));
    }
}
