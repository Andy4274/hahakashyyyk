package com.jassoftware.textmeeting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class OrgAndLocActivity extends Activity {

	EditText title;
	EditText name;
	EditText number;
	EditText loc;
	Button save;
	Button set;
	Button next;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create scrolling frame
        ScrollView frame = (ScrollView)findViewById(R.id.frame);
        LinearLayout filler = new LinearLayout(this);
        filler = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.organizer, null);
        frame.addView((View)filler);
        //find things
        title = (EditText)findViewById(R.id.org_title);
        name = (EditText)findViewById(R.id.org_name);
        number = (EditText)findViewById(R.id.org_number);
        loc = (EditText)findViewById(R.id.org_loc);
        save = (Button)findViewById(R.id.org_save);
        set = (Button)findViewById(R.id.org_set);
        next = (Button)findViewById(R.id.org_next);
        //set up buttons
        save.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				saveToPrefs();
			}
        });
        set.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				setOrgAndLoc();
			}
        });
        next.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(), com.jassoftware.textmeeting.DatesAndTimesActivity.class);
				startActivity(i);
			}
        });
        //read from prefs
        readPrefs();
    }
	
	 protected void setOrgAndLoc() {
		MainActivity.meeting.setTitle(title.getText().toString());
		MainActivity.meeting.setOrg(name.getText().toString()+":"+number.getText().toString());
		MainActivity.meeting.setLoc(loc.getText().toString());
	}

	private void readPrefs() {
		final String orgname;
		final String orgnum;
		String orgloc;
		String meeting_title;
		
		SharedPreferences prefs = getSharedPreferences("textmeeting", 0);
		name.setText(prefs.getString("name", ""));
		number.setText(prefs.getString("number",""));
		loc.setText(prefs.getString("loc",""));
		String org = MainActivity.meeting.getOrg();
		orgloc = MainActivity.meeting.getLoc();
		meeting_title = MainActivity.meeting.getTitle();
		if (meeting_title!=null){
			if (meeting_title.length()>0){
				title.setText(meeting_title);
			}
		}
		if ((org!=null) || (MainActivity.meeting.getLasts()!=null)){
			int sep = org.indexOf(":");
			orgname = org.substring(0, sep);
			orgnum = org.substring(sep+1);
			if ((!orgname.equalsIgnoreCase(name.getText().toString()) ||
					(!orgnum.equalsIgnoreCase(number.getText().toString())) ||
					(!orgloc.equalsIgnoreCase(loc.getText().toString())))){
				AlertDialog.Builder donotMatch = new AlertDialog.Builder(this);
				donotMatch.setTitle("Meeting and saved preferences do not match");
				donotMatch.setMessage("The organizer and location data saved for this meeting do not match the data saved to the preferences.  Which data do you want to use?");
				donotMatch.setNegativeButton("Preferences Data", 
					new DialogInterface.OnClickListener() {
			      		@Override
			      		public void onClick(DialogInterface dialog, int id) {
			      			dialog.dismiss();
			      		}
			    	});
				donotMatch.setPositiveButton("Meeting Data",
					new DialogInterface.OnClickListener() {
	      				@Override
	      				public void onClick(DialogInterface dialog, int id) {
	      					name.setText(orgname);
	      					number.setText(orgnum);
	      					loc.setText(MainActivity.meeting.getLoc());
	      					dialog.dismiss();
	      				}
	    			});
				donotMatch.create();
				donotMatch.show();
			}
		}
	}

	protected void saveToPrefs() {
		SharedPreferences prefs = getSharedPreferences("textmeeting", 0);
		Editor edit = prefs.edit();
		edit.putString("name", name.getText().toString());
		edit.putString("number", number.getText().toString());
		edit.putString("loc", loc.getText().toString());
		edit.commit();
	}

	@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        MenuItem i = menu.findItem(R.id.org);
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


