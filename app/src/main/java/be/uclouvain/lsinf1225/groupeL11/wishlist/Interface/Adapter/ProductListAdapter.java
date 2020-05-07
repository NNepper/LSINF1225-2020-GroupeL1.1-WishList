package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.internal.VisibilityAwareImageButton;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Product;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.ProductDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListItemHolder> {

    private ArrayList<Product> products;
    private onItemClickListener productListClickListener;
    private onDragListener dragListener;
    public Boolean isMainUser;
    private Context context;


    public interface onItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onCheckClick(int position);
    }

    public interface onDragListener{
        void onStartDrag(ProductListItemHolder productListItemHolder, int position);
    }

    public void setOnItemClickLister(onItemClickListener listener){
        productListClickListener = listener;
    }

    public void setOnDragListener(onDragListener onDragListener){
        dragListener = onDragListener;
    }

    public static class ProductListItemHolder extends RecyclerView.ViewHolder{

        public ImageView deleteProductListButton;
        public TextView name;
        public TextView quantity;
        public CheckBox checkBox;
        public ImageView picture;
        public final RelativeLayout handleRelLayout;

        public ProductListItemHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            deleteProductListButton = itemView.findViewById(R.id.product_item_delete);
            name = itemView.findViewById(R.id.product_item_title);
            quantity = itemView.findViewById(R.id.product_item_quantity);
            checkBox = itemView.findViewById(R.id.product_item_checkbox);
            picture = itemView.findViewById(R.id.product_item_picture);
            handleRelLayout = itemView.findViewById(R.id.product_relative_layout);

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

    public ProductListAdapter(Context context, ArrayList<Product> products, Boolean notMainUser) {
        this.products = products;
        this.isMainUser = notMainUser;
        this.context = context;
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
    public void onBindViewHolder(@NonNull final ProductListAdapter.ProductListItemHolder holder, final int position) {
        Product currentProduct = products.get(position);
        ProductDAO productDAO = new ProductDAO(context);

        if (productDAO.checkImage(currentProduct.getId())){
            Bitmap image = productDAO.getImage(currentProduct.getId());
            holder.picture.setImageBitmap(image);
            holder.picture.setBackground(null);
        }

        if(currentProduct.purchased == 1){
            holder.checkBox.setChecked(true);
        }
        holder.name.setText(currentProduct.name);
        String quantityStr = "Quantity: " + currentProduct.quantity;
        holder.quantity.setText(quantityStr);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            private Vibrator vibe = (Vibrator) context.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            @Override
            public boolean onLongClick(View v) {
                vibe.vibrate(100);
                dragListener.onStartDrag(holder, position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
