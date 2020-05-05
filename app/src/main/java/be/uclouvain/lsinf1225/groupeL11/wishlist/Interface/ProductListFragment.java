package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Product;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.ProductDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.WishListDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter.ProductListAdapter;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class ProductListFragment extends Fragment {
    private RecyclerView productView;
    private ProductListAdapter productItemAdapter;
    private RecyclerView.LayoutManager productLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        final User mainUser = ((HomeActivity) getActivity()).mainUser;
        ProductDAO productDAO = new ProductDAO(getContext());
        ArrayList<Product> products = productDAO.get(bundle.getInt("wishListID"), null);

        final  View view = inflater.inflate(R.layout.fragment_product, container, false);

        TextView title = view.findViewById(R.id.product_list_title);
        title.setText(bundle.getString("wishListName"));

        productView = view.findViewById(R.id.product_recycler_view);
        productView.setHasFixedSize(true);
        productLayoutManager = new LinearLayoutManager(view.getContext());
        productItemAdapter = new ProductListAdapter(products);

        productView.setLayoutManager(productLayoutManager);
        productView.setAdapter(productItemAdapter);

        productItemAdapter.setOnItemClickLister(new ProductListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //TODO
            }

            @Override
            public void onDeleteClick(int position) {
                //TODO
            }

            @Override
            public void onCheckClick(int position) {
                //TODO
            }
        });

        return view;
    }
}
