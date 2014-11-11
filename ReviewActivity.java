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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ReviewActivity extends Activity {

	TextView org;
	TextView loc;
	TextView start;
	TextView ends;
	TextView lasts;
	TextView recur;
	TextView attL;
	Button editOrg;
	Button editDt;
	Button editRecur;
	Button editAttL;
	Button send;
	
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        ScrollView frame = (ScrollView)findViewById(R.id.frame);
	        LinearLayout filler = new LinearLayout(this);
	        filler = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.review, null);
	        frame.addView((View)filler);
	        //find things
	        org = (TextView)findViewById(R.id.rev_org);
	        loc = (TextView)findViewById(R.id.rev_loc);
	        start = (TextView)findViewById(R.id.rev_starts);
	        ends = (TextView)findViewById(R.id.rev_ends);
	        lasts = (TextView)findViewById(R.id.rev_lasts);
	        recur = (TextView)findViewById(R.id.rev_rec);
	        attL = (TextView)findViewById(R.id.rev_attL);
	        editOrg = (Button)findViewById(R.id.rev_org_button);
	        editDt = (Button)findViewById(R.id.rev_dt_button);
	        editRecur = (Button)findViewById(R.id.rev_rec_button);
	        editAttL = (Button)findViewById(R.id.rev_attL_button);
	        editOrg = (Button)findViewById(R.id.rev_org_button);
	        send = (Button)findViewById(R.id.start_new);
	        //fill out fields
	        String data = MainActivity.meeting.getOrg();
	        if (data!=null){
	        	org.setText(data);
	        } else {
	        	org.setText(R.string.notSet);
	        }
	        data = MainActivity.meeting.getLoc();
	        if (data!=null){
	        	loc.setText(data);
	        } else {
	        	loc.setText(R.string.notSet);
	        }
	        data = MainActivity.meeting.getStart();
	        if (data!=null){
	        	data = createDate(data);
	        	start.setText(data);
	        } else {
	        	start.setText(R.string.notSet);
	        }
	        data = MainActivity.meeting.getEnd();
	        if (data!=null){
	        	data = createDate(data);
	        	ends.setText(data);
	        } else {
	        	ends.setText(R.string.notSet);
	        }
	        data = MainActivity.meeting.getLasts();
	        if (data!=null){
	        	lasts.setText(data);
	        } else {
	        	lasts.setText(R.string.notSet);
	        }
	        data = MainActivity.meeting.getRec();
	        if (data!=null){
	        	recur.setText(data);
	        } else {
	        	recur.setText(R.string.notSet);
	        }
	        data = MainActivity.meeting.getAttL();
	        if (data!=null){
	        	attL.setText(data);
	        } else {
	        	attL.setText(R.string.notSet);
	        }
	        //set up buttons
	        editOrg.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getBaseContext(), com.jassoftware.textmeeting.OrgAndLocActivity.class);
					startActivity(i);
				}	        	
	        });
	        editDt.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getBaseContext(), com.jassoftware.textmeeting.DatesAndTimesActivity.class);
					startActivity(i);
				}	        	
	        });
	        editRecur.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getBaseContext(), com.jassoftware.textmeeting.RecurActivity.class);
					startActivity(i);
				}	        	
	        });
	        editAttL.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getBaseContext(), com.jassoftware.textmeeting.AttendeesActivity.class);
					startActivity(i);
				}	        	
	        });
	        send.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getBaseContext(), com.jassoftware.textmeeting.SendActivity.class);
					startActivity(i);
				}	        	
	        });
	    }

	  	public String createDate(String data){
	  		String d;
        	int year, month, day, hour, minute, ap;
        	Calendar s = Calendar.getInstance();
        	long TZ = s.getTimeZone().getOffset(s.getTimeInMillis());
        	long startTime = Long.parseLong(data);  //in GMT
        	startTime = startTime + TZ;  // in local TZ
        	s.setTimeInMillis(startTime);
        	year = s.get(Calendar.YEAR);
        	month = s.get(Calendar.MONTH);
        	day = s.get(Calendar.DAY_OF_MONTH);
        	hour = s.get(Calendar.HOUR);
        	minute = s.get(Calendar.MINUTE);
        	ap = s.get(Calendar.AM_PM);
        	d = data + ":  " + Integer.toString(year) + " ";
        	switch (month){
        		case Calendar.JANUARY:
        			d = d + "Jan. ";
        			break;
        		case Calendar.FEBRUARY:
        			d = d + "Feb. ";
        			break;
        		case Calendar.MARCH:
        			d = d + "Mar. ";
        			break;
        		case Calendar.APRIL:
        			d = d + "Apr. ";
        			break;
        		case Calendar.MAY:
        			d = d + "May ";
        			break;
        		case Calendar.JUNE:
        			d = d + "Jun. ";
        			break;
        		case Calendar.JULY:
        			d = d + "Jul. ";
        			break;
        		case Calendar.AUGUST:
        			d = d + "Aug. ";
        			break;
        		case Calendar.SEPTEMBER:
        			d = d + "Sep. ";
        			break;
        		case Calendar.OCTOBER:
        			d = d + "Oct. ";
        			break;
        		case Calendar.NOVEMBER:
        			d = d + "Nov. ";
        			break;
        		case Calendar.DECEMBER:
        			d = d + "Dec. ";
        			break;
        		default:
        	}
        	d = d + Integer.toString(day) + ", ";
        	d = d + hour + ":" + minute + " ";
        	switch (ap){
        		case Calendar.AM:
        			d = d + "AM";
        			break;
        		case Calendar.PM:
        			d = d + "PM";
        			break;
        		default:		
        	}
        	return d;
	  	}

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        MenuItem i = menu.findItem(R.id.rev);
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
