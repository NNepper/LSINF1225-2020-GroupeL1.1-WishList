package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;
import android.widget.Toast;

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
            // Provisional fix for the unicity error
            // values.put(EMAIL, user.email);
            // values.put(PASSWORD, user.getPassword());
            values.put(ADDRESS, user.address);
            values.put(COLOR, user.color);
            values.put(SHOES, user.shoeSize);
            values.put(TROUSERS, user.trouserSize);
            values.put(TSHIRT, user.tshirtSize);
            values.put(PRIVACY, user.privacy);

            // Updating profile picture url for user with that userName
            db.update(USER_TABLE, values, USER_ID + " = " + user.id, null);
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
                // user.following = followDAO.getFollowing(user.getId(), db);
                // user.wishlists = wishListDAO.getWishLists(user.getId(), db);
                // user.interests = interestDAO.getInterests(user.getId(), db);
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

    public Boolean addFollow(User main, User friend){
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();

            values.put(UHF_ID, main.getId());
            values.put(UHF_PENDING, friend.privacy);
            values.put(UHF_FRIEND, friend.getId());

            db.insert(UHF_TABLE, null, values);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.d("SQL", "Error while trying to add or update user");
            return false;
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Function deleting the follow's relation between 2 specified User
     * @param main
     * @param friend
     * @return
     */
    public Boolean unfollow(User main, User friend){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        String whereClauses = UHF_ID + "=" + main.getId() + " AND " + UHF_FRIEND + "=" + friend.getId();
        try {
            db.delete(UHF_TABLE, whereClauses , null);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<User> getFollowable(int userID){
        ArrayList<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            String getQuery = String.format(
                    "SELECT * FROM User u " +
                            "WHERE u.userID != '%s' AND u.userID NOT IN " +
                            "    (SELECT frienduserID FROM User_has_Friends uhf WHERE uhf.userID == '%s')"
                    , userID, userID);

            Cursor cursor = db.rawQuery(getQuery, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    User user = new User(cursor.getInt(0), cursor.getString(4), cursor.getString(3), cursor.getString(5));
                    user.lastname = cursor.getString(2);
                    user.firstname = cursor.getString(1);
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
     * Function accepting or refusing the specified "friend" 's request
     * @param main
     * @param friend
     */
    public void setPending(User main, User friend, Boolean accept){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        String whereQuery = UHF_ID + "=" + main.getId() + " AND " + UHF_FRIEND + "=" + friend.getId();

        try {
            if (accept){
                ContentValues values = new ContentValues();
                values.put(UHF_PENDING, 0);
                db.update(UHF_TABLE, values, whereQuery,null);
                db.setTransactionSuccessful();
            } else {
                db.delete(UHF_TABLE, whereQuery , null);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
        }
        finally {
            db.endTransaction();
        }
    }


    /**
     * Function returning all the waiting follow to be accepted (or refused)
     * @param main
     * @return
     */
    public ArrayList<User> getPending(User main){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        ArrayList<User> userList = new ArrayList<>();

        try {
            String getQuery = String.format(
                    "SELECT * FROM User u" +
                            "WHERE u.userID != '%s' AND u.userID IN" +
                            "    (SELECT frienduserID FROM User_has_Friends uhf WHERE uhf.userID == '%s' AND uhf.pending == 1)"
                    , main.getId(), main.getId());

            Cursor cursor = db.rawQuery(getQuery, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    User user = new User(cursor.getInt(0), cursor.getString(4), cursor.getString(3), cursor.getString(5));
                    user.lastname = cursor.getString(2);
                    user.firstname = cursor.getString(1);
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
        } finally {
            db.endTransaction();
        }
    }
}