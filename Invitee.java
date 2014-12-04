package com.jassoftware.textmeeting;

import java.util.ArrayList;

public class Invitee {

	private String name;
	private String number;
	private String sent;	//should use the strings defined below
	private String delivered;  //should use the strings defined below
	private ArrayList<Integer> sent_bad;
	private ArrayList<Integer> del_bad;
	
	public final static String SENT_OK = "Sent OK";
	public final static String SENT_NOT = "Not yet Sent";
	public final static String SENT_BAD = "Not Sent";
	public final static String DELIVERED_OK = "Delivered OK";
	public final static String DELIVERED_NOT = "Not yet Delivered";
	public final static String DELIVERED_BAD = "Delivey caused an error";
	
	public Invitee(String name, String num){
		this.name = name;
		this.number = num;
		sent = SENT_NOT;
		sent_bad = new ArrayList<Integer>();
		delivered = DELIVERED_NOT;
		del_bad = new ArrayList<Integer>();
	}

	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}
	
	public String getDelivered() {
		return delivered;
	}

	public void setDelivered(String delivered) {
		this.delivered = delivered;
	}

	public String getSent() {
		return sent;
	}
	
	public void setSent(String sent) {
		this.sent = sent;
	}
	
	public void sent_bad_add(int i){
		sent_bad.add(i);
	}
	
	public void del_bad_add(int i){
		del_bad.add(i);
	}
	
	public ArrayList<Integer> getSent_bad(){
		return sent_bad;
	}
	
	public ArrayList<Integer>getDel_bad(){
		return del_bad;
	}
	
	public boolean sent_bad_remove(int i){
		boolean found = false;
		for (int j=0;j<sent_bad.size();j++){
			if (sent_bad.get(j)==i){
				found = true;
				sent_bad.remove(j);
			}
		}
		return found;
	}
	
	public boolean del_bad_remove(int i){
		boolean found = false;
		for (int j=0;j<del_bad.size();j++){
			if (del_bad.get(j)==i){
				found = true;
				del_bad.remove(j);
			}
		}
		return found;
	}
	
	public void sent_bad_clear(){
		sent_bad.clear();
	}
	
	public void del_bad_clear(){
		del_bad.clear();
	}
	
}

