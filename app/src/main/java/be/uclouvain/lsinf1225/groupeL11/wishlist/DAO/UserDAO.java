package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

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
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();

        Boolean noError = true;
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();

            values.put(FIRSTNAME, user.firstname);
            values.put(LASTNAME, user.lastname);
            values.put(USERNAME, user.username);
            values.put(EMAIL, user.email);
            values.put(PASSWORD, user.password);
            values.put(ADDRESS, user.address);
            values.put(COLOR, user.color);
            values.put(SHOES, user.shoeSize);
            values.put(TROUSERS, user.trouserSize);
            values.put(TSHIRT, user.tshirtSize);
            values.put(PRIVACY, user.privacy);


            // First try to update the user in case the user already exists in the database
            int rows = (int) db.insert(USER_TABLE, null, values);
            user.setId(rows);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("SQL", "Error while trying to add or update user");
            noError = false;
        } finally {
            db.endTransaction();
            db.close();
            return noError;
        }
    }


    public Boolean update(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIRSTNAME, user.firstname);
        values.put(LASTNAME, user.lastname);
        values.put(USERNAME, user.username);
        values.put(EMAIL, user.email);
        values.put(PASSWORD, user.password);
        values.put(ADDRESS, user.address);
        values.put(COLOR, user.color);
        values.put(SHOES, user.shoeSize);
        values.put(TROUSERS, user.trouserSize);
        values.put(TSHIRT, user.tshirtSize);
        values.put(PRIVACY, user.privacy);

        // Updating profile picture url for user with that userName
        user.id = (int) db.update(USER_TABLE, values, USER_ID + " = " + user.id,null);
        return user.id > 0;
    }

    /**
     * Getter to access to the User given the email address
     * @param email
     */
    public User read(String email){
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        try {
            String getQuery = "SELECT * FROM User u WHERE u.email == '" + email + "'";
            Cursor cursor = db.rawQuery(getQuery, null);
            db.close();
            WishListDAO wishListDAO = new WishListDAO(context);
            FollowDAO followDAO = new FollowDAO(context);
            InterestDAO interestDAO= new InterestDAO(context);
            User user = new User(null, null, null);

            cursor.moveToFirst();
            user.setId(cursor.getInt(0));
            user.username =  cursor.getString(1);
            user.lastname = cursor.getString(2);
            user.username = cursor.getString(3);
            user.email = cursor.getString(4);
            user.password = cursor.getString(5);
            Log.d("SQL", user.password);
            user.address = cursor.getString(6);
            user.color = cursor.getString(7);
            user.shoeSize = cursor.getInt(8);
            user.trouserSize = cursor.getString(9);
            user.tshirtSize = cursor.getString(10);
            user.privacy = cursor.getInt(11);

            user.wishlists = wishListDAO.getWishLists(user.getId());
            user.following = followDAO.getFollowing(user.getId());
            user.interests = interestDAO.getInterests(user.getId());

            return user;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return null;
        }
        finally {
            db.close();
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
            db.close();
        }
    }



}