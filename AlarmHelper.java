package com.example.dfma_app_656995;

import android.content.Context;
import android.content.Intent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class AlarmHelper implements Comparable<AlarmHelper>{
    private android.content.Context Context;
    private long Id;
    private String Title;
    private String Tag;
    private String Description;
    private long Date;
    private boolean Enabled;
    private int Event;
    private int Days;
    private long NextEvent;

    public static final int ONCE = 0;
    public static final int WEEKLY = 1;

    public static final int NEVER = 0;
    public static final int EVERY_DAY = 0x7f;

    public AlarmHelper (Context context)
    {
        Context = context;
        Id = 0;
        Title = "";
        Tag = "";
        Description = "";
        Date = System.currentTimeMillis();
        Enabled = true;
        Event = ONCE;
        Days = EVERY_DAY;
        update();
    }

    public long getId()
    {
        return Id;
    }

    public void setId(long id)
    {
        Id = id;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        Title = title;
    }

    public String getTag() {
        return Tag;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public void setTag(String tag) {
        this.Tag = tag;
    }

    public int getEvent()
    {
        return Event;
    }

    public void setEvent(int event)
    {
        Event = event;
        update();
    }

    public long getDate()
    {
        return Date;
    }

    public void setDate(long date)
    {
        Date = date;
        update();
    }

    public boolean getEnabled()
    {
        return Enabled;
    }

    public void setEnabled(boolean enabled)
    {
        Enabled = enabled;
    }

    public int getDays()
    {
        return Days;
    }

    public void setDays(int days)
    {
        Days = days;
        update();
    }

    public long getNextEvent()
    {
        return NextEvent;
    }

    public boolean getOutdated()
    {
        return NextEvent < System.currentTimeMillis();
    }

    public int compareTo(AlarmHelper aThat)
    {
        final long thisNext = getNextEvent();
        final long thatNext = aThat.getNextEvent();
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if (this == aThat)
            return EQUAL;

        if (thisNext > thatNext)
            return AFTER;
        else if (thisNext < thatNext)
            return BEFORE;
        else
            return EQUAL;
    }

    public void update()
    {
        Calendar now = Calendar.getInstance();

        if (Event == WEEKLY)
        {
            Calendar alarm = Calendar.getInstance();

            alarm.setTimeInMillis(Date);
            alarm.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

            if (Days != NEVER)
            {
                while (true)
                {
                    int day = (alarm.get(Calendar.DAY_OF_WEEK) + 5) % 7;

                    if (alarm.getTimeInMillis() > now.getTimeInMillis() && (Days & (1 << day)) > 0)
                        break;

                    alarm.add(Calendar.DAY_OF_MONTH, 1);
                }
            }
            else
            {
                alarm.add(Calendar.YEAR, 10);
            }

            NextEvent = alarm.getTimeInMillis();
        }
        else
        {
            NextEvent = Date;
        }

        Date = NextEvent;
    }

    public void toIntent(Intent intent)
    {
        intent.putExtra("com.taradov.alarmme.id", Id);
        intent.putExtra("com.taradov.alarmme.title", Title);
        intent.putExtra("com.taradov.alarmme.title", Tag);
        intent.putExtra("com.taradov.alarmme.title", Description);
        intent.putExtra("com.taradov.alarmme.title", Title);
        intent.putExtra("com.taradov.alarmme.date", Date);
        intent.putExtra("com.taradov.alarmme.alarm", Enabled);
        intent.putExtra("com.taradov.alarmme.occurence", Event);
        intent.putExtra("com.taradov.alarmme.days", Days);
    }

    public void fromIntent(Intent intent)
    {
        Id = intent.getLongExtra("com.taradov.alarmme.id", 0);
        Title = intent.getStringExtra("com.taradov.alarmme.tag");
        Title = intent.getStringExtra("com.taradov.alarmme.description");
        Title = intent.getStringExtra("com.taradov.alarmme.title");
        Date = intent.getLongExtra("com.taradov.alarmme.date", 0);
        Enabled = intent.getBooleanExtra("com.taradov.alarmme.alarm", true);
        Event = intent.getIntExtra("com.taradov.alarmme.occurence", 0);
        Days = intent.getIntExtra("com.taradov.alarmme.days", 0);
        update();
    }

    public void serialize(DataOutputStream dos) throws IOException
    {
        dos.writeLong(Id);
        dos.writeUTF(Title);
        dos.writeUTF(Tag);
        dos.writeUTF(Description);
        dos.writeLong(Date);
        dos.writeBoolean(Enabled);
        dos.writeInt(Event);
        dos.writeInt(Days);
    }

    public void deserialize(DataInputStream dis) throws IOException
    {
        Id = dis.readLong();
        Title = dis.readUTF();
        Tag = dis.readUTF();
        Description = dis.readUTF();
        Date = dis.readLong();
        Enabled = dis.readBoolean();
        Event = dis.readInt();
        Days = dis.readInt();
        update();
    }
}
