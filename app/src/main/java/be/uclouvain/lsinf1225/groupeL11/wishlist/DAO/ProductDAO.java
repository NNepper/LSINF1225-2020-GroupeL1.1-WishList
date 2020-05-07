package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
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
                    prod.rating = cursor.getFloat(8);

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

    public boolean create(Product prod){
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues val1 = new ContentValues();

            val1.put(PROD_DESC, prod.description);
            val1.put(PROD_LINK, prod.link);
            val1.put(PROD_NAME, prod.name);
            val1.put(PROD_POSITION, prod.position);
            val1.put(PROD_PURCHASSED, prod.purchased);
            val1.put(PROD_QUANTITY, prod.quantity);
            val1.put(PROD_WISHLIST, prod.wishlist.getId());
            val1.put(PROD_RATING, prod.rating);

            int rows = (int) db.insert(PRODUCT_TABLE, null, val1);
            prod.setId(rows);

            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return false;
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
            values.put(PROD_RATING, product.rating);

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
                prod.rating = cursor.getFloat(8);
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

    public Boolean createImage(int ID, Bitmap image){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bArray = bos.toByteArray();

        try {
            ContentValues values = new ContentValues();


            values.put(PHI_ID, ID);
            values.put(PHI_IMAGE, bArray);

            db.insert(PHI_TABLE, null, values);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.d("SQL", "Error while trying to add image");
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public Boolean changeImage(int ID, Bitmap image){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bArray = bos.toByteArray();

        try {
            ContentValues values = new ContentValues();

            values.put(PHI_ID, ID);
            values.put(PHI_IMAGE, bArray);

            db.update(PHI_TABLE, values, PHI_ID + "=" + ID, null);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.d("SQL", "Error while trying to add image");
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public Boolean checkImage(int ID){
        Bitmap image = null;
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        try {
            String getQuery = "SELECT * FROM Product_has_image phi WHERE phi.productID == '" + ID + "'";
            Cursor cursor = db.rawQuery(getQuery, null);
            db.setTransactionSuccessful();

            if(cursor.getCount() > 0) {
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return false;
        }
        finally {
            db.endTransaction();
        }
    }

    public Bitmap getImage(int ID){
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        try {
            String getQuery = "SELECT * FROM Product_has_image phi WHERE phi.productID == '" + ID + "'";
            Cursor cursor = db.rawQuery(getQuery, null);
            cursor.moveToFirst();

            byte[] temp = cursor.getBlob(1);

            Bitmap image = BitmapFactory.decodeByteArray(temp, 0 ,temp.length);
            return image;
        } catch (Exception e) {
            Log.d("SQL", "error while getting image");
            Log.d("SQL", e.getMessage());
            return null;
        }
        finally {
            db.endTransaction();
        }
    }
}
