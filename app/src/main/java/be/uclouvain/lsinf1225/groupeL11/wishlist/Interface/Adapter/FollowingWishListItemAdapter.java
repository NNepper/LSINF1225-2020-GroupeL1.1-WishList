package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class FollowingWishListItemAdapter extends RecyclerView.Adapter<FollowingWishListItemAdapter.WishlistViewHolder> {

    private ArrayList<WishList> wishLists;
    private onItemClickListener wishlistClickListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        wishlistClickListener = listener;
    }

    public static class WishlistViewHolder extends RecyclerView.ViewHolder{

        public TextView wishlistName;

        public WishlistViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            wishlistName = itemView.findViewById(R.id.follow_wishlist_name);

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
        }
    }

    public FollowingWishListItemAdapter(ArrayList<WishList> wishLists){this.wishLists = wishLists;}

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_follow_wishlist_item, parent, false);
        WishlistViewHolder wlvh = new WishlistViewHolder(view, wishlistClickListener);
        return wlvh;
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
        WishList currentWishlist = wishLists.get(position);

        holder.wishlistName.setText(currentWishlist.name);
    }

    @Override
    public int getItemCount() {return wishLists.size();}
}
