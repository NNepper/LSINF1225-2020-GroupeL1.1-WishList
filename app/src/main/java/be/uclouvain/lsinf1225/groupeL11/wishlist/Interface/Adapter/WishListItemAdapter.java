package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

import java.util.ArrayList;
import java.util.List;

public class WishListItemAdapter extends ArrayAdapter<WishList> {

    private Context context;
    private ArrayList<WishList> wishList;
    private LayoutInflater inflater;
    private int layoutResource;

    public WishListItemAdapter(Context context, int layoutResource, ArrayList<WishList> wishList){
        super(context, layoutResource, wishList);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.wishList = wishList;
    }

    @Override
    public int getCount() {
        return wishList.size();
    }

    @Override
    public WishList getItem(int position) {
        return wishList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view =  inflater.inflate(R.layout.adapter_wishlist_item, parent, false);
        }

        WishList x = getItem(position);

        if (x != null){
            TextView titleView = view.findViewById(R.id.item_wishlist_title);

            if (titleView != null){
                titleView.setText(x.name);
            }
        }
        return view;
    }
}
