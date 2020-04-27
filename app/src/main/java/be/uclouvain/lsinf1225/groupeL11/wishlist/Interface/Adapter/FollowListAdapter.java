package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class FollowListAdapter extends RecyclerView.Adapter<FollowListAdapter.FollowListViewHolder> {

    private ArrayList<User> followList;
    private onItemClickListener followClickListener;

    public interface onItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickLister(onItemClickListener listener){
        followClickListener = listener;
    }

    public static class FollowListViewHolder extends RecyclerView.ViewHolder{

        public ImageView followProfilPic;
        public TextView followUsername;
        public ImageView deleteFollowButton;

        public FollowListViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            followProfilPic = itemView.findViewById(R.id.follow_item_profil_pic);
            followUsername = itemView.findViewById(R.id.follow_item_friend_username);
            deleteFollowButton = itemView.findViewById(R.id.follow_item_delete_friend);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            deleteFollowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public FollowListAdapter(ArrayList<User> friendList){
        this.followList = friendList;
    }

    @NonNull
    @Override
    public FollowListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_follow_item, parent, false);
        FollowListViewHolder flvh = new FollowListViewHolder(view, followClickListener);
        return flvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FollowListViewHolder holder, int position) {
        User currentFriend = followList.get(position);

        holder.followProfilPic.setImageResource(R.drawable.ic_default_image_profile); // get the default profile pic
        holder.followUsername.setText(currentFriend.username);

    }

    @Override
    public int getItemCount() {
        return followList.size();
    }
}
