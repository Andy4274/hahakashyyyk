package com.jassoftware.textmeeting;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayMeetingActivity extends Activity {

	Intent i;
	Button done;
	Button add;
	TextView title;
	TextView org;
	TextView loc;
	TextView start;
	TextView end;
	TextView last;
	TextView recur;
	TextView attendees;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScrollView frame = (ScrollView)findViewById(R.id.frame);
        LinearLayout filler = new LinearLayout(this);
        filler = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.display, null);
        frame.addView((View)filler);
        //find views
        done = (Button)findViewById(R.id.dis_done);
        add = (Button)findViewById(R.id.dis_add);
        title = (TextView)findViewById(R.id.dis_title);
        org = (TextView)findViewById(R.id.dis_org);
        loc = (TextView)findViewById(R.id.dis_loc);
        start = (TextView)findViewById(R.id.dis_start);
        end = (TextView)findViewById(R.id.dis_end);
        last = (TextView)findViewById(R.id.dis_last);
        recur = (TextView)findViewById(R.id.dis_rec);
        attendees = (TextView)findViewById(R.id.dis_att);
        //set up butons
        done.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		finish();
        	}
        });
        add.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		sendToCalendar();
        	}
        });
        //fill fields
        i = getIntent();
        title.setText(i.getStringExtra("title"));
        org.setText(i.getStringExtra("org"));
        loc.setText(i.getStringExtra("loc"));
        start.setText(getDateTime(i.getStringExtra("start")));
        end.setText(getDateTime(i.getStringExtra("end")));
        if (i.getStringExtra("last").equalsIgnoreCase("null")){
        	last.setText(i.getStringExtra("last"));
        } else {
        	last.setText(i.getStringExtra("last") + " minutes");
        }
        recur.setText(i.getStringExtra("recur"));
        attendees.setText(i.getStringExtra("att"));
        
	}

	private CharSequence getDateTime(String time) {
		if (time.equalsIgnoreCase("null")){
			return time;
		}
		String data;
		int year, month, day, hour, minute, ap;
		Calendar c = Calendar.getInstance();
		long TZ = c.getTimeZone().getOffset(c.getTimeInMillis());
		long datetime = Long.parseLong(time);
		datetime = datetime + TZ;
		c.setTimeInMillis(datetime);
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR);
		minute = c.get(Calendar.MINUTE);
		ap = c.get(Calendar.AM_PM);
		data = time + ":  ";
		data=data+Integer.toString(year) + " ";
		switch (month){
			case Calendar.JANUARY:
				data = data + "Jan. ";
				break;
			case Calendar.FEBRUARY:
				data = data + "Feb. ";
				break;
			case Calendar.MARCH:
				data = data + "Mar. ";
				break;
			case Calendar.APRIL:
				data = data + "Apr. ";
				break;
			case Calendar.MAY:
				data = data + "May ";
				break;
			case Calendar.JUNE:
				data = data + "Jun. ";
				break;
			case Calendar.JULY:
				data = data + "Jul. ";
				break;
			case Calendar.AUGUST:
				data = data + "Aug. ";
				break;
			case Calendar.SEPTEMBER:
				data = data + "Sep. ";
				break;
			case Calendar.OCTOBER:
				data = data + "Oct. ";
				break;
			case Calendar.NOVEMBER:
				data = data + "Nov. ";
				break;
			case Calendar.DECEMBER:
				data = data + "Dec. ";
				break;
			default:
		}
		data = data + Integer.toString(day) + "  ";
		data = data + Integer.toString(hour) + ":";
		if (minute<10){
			data = data + "0";
		}
		data = data + Integer.toString(minute) + " ";
		switch (ap){
			case Calendar.AM:
				data = data + "AM";
				break;
			case Calendar.PM:
				data = data + "PM";
				break;
			default:		
		}		
		return data;
	}

	protected void sendToCalendar() {
		long TZ;
		long starttime;
		long endtime;
		
		Calendar c = Calendar.getInstance();
		//c.setTimeZone(TimeZone.getTimeZone("Greenwich"));
		Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.ORGANIZER, i.getStringExtra("org"));
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, i.getStringExtra("loc"));
        intent.putExtra(CalendarContract.Events.TITLE, i.getStringExtra("title"));
        TZ = c.get(Calendar.ZONE_OFFSET) + c.get(Calendar.DST_OFFSET); //c.getTimeZone().getOffset(c.getTimeInMillis());
        starttime = Long.parseLong(i.getStringExtra("start")) + TZ;
        c.setTimeInMillis(starttime);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, c.getTimeInMillis());
        Toast t = Toast.makeText(getBaseContext(), "TZ = " + Long.toString(TZ) +" Start:" + getDateTime(Long.toString(starttime)), Toast.LENGTH_LONG);
        t.show();
		if (i.getStringExtra("end").equalsIgnoreCase("null")){
			intent.putExtra(CalendarContract.Events.DURATION, i.getStringExtra("last"));
		} else {
	        endtime = Long.parseLong(i.getStringExtra("end")) + TZ;
	        c.setTimeInMillis(endtime);
			intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, c.getTimeInMillis());
		}
		intent.putExtra(CalendarContract.Events.RRULE, i.getStringExtra("recur"));
		intent.putExtra(CalendarContract.Events.DESCRIPTION, "Attendees:\n" + i.getStringExtra("att"));
		intent.putExtra(CalendarContract.Events.EVENT_TIMEZONE, c.getTimeZone());
		startActivity(intent);		
	}
	
}

