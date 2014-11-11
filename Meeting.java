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
		
		public Meeting(){
			originator = null; 
			attendeeList = ""; 
			startTime = null;
			endTime = null;
			lasts = null;
			recurs = null;
			location = null;
		}
		
		public Meeting(String s){  //to create a Meeting from a string created by the toText fn.
			if (s.startsWith("<textmeeting>")){
				
			} else {
				originator = null; 
				attendeeList = null;
				startTime = null;
				endTime = null;
				lasts = null;
				recurs = null;
				location = null;
			}
		}
		
		public String toText(){
			String msg = "";
			msg = "<textmeeting><org>" + originator + "</org>";
			msg = msg + "<start>" +	startTime + "</start>";
			if (endTime != null){
				msg = msg + "<end>" + endTime + "</end>";
			} else {
				msg = msg + "<len>" + lasts + "</len>";
			}
			msg = msg + "<loc>" + location + "</loc>";
			msg = msg + "<attendees>" + attendeeList + "</attendees>";
			msg = msg + "<recur>" + recurs + "</recur>";
			msg = msg + "</textmeeting>";
			return msg;
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
