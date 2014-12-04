package com.jassoftware.textmeeting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.telephony.SmsMessage;

public class MsgReceiver extends BroadcastReceiver {

	String Tag = "<textmeeting>";
	String org = "<org>";
	String org_end = "</org>";
	String loc = "<loc>";
	String loc_end = "</loc>";
	String title = "<title>";
	String title_end = "</title>";
	String start = "<start>";
	String start_end = "</start>";
	String end = "<end>";
	String end_end = "</end>";
	String last = "<len>";
	String last_end = "</len>";
	String rec = "<recur>";
	String rec_end = "</recur>";
	String att = "<attendees>";
	String att_end = "</attendees>";
	int starts;
	int ends;
	String data;
	
	
	
	
	
	@Override
	public void onReceive(Context c, Intent intent) {
		//---get the SMS message passed in---
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        String messageReceived = "";            
        if (bundle != null)
        {
            //---retrieve the SMS message received---
           Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++)
            {
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
                messageReceived += msgs[i].getMessageBody().toString();
                messageReceived += "\n";        
            }
        }
        if (messageReceived.substring(0, Tag.length()).equalsIgnoreCase(Tag)){
        	//it is ours
        	// read tags
        	Intent j = new Intent(c, com.jassoftware.textmeeting.DisplayMeetingActivity.class);
        	j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	starts = messageReceived.indexOf(org) + org.length();
        	ends = messageReceived.indexOf(org_end);
        	if (starts<ends){
        		data = messageReceived.substring(starts, ends);
        		j.putExtra("org", data);
        	} else {
        		j.putExtra("org", "null");
        	}
        	starts = messageReceived.indexOf(title) + title.length();
        	ends = messageReceived.indexOf(title_end);
        	if (starts<ends){
        		data = messageReceived.substring(starts, ends);
        		j.putExtra("title", data);
        	} else {
    			j.putExtra("title", "No Title");
    		}
        	starts = messageReceived.indexOf(loc) + loc.length();
        	ends = messageReceived.indexOf(loc_end);
        	if (starts<ends){
        		data = messageReceived.substring(starts, ends);
        		String data2="";		//strip odd \n's that appear here somehow.
        		for (int i = 0; i<data.length();i++){
        			if (!(data.substring(i, i+1).equalsIgnoreCase("\n"))){
        				data2 = data2 + data.substring(i, i+1);
        			}
        		}
        		data = data2;
        		j.putExtra("loc", data);
        	} else {
        		j.putExtra("loc", "null");
        	}
        	starts = messageReceived.indexOf(start) + start.length();
        	ends = messageReceived.indexOf(start_end);
        	if (starts<ends){
        		data = messageReceived.substring(starts, ends);
        	   	j.putExtra("start", data);
        	} else {
        		j.putExtra("start", "null");
        	}
        	starts = messageReceived.indexOf(end) + end.length();
        	ends = messageReceived.indexOf(end_end);
        	if (starts<ends){
        		data = messageReceived.substring(starts, ends);
        		j.putExtra("end", data);
        	} else {
        		j.putExtra("end", "null");
        	}
        	starts = messageReceived.indexOf(last) + last.length();
        	ends = messageReceived.indexOf(last_end);
        	if (starts<ends){
        		data = messageReceived.substring(starts, ends);
        		j.putExtra("last", data);
        	} else {
        		j.putExtra("last", "null");
        	}
        	starts = messageReceived.indexOf(rec) + rec.length();
        	ends = messageReceived.indexOf(rec_end);
        	if (starts<ends){
        		data = messageReceived.substring(starts, ends);
        		j.putExtra("recur", data);
        	} else {
        		j.putExtra("recur", "null");
        	}
        	starts = messageReceived.indexOf(att) + att.length();
        	ends = messageReceived.indexOf(att_end);
        	if (starts<ends){
        		data = messageReceived.substring(starts, ends);
        		j.putExtra("att", data);
        	} else {
        		j.putExtra("att", "null");
        	}
        	c.startActivity(j);
        	//create intent
        	//Intent i = new Intent(c)
            //.setData(Events.CONTENT_URI)
            //.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, m.getStart())
            
            
            //;
        	/*
        	Intent intent = new Intent(Intent.ACTION_INSERT)
            .setData(Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
            .putExtra(Events.TITLE, "Yoga")
            .putExtra(Events.DESCRIPTION, "Group class")
            .putExtra(Events.EVENT_LOCATION, "The gym")
            .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
            .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
             startActivity(intent);
        	*/
        	
        	
        } //else it isn't ours, so do nothing
	}

}

