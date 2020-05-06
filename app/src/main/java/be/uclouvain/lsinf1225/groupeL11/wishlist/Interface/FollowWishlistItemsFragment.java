package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Product;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.ProductDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter.ProductListAdapter;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class FollowWishlistItemsFragment extends Fragment {

    private RecyclerView productView;
    private ProductListAdapter productItemAdapter;
    private RecyclerView.LayoutManager productLayoutManager;

    private TextView wishlistName;
    private FloatingActionButton addWishListFAB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home_wishlists, container, false);
        Bundle bundle = this.getArguments();
        final ProductDAO productDAO = new ProductDAO(getContext());
        final ArrayList<Product> wishlisItems = productDAO.get(bundle.getInt("wishlistID"), null);

        wishlistName = view.findViewById(R.id.title_wishlist);
        wishlistName.setText(bundle.getString("wishlistName"));

        addWishListFAB = view.findViewById(R.id.addWishlistButton);
        addWishListFAB.hide();

        productView = view.findViewById(R.id.wishlist_recycler_view);
        productView.setHasFixedSize(true);
        productLayoutManager = new LinearLayoutManager(view.getContext());
        productItemAdapter = new ProductListAdapter(wishlisItems, false);

        productView.setLayoutManager(productLayoutManager);
        productView.setAdapter(productItemAdapter);

        // TODO set on clickListerners
        productItemAdapter.setOnItemClickLister(new ProductListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //TODO
            }

            @Override
            public void onDeleteClick(int position) {
                // Nothing to do here because visiting user can't delete an item inside of a wishlist he is viewing
            }

            @Override
            public void onCheckClick(int position){
                // TODO check if works when DAO is updated
                Product product = wishlisItems.get(position);
                product.purchased = product.purchased == 0? 1 : 0;
                productDAO.update(product);
            }
        });

        return view;
    }
}
