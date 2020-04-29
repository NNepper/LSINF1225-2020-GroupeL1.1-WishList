package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Product;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;

public class ProductDAO extends MyDatabaseHelper {
    public ProductDAO(Context context) {
        super(context);
    }

    public ArrayList<Product> get(int wishListID){
        ArrayList<Product> prodList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            String getQuery = String.format(
                    "SELECT * FROM Products P \n" +
                    "WHERE P.wishlistID == '%s'", wishListID);

            Cursor cursor = db.rawQuery(getQuery, null);
            db.close();

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Product prod = new Product(cursor.getInt(0));
                    prod.name = cursor.getString(1);
                    prod.description = cursor.getString(2);
                    prod.link = cursor.getString(4);
                    prod.purchased = cursor.getInt(5);
                    prod.position = cursor.getInt(6);
                    prod.quantity = cursor.getInt(7);

                    prodList.add(prod);
                    cursor.moveToNext();
                }
            }

            return prodList;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return null;
        }
        finally {
            db.close();
        }
    }
}
