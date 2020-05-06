package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Product;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.ProductDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class ProductDetailFragment extends Fragment {

    Product product;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final User mainUser = ((HomeActivity) getActivity()).mainUser;
        final View view = inflater.inflate(R.layout.fragment_product_details, container, false);

        final ProductDAO productDAO = new ProductDAO(getContext());
        Bundle bundle = this.getArguments();
        product = productDAO.read(bundle.getInt("productID"));

        TextView title = view.findViewById(R.id.product_details_title);
        title.setText(product.name);

        ImageView picture = view.findViewById(R.id.product_details_picture);
        //TODO set picture with picture.setImageBitmap(bm);

        TextView description = view.findViewById(R.id.product_details_description);
        description.setText(product.description);

        TextView quantity = view.findViewById(R.id.product_details_quantity);
        String strQuantity = "Quantity: " + product.quantity;
        quantity.setText(strQuantity);

        TextView link = view.findViewById(R.id.product_details_link);
        link.setText(product.link);

        return view;
    }
}