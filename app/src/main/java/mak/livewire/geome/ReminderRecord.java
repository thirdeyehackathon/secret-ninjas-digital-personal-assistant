package mak.livewire.geome;

/**
 * Created by Mak on 20-03-2016.
 */
public class ReminderRecord {
    int id;
    String location;
    float lat,lon;
    String forr,about;
    float before;

    public ReminderRecord(String location,float lat,float lon,String forr,String about,float before)
    {
        this.location=location;
        this.lat=lat;
        this.lon=lon;
        this.forr=forr;
        this.about=about;
        this.before=before;

    }

    public ReminderRecord(int id,String location,float lat,float lon,String forr,String about,float before)
    {   this.id=id;
        this.location=location;
        this.lat=lat;
        this.lon=lon;
        this.forr=forr;
        this.about=about;
        this.before=before;

    }
}
