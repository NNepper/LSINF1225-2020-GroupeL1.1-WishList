package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Product;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.ProductDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class ProductEditFragment extends Fragment {

    EditText name;
    EditText description;
    EditText link;
    EditText quantity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final User mainUser = ((HomeActivity) getActivity()).mainUser;
        final View view = inflater.inflate(R.layout.fragment_product_edit, container, false);

        final ProductDAO productDAO = new ProductDAO(getContext());
        final Bundle bundle = this.getArguments();
        final Product product = productDAO.read(bundle.getInt("productID"));

        name = view.findViewById(R.id.product_edit_name);
        description = view.findViewById(R.id.product_edit_description);
        link = view.findViewById(R.id.product_edit_link);
        quantity = view.findViewById(R.id.product_edit_quantity);

        name.setText(product.name);
        description.setText(product.description);
        link.setText(product.link);
        String strQuantity = "" + product.quantity;
        quantity.setText(strQuantity);


        Button submit = view.findViewById(R.id.product_edit_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length() == 0) {
                    showToast("A product name is mandatory !");
                    return;
                }
                product.name = name.getText().toString();
                product.description = description.getText().toString();
                product.link = link.getText().toString();
                product.quantity = Integer.parseInt(quantity.getText().toString());

                if(! productDAO.update(product)){
                    CharSequence toastText = "DB update error";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getContext(), toastText, duration);
                    toast.show();
                }

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void showToast(String stringToShow){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getContext(), stringToShow, duration);
        toast.show();
    }
}
