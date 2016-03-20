package mak.livewire.geome;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;


public class ShowReminders extends Activity {
LinearLayout ll;


    Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reminders);
        ll = (LinearLayout)findViewById(R.id.ll);
        delete=(Button)findViewById(R.id.button);
        final SqlHelper sqlHelper=new SqlHelper(this);
        final List<ReminderRecord> list=sqlHelper.getAllRecords();
        final int count=list.size();
        final CheckBox[] cbs=new CheckBox[count];

        if(count==0)
        {TextView temp=new TextView(this);
            temp.setText("No Reminder Records available");
            temp.setTextColor(Color.BLACK);
            temp.setPadding(200,200,0,0);
            ll.addView(temp);
            delete.setEnabled(false);
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<count;i++)
                    if(cbs[i].isChecked())
                        sqlHelper.deleteRecord(list.get(i));
                startActivity(new Intent(getApplicationContext(),ShowReminders.class));
                finish();

            }
        });



        for(int i=0;i<count;i++)
        {
            cbs[i]=new CheckBox(getApplicationContext());
            cbs[i].setText("At "+list.get(i).location+" for "+list.get(i).forr);
            cbs[i].setTextColor(Color.BLACK);
            ll.addView(cbs[i]);
        }






    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
