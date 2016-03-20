package mak.livewire.geome;

import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.*;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends Activity {
Button next,done;
CheckBox checkBox;
    EditText t1,t2;
    Geocoder geocoder;
    AppLocationService appLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       final TextView tv=(TextView)findViewById(R.id.textView2);
        geocoder=new Geocoder(getApplicationContext());
         next=(Button)findViewById(R.id.next);
        checkBox=(CheckBox)findViewById(R.id.checkBox);
        t1=(EditText)findViewById((R.id.editText));
        t2=(EditText)findViewById((R.id.editText2));
//initialize
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final Editor editor = pref.edit();

        appLocationService = new AppLocationService(
                MainActivity.this);

        String name=pref.getString("name",null);
        String home=pref.getString("home",null);
        if(pref.getBoolean("initDone",false)) {
          //  Toast.makeText(getApplicationContext(), name + " " + home, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),tasks.class));
            finish();
        }  // go to task activity


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Address> list=new LinkedList<Address>();
                Address address;
                String name =t1.getText().toString();
                String home =t2.getText().toString();
                if(name.equalsIgnoreCase("")||(home.equalsIgnoreCase("")&&!checkBox.isChecked()))
                {
                    Toast.makeText(getApplicationContext(),"Enter Proper Name/Home",Toast.LENGTH_SHORT).show();
                    return;
                }
                editor.putString("name",name);
                editor.putString("home",home);
                editor.commit();


                Toast.makeText(getApplicationContext(),"Please Wait...",Toast.LENGTH_SHORT).show();

                if(!checkBox.isChecked())
                {try {
                    list=geocoder.getFromLocationName(home, 3);
                    //address=list.get(0);
                    //tv.setText(address.toString()); //for debug
                    //Toast.makeText(getApplicationContext(),address.toString(),Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }}
                else
                {
                    Location gpsLocation = appLocationService
                            .getLocation(LocationManager.GPS_PROVIDER);
                    if (gpsLocation != null) {
                        try {
                            list=geocoder.getFromLocation(gpsLocation.getLatitude(), gpsLocation.getLongitude(), 3);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                  else showSettingsAlert();

                }




                setContentView(R.layout.selecthome);

                setRadio(list);


            }
        }); // on click of button

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked())
                {t2.setEnabled(false);
                    t2.setText("");
                    t2.setHint("");
                  //  t2.setFocusable(false);
                }
                else
                {t2.setEnabled(true);
                t2.setHint("Home ( ex Chennai )");}
            }
        });
    }
int getRadioNumber(RadioButton[] rb,int id)
{for(int i=0;i<3;i++)
    if(rb[i].getId()==id) return i;
    return 0;}

void setRadio(final List<Address> list)
{final RadioButton[] rb=new RadioButton[3];
   final RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup);
    done=(Button)findViewById(R.id.done);
done.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(getApplicationContext(),tasks.class);
i.putExtra("home",list.get(getRadioNumber(rb,rg.getCheckedRadioButtonId())));
        //Toast.makeText(getApplicationContext(),getRadioNumber(rb,rg.getCheckedRadioButtonId())+"radio",Toast.LENGTH_SHORT).show();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final Editor editor = pref.edit();

        editor.putBoolean("initDone",true);

        editor.commit();

        startActivity(i);
        finish();
    }
});



//Toast.makeText(getApplicationContext(),list.size()+"",Toast.LENGTH_SHORT).show();
    for(int i=0;i<list.size();i++)
    {rb[i]=new RadioButton(getApplicationContext());
        rb[i].setTextColor(Color.BLACK);
        rb[i].setText(list.get(i).getFeatureName() + " , " + list.get(i).getAdminArea() + " , " + list.get(i).getPostalCode());
        rg.addView(rb[i],i);

    }
   // rg.addView(rb.setText(list.get(0).getFeatureName() + "," + list.get(0).getAdminArea() + "," + list.get(0).getPostalCode()));
   /* rb.setText(list.get(1).getFeatureName()+","+list.get(1).getAdminArea()+","+list.get(1).getPostalCode());
    rb.setText(list.get(2).getFeatureName()+","+list.get(2).getAdminArea()+","+list.get(2).getPostalCode());*/
}


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainActivity.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        MainActivity.this.startActivity(intent);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
