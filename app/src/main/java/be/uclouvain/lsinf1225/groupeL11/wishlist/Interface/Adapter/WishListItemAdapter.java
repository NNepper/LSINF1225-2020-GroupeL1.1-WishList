package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

import java.util.List;

public class WishListItemAdapter extends BaseAdapter {

    private Context context;
    private List<WishList> wishList;
    private LayoutInflater inflater;

    public WishListItemAdapter(Context context, List<WishList> wishList){
        this.context = context;
        this.wishList = wishList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return wishList.size();
    }

    @Override
    public Object getItem(int position) {
        return wishList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.adapter_wishlist_item, null);
        return view;
    }
}
