package com.jassoftware.textmeeting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class RecurActivity extends Activity {

		//freq
		String[] freq_vals = {"SECONDLY", "MINUTELY", "HOURLY", "DAILY", "WEEKLY", "MONTHLY", "YEARLY"};
		ArrayAdapter<String> ad_freq;
		Spinner freq;
		Button addfreq;
		//radio buttons
		RadioGroup rg;
		RadioButton untilRB;
		RadioButton countRB;
		//until
		Calendar c;
		ArrayList<Integer> years;
		ArrayAdapter<Integer> ad_years;
		Spinner until_year;
		String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
		ArrayAdapter<String> ad_months;
		Spinner until_month;
		ArrayList<Integer> days;
		ArrayAdapter<Integer> ad_days;
		Spinner until_day;
		ArrayList<Integer> hours;
		ArrayAdapter<Integer> ad_hours;
		Spinner until_hour;
		TextView until_colon;
		ArrayList<Integer> minutes;
		ArrayAdapter<Integer> ad_minutes;
		Spinner until_minute;
		//count
		EditText count;
		Button adduorc;
		//interval
		CheckBox intervalCB;
		TextView intervalTV;
		EditText interval;
		Button addint;
		// week start
		String[] wkdys = {"SU", "MO", "TU", "WE", "TH", "FR", "SA"};
		ArrayAdapter<String> ad_wkdys;
		Spinner wkst;	
		Button addwkst;
		//rule spinner			0		1			2		3		4			5			6			7			8		
		String[] parts = {"BYSECOND","BYMINUTE","BYHOUR","BYDAY","BYMONTHDAY","BYYEARDAY","BYWEEKNO","BYMONTH","BYSETPOS"};
		ArrayAdapter<String> ad_parts;
		Spinner rule_spinner;
		Button rule_add;
		//remove
		TextView rrule;
		HashMap<String, String> rule;
		ArrayList<String> ruleparts;
		ArrayAdapter<String> ad_ruleparts;
		Spinner remove_part;
		Button remove;
		//next activity
		Button set;
		Button next;
	
		Builder db; 
		LayoutInflater inflater;
		static Context context;
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        ScrollView frame = (ScrollView)findViewById(R.id.frame);
	        LinearLayout filler = new LinearLayout(this);
	        filler = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.recur, null);
	        frame.addView((View)filler);
	        inflater = getLayoutInflater();
	        context = this;
	        //find
	        freq = (Spinner)findViewById(R.id.rec_freq_spinner);
	        addfreq = (Button)findViewById(R.id.byhour_add);
	        rg = (RadioGroup)findViewById(R.id.rec_rg);
	        untilRB = (RadioButton)findViewById(R.id.rec_until_rb);
	        countRB = (RadioButton)findViewById(R.id.rec_count_rb);
	        until_year = (Spinner)findViewById(R.id.rec_until_year);
	        until_month = (Spinner)findViewById(R.id.rec_until_month);
	        until_day = (Spinner)findViewById(R.id.rec_until_day);
	        until_hour = (Spinner)findViewById(R.id.rec_until_hour);
	        until_colon = (TextView)findViewById(R.id.rec_until_colon);
	        until_minute = (Spinner)findViewById(R.id.rec_until_minute);
	        count = (EditText)findViewById(R.id.rec_count);
	        adduorc = (Button)findViewById(R.id.rec_add_uorc);
	        intervalCB = (CheckBox)findViewById(R.id.rec_intCB);
	        intervalTV = (TextView)findViewById(R.id.textView3);
	        interval = (EditText)findViewById(R.id.rec_intervalET);
	        addint = (Button)findViewById(R.id.rec_add_int);
	        wkst = (Spinner)findViewById(R.id.byhour_hour);
	        addwkst = (Button)findViewById(R.id.rec_add_wkst);
	        rule_spinner = (Spinner)findViewById(R.id.rec_by_xxx_spinner);
	        rule_add = (Button)findViewById(R.id.rec_by_xxx_button);
	        rrule = (TextView)findViewById(R.id.rec_rule);
	        remove_part = (Spinner)findViewById(R.id.rec_rem_spinner);
	        remove = (Button)findViewById(R.id.rec_remove);
	        set = (Button)findViewById(R.id.rec_set);
	        next = (Button)findViewById(R.id.rec_next);
	        //set up lists and adapters
	        	//freq
	        	ad_freq = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, freq_vals);
	        	freq.setAdapter(ad_freq);
	        	freq.requestFocus();
	        	//until_year
	        	c = Calendar.getInstance();
	        	years = new ArrayList<Integer>();
	        	int thisyear = c.get(Calendar.YEAR);
	        	years.add(thisyear);
	        	for(int i=1;i<5;i++){
	        		years.add(thisyear+i);
	        	}
	        	ad_years = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, years);
	        	until_year.setAdapter(ad_years);
	        	//until_month
	        	ad_months = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, months);
	        	until_month.setAdapter(ad_months);
	        	//until_day
	        	days = new ArrayList<Integer>();
	        	for(int i=1;i<32;i++){
	        		days.add(i);
	        	}
	        	ad_days = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, days);
	        	until_day.setAdapter(ad_days);
	        	//until_hour
	        	hours = new ArrayList<Integer>();
	        	for (int i = 0;i<24;i++){
	        		hours.add(i);
	        	}
	        	ad_hours = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, hours);
	        	until_hour.setAdapter(ad_hours);
	        	//until_minute
	        	minutes = new ArrayList<Integer>();
	        	for (int i = 0;i<60;i++){
	        		minutes.add(i);
	        	}
	        	ad_minutes = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, minutes);
	        	until_minute.setAdapter(ad_minutes);
	        	//week start
	        	ad_wkdys = new ArrayAdapter(this, android.R.layout.simple_list_item_1, wkdys);
	        	wkst.setAdapter(ad_wkdys);
	        	//rule_spinner
	        	ad_parts = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, parts);
	        	rule_spinner.setAdapter(ad_parts);
	        	rule_spinner.setSelection(0);
	        	//remove rule part spinner
	        	rrule.setText("");
	        	rule = new HashMap<String, String>();
	        	ruleparts = new ArrayList<String>();
	        	ad_ruleparts = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ruleparts);
	        	remove_part.setAdapter(ad_ruleparts);
	        //other misc starting values	
	        	
	        //set up buttons
	        //add freq
	        	addfreq.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						String key = "FREQ";
						String val = "FREQ=" + freq.getSelectedItem();
						rule.put(key, val);
						buildrrule();
						buildparts();
					}
		        });
	        //untilRB
	        	untilRB.setOnCheckedChangeListener(new OnCheckedChangeListener(){
					@Override
					public void onCheckedChanged(CompoundButton b,	boolean checked) {
						count.setEnabled(!checked);
						until_year.setEnabled(checked);
						until_month.setEnabled(checked);
						until_day.setEnabled(checked);	
						until_hour.setEnabled(checked);
						until_colon.setEnabled(checked);
						until_minute.setEnabled(checked);
					}
		        });
	        	untilRB.performClick();
	        //countRB
	        	countRB.setOnCheckedChangeListener(new OnCheckedChangeListener(){
					@Override
					public void onCheckedChanged(CompoundButton b,	boolean checked) {
						count.setEnabled(checked);
						until_year.setEnabled(!checked);
						until_month.setEnabled(!checked);
						until_day.setEnabled(!checked);	
						until_hour.setEnabled(!checked);
						until_colon.setEnabled(!checked);
						until_minute.setEnabled(!checked);
					}
		        });
	        //add until or count
	        	adduorc.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						String key, val;
						if (untilRB.isChecked()){  //add until and clear count if present
							key = "UNTIL";
							val = "UNTIL=" + until_year.getSelectedItem();
							int i = until_month.getSelectedItemPosition()+1;
							if (i<10){
								val = val + "0" + Integer.toString(i);
							} else {
								val = val + Integer.toString(i);
							}
							i =	until_day.getSelectedItemPosition()+1;
							if (i<10){
								val = val + "0" + Integer.toString(i);
							} else {
								val = val + Integer.toString(i);
							}
							val = val + "T";
							i =	until_hour.getSelectedItemPosition();
							if (i<10){
								val = val + "0" + Integer.toString(i);
							} else {
								val = val + Integer.toString(i);
							}
							i =	until_minute.getSelectedItemPosition();
							if (i<10){
								val = val + "0" + Integer.toString(i);
							} else {
								val = val + Integer.toString(i);
							}
							val = val + "00Z";	//add 00 seconds
							if (rule.containsKey("COUNT")){
								rule.remove("COUNT");
							}
							
						} else {					//add count and clear until if present
							if (count.getText().toString().length()!=0){
								key = "COUNT";
								val = "COUNT=" + count.getText().toString();
									if (rule.containsKey("UNTIL")){
									rule.remove("UNTIL");
								}
							} else {	//skip if count is empty
								return;
							}
						}
						rule.put(key, val);
						buildrrule();
						buildparts();
					}
		        });
	        //intervalCB
	        	intervalCB.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if (intervalCB.isChecked()){
							intervalTV.setEnabled(true);
							interval.setEnabled(true);
							interval.requestFocus();
						} else {
							intervalTV.setEnabled(false);
							interval.setEnabled(false);
						}
					}
		        });
	        	intervalCB.performClick();
	        	intervalCB.performClick();
	        //add interval
	        	addint.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if (interval.getText().toString().length()!=0){
							String key = "INTERVAL";
							String val = "INTERVAL=" + interval.getText();
							rule.put(key, val);
							buildrrule();
							buildparts();
						} else {		//skip if empty
							return;
						}
					}
		        });
	        //add weekstart
	        	addwkst.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						String key = "WKST";
						String val = "WKST=" + wkst.getSelectedItem();
						rule.put(key, val);
						buildrrule();
						buildparts();
					}
		        });
	        //rule_add
	        	rule_add.setOnClickListener(new OnClickListener(){
	        		@Override
	        		public void onClick(View v){
	        			add_rule();
	        		}
	        	});
	        //remove
	        	remove.setOnClickListener(new OnClickListener(){
	        		@Override
	        		public void onClick(View v){
	        			String key = remove_part.getSelectedItem().toString();
	        			rule.remove(key);
	        			buildrrule();
	        			buildparts();
	        		}
	        	});
	        //set
	        	set.setOnClickListener(new OnClickListener(){
	        		@Override
	        		public void onClick(View v) {
	        			MainActivity.meeting.setRec(rrule.getText().toString());
	        		}
	        	});
	        //next	
	        	next.setOnClickListener(new OnClickListener(){
	        		@Override
	        		public void onClick(View v) {
	        			Intent i = new Intent(getBaseContext(), com.jassoftware.textmeeting.ReviewActivity.class);
	        			startActivity(i);
	        		}
	        	});
	        	
	        //set up other reactions
	        	//change days when until_month is selected
	        	until_month.setOnItemSelectedListener(new OnItemSelectedListener(){
					@Override
					public void onItemSelected(AdapterView<?> ad_view, View view, int pos, long id) {
						switch (pos){	//pos = month number -1   jan = 0...
							case 0:	//jan
							case 2:	//mar
							case 4:	//may
							case 6:	//jul
							case 7:	//aug
							case 19://oct
							case 11://dec
								days.clear();
								for(int i=1;i<32;i++){
									days.add(i);
								}
								ad_days.notifyDataSetChanged();
								break;
							case 3:	//apr
							case 5:	//jun
							case 8:	//sep
							case 10://nov
								days.clear();
								for(int i=1;i<31;i++){
									days.add(i);
								}
								ad_days.notifyDataSetChanged();
								break;
							case 1:	//feb
								int year = years.get(until_year.getSelectedItemPosition());
								if (year%4==0){
									days.clear();
									for(int i=1;i<30;i++){
										days.add(i);
									}
									ad_days.notifyDataSetChanged();
									break;
								} else {
									days.clear();
									for(int i=1;i<29;i++){
										days.add(i);
									}
									ad_days.notifyDataSetChanged();
									break;
								}
						}
					}
					@Override
					public void onNothingSelected(AdapterView<?> ad_view) {
					}
	        	});
	        	//change days if a leap year is selected and month of Feb is currently selected in until_month
	        	until_year.setOnItemSelectedListener(new OnItemSelectedListener(){
					@Override
					public void onItemSelected(AdapterView<?> ad_view, View view, int pos, long id) {
						int year = years.get(pos);
						if (until_month.getSelectedItemPosition()==1){ //0== JAN, 1 == FEB
							if (year%4==0){
								if (days.size()!=29){
									days.clear();
									for(int i=1;i<30;i++){
										days.add(i);
									}
									ad_days.notifyDataSetChanged();
	        					}			
							} else {
								if (days.size()!=28){
									days.clear();
									for(int i=1;i<29;i++){
										days.add(i);
									}
									ad_days.notifyDataSetChanged();
								}
							}
						}
					}
					@Override
					public void onNothingSelected(AdapterView<?> ad_view) {
					}
	        	});	
		}


		protected void buildparts() {
			ruleparts.clear();
			for (String key : rule.keySet()){
				ruleparts.add(key);
			}
			ad_ruleparts.notifyDataSetChanged();
		}


		protected void buildrrule() {
			String data = "";
			for (String key : rule.keySet()){
				if (data.length()!=0){
					data = data + ";\n" + rule.get(key);
				} else {
					data = rule.get(key);
				}
				rrule.setText(data);
			}
		}

		protected String getRule(String key){
			String val = null;
			if (rule.containsKey(key)){
				val = rule.get(key);
				int sep = val.indexOf("=");
				val = val.substring(sep+1);
			}
			return val;
		}
	
		protected void add_rule() {
			View v;
			String[] plusminus = {"+","-"};
			ArrayAdapter<String> ad_pm = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, plusminus);
			final Spinner pm;
			final Spinner num;
			Button add;
			Button clear;
			final TextView list;
			
	    	switch (rule_spinner.getSelectedItemPosition()){
	    		case 0:		//by second
	    			db = new AlertDialog.Builder(this);
	    			v = inflater.inflate(R.layout.bysecond, null);
	    			db.setView(v);
	    			num = (Spinner)v.findViewById(R.id.bysec_sec);
	    			add = (Button)v.findViewById(R.id.bysec_add);
	    			list = (TextView)v.findViewById(R.id.bysec_list);
	    			clear = (Button)v.findViewById(R.id.bysec_clear);
	    			final ArrayList<Integer> secs;
	    			ArrayAdapter<Integer> ad_secs;
	    			secs = new ArrayList<Integer>();
	    			for (int i=0;i<60;i++){
	    				secs.add(i);
	    			}
	    			ad_secs = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line, secs);
	    			num.setAdapter(ad_secs);
	    			list.setText(getRule("BYSECOND"));
	    			add.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					String data = list.getText().toString();
	    					if (data.length()!=0){
	    						list.setText(list.getText().toString() + "," + secs.get(num.getSelectedItemPosition()));
	    					} else {
	    						int i = num.getSelectedItemPosition();
	    						list.setText(Integer.toString(secs.get(i)));
	    					}
	    				}
	    			});
	    			clear.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					list.setText("");
	    				}
	    			});
	    			db.setNegativeButton("Cancel", cancel);
	    			db.setPositiveButton("OK", ok);
	    			db.create();
	    			db.show();
	    			break;
	    		case 1:		//by minute
	    			db = new AlertDialog.Builder(this);
	    			v = inflater.inflate(R.layout.byminute, null);
	    			db.setView(v);
	    			num = (Spinner)v.findViewById(R.id.bymin_min);
	    			add = (Button)v.findViewById(R.id.bymin_add);
	    			list = (TextView)v.findViewById(R.id.bymin_list);
	    			clear = (Button)v.findViewById(R.id.bymin_clear);
	    			final ArrayList<Integer> mins;
	    			ArrayAdapter<Integer> ad_mins;
	    			mins = new ArrayList<Integer>();
	    			for (int i=0;i<60;i++){
	    				mins.add(i);
	    			}
	    			ad_mins = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line, mins);
	    			num.setAdapter(ad_mins);
	    			list.setText(getRule("BYMINUTE"));
	    			add.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					String data = list.getText().toString();
	    					if (data.length()!=0){
	    						list.setText(list.getText().toString() + "," + mins.get(num.getSelectedItemPosition()));
	    					} else {
	    						int i = num.getSelectedItemPosition();
	    						list.setText(Integer.toString(mins.get(i)));
	    					}
	    				}
	    			});
	    			clear.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					list.setText("");
	    				}
	    			});
	    			db.setNegativeButton("Cancel", cancel);
	    			db.setPositiveButton("OK", ok);
	    			db.create();
	    			db.show();
	    			break;
	    		case 2:		//by hour
	    			db = new AlertDialog.Builder(this);
	    			v = inflater.inflate(R.layout.byhour, null);
	    			db.setView(v);
	    			num = (Spinner)v.findViewById(R.id.byhour_hour);
	    			add = (Button)v.findViewById(R.id.byhour_add);
	    			list = (TextView)v.findViewById(R.id.byhour_list);
	    			clear = (Button)v.findViewById(R.id.byhour_clear);
	    			final ArrayList<Integer> hours;
	    			ArrayAdapter<Integer> ad_hours;
	    			hours = new ArrayList<Integer>();
	    			for (int i=0;i<24;i++){
	    				hours.add(i);
	    			}
	    			ad_hours = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line, hours);
	    			num.setAdapter(ad_hours);
	    			list.setText(getRule("BYHOUR"));
	    			add.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					String data = list.getText().toString();
	    					if (data.length()!=0){
	    						list.setText(list.getText().toString() + "," + hours.get(num.getSelectedItemPosition()));
	    					} else {
	    						int i = num.getSelectedItemPosition();
	    						list.setText(Integer.toString(hours.get(i)));
	    					}
	    				}
	    			});
	    			clear.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					list.setText("");
	    				}
	    			});
	    			db.setNegativeButton("Cancel", cancel);
	    			db.setPositiveButton("OK", ok);
	    			db.create();
	    			db.show();
	    			break;
	    		case 3:		//by day
	    			String[] wkdys = {"SU", "MO", "TU", "WE", "TH", "FR", "SA"};
	    			ArrayAdapter<String> ad_wkday = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, wkdys);
	    			final Spinner wkday;
	    			final CheckBox wknoCB;
	    			db = new AlertDialog.Builder(this);
	    			v = inflater.inflate(R.layout.byday, null);
	    			db.setView(v);
	    			wknoCB = (CheckBox)v.findViewById(R.id.byday_weeknoCB);
	    			pm = (Spinner)v.findViewById(R.id.byday_plusminus);
	    			num = (Spinner)v.findViewById(R.id.byday_day);
	    			wkday = (Spinner)v.findViewById(R.id.byday_wkday);
	    			add = (Button)v.findViewById(R.id.byday_add);
	    			list = (TextView)v.findViewById(R.id.byday_list);
	    			clear = (Button)v.findViewById(R.id.byday_clear);
	    			final ArrayList<Integer> wkdays;
	    			ArrayAdapter<Integer> ad_wkdays;
	    			wkdays = new ArrayList<Integer>();
	    			for (int i=1;i<54;i++){
	    				wkdays.add(i);
	    			}
	    			ad_wkdays = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line, wkdays);
	    			num.setAdapter(ad_wkdays);
	    			pm.setAdapter(ad_pm);
	    			wkday.setAdapter(ad_wkday);
	    			list.setText(getRule("BYDAY"));
	    			wknoCB.setOnCheckedChangeListener(new OnCheckedChangeListener(){
						@Override
						public void onCheckedChanged(CompoundButton cb,	boolean checked) {
							pm.setEnabled(checked);
							num.setEnabled(checked);
						}
	    			});
	    			wknoCB.performClick();
	    			wknoCB.performClick();
	    			add.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					String data = list.getText().toString();
	    					String newdata = "";
	    					if (wknoCB.isChecked()){
	    						if (data.length()!=0){
		    						if (pm.getSelectedItemPosition()==0){  //"+"
		    							newdata = data + "," + wkdays.get(num.getSelectedItemPosition()) + wkday.getSelectedItem();
		    						} else {	//"-"
		    							newdata = data +  ",-" + wkdays.get(num.getSelectedItemPosition()) + wkday.getSelectedItem();
		    						}
		    					} else {
		    						if (pm.getSelectedItemPosition()==0){  //"+"
		    							newdata = wkdays.get(num.getSelectedItemPosition()) + (String)wkday.getSelectedItem();
		    						} else {	//"-"
		    							newdata = "-" + wkdays.get(num.getSelectedItemPosition()) + wkday.getSelectedItem();
		    						}
		    					}
	    					} else {
	    						if (data.length()!=0){
	    							newdata = data + "," + wkday.getSelectedItem();
	    						} else {
	    							newdata = (String) wkday.getSelectedItem();
	    						}
	    					}
	    					list.setText(newdata);
	    				}
	    			});
	    			clear.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					list.setText("");
	    				}
	    			});
	    			db.setNegativeButton("Cancel", cancel);
	    			db.setPositiveButton("OK", ok);
	    			db.create();
	    			db.show();
	    			break;
	    		case 4:		//by month day
	    			db = new AlertDialog.Builder(this);
	    			v = inflater.inflate(R.layout.bymonthday, null);
	    			db.setView(v);
	    			num = (Spinner)v.findViewById(R.id.bymonday_day);
	    			add = (Button)v.findViewById(R.id.bymonday_add);
	    			list = (TextView)v.findViewById(R.id.bymonday_list);
	    			clear = (Button)v.findViewById(R.id.bymonday_clear);
	    			final ArrayList<Integer> mdays;
	    			ArrayAdapter<Integer> ad_mdays;
	    			mdays = new ArrayList<Integer>();
	    			for (int i=1;i<32;i++){
	    				mdays.add(i);
	    			}
	    			ad_mdays = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line, mdays);
	    			num.setAdapter(ad_mdays);
	    			list.setText(getRule("BYMONTHDAY"));
	    			add.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					String data = list.getText().toString();
	    					if (data.length()!=0){
	    						list.setText(list.getText().toString() + "," + mdays.get(num.getSelectedItemPosition()));
	    					} else {
	    						int i = num.getSelectedItemPosition();
	    						list.setText(Integer.toString(mdays.get(i)));
	    					}
	    				}
	    			});
	    			clear.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					list.setText("");
	    				}
	    			});
	    			db.setNegativeButton("Cancel", cancel);
	    			db.setPositiveButton("OK", ok);
	    			db.create();
	    			db.show();
	    			break;
	    		case 5:		//by year day
	    			db = new AlertDialog.Builder(this);
	    			v = inflater.inflate(R.layout.byyearday, null);
	    			db.setView(v);
	    			num = (Spinner)v.findViewById(R.id.byyearday_day);
	    			add = (Button)v.findViewById(R.id.byyearday_add);
	    			list = (TextView)v.findViewById(R.id.byyearday_list);
	    			clear = (Button)v.findViewById(R.id.byyearday_clear);
	    			final ArrayList<Integer> ydays;
	    			ArrayAdapter<Integer> ad_ydays;
	    			ydays = new ArrayList<Integer>();
	    			for (int i=1;i<367;i++){
	    				ydays.add(i);
	    			}
	    			ad_ydays = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line, ydays);
	    			num.setAdapter(ad_ydays);
	    			list.setText(getRule("BYYEARDAY"));
	    			add.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					String data = list.getText().toString();
	    					if (data.length()!=0){
	    						list.setText(list.getText().toString() + "," + ydays.get(num.getSelectedItemPosition()));
	    					} else {
	    						int i = num.getSelectedItemPosition();
	    						list.setText(Integer.toString(ydays.get(i)));
	    					}
	    				}
	    			});
	    			clear.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					list.setText("");
	    				}
	    			});
	    			db.setNegativeButton("Cancel", cancel);
	    			db.setPositiveButton("OK", ok);
	    			db.create();
	    			db.show();
	    			break;
	    		case 6:		//by week number
	    			db = new AlertDialog.Builder(this);
	    			v = inflater.inflate(R.layout.byweekno, null);
	    			db.setView(v);
	    			num = (Spinner)v.findViewById(R.id.byweekno_week);
	    			add = (Button)v.findViewById(R.id.byweekno_add);
	    			list = (TextView)v.findViewById(R.id.byweekno_list);
	    			clear = (Button)v.findViewById(R.id.byweekno_clear);
	    			final ArrayList<Integer> weeknos;
	    			ArrayAdapter<Integer> ad_weeknos;
	    			weeknos = new ArrayList<Integer>();
	    			for (int i=1;i<54;i++){
	    				weeknos.add(i);
	    			}
	    			ad_weeknos = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line, weeknos);
	    			num.setAdapter(ad_weeknos);
	    			list.setText(getRule("BYWEEKNO"));
	    			add.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					String data = list.getText().toString();
	    					if (data.length()!=0){
	    						list.setText(list.getText().toString() + "," + weeknos.get(num.getSelectedItemPosition()));
	    					} else {
	    						int i = num.getSelectedItemPosition();
	    						list.setText(Integer.toString(weeknos.get(i)));
	    					}
	    				}
	    			});
	    			clear.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					list.setText("");
	    				}
	    			});
	    			db.setNegativeButton("Cancel", cancel);
	    			db.setPositiveButton("OK", ok);
	    			db.create();
	    			db.show();
	    			break;
	    		case 7:		//by month
	    			db = new AlertDialog.Builder(this);
	    			v = inflater.inflate(R.layout.bymonth, null);
	    			db.setView(v);
	    			num = (Spinner)v.findViewById(R.id.bymon_mon);
	    			add = (Button)v.findViewById(R.id.bymon_add);
	    			list = (TextView)v.findViewById(R.id.bymon_list);
	    			clear = (Button)v.findViewById(R.id.bymon_clear);
	    			final ArrayList<Integer> mons;
	    			ArrayAdapter<Integer> ad_mons;
	    			mons = new ArrayList<Integer>();
	    			for (int i=1;i<13;i++){
	    				mons.add(i);
	    			}
	    			ad_mons = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line, mons);
	    			num.setAdapter(ad_mons);
	    			list.setText(getRule("BYMONTH"));
	    			add.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					String data = list.getText().toString();
	    					if (data.length()!=0){
	    						list.setText(list.getText().toString() + "," + mons.get(num.getSelectedItemPosition()));
	    					} else {
	    						int i = num.getSelectedItemPosition();
	    						list.setText(Integer.toString(mons.get(i)));
	    					}
	    				}
	    			});
	    			clear.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					list.setText("");
	    				}
	    			});
	    			db.setNegativeButton("Cancel", cancel);
	    			db.setPositiveButton("OK", ok);
	    			db.create();
	    			db.show();
	    			break;
	    		case 8:		//by set pos
	    			db = new AlertDialog.Builder(this);
	    			v = inflater.inflate(R.layout.bysetpos, null);
	    			db.setView(v);
	    			pm = (Spinner)v.findViewById(R.id.bysetpos_pm);
	    			num = (Spinner)v.findViewById(R.id.bysetpos_pos);
	    			add = (Button)v.findViewById(R.id.bysetpos_add);
	    			list = (TextView)v.findViewById(R.id.bysetpos_list);
	    			clear = (Button)v.findViewById(R.id.bysetpos_clear);
	    			pm.setAdapter(ad_pm);
	    			final ArrayList<Integer> pos;
	    			ArrayAdapter<Integer> ad_pos;
	    			pos = new ArrayList<Integer>();
	    			for (int i=1;i<367;i++){
	    				pos.add(i);
	    			}
	    			ad_pos = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line, pos);
	    			num.setAdapter(ad_pos);
	    			list.setText(getRule("BYSETPOS"));
	    			add.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					String data = list.getText().toString();
	    					if (data.length()!=0){
	    						if (pm.getSelectedItemPosition()==0){	//positive
	    							list.setText(list.getText().toString() + "," + pos.get(num.getSelectedItemPosition()));
	    						} else {				//negative
	    							list.setText(list.getText().toString() + ",-" + pos.get(num.getSelectedItemPosition()));
	    						}
	    					} else {
	    						if (pm.getSelectedItemPosition()==0){	//positive
	    							int i = num.getSelectedItemPosition();
		    						list.setText(Integer.toString(pos.get(i)));
	    						} else {		//negative	
	    							int i = num.getSelectedItemPosition();
		    						list.setText("-" + Integer.toString(pos.get(i)));
	    						}
	    					}
	    				}
	    			});
	    			clear.setOnClickListener(new OnClickListener(){
	    				@Override
	    				public void onClick(View v){
	    					list.setText("");
	    				}
	    			});
	    			db.setNegativeButton("Cancel", cancel);
	    			db.setPositiveButton("OK", ok);
	    			db.create();
	    			db.show();
	    			break;
	    		default:	    	
	    	}
		}
	    
	    DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int key = rule_spinner.getSelectedItemPosition();
				switch (key){
					case 0:
						addBySecond(dialog);
						break;
					case 1:
						addByMinute(dialog);
						break;
					case 2:
						addByHour(dialog);
						break;
					case 3:
						addByDay(dialog);
						break;
					case 4:
						addByMonthDay(dialog);
						break;
					case 5:
						addByYearDay(dialog);
						break;
					case 6:
						addByWeekNo(dialog);
						break;
					case 7:
						addByMonth(dialog);
						break;
					case 8:
						addBySetPos(dialog);
						break;
				}
				dialog.dismiss();
			}
		};

		DialogInterface.OnClickListener cancel = new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		};


	    protected void addBySetPos(DialogInterface d) {
	    	View v = ((Dialog) d).findViewById(R.id.BySetPos);
			TextView list = (TextView)v.findViewById(R.id.bysetpos_list);
			String key = "BYSETPOS";
			if(list.getText().length()!=0){
				String val = key + "=";
				val = val + list.getText();
				rule.put(key, val);
			} else {
				rule.remove(key);
			}
			buildrrule();
			buildparts();
		}


		protected void addByMonth(DialogInterface d) {
			View v = ((Dialog) d).findViewById(R.id.ByMonth);
			TextView list = (TextView)v.findViewById(R.id.bymon_list);
			String key = "BYMONTH";
			if(list.getText().length()!=0){
				String val = key + "=";
				val = val + list.getText();
				rule.put(key, val);
			} else {
				rule.remove(key);
			}
			buildrrule();
			buildparts();
		}


		protected void addByWeekNo(DialogInterface d) {
			View v = ((Dialog) d).findViewById(R.id.ByWeekNo);
			TextView list = (TextView)v.findViewById(R.id.byweekno_list);
			String key = "BYWEEKNO";
			if(list.getText().length()!=0){
				String val = key + "=";
				val = val + list.getText();
				rule.put(key, val);
			} else {
				rule.remove(key);
			}
			buildrrule();
			buildparts();
		}


		protected void addByYearDay(DialogInterface d) {
			View v = ((Dialog) d).findViewById(R.id.ByYearDay);
			TextView list = (TextView)v.findViewById(R.id.byyearday_list);
			String key = "BYYEARDAY";
			if(list.getText().length()!=0){
				String val = key + "=";
				val = val + list.getText();
				rule.put(key, val);
			} else {
				rule.remove(key);
			}
			buildrrule();
			buildparts();
		}


		protected void addByMonthDay(DialogInterface d) {
			View v = ((Dialog) d).findViewById(R.id.ByMonthDay);
			TextView list = (TextView)v.findViewById(R.id.bymonday_list);
			String key = "BYMONTHDAY";
			if(list.getText().length()!=0){
				String val = key + "=";
				val = val + list.getText();
				rule.put(key, val);
			} else {
				rule.remove(key);
			}
			buildrrule();
			buildparts();
		}


		protected void addByDay(DialogInterface d) {
			View v = ((Dialog) d).findViewById(R.id.ByDay);
			TextView list = (TextView)v.findViewById(R.id.byday_list);
			String key = "BYDAY";
			if(list.getText().length()!=0){
				String val = key + "=";
				val = val + list.getText();
				rule.put(key, val);
			} else {
				rule.remove(key);
			}
			buildrrule();
			buildparts();
		}


		protected void addByHour(DialogInterface d) {
			View v = ((Dialog) d).findViewById(R.id.ByHour);
			TextView list = (TextView)v.findViewById(R.id.byhour_list);
			String key = "BYHOUR";
			if(list.getText().length()!=0){
				String val = key + "=";
				val = val + list.getText();
				rule.put(key, val);
			} else {
				rule.remove(key);
			}
			buildrrule();
			buildparts();
		}


		protected void addByMinute(DialogInterface d) {
			View v = ((Dialog) d).findViewById(R.id.ByMinute);
			TextView list = (TextView)v.findViewById(R.id.bymin_list);
			String key = "BYMINUTE";
			if(list.getText().length()!=0){
				String val = key + "=";
				val = val + list.getText();
				rule.put(key, val);
			} else {
				rule.remove(key);
			}
			buildrrule();
			buildparts();
		}


		protected void addBySecond(DialogInterface d) {
			View v = ((Dialog) d).findViewById(R.id.BySecond);
			TextView list = (TextView)v.findViewById(R.id.bysec_list);
			String key = "BYSECOND";
			if(list.getText().length()!=0){
				String val = key + "=";
				val = val + list.getText();
				rule.put(key, val);
			} else {
				rule.remove(key);
			}
			buildrrule();
			buildparts();
		}

		@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        MenuItem i = menu.findItem(R.id.rec);
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

/*
 to add a rule part (like frequency, or interval):
  	1:	add an entry to rule ("FREQ", "FREQ = SECONDLY")
  	2.  call buildrrule() to generate string version of entire rrule.
  	3.  call buildparts() to get all the rule parts for the remove spinner.  This will notify the adapter that the data has changed.
  
  
 */
