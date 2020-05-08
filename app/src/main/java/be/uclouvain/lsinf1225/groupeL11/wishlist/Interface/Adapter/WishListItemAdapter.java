package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class WishListItemAdapter extends RecyclerView.Adapter<WishListItemAdapter.WishListItemHolder> {

    private ArrayList<WishList> wishLists;
    private onItemClickListener wishListClickListener;

    public interface onItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickLister(onItemClickListener listener){
        wishListClickListener = listener;
    }

    public static class WishListItemHolder extends RecyclerView.ViewHolder{

        public ImageView deleteWishListButton;
        public TextView name;
        public TextView description;

        public WishListItemHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            deleteWishListButton = itemView.findViewById(R.id.wishlist_delete);
            name = itemView.findViewById(R.id.item_wishlist_title);
            description = itemView.findViewById(R.id.item_wishlist_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            deleteWishListButton.setOnClickListener(new View.OnClickListener() {
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

    public WishListItemAdapter(ArrayList<WishList> wishlists) {
        this.wishLists = wishlists;
    }

    @NonNull
    @Override
    public WishListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_wishlist_item, parent, false);
        WishListItemHolder wlih = new WishListItemHolder(view, wishListClickListener);
        return wlih;
    }

    @Override
    public void onBindViewHolder(@NonNull WishListItemAdapter.WishListItemHolder holder, int position) {
        WishList currentWL = wishLists.get(position);
        holder.description.setText(currentWL.description);
        holder.name.setText(currentWL.name);
    }

    @Override
    public int getItemCount() {
        return wishLists.size();
    }
}
