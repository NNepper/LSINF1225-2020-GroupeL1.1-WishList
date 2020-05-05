package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;

public class FollowDAO extends MyDatabaseHelper {
    public FollowDAO(Context context) {
        super(context);
    }


    public ArrayList<User> getFollowing(int userID, SQLiteDatabase db){
        if(db == null) db = this.getWritableDatabase();
        UserDAO userDAO = new UserDAO(context);

        ArrayList<User> followingList = new ArrayList<>();

        try {
            String getQuery = String.format(
                    "SELECT * FROM User_has_Friends uhf " +
                            "WHERE uhf.userID == '%s' AND uhf.pending == 0", userID);

            Cursor cursor = db.rawQuery(getQuery, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    //Check if not pending
                    if (cursor.getInt(2) == 0){
                        followingList.add( userDAO.get(cursor.getInt(1 ), db) );
                    }
                    cursor.moveToNext();
                }
            }
            return followingList;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return null;
        }
    }

    public ArrayList<User> getFollowers(int userID){
        SQLiteDatabase db = this.getWritableDatabase();
        UserDAO userDAO = new UserDAO(context);
        db.beginTransaction();

        ArrayList<User> followingList = new ArrayList<>();

        try {
            String getQuery = String.format(
                    "SELECT * FROM User_has_Friends uhf " +
                            "WHERE uhf.frienduserID == '%s'", userID);

            Cursor cursor = db.rawQuery(getQuery, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    //Check if not pending
                    if (cursor.getInt(2) == 0){
                        followingList.add( userDAO.get( cursor.getInt(0 ), db) );
                    }
                    cursor.moveToNext();
                }
            }
            db.setTransactionSuccessful();
            return followingList;
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
}
