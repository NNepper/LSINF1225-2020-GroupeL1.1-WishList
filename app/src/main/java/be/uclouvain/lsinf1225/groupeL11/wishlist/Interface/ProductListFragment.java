package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        final ProductDAO productDAO = new ProductDAO(getContext());
        final ArrayList<Product> products = productDAO.get(bundle.getInt("wishListID"), null);

        final  View view = inflater.inflate(R.layout.fragment_product, container, false);

        TextView title = view.findViewById(R.id.product_list_title);
        title.setText(bundle.getString("wishListName"));

        productView = view.findViewById(R.id.product_recycler_view);
        productView.setHasFixedSize(true);
        productLayoutManager = new LinearLayoutManager(view.getContext());
        productItemAdapter = new ProductListAdapter(products, true);

        productView.setLayoutManager(productLayoutManager);
        productView.setAdapter(productItemAdapter);

        productItemAdapter.setOnItemClickLister(new ProductListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Product clickedProduct= products.get(position);
                Bundle data = new Bundle();
                data.putInt("productID", clickedProduct.getId());
                data.putString("productName", clickedProduct.name);

                Fragment productDetailFragment = new ProductDetailFragment();
                productDetailFragment.setArguments(data);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,productDetailFragment).commit(); //display the clicked fragment
            }

            @Override
            public void onDeleteClick(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete Product");
                builder.setMessage("Are you sure you want to delete " + products.get(position).name + "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(productDAO.delete(products.get(position).getId())) {
                            products.remove(position);
                            productItemAdapter.notifyDataSetChanged();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }

            @Override
            public void onCheckClick(int position) {
                if(products.get(position).purchased == 1){
                    products.get(position).purchased = 0;
                }
                else{
                    products.get(position).purchased = 1;
                }
                if(productDAO.update(products.get(position))){
                    CharSequence text = "Check !";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getContext(), text, duration);
                    toast.show();
                }
            }
        });

        return view;
    }
}
