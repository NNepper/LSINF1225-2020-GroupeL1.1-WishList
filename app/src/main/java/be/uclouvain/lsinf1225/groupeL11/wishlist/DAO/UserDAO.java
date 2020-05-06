package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;

public class UserDAO extends MyDatabaseHelper {

    public UserDAO(Context context) {
        super(context);
    }


    /**
     * Add a user to the Database and set his ID
     * @param user
     */
    public Boolean create(User user){
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();

            values.put(FIRSTNAME, user.firstname);
            values.put(LASTNAME, user.lastname);
            values.put(USERNAME, user.username);
            values.put(EMAIL, user.email);
            values.put(PASSWORD, user.getPassword());
            values.put(ADDRESS, user.address);
            values.put(COLOR, user.color);
            values.put(SHOES, user.shoeSize);
            values.put(TROUSERS, user.trouserSize);
            values.put(TSHIRT, user.tshirtSize);
            values.put(PRIVACY, user.privacy);


            int rows = (int) db.insert(USER_TABLE, null, values);
            user.setId(rows);

            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.d("SQL", "Error while trying to add or update user");
            return false;
        } finally {
            db.endTransaction();
        }
    }


    public Boolean update(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(FIRSTNAME, user.firstname);
            values.put(LASTNAME, user.lastname);
            values.put(USERNAME, user.username);
            values.put(ADDRESS, user.address);
            values.put(COLOR, user.color);
            values.put(SHOES, user.shoeSize);
            values.put(TROUSERS, user.trouserSize);
            values.put(TSHIRT, user.tshirtSize);
            values.put(PRIVACY, user.privacy);

            // Updating profile picture url for user with that userName
            db.update(USER_TABLE, values, USER_ID + " = " + user.id, null);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.d("SQL", "Error while trying to update user");
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public Boolean updatePrivacy(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(PRIVACY, user.privacy);

        db.update(USER_TABLE, values, USER_ID + " = " + user.id,null);
        db.setTransactionSuccessful();
        db.endTransaction();
        return true;
    }

    /**
     * Getter to access to the User given the email address
     * @param email
     */
    public User read(String email){
        User user = null;
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        try {
            String getQuery = "SELECT * FROM User u WHERE u.email == '" + email + "'";
            Cursor cursor = db.rawQuery(getQuery, null);

            WishListDAO wishListDAO = new WishListDAO(context);
            FollowDAO followDAO = new FollowDAO(context);
            InterestDAO interestDAO= new InterestDAO(context);

            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                user = new User(cursor.getString(4), cursor.getString(3), cursor.getString(5));
                user.setId(cursor.getInt(0));
                user.firstname = cursor.getString(1);
                user.lastname =  cursor.getString(2);
                user.setPassword(cursor.getString(5));
                user.address = cursor.getString(6);
                user.color = cursor.getString(7);
                user.shoeSize = cursor.getInt(8);
                user.trouserSize = cursor.getString(9);
                user.tshirtSize = cursor.getString(10);
                user.privacy = cursor.getInt(11);

            }
            return user;
        } catch (Exception e) {
            Log.d("SQL", "error while getting user");
            Log.d("SQL", e.getMessage());
            return null;
        }
        finally {
            db.endTransaction();
        }
    }

    // Delete the specified user
    public void delete(User user) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(USER_TABLE, USER_ID + "=" + user.id, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }


    // Utility Getter for DAO
    public User get(int userID, SQLiteDatabase db) {
        if(db == null) db = this.getWritableDatabase();

        User user = null;

        WishListDAO wishListDAO = new WishListDAO(context);
        InterestDAO interestDAO= new InterestDAO(context);


        try {
            String getQuery = "SELECT * FROM User u WHERE u.userID == '"+ userID + "'";
            Cursor cursor = db.rawQuery(getQuery, null);

            cursor.moveToFirst();

            user = new User(cursor.getString(4), cursor.getString(3), cursor.getString(5));
            user.setId(userID);
            user.firstname = cursor.getString(1);
            user.lastname =  cursor.getString(2);
            user.setPassword(cursor.getString(5));
            user.address = cursor.getString(6);
            user.color = cursor.getString(7);
            user.shoeSize = cursor.getInt(8);
            user.trouserSize = cursor.getString(9);
            user.tshirtSize = cursor.getString(10);
            user.privacy = cursor.getInt(11);

            user.wishlists = wishListDAO.getWishLists(user.getId(), db);
            user.following = null;                                          //Avoiding charging the whole database
            user.interests = interestDAO.getInterests(user.getId(), db);
            return user;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return null;
        }
    }

    // Utility Getter for search
    public ArrayList<User> getAllUser(int UserID){
        ArrayList<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            String getQuery = String.format(
                    "SELECT * FROM User U WHERE U.userID != '%s'", UserID);

            Cursor cursor = db.rawQuery(getQuery, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    User user = new User(cursor.getInt(0), cursor.getString(4), cursor.getString(3), cursor.getString(5));
                    user.username =  cursor.getString(1);
                    user.lastname = cursor.getString(2);
                    user.address = cursor.getString(6);
                    user.color = cursor.getString(7);
                    user.shoeSize = cursor.getInt(8);
                    user.trouserSize = cursor.getString(9);
                    user.tshirtSize = cursor.getString(10);
                    user.privacy = cursor.getInt(11);

                    userList.add(user);
                    cursor.moveToNext();
                }
            }
            db.setTransactionSuccessful();
            return userList;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return null;
        }
        finally {
            db.endTransaction();
        }
    }

    /**
     * Function checking if a specified email is valid regarding the unicity constraint
     * @param email
     * @return true if can be created else return false
     */
    public Boolean check(String email){
        User user = null;
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        try {
            String getQuery = "SELECT * FROM User u WHERE u.email == '" + email + "'";
            Cursor cursor = db.rawQuery(getQuery, null);
            db.setTransactionSuccessful();

            if(cursor.getCount() > 0) {
                return false;
            }
            else {

                return true;
            }
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return false;
        }
        finally {
            db.endTransaction();
        }
    }

    public Boolean createImage(User mainUser, Bitmap image){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bArray = bos.toByteArray();

        try {
            ContentValues values = new ContentValues();


            values.put(UHP_USERID, mainUser.getId());
            values.put(UHP_IMAGE, bArray);

            db.insert(UHP_TABLE, null, values);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.d("SQL", "Error while trying to add image");
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public Boolean changeImage(User mainUser, Bitmap image){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bArray = bos.toByteArray();

        try {
            ContentValues values = new ContentValues();

            values.put(UHP_USERID, mainUser.getId());
            values.put(UHP_IMAGE, bArray);

            db.update(UHP_TABLE, values, UHP_USERID + "=" + mainUser.getId(), null);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.d("SQL", "Error while trying to add image");
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public Boolean checkImage(User mainUser){
        Bitmap image = null;
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        try {
            String getQuery = "SELECT * FROM User_has_Profil uhi WHERE uhi.userID == '" + mainUser.getId() + "'";
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

    public Bitmap getImage(User mainUser){
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        try {
            String getQuery = "SELECT * FROM User_has_Profil uhi WHERE uhi.userID == '" + mainUser.getId() + "'";
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