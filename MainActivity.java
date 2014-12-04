package com.jassoftware.textmeeting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;


public class MainActivity extends Activity {

	static String location;
	static Meeting meeting;
	Button newMeeting;
	Button editAttL;
	Button done;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScrollView frame = (ScrollView)findViewById(R.id.frame);
        LinearLayout filler = new LinearLayout(this);
        filler = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.start, null);
        frame.addView((View)filler);
        meeting = new Meeting();
        //find stuff
        newMeeting = (Button)findViewById(R.id.rev_send);
        editAttL = (Button)findViewById(R.id.start_edit);
        done = (Button)findViewById(R.id.start_done);
        //set up buttons
        newMeeting.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		meeting = new Meeting();
        		Intent i = new Intent(getBaseContext(), com.jassoftware.textmeeting.OrgAndLocActivity.class);
        		startActivity(i);
        	}
        });
        editAttL.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		Intent i = new Intent(getBaseContext(), com.jassoftware.textmeeting.AttendeeListActivity.class);
        		startActivity(i);
        	}
        });
        done.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		finish();
        	}
        });
        location = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/TextMeeting";
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem i = menu.findItem(R.id.start);
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

