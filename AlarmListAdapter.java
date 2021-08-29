/**************************************************************************
 *
 * Copyright (C) 2012-2015 Alex Taradov <alex@taradov.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *************************************************************************/

package com.example.dfma_app_656995;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.content.Intent;
import android.content.Context;
import android.widget.TextView;
import android.widget.BaseAdapter;

import androidx.annotation.RequiresApi;

class AlarmListAdapter extends BaseAdapter
{
  private final String TAG = "AlarmMe";

  private Context Context;
  private DataSource DataSource;
  private LayoutInflater Inflater;
  private DateTime DateTime;
  private int Passed;
  private int Still;
  private AlarmManager AlarmManager;

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public AlarmListAdapter(Context context)
  {
    Context = context;
    DataSource = DataSource.getInstance(context);

    Log.i(TAG, "AlarmListAdapter.create()");

    Inflater = LayoutInflater.from(context);
    DateTime = new DateTime(context);

    Passed = Context.getResources().getColor(R.color.alarm_title_outdated);
    Still = Context.getResources().getColor(R.color.alarm_title_active);

    AlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

    dataSetChanged();
  }

  public void save()
  {
    DataSource.save();
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public void update(AlarmHelper alarm)
  {
    DataSource.update(alarm);
    dataSetChanged();
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public void updateAlarms()
  {
    Log.i(TAG, "AlarmListAdapter.updateAlarms()");
    for (int i = 0; i < DataSource.size(); i++)
      DataSource.update(DataSource.get(i));
    dataSetChanged();
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public void add(AlarmHelper alarm)
  {
    DataSource.add(alarm);
    dataSetChanged();
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public void delete(int index)
  {
    cancelAlarm(DataSource.get(index));
    DataSource.remove(index);
    dataSetChanged();
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public void onSettingsUpdated()
  {
    DateTime.update();
    dataSetChanged();
  }

  public int getCount()
  {
    return DataSource.size();
  }

  public AlarmHelper getItem(int position)
  {
    return DataSource.get(position);
  }

  public long getItemId(int position)
  {
    return position;
  }

  public View getView(int position, View convertView, ViewGroup parent)
  {
    ViewHolder holder;
    AlarmHelper alarm = DataSource.get(position);

    if (convertView == null)
    {
      convertView = Inflater.inflate(R.layout.list_item, null);

      holder = new ViewHolder();
      holder.title = (TextView)convertView.findViewById(R.id.item_title);
      //holder.tag = (TextView)convertView.findViewById(R.id.item_tag);
      //holder.description = (TextView)convertView.findViewById(R.id.item_description);
      holder.details = (TextView)convertView.findViewById(R.id.item_details);

      convertView.setTag(holder);
    }
    else
    {
      holder = (ViewHolder)convertView.getTag();
    }
  
    holder.title.setText(alarm.getTitle());
    //holder.tag.setText(alarm.getTag());
    //holder.description.setText(alarm.getDescription());
    holder.details.setText(DateTime.formatDetails(alarm) + (alarm.getEnabled() ? "" : " [disabled]"));

    if (alarm.getOutdated())
      holder.title.setTextColor(Passed);
    else
      holder.title.setTextColor(Still);

    return convertView;
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  private void dataSetChanged()
  {
    for (int i = 0; i < DataSource.size(); i++)
      setAlarm(DataSource.get(i));

    notifyDataSetChanged();
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  private void setAlarm(AlarmHelper alarm)
  {
    PendingIntent sender;
    Intent intent;

    if (alarm.getEnabled() && !alarm.getOutdated())
    {
      intent = new Intent(Context, AlarmReceiver.class);
      alarm.toIntent(intent);
      sender = PendingIntent.getBroadcast(Context, (int)alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
      AlarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.getDate(), sender);
      Log.i(TAG, "AlarmListAdapter.setAlarm(" + alarm.getId() + ", '" + alarm.getTitle() + "', '" + alarm.getTag() + "', '" + alarm.getDescription() + "', " + alarm.getDate()+")");
    }
  }

  private void cancelAlarm(AlarmHelper alarm)
  {
    PendingIntent sender;
    Intent intent;

    intent = new Intent(Context, AlarmReceiver.class);
    sender = PendingIntent.getBroadcast(Context, (int)alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    AlarmManager.cancel(sender);
  }

  static class ViewHolder
  {
    TextView description;
    TextView tag;
    TextView title;
    TextView details;
  }
}

