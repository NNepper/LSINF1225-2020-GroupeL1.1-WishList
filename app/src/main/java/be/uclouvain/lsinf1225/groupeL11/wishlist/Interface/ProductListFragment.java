package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Product;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.ProductDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.WishListDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter.ProductListAdapter;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class ProductListFragment extends Fragment {
    private RecyclerView productView;
    private ProductListAdapter productItemAdapter;
    private RecyclerView.LayoutManager productLayoutManager;

    private ItemTouchHelper itemTouchHelper;


    boolean isOpen;

    private FloatingActionButton addItemButton;
    private FloatingActionButton editWishlistNameButton;
    private FloatingActionButton validatePositionsButton;
    private FloatingActionButton menuOpenerButton;
    private String newWishListName;
    private TextView title;

    private WishList wishList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        final User mainUser = ((HomeActivity) getActivity()).mainUser;
        final ProductDAO productDAO = new ProductDAO(getContext());
        final WishListDAO wishListDAO = new WishListDAO(getContext());
        final ArrayList<Product> products = productDAO.get(bundle.getInt("wishListID"), null);
        wishList = wishListDAO.read(bundle.getInt("wishListID"));

        isOpen = false;

        final  View view = inflater.inflate(R.layout.fragment_product, container, false);

        title = view.findViewById(R.id.product_list_title);
        title.setText(wishList.name);

        productView = view.findViewById(R.id.product_recycler_view);
        productView.setHasFixedSize(true);
        productLayoutManager = new LinearLayoutManager(view.getContext());
        productItemAdapter = new ProductListAdapter(getContext(), products, true);

        productView.setLayoutManager(productLayoutManager);
        productView.setAdapter(productItemAdapter);

        itemTouchHelper = new ItemTouchHelper(createSimpleCallBack(products));
        itemTouchHelper.attachToRecyclerView(productView);

        productItemAdapter.setOnItemClickLister(new ProductListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Product clickedProduct= products.get(position);
                Bundle data = new Bundle();
                data.putInt("productID", clickedProduct.getId());
                data.putBoolean("isMainUser", productItemAdapter.isMainUser);

                Fragment productDetailFragment = new ProductDetailFragment();
                productDetailFragment.setArguments(data);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,productDetailFragment).addToBackStack(null).commit(); //display the clicked fragment
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
                            productItemAdapter.notifyItemRemoved(position);
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
                products.get(position).purchased = products.get(position).purchased == 0? 1 : 0;
                if(! productDAO.updatePurchased(products.get(position))){
                    CharSequence text = "Error DB update";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getContext(), text, duration);
                    toast.show();
                }
            }
        });

        productItemAdapter.setOnDragListener(new ProductListAdapter.onDragListener() {
            @Override
            public void onStartDrag(ProductListAdapter.ProductListItemHolder productListItemHolder, int position) {
                itemTouchHelper.startDrag(productListItemHolder);
        }
        });

        this.addItemButton = view.findViewById(R.id.product_add_FAB);
        this.editWishlistNameButton = view.findViewById(R.id.product_edit_FAB);
        this.menuOpenerButton = view.findViewById(R.id.product_open_FAB);

        menuOpenerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuOpener();
            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add a new item");
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_item, (ViewGroup) getView(), false);
                final EditText itemName = viewInflated.findViewById(R.id.new_item_name);
                final EditText itemQuantity = viewInflated.findViewById(R.id.new_item_quantity);
                final EditText itemWebLink = viewInflated.findViewById(R.id.new_item_web_link);
                builder.setView(viewInflated)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Integer.parseInt(itemQuantity.getText().toString());
                                } catch (Exception e) {
                                    showToast("A quantity is an integer !");
                                    return;
                                }
                                if(itemName.getText().toString().length() == 0 || itemWebLink.getText().toString().length() == 0){
                                    showToast("Please enter informations");
                                    return;
                                }
                                Product newProduct = new Product(itemName.getText().toString(), Integer.parseInt(itemQuantity.getText().toString()), itemWebLink.getText().toString(), products.size());
                                newProduct.wishlist = wishList;
                                if (productDAO.create(newProduct)) {
                                    products.add(newProduct);
                                    productItemAdapter.notifyItemInserted(newProduct.position);
                                } else {
                                    showToast("Error while trying to create a new item");
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
        });

        editWishlistNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Rename Wishlist");
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_wishlist, (ViewGroup) getView(), false);
                final EditText wishListName = (EditText) viewInflated.findViewById(R.id.edit_wishlist_name);
                wishListName.setText(wishList.name);
                final EditText wishlistDescription = (EditText) viewInflated.findViewById(R.id.edit_wishlist_description);
                wishlistDescription.setText(wishList.description);
                builder.setView(viewInflated)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (wishListName.getText().toString().length() == 0 || wishlistDescription.getText().toString().length() == 0) {
                                    showToast("You have to specify a name and a description");
                                    return;
                                }
                                wishList.name = wishListName.getText().toString();
                                wishList.description =wishlistDescription.getText().toString();
                                wishListDAO.update(wishList);
                                title.setText(wishList.name);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        validatePositionsButton = view.findViewById(R.id.product_validate_order_FAB);
        validatePositionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishList.products = products;
                wishList.reorder(getContext());
                showToast("Order saved");
            }
        });

        return view;
    }

    private void menuOpener(){
        if(isOpen){
            addItemButton.setVisibility(View.INVISIBLE);
            editWishlistNameButton.setVisibility(View.INVISIBLE);
            validatePositionsButton.setVisibility(View.INVISIBLE);
        }else{
            addItemButton.setVisibility(View.VISIBLE);
            editWishlistNameButton.setVisibility(View.VISIBLE);
            validatePositionsButton.setVisibility(View.VISIBLE);
        }
        isOpen = !isOpen;
    }

    private void showToast(String stringToShow){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getContext(), stringToShow, duration);
        toast.show();
    }

    private ItemTouchHelper.SimpleCallback createSimpleCallBack(final ArrayList<Product> products){
        return new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                Collections.swap(products, fromPosition, toPosition);
                recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
                products.get(fromPosition).position = fromPosition+1;
                products.get(toPosition).position = toPosition+1;
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Nothing to do here
            }
        };
    }
}
