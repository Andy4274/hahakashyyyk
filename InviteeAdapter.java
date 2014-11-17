package com.jassoftware.textmeeting;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class InviteeAdapter extends BaseAdapter {

	static class ViewHolder {
		TextView name;
		TextView number;
		TextView sent;
		TextView delivered;
	}
	LayoutInflater inflater;
	ArrayList<Invitee> list;
	Context c;
	
	public InviteeAdapter(Context context, ArrayList<Invitee> list){
		this.list = list;
		c = context;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int pos) {
		return list.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {	
		
		ViewHolder holder;
		if (view==null){
			holder = new ViewHolder();
			inflater = ((Activity)c).getLayoutInflater();
			view = inflater.inflate(R.layout.invitee_item, parent, false);
			holder.name = (TextView)view.findViewById(R.id.name);
			holder.number = (TextView)view.findViewById(R.id.number);
			holder.sent = (TextView)view.findViewById(R.id.sent);
			holder.delivered = (TextView)view.findViewById(R.id.delivered);
			view.setTag(holder);
		} else {
			holder = (ViewHolder)view.getTag();
		}
		Invitee i = list.get(pos);
		holder.name.setText(i.getName());
		holder.number.setText(i.getNumber());
		holder.sent.setText(i.getSent());
		holder.delivered.setText(i.getDelivered());
		return view;
	}

	@Override
	public void notifyDataSetChanged() {
		boolean done = true;
		boolean bad = false;
		for (int i = 0;i<list.size();i++){
			Invitee inv = list.get(i);
			if (inv.getDelivered()==Invitee.DELIVERED_NOT){
				done=false;
			}
			if ((inv.getSent()==Invitee.SENT_BAD)||(inv.getDelivered()==Invitee.DELIVERED_BAD)){
				bad = true;
			}
		}
		if (done&&bad){
			SendActivity.enableResend();
		}
		
		super.notifyDataSetChanged();
	}

}
