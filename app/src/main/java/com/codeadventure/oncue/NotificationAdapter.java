package com.codeadventure.oncue;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Deep on 8/13/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final List<NotificationItems> mValues;

    public static class NotificationItems {
        final String id;
        final String name;
        final String content;
        final boolean favourite;

        public NotificationItems(String id, String name, String content, Boolean favourites) {
            this.id = id;
            this.name = name;
            this.content = content;
            this.favourite = favourites;
        }
    }


    public NotificationAdapter(List<NotificationItems> items) {
        mValues = items;
    }

    public NotificationAdapter() {
        mValues = null;
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_notifications, parent, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotificationAdapter.ViewHolder holder, int position) {
        final NotificationItems reminderItem = mValues.get(position);

        holder.mName.setText(reminderItem.name);
        holder.mContent.setText(reminderItem.content);
        holder.mdate.setText(reminderItem.id.substring(0,10));

        Context c = holder.mView.getContext();

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mName;
        final TextView mContent;
        final ImageButton mFavourite;
        final TextView mdate;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.id);
            mContent = (TextView) view.findViewById(R.id.content);
            mFavourite = (ImageButton) view.findViewById(R.id.favourite);
            mdate = (TextView) view.findViewById(R.id.date);

            mFavourite.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ;
                }
            });

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                }
            });
        }
    }

}