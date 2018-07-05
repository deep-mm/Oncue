package com.codeadventure.oncue;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.util.Comparator;
import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    public static class FriendItem {
        final String id;
        final String name;
        final String image;

        public FriendItem(String id, String name, String image) {
            this.id = id;
            this.name = name;
            this.image = image;
        }

    }

    public static Comparator<FriendsAdapter.FriendItem> FriendNameComparator = new Comparator<FriendsAdapter.FriendItem>() {

        public int compare(FriendsAdapter.FriendItem s1, FriendsAdapter.FriendItem s2) {
            String friendName1 = s1.name.toUpperCase();
            String friendName2 = s2.name.toUpperCase();

            return friendName1.compareTo(friendName2);
        }
    };

    private final List<FriendItem> mValues;

    public FriendsAdapter(List<FriendItem> items) {
       mValues = items;
   }

    public FriendsAdapter() {
       mValues=null;
    }

    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_friends, parent, false);
        return new FriendsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FriendsAdapter.ViewHolder holder, int position) {
        final FriendItem friendItem = mValues.get(position);

        holder.mName.setText(friendItem.name);
        File file = new File(android.os.Environment.getExternalStorageDirectory().toString()
                +"/" + "OnCue" + "/" + friendItem.id+".jpg");
        displayProfilePic(holder.mProfilePic,file);


        Context c = holder.mView.getContext();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mName;
        final Context context;
        final ImageView mProfilePic;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            context = view.getContext();
            mName = (TextView) view.findViewById(R.id.name);
            mProfilePic = (ImageView) view.findViewById(R.id.image);

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,AddReminder.class);
                    intent.putExtra("fb_id",mValues.get(getAdapterPosition()).id);
                    intent.putExtra("type", "friend");
                    intent.putExtra("name", mValues.get(getAdapterPosition()).name);
                    context.startActivity(intent);
                }
            });
        }
    }

    private void displayProfilePic(ImageView imageView, File file) {

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(40)
                .oval(false)
                .build();
        Picasso.with(imageView.getContext())
                .load(file)
                .placeholder(R.drawable.circle)
                .transform(transformation)
                .into(imageView);

    }

}
