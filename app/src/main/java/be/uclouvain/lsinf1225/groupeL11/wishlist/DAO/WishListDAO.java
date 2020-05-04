package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.content.ContentValues;
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


    ArrayList<WishList> getWishLists(int userID, SQLiteDatabase db){
        ArrayList<WishList> list = new ArrayList<>();
        ProductDAO productDAO = new ProductDAO(context);

        if(db == null) db = this.getWritableDatabase();
        try {
            String getQuery = "SELECT wishlistID, name, description FROM WishList w," +
                    "    (SELECT wishlistID AS ID FROM User_has_Wishlist uhw " +
                    "    WHERE uhw.userID == '" + userID + "')" +
                    "WHERE w.wishlistID == ID";

            Cursor cursor = db.rawQuery(getQuery, null);
            if(cursor.getCount() == 0) return list;
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    WishList wishList = new WishList(cursor.getInt(0));
                    wishList.products = productDAO.get(cursor.getInt(0), db);
                    wishList.name = cursor.getString(1);
                    wishList.description = cursor.getString(2);
                    list.add(wishList);
                    cursor.moveToNext();
                }
            }
            return list;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return null;
        }
        finally {
            productDAO.close();
        }
    }

    public ArrayList<WishList> readWishLists(int userID){
        ArrayList<WishList> list = new ArrayList<>();
        ProductDAO productDAO = new ProductDAO(context);

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            String getQuery = "SELECT wishlistID, name, description FROM WishList w," +
                    "    (SELECT wishlistID AS ID FROM User_has_Wishlist uhw " +
                    "    WHERE uhw.userID == '" + userID + "')" +
                    "WHERE w.wishlistID == ID";

            Cursor cursor = db.rawQuery(getQuery, null);
            if(cursor.getCount() == 0) return list;
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    WishList wishList = new WishList(cursor.getInt(0));
                    wishList.products = productDAO.get(cursor.getInt(0), db);
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

    public Boolean create(WishList wishList, User user){
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues val1 = new ContentValues();

            val1.put(WISHLIST_NAME, wishList.name);
            val1.put(WISHLIST_DESC, wishList.description);

            int rows = (int) db.insert(WISHLIST_TABLE, null, val1);
            wishList.setId(rows);

            ContentValues val2 = new ContentValues();

            val2.put(UHW_ID, wishList.getId());
            val2.put(UHW_USERID, user.getId());
            db.insert(UHW_TABLE, null, val2);

            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public Boolean delete(int wishlistID){
        return true;
    }

}
