package mak.livewire.geome;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class tasks extends Activity {
TextView greetings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        startService(new Intent(getApplicationContext(),ReminderService.class));
        Intent i=getIntent();
        SharedPreferences  pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); //to get i/p name and home addresss
        final SharedPreferences.Editor editor = pref.edit();
        Address homeAddress = i.getParcelableExtra("home");
        if(homeAddress!=null) {
           // gets address recieved
            editor.putFloat("homelat", (float) homeAddress.getLatitude());
            editor.putFloat("homelong", (float) homeAddress.getLongitude());
            editor.commit();
            Toast.makeText(getApplicationContext(), pref.getFloat("homelat",0.01f) + "" + pref.getFloat("homelong",0.0f), Toast.LENGTH_SHORT).show();

          // Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_SHORT).show();


        }

        greetings=(TextView)findViewById(R.id.greetings);


        greetings.setText("Hello "+ pref.getString("name","")+" :) ");// init of tasks till here



final Button setreminders=(Button)findViewById(R.id.setR);
        final Button showreminders=(Button)findViewById(R.id.show);

        showreminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ShowReminders.class));
            }
        });

setreminders.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(),SetReminders.class));
    }
});

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tasks, menu);
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
