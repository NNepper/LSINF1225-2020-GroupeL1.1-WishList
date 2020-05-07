package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter;

import android.util.Log;
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

public class FollowRequestAdapter extends RecyclerView.Adapter<FollowRequestAdapter.FollowRequestViewHolder> {
    private ArrayList<User> followRequest;
    private onItemClickListener followRequestClickListener;

    public interface onItemClickListener{
        void onItemClick(int position);
        void onAcceptClick(int position);
    }

    public void setOnItemClickLister(onItemClickListener listener){
        followRequestClickListener = listener;
    }

    public static class FollowRequestViewHolder extends RecyclerView.ViewHolder{

        public ImageView followRequestProfilePic;
        public TextView followRequestUsername;
        public ImageView acceptFollowButton;

        public FollowRequestViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            followRequestProfilePic = itemView.findViewById(R.id.follow_request_profil_pic);
            followRequestUsername = itemView.findViewById(R.id.follow_request_username);
            acceptFollowButton = itemView.findViewById(R.id.follow_request_accept);
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

            // il est null !!
            acceptFollowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { // TODO changer pour pouvoir ajouter l'user à la liste du main
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onAcceptClick(position);
                        }
                    }
                }
            });
        }
    }

    public FollowRequestAdapter(ArrayList<User> followRequest){
        this.followRequest = followRequest;
    }

    @NonNull
    @Override
    public FollowRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_follow_request_item, parent, false);
        FollowRequestViewHolder flvh = new FollowRequestViewHolder(view, followRequestClickListener);
        return flvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FollowRequestViewHolder holder, int position) {
        User currentFriend = followRequest.get(position);

        holder.followRequestProfilePic.setImageResource(R.drawable.ic_default_image_profile); // get the default profile pic
        holder.followRequestUsername.setText(currentFriend.username);

    }

    @Override
    public int getItemCount() {
        return followRequest.size();
    }
}
