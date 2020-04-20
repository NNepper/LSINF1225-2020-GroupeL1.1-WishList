package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.nio.file.DirectoryIteratorException;

import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class DialogFragmentNewWishlist extends DialogFragment {

    private EditText wishlistName;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_new_wishlist); // works only on API level 21 and above -> currently on API level 19
        builder.setMessage("Create a new wishlist").setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { // validation by the user -> create a new wishlist
                // TODO get the name of the new wishlist
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { // user canceled the creation of the wishlist
                dialog.cancel();
            }
        });

        return builder.create();
    }
}
