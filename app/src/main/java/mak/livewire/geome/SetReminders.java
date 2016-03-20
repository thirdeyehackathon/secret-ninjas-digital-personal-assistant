package mak.livewire.geome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class SetReminders extends Activity {
EditText at,forr,about,before;
    CheckBox checkBox;
    Button set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminders);
        at=(EditText)findViewById(R.id.editText3);
        forr=(EditText)findViewById(R.id.editText4);
        about=(EditText)findViewById(R.id.editText5);
        before=(EditText)findViewById(R.id.editText6);
        checkBox=(CheckBox)findViewById(R.id.checkBox2);
        set=(Button)findViewById(R.id.set);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked())
                {
                    at.setEnabled(false);

                }
                else at.setEnabled(true);
            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((at.getText().toString().equalsIgnoreCase("")&&!checkBox.isChecked())||forr.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter location and For",Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SqlHelper sqlHelper=new SqlHelper(getApplicationContext());
                String loc;
                float lat=0.0f,lon=0.0f,bf=10.0f;
                if(checkBox.isChecked())
                {loc="home";
                lat=pref.getFloat("homelat",0.0f);
                    lon=pref.getFloat("homelong",0.0f);
                }
                else {
                    loc = at.getText().toString();
//get location from gps
                    List<Address> list=new LinkedList<Address>();
                    try {
                       list = new Geocoder(getApplicationContext()).getFromLocationName(loc, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    lat=(float)list.get(0).getLatitude();
                    lon=(float)list.get(0).getLongitude();
                    if(!before.getText().toString().equalsIgnoreCase(""))
                   bf=Float.parseFloat(before.getText().toString());


                }
                ReminderRecord rr=new ReminderRecord(loc,lat,lon,forr.getText().toString(),about.getText().toString(),bf);
                sqlHelper.putRecord(rr);
            }
        });
    }


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                SetReminders.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        SetReminders.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    finish();
    }
}
