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
import android.widget.Toast;

import com.example.adars.gotchya.Core.Functions;
import com.example.adars.gotchya.Core.Threading.GhostThreads.GhostCounter;
import com.example.adars.gotchya.Core.Threading.ThreadHelper;
import com.example.adars.gotchya.DataModel.DataModel.UserModel;
import com.example.adars.gotchya.DataModel.DomainModel.ApplicationReport;
import com.example.adars.gotchya.DataModel.DomainModel.Device;
import com.example.adars.gotchya.DataModel.Repository.ApplicationReportRepository;
import com.example.adars.gotchya.Sensors.DeviceInfo;
import com.example.adars.gotchya.Sensors.SensorsDataCreator;
import com.example.adars.gotchya.Sensors.Sensors_data;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageButton imageButtonRun;
    private Button checkPOST;
    private boolean toggled = false;

    GhostCounter ghostCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
//        setTitle("Witaj '" + UserModel.getInstance().getCurrentUser().getLogin() + "'");
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

        long interval = ThreadHelper.convertToMillis(1, TimeUnit.SECONDS);
        ghostCounter = new GhostCounter(this, interval, true);
        startService(ghostCounter.getIntent());

        checkPOST = findViewById(R.id.button_check_POST);
        checkPOST.setOnClickListener((l) ->
                ApplicationReportRepository.getInstance().insert(ApplicationReportRepository.example()));
    }

    private void imageButtonRunClick() {
        toggled = !toggled;
        int drawableID = toggled ?
                R.drawable.button_wylacz_zabezpieczenie :
                R.drawable.button_uruchom_zabezpieczenie;
        imageButtonRun.setImageDrawable(getResources().getDrawable(drawableID));

        if (toggled) {
            DeviceInfo deviceInfo = new DeviceInfo();
            Device device = new Device();
            device.setID(111);
            device.setMacAddress(deviceInfo.getMAcAddress());
            Toast.makeText(this, device.getMacAddress(), Toast.LENGTH_LONG).show();

            Sensors_data sensorsData = SensorsDataCreator.createSensorData(this,"","");

            ApplicationReport report = new ApplicationReport();
            report.setCreatedAt(new Date());
            report.setUpdatedAt(new Date());
            report.setDeviceIP("192.168.1.1");
            report.setSpeed((0.1 + Math.random() * 10) + "");
            report.setNearestObject("Uniwersytet Kazimierza Wielkiego");
            report.setCoordinates(sensorsData.getLatitude() + sensorsData.getLongitde());

            //TODO: Zdjęcia z kamer do URL !!!
            report.setFrontCameraImage("");
            report.setBackCameraImage("");

            report.setDevice(device);

            //ApplicationReportRepository.getInstance().insert(report);
        }
        else {

        }
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
        stopService(ghostCounter.getIntent());
        super.onDestroy();
    }
}
