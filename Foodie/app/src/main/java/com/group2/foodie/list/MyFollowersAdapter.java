package com.group2.foodie.list;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.group2.foodie.R;
import com.group2.foodie.model.Follower;

import java.util.ArrayList;
import java.util.List;

public class MyFollowersAdapter extends RecyclerView.Adapter<MyFollowersAdapter.ViewHolder> {
    private List<Follower> followers;
    private OnClickListener clickListener;
    private OnRemoveListener removeListener;

    public MyFollowersAdapter() {
        this.followers = new ArrayList<>();
    }

    public void setFollowers(List<Follower> followers) {
        this.followers = followers;
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setOnRemoveListener(OnRemoveListener removeListener) {
        this.removeListener = removeListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listitem_my_followers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Follower follower = followers.get(position);
        viewHolder.image.setImageResource(R.drawable.ic_full_heart);
        viewHolder.username.setText(follower.getUsername());
        viewHolder.handle.setText(follower.getEmail());

        Log.d("foodiefollower", "Follows? " + follower.isFollowed());

        if (follower.isFollowed()) {
            viewHolder.followBtn.setText("Following");
            viewHolder.followBtn.setBackgroundColor(Color.parseColor("00FF00"));
        } else {
            viewHolder.followBtn.setText("Follow");
        }
    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView username;
        private TextView handle;
        private Button followBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.myFollowersItem_image);
            username = itemView.findViewById(R.id.myFollowersItem_username);
            handle = itemView.findViewById(R.id.myFollowersItem_handle);
            followBtn = itemView.findViewById(R.id.myFollowersItem_followBtn);

            itemView.setOnClickListener(v -> clickListener.onClick(followers.get(getBindingAdapterPosition())));
            followBtn.setOnClickListener(v -> removeListener.onRemove(followers.get(getBindingAdapterPosition())));
        }
    }

    public interface OnClickListener {
        void onClick(Follower user);
    }

    public interface OnRemoveListener {
        void onRemove(Follower user);
    }
}
