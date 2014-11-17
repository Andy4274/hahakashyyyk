package com.jassoftware.textmeeting;
import java.util.ArrayList;

import com.jassoftware.textmeeting.SimpleContact;

public class Meeting {

		String originator;   //a single SimpleContact string
		String attendeeList; //a list of SimpleContact strings, \n as separator
		String startTime;
		String endTime;
		String lasts;
		String recurs;
		String location;
		String title;
		
		public Meeting(){
			originator = null; 
			attendeeList = ""; 
			startTime = null;
			endTime = null;
			lasts = null;
			recurs = null;
			location = null;
			title = null;
		}
		
		public Meeting(String s){  //to create a Meeting from a string created by the toText fn.
			int starts, ends;
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
			String data;
			
			if (s.startsWith("<textmeeting>")){
				starts = s.indexOf(org) + org.length();
	        	ends = s.indexOf(org_end);
	        	if (starts<ends){
	        		data = s.substring(starts, ends);
	        		originator = data;
	        	} else {
	        		originator = null;
	        	}
	        	starts = s.indexOf(title) + title.length();
	        	ends = s.indexOf(title_end);
	        	if (starts<ends){
	        		data = s.substring(starts, ends);
	        		title = data;
	        	} else {
	    			title = null;
	    		}
	        	starts = s.indexOf(loc) + loc.length();
	        	ends = s.indexOf(loc_end);
	        	if (starts<ends){
	        		data = s.substring(starts, ends);
	        		location = data;
	        	} else {
	        		location = null;
	        	}
	        	starts = s.indexOf(start) + start.length();
	        	ends = s.indexOf(start_end);
	        	if (starts<ends){
	        		data = s.substring(starts, ends);
	        	   	startTime = data;
	        	} else {
	        		startTime = null;
	        	}
	        	starts = s.indexOf(end) + end.length();
	        	ends = s.indexOf(end_end);
	        	if (starts<ends){
	        		data = s.substring(starts, ends);
	        		endTime = data;
	        	} else {
	        		endTime = null;
	        	}
	        	starts = s.indexOf(last) + last.length();
	        	ends = s.indexOf(last_end);
	        	if (starts<ends){
	        		data = s.substring(starts, ends);
	        		lasts = data;
	        	} else {
	        		lasts = null;
	        	}
	        	starts = s.indexOf(rec) + rec.length();
	        	ends = s.indexOf(rec_end);
	        	if (starts<ends){
	        		data = s.substring(starts, ends);
	        		recurs = data;
	        	} else {
	        		recurs = null;
	        	}
	        	starts = s.indexOf(att) + att.length();
	        	ends = s.indexOf(att_end);
	        	if (starts<ends){
	        		data = s.substring(starts, ends);
	        		attendeeList = data;
	        	} else {
	        		attendeeList = null;
	        	}
			} else {
				originator = null; 
				attendeeList = null;
				startTime = null;
				endTime = null;
				lasts = null;
				recurs = null;
				location = null;
				title = null;
			}
		}
		
		public void setTitle(String data) {
			title = data;			
		}
		public String getTitle(){
			return title;
		}

		public String toText(){
			String msg = "<textmeeting>";
			msg = msg + "\n<title>" + title + "</title>";
			msg = msg +	"\n<org>" + originator + "</org>";
			msg = msg + "\n<start>" +	startTime + "</start>";
			if (endTime != null){
				msg = msg + "\n<end>" + endTime + "</end>";
				msg = msg + "\n<len>null</len>";
			} else {
				msg = msg + "\n<end>null</end>";
				msg = msg + "\n<len>" + lasts + "</len>";
			}
			msg = msg + "\n<loc>" + location + "</loc>";
			if ((attendeeList==null)||(attendeeList.length()==0)){
				msg = msg + "\n<attendees>null</attendees>";	//\n added to avoid unpredictably added \n's
			} else {
				msg = msg + "\n<attendees>" + attendeeList + "\n</attendees>";
			}
			if (recurs == null){
				msg = msg + "\n<recur>null</recur>";
			} else {
				msg = msg + "\n<recur>" + recurs + "</recur>";
			}
			msg = msg + "\n</textmeeting>";
			return msg;
		}
		
		public boolean isEmpty(String s){
			boolean empty = false;
			if ((s==null)||(s.length()==0)){
				empty=true;
			}
			return empty;
		}
		
		public boolean isReady(){
			boolean ready = true;
			if (isEmpty(originator)){
				ready = false;
			}
			if (isEmpty(startTime)){
				ready = false;
			}
			if (isEmpty(endTime)&&isEmpty(lasts)){  //one or the other must be set, but never both
				ready=false;
			}
			if (isEmpty(location)){
				ready=false;
			}
			if (isEmpty(attendeeList)){
				ready=false;
			}
			//recurs is optional
			return ready;
		}

		public void setOrg(String o) {
			originator = o;
		}
		public String getOrg(){
			return originator;
		}
		
		public void setAtt(String l){
			attendeeList = l;
		}
		public String getAttL(){
			return attendeeList;
		}
		public void setAtt(ArrayList<SimpleContact> list){
			if (list.size()!=0){
				if (attendeeList==null){
					attendeeList="";
				}
				for (int i=0;i<list.size();i++){
					if (attendeeList.equalsIgnoreCase("")){
						attendeeList = list.get(i).toString();
					} else {
						attendeeList = attendeeList + "\n" + list.get(i).toString();
					}
				}
			}
		}
		public ArrayList<SimpleContact> getAtt(){
			if (attendeeList==null){
				return null;
			}
			if (attendeeList.length()==0){
				return null;
			}
			
			
			
			if((attendeeList!=null)||(!attendeeList.equalsIgnoreCase(""))){
				ArrayList<SimpleContact> list = new ArrayList<SimpleContact>();
				SimpleContact contact;
				String n_n;  //holds a SimpleContact string
				String sub;  //holds the shortened substring of attendeeList
				int subsep;  //holds position of next "\n"
				sub = attendeeList;
				Boolean done = false;
				while (!done){
					//find end of the first contact
					subsep = sub.indexOf("\n");
					//get string of first contact
					if (subsep == -1){ //result when there is no more separator character
						done = true;
						n_n = sub;
					} else {
						n_n = sub.substring(0, subsep);
					}//create a contact
					contact = new SimpleContact(n_n);
					//add it to the list
					list.add(contact);
					//cut that contact from the list
					sub = sub.substring(subsep + 1);
				}
				return list;
			} else {
				return null;
			}
		}
		public Boolean attIsEmpty(){
			return (attendeeList == null);
		}
		public void clearAtt(){
			attendeeList = null;
		}
		
		public void setLoc(String s){
			location = s;
		}
		public String getLoc(){
			return location;
		}
		
		public void setRec(String s){
			recurs = s;
		}
		public String getRec(){
			return recurs;
		}
		
		public void setStart(String s){
			startTime = s;
		}
		public String getStart(){
			return startTime;
		}
		
		public void setEnd(String s){
			endTime = s;
		}
		public String getEnd(){
			return endTime;
		}
		
		public void setLasts(String s){
			lasts = s;
		}
		public String getLasts(){
			return lasts;
		}
}
