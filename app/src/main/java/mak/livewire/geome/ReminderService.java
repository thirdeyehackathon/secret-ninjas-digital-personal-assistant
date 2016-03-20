package mak.livewire.geome;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.*;

/**
 * Created by Mak on 19-03-2016.
 */
public class ReminderService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        // longs
        super.onStart(intent, startId);
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}
