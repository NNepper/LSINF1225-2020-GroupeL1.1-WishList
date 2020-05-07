package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.internal.VisibilityAwareImageButton;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Product;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListItemHolder> {

    private ArrayList<Product> products;
    private onItemClickListener productListClickListener;
    public Boolean isMainUser;

    public interface onItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onCheckClick(int position);
    }

    public void setOnItemClickLister(onItemClickListener listener){
        productListClickListener = listener;
    }

    public static class ProductListItemHolder extends RecyclerView.ViewHolder{

        public ImageView deleteProductListButton;
        public TextView name;
        public TextView quantity;
        public CheckBox checkBox;
        public ImageView picture;

        public ProductListItemHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            deleteProductListButton = itemView.findViewById(R.id.product_item_delete);
            name = itemView.findViewById(R.id.product_item_title);
            quantity = itemView.findViewById(R.id.product_item_quantity);
            checkBox = itemView.findViewById(R.id.product_item_checkbox);
            picture = itemView.findViewById(R.id.product_item_picture);

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

            deleteProductListButton.setOnClickListener(new View.OnClickListener() {
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

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onCheckClick(position);
                        }
                    }
                }
            });
        }
    }

    public ProductListAdapter(ArrayList<Product> products, Boolean notMainUser) {
        this.products = products;
        this.isMainUser = notMainUser;
    }

    @NonNull
    @Override
    public ProductListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_product_item, parent, false);
        ImageView trash = view.findViewById(R.id.product_item_delete);
        if(!this.isMainUser) trash.setVisibility(View.GONE);
        ProductListItemHolder plih = new ProductListItemHolder(view, productListClickListener);
        return plih;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ProductListItemHolder holder, int position) {
        Product currentProduct = products.get(position);
        //TODO: change picture ?
        //holder.picture.setImageBitmap(bm); --> bm must be the picture

        if(currentProduct.purchased == 1){
            holder.checkBox.setChecked(true);
        }
        holder.name.setText(currentProduct.name);
        String quantityStr = "Quantity: " + currentProduct.quantity;
        holder.quantity.setText(quantityStr);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
