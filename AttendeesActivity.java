package com.jassoftware.textmeeting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AttendeesActivity extends Activity {

	ArrayList<String> contact_names;
	ArrayList<String> ids;
	ArrayAdapter<String> ad_names;
	AutoCompleteTextView name;
	Button get_nums;
	ArrayList<String> nums_list;
	ArrayAdapter<String> ad_nums;
	Spinner nums;
	Button add_contact;
	ArrayList<String> filenames;
	ArrayAdapter<String> ad_lists;
	Spinner lists;
	Button add_list;
	Button edit_lists;
	ArrayList<SimpleContact> attendees;
	TextView attList;
	ArrayList<String> invited_names;
	ArrayAdapter<String> ad_rem;
	Spinner remove;
	Button rem;
	Button clear;
	Button set;
	Button next;
	
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        ScrollView frame = (ScrollView)findViewById(R.id.frame);
	        LinearLayout filler = new LinearLayout(this);
	        filler = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.attendees, null);
	        frame.addView((View)filler);
	        //find
	        name = (AutoCompleteTextView)findViewById(R.id.att_name);
	        get_nums = (Button)findViewById(R.id.att_get_num_button);
	        nums = (Spinner)findViewById(R.id.att_num_spinner);
	        add_contact = (Button)findViewById(R.id.att_add_contact_button);
	        lists = (Spinner)findViewById(R.id.att_list_spinner);
	        add_list = (Button)findViewById(R.id.att_add_list_button);
	        edit_lists = (Button)findViewById(R.id.att_edit_button);
	        attList = (TextView)findViewById(R.id.att_list);
	        remove = (Spinner)findViewById(R.id.att_rem_spinner);
	        rem = (Button)findViewById(R.id.att_remove_button);
	        clear = (Button)findViewById(R.id.att_clear_button);
	        set = (Button)findViewById(R.id.att_set);
	        next = (Button)findViewById(R.id.att_next);
	        //initialize ArrayLists and adapters
	        //contact names
	        contact_names = new ArrayList<String>();
			ids = new ArrayList<String>();
			String proj[] = {ContactsContract.Data._ID, ContactsContract.Contacts.DISPLAY_NAME};
			Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, proj, null, null, null);
			if (c.moveToFirst()){
				do {
					contact_names.add(c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
					ids.add(c.getString(c.getColumnIndex(ContactsContract.Data._ID)));
				} while (c.moveToNext());
			}
			ad_names = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contact_names);
	        name.setAdapter(ad_names);
	        //nums
	        nums_list = new ArrayList<String>();
	        ad_nums = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nums_list);
	        nums.setAdapter(ad_nums);
	        //lists
	        filenames = new ArrayList<String>();
	        File f = new File(MainActivity.location);
	        File[] files = f.listFiles();
	        for(int i=0;i<files.length;i++){
	        	if (!files[i].isDirectory()){
	        		filenames.add(files[i].getName());
	        	}
	        }
	        ad_lists = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filenames);
	        lists.setAdapter(ad_lists);
			//attList
	        attendees = new ArrayList<SimpleContact>();
	        attendees = MainActivity.meeting.getAtt();  //get any attendees already set
	        String s = new String();
	        if (attendees == null){
	        	attendees = new ArrayList<SimpleContact>();
	        } else {
	        	for (int i = 0; i < attendees.size();i++){
	        		if (i==0){
        				s = attendees.get(i).toString();
        			} else {
        				s = s + "\n" + attendees.get(i).toString();
        			}
	        	}
	        }
	        attList.setText(s);
	        //remove spinner
	        invited_names = new ArrayList<String>();
	        if (attendees!=null){
	        	for (int i=0;i<attendees.size();i++){
	        		invited_names.add(attendees.get(i).toString());
	        	}
	        }
	    	ad_rem = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, invited_names);
	    	remove.setAdapter(ad_rem);
	        //set up buttons
	        get_nums.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		nums_list.clear();
	        		String n = name.getText().toString();
					Boolean found = false;	
					int i;
					for (i=0; i<contact_names.size(); i=i+1){
						if (contact_names.get(i).equalsIgnoreCase(n)){
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
							nums_list.add(pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
						}
						ad_nums.notifyDataSetChanged();
					}else{
						Toast t = Toast.makeText(getBaseContext(), "Please use a name from your contacts", Toast.LENGTH_SHORT);
						t.show();
					}
	        	}
	        });
	        add_contact.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		SimpleContact newcontact = new SimpleContact(name.getText().toString(), nums.getSelectedItem().toString());
	        		if (!listContains(attendees, newcontact)){
	        			attendees.add(newcontact);
	        			attList.setText(attList.getText().toString() + "\n" + newcontact.toString());
	        			name.setText("");
	        			nums_list.clear();
	        			ad_nums.notifyDataSetChanged();
	        		}else{
						Toast t = Toast.makeText(getBaseContext(), "You have already added this contact.", Toast.LENGTH_SHORT);
						t.show();
					}
	        	}
	        });
	        add_list.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		File f = new File(MainActivity.location, filenames.get(lists.getSelectedItemPosition()));
	        		String msg="";
	        		try {
						BufferedReader br = new BufferedReader(new FileReader(f));
						String s = "";
						while ((s = br.readLine()) != null){
							msg = msg + s + "\n";
						}
						br.close();
					} catch (FileNotFoundException e) {
						Log.e("AttendeesActivity", "Failed to open file: " + e.getMessage());
					} catch (IOException e) {
						Log.e("AttendeesActivity", "Failed to read file: " + e.getMessage());
					}
	        		//list is in msg
	        		int sep = msg.indexOf("\n");
	        		String ncstring = "";
	        		SimpleContact nc;
	        		do {
	        			ncstring = msg.substring(0, sep);
	        			nc = new SimpleContact(ncstring);
	        			if (!listContains(attendees, nc)){
	        				attendees.add(nc);
	        				invited_names.add(nc.getName());
	        				attList.setText(attList.getText().toString() + "\n" + nc.toString());
	        			}
	        			msg = msg.substring(sep+1);
	        		}while((sep = msg.indexOf("\n"))!= -1);
	        		if (!msg.equalsIgnoreCase("")){
	        			nc = new SimpleContact(msg);
	        			if (!listContains(attendees, nc)){
	        				attendees.add(nc);
	        				invited_names.add(nc.getName());
	        				attList.setText(attList.getText().toString() + "\n" + nc.toString());
	        			}
	        		}
	        		ad_rem.notifyDataSetChanged();
	        	}
	        });
	        edit_lists.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		Intent i = new Intent(getBaseContext(), com.jassoftware.textmeeting.AttendeeListActivity.class);
	        		startActivity(i);
	        	}
	        });
	        rem.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		//get position
	        		int pos = remove.getSelectedItemPosition();
	        		//remove from list
	        		attendees.remove(pos);
	        		invited_names.remove(pos);
	        		//update spinner
	        		ad_rem.notifyDataSetChanged();
	        		remove.setSelection(0); 
	        		//update display of list
	        		String s = new String();
	        		for (int i = 0; i < attendees.size();i++){
	        			if (i==0){
	        				s = attendees.get(i).toString();
	        			} else {
	        				s = s + "\n" + attendees.get(i).toString();
	        			}
	    	        }
	    	        attList.setText(s);
	        	}
	        });
	        clear.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		attendees.clear();
	        		invited_names.clear();
	        		ad_rem.notifyDataSetChanged();
	        		attList.setText("");
	        	}
	        });
	        set.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		MainActivity.meeting.clearAtt();
	        		MainActivity.meeting.setAtt(attendees);
	        	}
	        });
	        next.setOnClickListener(new OnClickListener(){
	        	@Override
	        	public void onClick(View v){
	        		Intent i = new Intent(getBaseContext(), com.jassoftware.textmeeting.RecurActivity.class);
	        		startActivity(i);
	        	}
	        });
	        
	    }


	  	protected boolean listContains(ArrayList<SimpleContact> contactList, SimpleContact newcontact) {
			boolean found = false;
			SimpleContact listitem;
	    	for(int i = 0; i < contactList.size(); i++){
				listitem = contactList.get(i);
				if (listitem.equals(newcontact)){
					found = true;
				}
			}
	    	return found;
		}


		@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        MenuItem i = menu.findItem(R.id.att);
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
