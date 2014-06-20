package com.internship.remindersfacebookapp.app;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.internship.remindersfacebookapp.adapters.ReminderListAdapter;
import com.internship.remindersfacebookapp.adapters.SQLiteAdapter;
import com.internship.remindersfacebookapp.models.FacebookUser;

public class RemindersFragment extends ListFragment {
private static int BUNDLE_SIZE = 1;
private String header;
private FacebookUser mFacebookUser;
private View mView;
private SQLiteAdapter db;
    public RemindersFragment newInstance(String message) {
        this.setArguments(new Bundle(BUNDLE_SIZE));
        header = message;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.reminders_fragments, container, false);
	    db = new SQLiteAdapter(mView.getContext());
	    Bundle extras = getActivity().getIntent().getExtras();
        TextView textView = (TextView) mView.findViewById(R.id.profile_name);
        textView.setText(header);
	    mFacebookUser = new FacebookUser(
			    extras.getString(FacebookUser.USERNAME),
			    extras.getString(FacebookUser.MAIL),
			    extras.getString(FacebookUser.IMAGE));
        refreshList(mView,db);
        return mView;
    }

    @Override
    public void onResume() {
        //db.deleteAllReminders();
        refreshList(mView,db);
        super.onResume();

    }

    public void refreshList(View view, SQLiteAdapter db){
        if(header.equals(ViewPagerActivity.HEADER_1)){
            this.setListAdapter(new ReminderListAdapter(view.getContext(), db.selectReminder(mFacebookUser,1)));
        }
        if(header.equals(ViewPagerActivity.HEADER_2)){
            this.setListAdapter(new ReminderListAdapter(view.getContext(), db.selectReminder(mFacebookUser,0)));
        }
    }
}