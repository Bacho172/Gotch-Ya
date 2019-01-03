package com.example.adars.gotchya;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.adars.gotchya.Core.Functions;
import com.example.adars.gotchya.Core.Threading.GhostThreads.GhostTracker;
import com.example.adars.gotchya.Core.Threading.ThreadHelper;
import com.example.adars.gotchya.DataModel.DataModel.UserModel;

import java.util.concurrent.TimeUnit;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageButton imageButtonRun;
    private Button checkPOST;
    private boolean toggled = false;

    private long listenerInterval = ThreadHelper.convertToMillis(100, TimeUnit.MILLISECONDS);
    private long sendingInterval = ThreadHelper.convertToMillis(5, TimeUnit.SECONDS);
    GhostTracker ghostTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setTitle(R.string.app_name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(view -> Snackbar.make(view, UserModel.getInstance().getCurrentUser().getLogin(), Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        imageButtonRun = findViewById(R.id.imageButtonToggleSecurity);
        imageButtonRun.setOnClickListener((l) -> imageButtonRunClick());

//        checkPOST = findViewById(R.id.button_check_POST);
//        checkPOST.setOnClickListener((l) ->
//                ApplicationReportRepository.getInstance().insert(ApplicationReportRepository.example()));

        ghostTracker = new GhostTracker(this, listenerInterval, sendingInterval);

    }

    private void imageButtonRunClick() {
        toggled = !toggled;
        int drawableID = toggled ?
                R.drawable.button_wylacz_zabezpieczenie :
                R.drawable.button_uruchom_zabezpieczenie;
        imageButtonRun.setImageDrawable(getDrawable(drawableID));

        if (toggled) {
            performHomeButtonPress();
            if (ghostTracker != null) {
                ghostTracker = new GhostTracker(this, listenerInterval, sendingInterval);
            }
            ghostTracker.start();
            startService(ghostTracker.getIntent());
        }
        else {
            ghostTracker.forceDeath();
            ghostTracker.stop();
            //stopService(ghostTracker.getIntent());
            //ghostTracker = null;
        }
    }

    private void performHomeButtonPress() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.nav_about_us:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.nav_log_out:
                UserModel.getInstance().logOut();
                Functions.clearUserDataConfig(this);
                startActivity(new Intent(this, LogInActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        stopService(ghostTracker.getIntent());
        super.onDestroy();
    }
}
