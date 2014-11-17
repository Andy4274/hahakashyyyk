package com.jassoftware.textmeeting;

import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SendActivity extends Activity {

	TextView num_to_send;
	int sent;
	int sent_max;
	int count;
	TextView progress;
	ArrayList<SimpleContact> attendees;
	ArrayList<Invitee> invitees;
	InviteeAdapter ad_inv;
	ListView status_list;
	static Button resend;
	Button back;
	
	String text;
	ArrayList<String> msg;
	ArrayList<String> numbers;
	ArrayList<PendingIntent> sentIntents;
	ArrayList<PendingIntent> delIntents;
	final ArrayList<Invitee> results = new ArrayList<Invitee>();
	String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
	SmsManager sms;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //	should not put a listview in a scrollview.
        //setContentView(R.layout.activity_main);
        //ScrollView frame = (ScrollView)findViewById(R.id.frame);
        //LinearLayout filler = new LinearLayout(this);
        //filler = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.send, null);
        //frame.addView((View)filler);
        
        setContentView(R.layout.send);
        //find
        num_to_send = (TextView)findViewById(R.id.send_num_to_send);
        progress = (TextView)findViewById(R.id.send_progress);
        status_list = (ListView)findViewById(R.id.send_status_list);
        resend = (Button)findViewById(R.id.send_resend_button);
        back = (Button)findViewById(R.id.send_back);
        //set up list
        attendees = MainActivity.meeting.getAtt();
        invitees = new ArrayList<Invitee>();
        if (attendees.size()!=0){		//this is for the initial view.  it will be refilled with each part of the text for each person as it is sent to them.
        	for(int j = 0; j < attendees.size(); j++){
        		SimpleContact a = attendees.get(j);
        		Invitee i = new Invitee(a.getName(), a.getNumber());
        		invitees.add(i);
        	}
        }
        ad_inv = new InviteeAdapter(this, invitees);
        status_list.setAdapter(ad_inv);
        ad_inv.notifyDataSetChanged();
        //setup buttons
        resend.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		SendMsg();
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
        //misc
        sent_max = attendees.size();
        sent = 0;
        num_to_send.setText("Sending to " + sent_max + " attendees.");
        progress.setText("Sent " + sent + " of " + sent_max + " messages.");
        sms = SmsManager.getDefault();
        
    }

    private void createMsg() {
		text = MainActivity.meeting.toText();
		msg = sms.divideMessage(text);
		count = msg.size();
	}    

	private void SendMsg() {
		//for debug use only -- send to 9196161559
		//sms.sendMultipartTextMessage("9196161559", null, msg, null, null);

		//create message
        createMsg();
        
        //add originator to attendees if the number isn't already in the list
		SimpleContact org = new SimpleContact(MainActivity.meeting.getOrg());
		boolean found = false;
		for (int i=0;i<attendees.size();i++){
			if (org.getNumber().toString().equalsIgnoreCase(attendees.get(i).getNumber().toString())){
				found = true;
			}
		}
		if (!found){
			attendees.add(org);
			invitees.add(new Invitee(org.getName(), org.getNumber()));
			ad_inv.notifyDataSetChanged();
		}
		sent_max = attendees.size();
		//num_to_send.setText("Sending to " + sent_max + " attendees.");
		/*registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context c, Intent intent) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        results.get(intent.getIntExtra("index", 0)).setSent(Invitee.SENT_OK);
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    	results.get(intent.getIntExtra("index", 0)).setSent(Invitee.SENT_BAD);
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                    	results.get(intent.getIntExtra("index", 0)).setSent(Invitee.SENT_BAD);
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                    	results.get(intent.getIntExtra("index", 0)).setSent(Invitee.SENT_BAD);
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                    	results.get(intent.getIntExtra("index", 0)).setSent(Invitee.SENT_BAD);
                        break;
                }
            }
        }, new IntentFilter(SENT));
        
      //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context c, Intent intent) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                    	results.get(intent.getIntExtra("index", 0)).setDelivered(Invitee.DELIVERED_OK);
                        break;
                    case Activity.RESULT_CANCELED:
                    	results.get(intent.getIntExtra("index", 0)).setDelivered(Invitee.DELIVERED_BAD);
                        break;                        
                }
            }
        }, new IntentFilter(DELIVERED));   
        */
		MasterSender ms = new MasterSender();
		ms.execute("null");
       
	}
	
	public static void enableResend(){
		resend.setEnabled(true);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    //add AsyncTask that calls one person and sends all the parts of the text and checks delivery of each
    // invitees will hold each part of the message.  name will be left as name, number will be set to part number

    private class MasterSender extends AsyncTask<String, Void, String>{
    	
    	@Override
		protected String doInBackground(String... s) {
    		 ArrayList<Sender> sendList = new ArrayList<Sender>();
    			for(int i=0;i<attendees.size();i++){
    				Sender sender = new Sender(i);
    				sendList.add(sender);
    				sender.execute(i);
    				sent++;
    		        //progress.setText("Sent " + sent + " of " + sent_max + " messages.");
    				try {
    					Thread.sleep(3000);
    				} catch (InterruptedException e) {
    					Log.e("Send Delay", "error sleeping:  " + e.getMessage());
    				}
    			}
    		return s[0];
    	}
    }
    
    private class Sender extends AsyncTask<Integer, Void, Integer>{

    	public static final int STATUS_OK = 0;
    	public static final int STATUS_SEND_BAD = 1;
    	public static final int STATUS_DEL_BAD = 2;
    	public static final int STATUS_BOTH_BAD = 3;
    	
    	int status;
    	int index;
    	SmsManager sms;
    	
        
        
    	public Sender(){
    	}
    	
    	public Sender(int i){
    		index = i;
    	}
    	
    	public void setIndex(int i){
    		index = i;
    	}
		@Override
		protected Integer doInBackground(Integer... num) {
			sms = SmsManager.getDefault();
			ArrayList<PendingIntent> pi_sent = new ArrayList<PendingIntent>();
			ArrayList<PendingIntent> pi_del = new ArrayList<PendingIntent>();
			
			for(int i=0;i<msg.size();i++){
				results.add(new Invitee(attendees.get(index).getName(), Integer.toString(i)));
		        Intent SI = new Intent(SENT);
		        SI.putExtra("index",i);
		        Intent DI = new Intent(DELIVERED);
		        DI.putExtra("index",i);
		        PendingIntent sentPI = PendingIntent.getBroadcast(getBaseContext(), 0, SI, 0);
		        PendingIntent deliveredPI = PendingIntent.getBroadcast(getBaseContext(), 0, DI, 0);
		        pi_sent.add(sentPI);
		        pi_del.add(deliveredPI);
			}
			
		        //---when the SMS has been sent---
		         
		        
		        sms.sendMultipartTextMessage(attendees.get(index).getNumber(), null, msg, pi_sent, pi_del);

		        
		        //setStatus();
			return status;
		}
    	
		 @Override
		  protected void onPostExecute(Integer status) {
			 switch (status){
			 	case STATUS_OK:
			 		invitees.get(index).setSent(Invitee.SENT_OK);
					invitees.get(index).setDelivered(Invitee.DELIVERED_OK);
			 		break;
			 	case STATUS_SEND_BAD:
			 		 invitees.get(index).setSent(Invitee.SENT_BAD);
					 invitees.get(index).setDelivered(Invitee.DELIVERED_OK);
			 		break;
			 	case STATUS_DEL_BAD:
			 		 invitees.get(index).setSent(Invitee.SENT_OK);
					 invitees.get(index).setDelivered(Invitee.DELIVERED_BAD);
			 		break;
			 	case STATUS_BOTH_BAD:
			 		 invitees.get(index).setSent(Invitee.SENT_BAD);
					 invitees.get(index).setDelivered(Invitee.DELIVERED_BAD);
			 		break;
			 	default:
			 }
			 ad_inv.notifyDataSetChanged();
		  }
		 
		 private boolean finished(){
			 boolean finished = true;
			 for (int i = 0;i<results.size();i++){
				 if(results.get(i).getDelivered()==Invitee.DELIVERED_NOT){
					 finished = false;
				 }
				 if(results.get(i).getSent()==Invitee.SENT_NOT){
					 finished = false;
				 }
			 }
			 return finished;
		 }
		 
		 private void setStatus(){
			 boolean sent_bad = false;
			 boolean del_bad = false;
			 for (int i = 0;i<results.size();i++){
				 if(results.get(i).getDelivered()==Invitee.DELIVERED_BAD){
					 del_bad = true;
				 }
				 if(results.get(i).getSent()==Invitee.SENT_BAD){
					 sent_bad = true;
				 }
			 }
			 status = 0;
			 if (sent_bad){
				 status += STATUS_SEND_BAD;	//adds 1
			 }
			 if (del_bad){
				 status =+ STATUS_DEL_BAD;	//adds 2
			 }
		 }	//if both were added, total is 3 which is STATUS_BOTH_BAD
    }




}
