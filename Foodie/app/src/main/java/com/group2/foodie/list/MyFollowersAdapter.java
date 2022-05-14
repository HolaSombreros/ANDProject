package com.group2.foodie.list;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.group2.foodie.R;
import com.group2.foodie.model.Follower;
import com.group2.foodie.util.GlideApp;

import java.util.ArrayList;
import java.util.List;

public class MyFollowersAdapter extends RecyclerView.Adapter<MyFollowersAdapter.ViewHolder> {
    private List<Follower> followers;
    private OnClickListener clickListener;
    private OnRemoveListener removeListener;

    public MyFollowersAdapter() {
        this.followers = new ArrayList<>();
    }

    public void updateAndRefresh(Follower follower) {
        List<Follower> followers = this.followers;
        for (int i = 0; i < followers.size(); i++) {
            if (followers.get(i).getId().equals(follower.getId())) {
                followers.get(i).setFollows(!follower.isFollowed());
                break;
            }
        }

        setFollowers(followers);
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

        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/users/" + follower.getEmail());
        GlideApp.with(viewHolder.itemView).load(storageRef).into(viewHolder.image);
        viewHolder.username.setText(follower.getUsername());
        viewHolder.handle.setText(follower.getEmail());

        if (follower.isFollowed()) {
            viewHolder.followBtn.setText("Following");
            viewHolder.followBtn.setBackgroundColor(Color.parseColor("#00BB00"));
        } else {
            viewHolder.followBtn.setText("Follow");
            viewHolder.followBtn.setBackgroundColor(Color.parseColor("#03A9F4"));
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
