package com.jassoftware.textmeeting;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class DatesAndTimesActivity extends Activity {

	CalendarView startDate;
	TimePicker startTime;
	TimePicker endTime;
	NumberPicker lasts;
	RadioButton endsRb;
	RadioButton lastsRb;
	TextView endsTv;
	TextView lastsTv;
	TextView lastsTv2;
	Button set;
	Button next;
	RadioGroup rg;
	Calendar start;
	Calendar end;
	
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        ScrollView frame = (ScrollView)findViewById(R.id.frame);
	        LinearLayout filler = new LinearLayout(this);
	        filler = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.datesandtimes, null);
	        frame.addView((View)filler);
	        // find things
	        startDate = (CalendarView)findViewById(R.id.dt_start_date);
	        startTime = (TimePicker)findViewById(R.id.dt_start_time);
	        endTime = (TimePicker)findViewById(R.id.dt_end_time);
	        lasts = (NumberPicker)findViewById(R.id.dt_lasts);
	        endsRb = (RadioButton)findViewById(R.id.dt_rb_end);
	        lastsRb = (RadioButton)findViewById(R.id.dt_rb_last);
	        endsTv = (TextView)findViewById(R.id.dis_title);
	        lastsTv = (TextView)findViewById(R.id.textView5);
	        lastsTv2 = (TextView)findViewById(R.id.el_paste_box);
	        set = (Button)findViewById(R.id.dt_set_button);
	        next = (Button)findViewById(R.id.dt_next);
	        // fill fields
	        lasts.setMaxValue(240);  //four hours seems generous.
	        lasts.setValue(60);
	        endTime.setCurrentHour(endTime.getCurrentHour()+1);
	        start = Calendar.getInstance();
	        end = Calendar.getInstance();
	        start.setTimeInMillis(startDate.getDate());
	        start.set(Calendar.HOUR_OF_DAY, startTime.getCurrentHour());
	        start.set(Calendar.MINUTE, startTime.getCurrentMinute());
	        end.setTimeInMillis(startDate.getDate());
	        end.set(Calendar.HOUR_OF_DAY, endTime.getCurrentHour());
	        end.set(Calendar.MINUTE, endTime.getCurrentMinute());
	        // set up buttons
	        endsRb.setOnCheckedChangeListener(new OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton b, boolean isChecked) {
					if (b.getId() == endsRb.getId()){
						endTime.setEnabled(isChecked);
						endsTv.setEnabled(isChecked);
						lasts.setEnabled(!isChecked);
						lastsTv.setEnabled(!isChecked);
						lastsTv2.setEnabled(!isChecked);
					}
				} 	
	        });
	        lastsRb.setOnCheckedChangeListener(new OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton b, boolean isChecked) {
					if (b.getId() == lastsRb.getId()){
						lasts.setEnabled(isChecked);
						lastsTv.setEnabled(isChecked);
						lastsTv2.setEnabled(isChecked);
						endTime.setEnabled(!isChecked);
						endsTv.setEnabled(!isChecked);
					}
				} 	
	        });
	        endsRb.performClick();
	        lastsRb.performClick();
	        endsRb.performClick();
	        set.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		//get TZ offset
	        		long tz = start.getTimeZone().getOffset(start.getTimeInMillis());
	        		//get start date time
	        		long startT = start.getTimeInMillis() - tz;
	        		//get end time
	        		long endT = end.getTimeInMillis() - tz;
	        		//get how many minutes the meeting lasts
	        		String l = Integer.toString(lasts.getValue());
	        		//send start, ends,  and lasts
	        		MainActivity.meeting.setStart(Long.toString(startT));
	        		if (endsRb.isChecked()){
	        			MainActivity.meeting.setEnd(Long.toString(endT));
	        			MainActivity.meeting.setLasts(null);
	        		} else {  //lastsRb is checked
	        			MainActivity.meeting.setEnd(null);
	        			MainActivity.meeting.setLasts(l);
	        		}
	        	}
	        });
	        next.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		Intent i = new Intent(getBaseContext(), com.jassoftware.textmeeting.AttendeesActivity.class);
	        		startActivity(i);
	        	}
	        });
	        //set up automatic responses
	        startTime.setOnTimeChangedListener(new OnTimeChangedListener(){
				@Override
				public void onTimeChanged(TimePicker arg0, int h, int m) {
					start.set(Calendar.HOUR_OF_DAY, startTime.getCurrentHour());
			        start.set(Calendar.MINUTE, startTime.getCurrentMinute());
					endTime.setCurrentHour(h+1);
					endTime.setCurrentMinute(m);
					end.set(Calendar.HOUR_OF_DAY, endTime.getCurrentHour());
			        end.set(Calendar.MINUTE, endTime.getCurrentMinute());
				}
	        });
	        startDate.setOnDateChangeListener(new OnDateChangeListener(){
				@Override
				public void onSelectedDayChange(CalendarView arg0, int year, int month, int day) {
					start.set(Calendar.YEAR, year);
					start.set(Calendar.MONTH, month);
					start.set(Calendar.DAY_OF_MONTH, day);
					end.set(Calendar.YEAR, year);
					end.set(Calendar.MONTH, month);
					end.set(Calendar.DAY_OF_MONTH, day);				    
				}        	
	        });
	    }


	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        MenuItem i = menu.findItem(R.id.dt);
	        i.setEnabled(false);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        int id = item.getItemId();
	        Intent i;
	        switch (id){
        		case R.id.start:
        			i = new Intent(this, com.jassoftware.textmeeting.MainActivity.class);
        			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        			startActivity(i);
        			return true;
	        	case R.id.org:
	        		i = new Intent(this, com.jassoftware.textmeeting.OrgAndLocActivity.class);
	        		startActivity(i);
	        		return true;
	        	case R.id.att:
	        		i = new Intent(this, com.jassoftware.textmeeting.AttendeesActivity.class);
	        		startActivity(i);
	        		return true;
	        	case R.id.attL:
	        		i = new Intent(this, com.jassoftware.textmeeting.AttendeeListActivity.class);
	        		startActivity(i);
	        		return true;
	        	//case R.id.action_settings:
	        		//i = new Intent(this, com.jassoftware.textmeeting.OrgAndLocActivity.class);
	        		//startActivity(i);
	        		//return true;
	        	case R.id.dt:
	        		i = new Intent(this, com.jassoftware.textmeeting.DatesAndTimesActivity.class);
	        		startActivity(i);
	        		return true;
	        	case R.id.rec:
	        		i = new Intent(this, com.jassoftware.textmeeting.RecurActivity.class);
	        		startActivity(i);
	        		return true;
	        	case R.id.rev:
	        		i = new Intent(this, com.jassoftware.textmeeting.ReviewActivity.class);
	        		startActivity(i);
	        		return true;
	        	default:
	        		return super.onOptionsItemSelected(item);
	        }
	    }
	    
}


