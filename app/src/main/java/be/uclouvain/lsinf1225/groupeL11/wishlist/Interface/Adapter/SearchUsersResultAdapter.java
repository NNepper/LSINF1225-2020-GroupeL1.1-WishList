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

public class SearchUsersResultAdapter extends RecyclerView.Adapter<SearchUsersResultAdapter.SearchUsersResultViewHolder> {
    private ArrayList<User> searchUsersResult;
    private onItemClickListener searchUsersResultClickListener;

    public interface onItemClickListener{
        void onItemClick(int position);
        void onAddClick(int position);
    }

    public void setOnItemClickLister(onItemClickListener listener){
        searchUsersResultClickListener = listener;
    }

    public static class SearchUsersResultViewHolder extends RecyclerView.ViewHolder{

        public ImageView searchUsersResultProfilPic;
        public TextView searchUsersResultUsername;
        public ImageView addFollowButton;

        public SearchUsersResultViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            searchUsersResultProfilPic = itemView.findViewById(R.id.search_users_result_profil_pic);
            searchUsersResultUsername = itemView.findViewById(R.id.search_users_result_username);
            addFollowButton = itemView.findViewById(R.id.follow_item_add_friend);

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

            addFollowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { // TODO changer pour pouvoir ajouter l'user à la liste du main
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onAddClick(position);
                        }
                    }
                }
            });
        }
    }

    public SearchUsersResultAdapter(ArrayList<User> searchUsersResult){
        this.searchUsersResult = searchUsersResult;
    }

    @NonNull
    @Override
    public SearchUsersResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_search_users_result_item, parent, false);
        SearchUsersResultViewHolder flvh = new SearchUsersResultViewHolder(view, searchUsersResultClickListener);
        return flvh;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchUsersResultViewHolder holder, int position) {
        User currentFriend = searchUsersResult.get(position);

        holder.searchUsersResultProfilPic.setImageResource(R.drawable.ic_default_image_profile); // get the default profile pic
        holder.searchUsersResultUsername.setText(currentFriend.username);

    }

    @Override
    public int getItemCount() {
        return searchUsersResult.size();
    }
}
