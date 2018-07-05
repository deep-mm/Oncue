package com.codeadventure.oncue;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeadventure.oncue.database.offline.SharedData;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.util.List;

/**
 * Created by Deep on 8/12/2017.
 */

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private final List<ReminderItem> mValues;


    public static class ReminderItem {
        final String id;
        final String name;
        final String image;

        public ReminderItem(String id, String name, String image) {
            this.id = id;
            this.name = name;
            this.image = image;
        }
    }



    public ReminderAdapter(List<ReminderItem> items) {
        mValues = items;
    }

    public ReminderAdapter() {
        mValues=null;
    }

    @Override
    public ReminderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_reminders, parent, false);
        return new ReminderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReminderAdapter.ViewHolder holder, int position) {
        final ReminderItem reminderItem = mValues.get(position);

        // set name and display profile pic
        holder.mName.setText(reminderItem.name);
        SharedData sd = new SharedData();
        if(sd.getReminderEntity(reminderItem.id).getREMINDER_TYPE().equalsIgnoreCase("friend")) {
            File file = new File(android.os.Environment.getExternalStorageDirectory().toString()
                    +"/" + "OnCue" + "/" +reminderItem.image+".jpg");
            displayProfilePic(holder.mProfilePic,file);
            if(sd.getReminderEntity(reminderItem.id).getREMINDER_STATUS().equalsIgnoreCase("reminded")){
                holder.done.setVisibility(View.VISIBLE);}
            else
                holder.done.setVisibility(View.INVISIBLE);

        }

        else{
            holder.mProfilePic.setBackgroundResource(R.drawable.ic_action_time);
            if(sd.getReminderEntity(reminderItem.id).getREMINDER_STATUS().equalsIgnoreCase("reminded")){
                holder.done.setVisibility(View.VISIBLE);}
            else
                holder.done.setVisibility(View.INVISIBLE);}



        // check if user is following this friend and update follow button appearance
        Context c = holder.mView.getContext();

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mName;
        final ImageView mProfilePic;
        final ImageView done;
        private final Context context;

        public ViewHolder(View view) {
            super(view);
            context = view.getContext();
            mView = view;
            mName = (TextView) view.findViewById(R.id.title);
            mProfilePic = (ImageView) view.findViewById(R.id.image);
            done = (ImageView) view.findViewById(R.id.done);

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,Reminder.class);
                    intent.putExtra("id",mValues.get(getAdapterPosition()).id);
                    context.startActivity(intent);
                }
            });
        }
    }

    private void displayProfilePic(ImageView imageView, File file) {
        // helper method to load the profile pic in a circular imageview
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        Picasso.with(imageView.getContext())
                .load(file)
                .placeholder(R.drawable.circle)
                .transform(transformation)
                .into(imageView);

    }


}
