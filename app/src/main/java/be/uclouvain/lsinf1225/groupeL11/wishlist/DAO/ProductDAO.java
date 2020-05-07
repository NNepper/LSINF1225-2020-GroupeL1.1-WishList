package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Product;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;

public class ProductDAO extends MyDatabaseHelper {
    public ProductDAO(Context context) {
        super(context);
    }

    public ArrayList<Product> get(int wishListID, SQLiteDatabase db){
        ArrayList<Product> prodList = new ArrayList<>();


        if(db == null)db = this.getWritableDatabase();

        try {
            String getQuery = String.format(
                    "SELECT * FROM Products P \n" +
                    "WHERE P.wishlistID == '%s'", wishListID);

            Cursor cursor = db.rawQuery(getQuery, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Product prod = new Product(cursor.getInt(0));
                    prod.name = cursor.getString(1);
                    prod.description = cursor.getString(2);
                    prod.link = cursor.getString(3);
                    prod.purchased = cursor.getInt(4);
                    prod.position = cursor.getInt(5);
                    prod.quantity = cursor.getInt(6);

                    prodList.add(prod);
                    cursor.moveToNext();
                }
            }
            return prodList;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return null;
        }
    }

    public int create(Product prod){
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();

            values.put(PROD_DESC, prod.description);
            values.put(PROD_LINK, prod.link);
            values.put(PROD_NAME, prod.name);
            values.put(PROD_POSITION, prod.position);
            values.put(PROD_PURCHASSED, prod.purchased);
            values.put(PROD_QUANTITY, prod.quantity);
            values.put(PROD_WISHLIST, prod.wishlist.getId());

            int rows = (int) db.insert(PRODUCT_TABLE, null, values);
            prod.setId(rows);

            db.setTransactionSuccessful();
            return prod.getId();
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return -1;
        } finally {
            db.endTransaction();
        }
    }

    public boolean delete(int productID){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(PRODUCT_TABLE, PRODUCT_ID + "=" + productID, null);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public boolean update(Product product){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{

            ContentValues values = new ContentValues();

            values.put(PROD_DESC, product.description);
            values.put(PROD_LINK, product.link);
            values.put(PROD_NAME, product.name);
            values.put(PROD_POSITION, product.position);
            values.put(PROD_PURCHASSED, product.purchased);
            values.put(PROD_QUANTITY, product.quantity);

            db.update(PRODUCT_TABLE, values, PRODUCT_ID + "=" + product.getId(), null);
            db.setTransactionSuccessful();
            return true;

        } catch (Exception e){
            Log.d("SQL", e.getMessage());
            return false;
        }finally {
            db.endTransaction();
        }

    }

    public boolean updatePurchased(Product product){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{

            ContentValues values = new ContentValues();

            values.put(PROD_PURCHASSED, product.purchased);

            db.update(PRODUCT_TABLE, values, PRODUCT_ID + "=" + product.getId(), null);
            db.setTransactionSuccessful();
            return true;

        } catch (Exception e){
            Log.d("SQL", e.getMessage());
            return false;
        }finally {
            db.endTransaction();
        }
    }

    public Product read(int productId){
        SQLiteDatabase db = getReadableDatabase();
        db.beginTransaction();
        try {
            Product prod = null;
            String getQuery = String.format(
                    "SELECT * FROM Products P \n" +
                            "WHERE P.productID == '%s'", productId);

            Cursor cursor = db.rawQuery(getQuery, null);

            if (cursor.moveToFirst()) {
                prod = new Product(cursor.getInt(0));
                prod.name = cursor.getString(1);
                prod.description = cursor.getString(2);
                prod.link = cursor.getString(3);
                prod.purchased = cursor.getInt(4);
                prod.position = cursor.getInt(5);
                prod.quantity = cursor.getInt(6);
            }
            db.setTransactionSuccessful();
            return prod;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return null;
        } finally {
            db.endTransaction();
        }
    }
}
