package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Product;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;

public class WishListDAO extends MyDatabaseHelper{
    public WishListDAO(Context context) {
        super(context);
    }

    public void updateWishList(int wishListID){

        return;
    }

    public Boolean addWishList(WishList wishList){
        return true;
    }

    private WishList getWishList(int wishListID){
        return null;
    }

    public ArrayList<WishList> getWishLists(int userID){
        ArrayList<WishList> list = new ArrayList<>();
        ProductDAO productDAO = new ProductDAO(context);

        SQLiteDatabase db = getDB();
        db.beginTransaction();
        try {
            String getQuery = "SELECT name, description FROM WishList w," +
                    "    (SELECT wishlistID AS ID FROM User_has_Wishlist uhw " +
                    "    WHERE uhw.userID == 1)" +
                    "WHERE w.wishlistID == ID";

            Cursor cursor = db.rawQuery(getQuery, null);
            if(cursor.getCount() == 0) return list;
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    WishList wishList = new WishList(cursor.getInt(0));
                    wishList.products = productDAO.get(cursor.getInt(0));
                    wishList.name = cursor.getString(1);
                    wishList.description = cursor.getString(2);
                    list.add(wishList);
                    cursor.moveToNext();
                }
            }
            db.setTransactionSuccessful();
            return list;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return null;
        }
        finally {
            db.endTransaction();
            productDAO.close();
        }
    }

}
