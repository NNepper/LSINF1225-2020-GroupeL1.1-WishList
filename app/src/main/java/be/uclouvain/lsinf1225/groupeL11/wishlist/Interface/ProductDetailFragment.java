package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        Boolean isMainUser = bundle.getBoolean("isMainUser");

        TextView title = view.findViewById(R.id.product_details_title);
        title.setText(product.name);

        ImageView picture = view.findViewById(R.id.product_details_picture);
        picture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).prodID = product.getId();
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(photoPickerIntent, 1);
            }
        });
        if (productDAO.checkImage(product.getId())){
            Bitmap image = productDAO.getImage(product.getId());
            picture.setImageBitmap(image);
            picture.setBackground(null);
        }
        else {
            picture = view.findViewById(R.id.profilePic);
        }

        TextView description = view.findViewById(R.id.product_details_description);
        description.setText(product.description);

        TextView quantity = view.findViewById(R.id.product_details_quantity);
        String strQuantity = "Quantity: " + product.quantity;
        quantity.setText(strQuantity);

        TextView link = view.findViewById(R.id.product_details_link);
        link.setText(product.link);

        final RatingBar ratings = view.findViewById(R.id.product_details_ratings);
        ratings.setRating((float) product.rating);

        ImageView submit = view.findViewById(R.id.product_details_submit);
        FloatingActionButton edit = view.findViewById(R.id.product_details_edit);
        if(isMainUser){
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    product.rating = ratings.getRating();
                    ratings.setRating( product.rating);
                    if(productDAO.update(product)){
                        CharSequence text = "Ratings updated";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(getContext(), text, duration);
                        toast.show();
                    }
                    else{
                        CharSequence text = "Error DB update";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(getContext(), text, duration);
                        toast.show();
                    }

                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle data = new Bundle();
                    data.putInt("productID", product.getId());

                    Fragment productEdit = new ProductEditFragment();
                    productEdit.setArguments(data);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,productEdit).addToBackStack(null).commit();
                }
            });
        }
        else{
            submit.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
        }

        return view;
    }
}
