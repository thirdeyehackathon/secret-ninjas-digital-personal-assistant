package mak.livewire.geome;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by Mak on 20-03-2016.
 */
public class SqlHelper extends SQLiteOpenHelper{
    public static final String dbName="mydb";
    public static final String tableName="reminders";
    Context appContext;
    public SqlHelper(Context context) {

        super(context, dbName, null, 1); //to create a db
    appContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + tableName + "("
                + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT," + "location" + " TEXT,"
                + "lat" + " REAL," +"lon" + " REAL," + "forr" + " TEXT," + "about" + " TEXT," + "before" + " REAL" +   ")";
        db.execSQL(CREATE_TABLE);
        Toast.makeText(appContext,"db created",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//currently not needed

    }

    public void putRecord(ReminderRecord record)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("location",record.location);
        values.put("lat",record.lat);
        values.put("lon",record.lon);
        values.put("forr",record.forr);
        values.put("about",record.about);
        values.put("before",record.before);
        db.insert(tableName, null, values);
        //Toast.makeText(appContext,values.toString(),Toast.LENGTH_SHORT).show();
        Toast.makeText(appContext,"Reminder set at "+record.location+" for "+record.forr,Toast.LENGTH_SHORT).show();
        db.close();
    }


    public List<ReminderRecord> getAllRecords() {
        List<ReminderRecord> reminderRecordsList = new ArrayList<ReminderRecord>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + tableName;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ReminderRecord contact = new ReminderRecord(cursor.getInt(0),cursor.getString(1),cursor.getFloat(2),cursor.getFloat(3),cursor.getString(4),cursor.getString(5),cursor.getFloat(6));

                // Adding contact to list
                reminderRecordsList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return reminderRecordsList;
    }

    public void deleteRecord(ReminderRecord contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, "id" + " = ?",
                new String[] { String.valueOf(contact.id) });
        db.close();
    }

}
