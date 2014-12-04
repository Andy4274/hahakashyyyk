package com.jassoftware.textmeeting;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AttendeeListActivity extends Activity {

	ArrayList<SimpleContact> invitees;
	String location;
	ArrayList<String> filenames;
	ArrayAdapter<String> ad_lists;
	Spinner lists;
	Button use_this;
	TextView cur_list;
	ArrayList<String> names;
	ArrayList<String> ids;
	ArrayAdapter<String> ad_names;
	AutoCompleteTextView c_name;
	Button get_nums;
	ArrayList<String> numbers;
	ArrayAdapter<String> ad_nums;
	Spinner c_nums;
	Button add_c;
	EditText paste;
	Button scan;
	ArrayList<String> invited_names;
	ArrayAdapter<String> ad_i_names;
	Spinner remove_c;
	Button remove;
	Button clear;
	EditText list_name;
	Button save;
	TextView loc;
	Button back;
	
	
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        ScrollView frame = (ScrollView)findViewById(R.id.frame);
	        LinearLayout filler = new LinearLayout(this);
	        filler = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.editlists, null);
	        frame.addView((View)filler);
	        //find stuff
	        lists = (Spinner)findViewById(R.id.el_list_spinner);
	        use_this = (Button)findViewById(R.id.el_use_button);
	        cur_list = (TextView)findViewById(R.id.el_curr_list);
	        c_name = (AutoCompleteTextView)findViewById(R.id.el_contact_name);
	        get_nums = (Button)findViewById(R.id.el_get_num_button);
	        c_nums = (Spinner)findViewById(R.id.el_contact_numbers);
	        add_c = (Button)findViewById(R.id.el_add_contact_button);
	        paste = (EditText)findViewById(R.id.el_paste_box);
	        scan = (Button)findViewById(R.id.el_scan_paste_button);
	        remove_c = (Spinner)findViewById(R.id.el_remove_spinner);
	        remove = (Button)findViewById(R.id.el_remove_button);
	        clear = (Button)findViewById(R.id.el_clear_button);
	        list_name = (EditText)findViewById(R.id.el_list_name);
	        save = (Button)findViewById(R.id.el_save_button);
	        loc = (TextView)findViewById(R.id.el_loc);
	        back = (Button)findViewById(R.id.el_back);
	        //set up adapters and lists
	        //lists
	        location = MainActivity.location;
	        File l = new File(location);
	        l.mkdirs();
	        try {
				l.createNewFile();
			} catch (IOException e) {
				Log.e("AttendeeListActivity", "Not possible to create file:  " + e.getMessage());
			}
	        loc.setText(l.getAbsolutePath());	        
	        File[] files = l.listFiles();
	        filenames = new ArrayList<String>();
	        filenames.add("Create a new list");
	        if (files!=null){
	        	for(int i=0;i<files.length;i++){
	        		filenames.add(files[i].getName());
	        	}
	        }
	        ad_lists = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filenames);
	        lists.setAdapter(ad_lists);
	        //c_name
	        names = new ArrayList<String>();
			ids = new ArrayList<String>();
			String proj[] = {ContactsContract.Data._ID, ContactsContract.Contacts.DISPLAY_NAME};
			Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, proj, null, null, null);
			if (c.moveToFirst()){
				do {
					names.add(c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
					ids.add(c.getString(c.getColumnIndex(ContactsContract.Data._ID)));
				} while (c.moveToNext());
			}
			ad_names = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
			c_name.setAdapter(ad_names);
	        //c_nums
			numbers = new ArrayList<String>();
	        ad_nums = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, numbers);
	        c_nums.setAdapter(ad_nums);
	        //remove_c
	        invitees = new ArrayList<SimpleContact>();
	        invited_names = new ArrayList<String>();
	        ad_i_names = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, invited_names);
	        remove_c.setAdapter(ad_i_names);
	        list_name.setEnabled(false);
	        save.setEnabled(false);
	        //set up buttons
	        use_this.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		int pos = lists.getSelectedItemPosition();
	        		if (pos==0){  //selected Create new 
	        			invitees.clear();
	        			cur_list.setText("");
	        			invited_names.clear();
	        			ad_i_names.notifyDataSetChanged();
	        			list_name.setEnabled(true);
	        			save.setEnabled(true);
	        		} else {  //selected an existing file
	        			File f = new File(location + "/" + filenames.get(lists.getSelectedItemPosition()));
	        			try {
	        				//byte[] buffer = null;
	        				//FileInputStream fis = new FileInputStream(f);
							//fis = openFileInput(f.getName());
							//fis.read(buffer);
							//fis.close();
							//String s = buffer.toString();
	        				BufferedReader br = new BufferedReader(new FileReader(f));
							String s = "";
							String msg="";
							while ((s = br.readLine()) != null){
								msg = msg + s + "\n";
							}
							br.close();
							invitees.clear();
							invited_names.clear();
							scanList(msg);
						} catch (FileNotFoundException e) {
							Log.e("AttendeeListActivity", "Failed to open file " + e.getMessage());
						} catch (IOException e) {
							Log.e("AttendeeListActivity", "Failed to read file " + e.getMessage());
						}
	        			
	        			list_name.setEnabled(false);
	        			String s = f.getName();
	        			if(s.substring(s.length()-4).equalsIgnoreCase(".txt")){
	        				s = s.substring(0, s.length()-4);
	        			}
	        			list_name.setText(s);
	        			save.setEnabled(true);
	        		}
	        	}
	        });
	        get_nums.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		numbers.clear();
	        		String n = c_name.getText().toString();
					Boolean found = false;	
					int i;
					for (i=0; i<names.size(); i=i+1){
						if (names.get(i).equalsIgnoreCase(n)){
							found = true;
							break;
						}
					}
					if (found){
						String id = ids.get(i);
						Cursor pCur = getContentResolver().query(
						       ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						       null,
						       ContactsContract.CommonDataKinds.Phone.CONTACT_ID
						         + " = ?", new String[] { id }, null);
						while(pCur.moveToNext()){
							numbers.add(pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
						}
						ad_nums.notifyDataSetChanged();
					}else{
						Toast t = Toast.makeText(getBaseContext(), "Please use a name from your contacts", Toast.LENGTH_SHORT);
						t.show();
					}
	        	}
	        });
	        add_c.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		SimpleContact newc = new SimpleContact(c_name.getText().toString(), numbers.get(c_nums.getSelectedItemPosition()));
	        		invitees.add(newc);
	        		invited_names.add(newc.getName());
	        		ad_i_names.notifyDataSetChanged();
	        		String s = "";
	        		for (int i = 0;i<invitees.size();i++){
	        			if (i==0){
	        				s = invitees.get(0).toString();
	        			} else {
	        				s = s + "\n" + invitees.get(i).toString();
	        			}
	        		}
	        		cur_list.setText(s);
	        		c_name.setText("");
	        		numbers.clear();
	        		ad_nums.notifyDataSetChanged();
	        	}
	        });
	        scan.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		scanList(paste.getText().toString());
	        		paste.setText("");
	        	}
	        });
	        remove.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		int pos = remove_c.getSelectedItemPosition();
	        		invitees.remove(pos);
	        		invited_names.remove(pos);
	        		ad_i_names.notifyDataSetChanged();
	        		String s = "";
	        		for (int i = 0;i<invitees.size();i++){
	        			if (i==0){
	        				s = invitees.get(0).toString();
	        			} else {
	        				s = s + "\n" + invitees.get(i).toString();
	        			}
	        		}
	        		cur_list.setText(s);
	        	}
	        });
	        clear.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		invitees.clear();
	        		invited_names.clear();
	        		ad_i_names.notifyDataSetChanged();
	        		cur_list.setText("");	        		
	        	}
	        });
	        save.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		if (list_name.getText().toString().isEmpty()){
	        			Toast t = Toast.makeText(getBaseContext(), "You must enter a name for this list.", Toast.LENGTH_SHORT);
						t.show();
	        		} else {
	        			File f;
	        			String name = list_name.getText().toString();
	        			if (name.substring(name.length()-4).equalsIgnoreCase(".txt")){
	        				f = new File(location + "/" + name);
	        			} else {
	        				f = new File(location + "/" + name + ".txt");
	        			}
	        			try {
							FileOutputStream fos = new FileOutputStream(f);
							String s = cur_list.getText().toString();
							fos.write(s.getBytes());
							fos.close();
						} catch (FileNotFoundException e) {
							Log.e("AttendeeListActivity", "Failed to create output file: " + e.getMessage());
						} catch (IOException e) {
							Log.e("AttendeeListActivity", "Failed to write output file: " + e.getMessage());
						}
	        			File l = new File(location);
	        			File[] files = l.listFiles();
	        			filenames.clear();
	        			filenames.add("Create a new list");
	        			for (int i = 0;i<files.length;i++){
	        				filenames.add(files[i].getName());
	        			}
	        			ad_lists.notifyDataSetChanged();
	        			list_name.setEnabled(false);
	        			save.setEnabled(false);
	        			clear.performClick();
	        			list_name.setText("");
	        		}
	        	}
	        });
	        back.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		Intent i = new Intent(getBaseContext(), com.jassoftware.textmeeting.MainActivity.class);
	        		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        		startActivity(i);
	        	}
	        });
	        //set up responses and misc set up
	        lists.requestFocus();
	    }


	protected void scanList(String s) {
		int sep;
		String ncstring;
		SimpleContact nc;
		sep = s.indexOf("\n");
		do {			
			ncstring = s.substring(0,sep);
			nc = new SimpleContact(ncstring);
			if (!isIn(invitees, nc)){
				invitees.add(nc);
				invited_names.add(nc.getName());
			}
			s = s.substring(sep+1);
		} while((sep = s.indexOf("\n"))!=-1);
		if (!s.equalsIgnoreCase("")){
			nc = new SimpleContact(s);
			if (!isIn(invitees, nc)){
				invitees.add(nc);
				invited_names.add(nc.getName());
			}
		}
		ad_i_names.notifyDataSetChanged();
		String l = "";
		for(int i=0;i<invitees.size();i++){
			l = l + invitees.get(i) + "\n";
		}
		cur_list.setText(l);
	}

	public boolean isIn(ArrayList<SimpleContact> list, SimpleContact sc){
		boolean found = false;
		for(int i=0;i<list.size();i++){
			if (sc.equals(list.get(i))){
				found = true;
			}
		}
		return found;
	}

		@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        MenuItem i = menu.findItem(R.id.attL);
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

