package com.codeadventure.oncue;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.util.List;

/**
 * Created by Deep on 8/13/2017.
 */

public class AddReminderAdapter extends RecyclerView.Adapter<AddReminderAdapter.ViewHolder> {

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



    public AddReminderAdapter(List<ReminderItem> items) {
        mValues = items;
    }

    public AddReminderAdapter() {
        mValues=null;
    }

    @Override
    public AddReminderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recentfriends, parent, false);
        return new AddReminderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AddReminderAdapter.ViewHolder holder, int position) {
        final ReminderItem reminderItem = mValues.get(position);

        File file = new File(android.os.Environment.getExternalStorageDirectory().toString()
                +"/" + "OnCue" + "/" + reminderItem.id+".jpg");
        displayProfilePic(holder.mProfilePic,file);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView mProfilePic;
        private final Context context;

        public ViewHolder(View view) {
            super(view);
            context = view.getContext();
            mProfilePic = (ImageView) view.findViewById(R.id.profile_image);

            mProfilePic.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,AddReminder.class);
                    intent.putExtra("fb_id",mValues.get(getAdapterPosition()).id);
                    intent.putExtra("type", "friend");
                    intent.putExtra("name", mValues.get(getAdapterPosition()).name);
                    System.out.println("ccccc "+mValues.get(getAdapterPosition()).name);
                    context.startActivity(intent);
                }
            });
        }
    }

    private void displayProfilePic(ImageView imageView, File file) {
        // helper method to load the profile pic in a circular imageview
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(40)
                .oval(false)
                .build();
        Picasso.with(imageView.getContext())
                .load(file)
                .transform(transformation)
                .into(imageView);

    }


}
