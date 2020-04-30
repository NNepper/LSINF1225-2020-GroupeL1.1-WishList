package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

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


    /**
     * Function returning every user the mainUser follow
     * @param userID
     * @return ArrayList of User
     */
    public ArrayList<User> getFollowing(int userID){
        SQLiteDatabase db = this.getWritableDatabase();
        UserDAO userDAO = new UserDAO(context);
        db.beginTransaction();

        ArrayList<User> followingList = new ArrayList<>();

        try {
            String getQuery = String.format(
                    "SELECT * FROM User_has_Friends uhf " +
                            "WHERE uhf.userID == '%s'", userID);

            Cursor cursor = db.rawQuery(getQuery, null);
            db.close();

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    //Check if not pending
                    if (cursor.getInt(2) == 0){
                        followingList.add( userDAO.get( cursor.getInt(1 )) );
                    }
                    cursor.moveToNext();
                }
            }
            cursor.close();

            return followingList;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return null;
        }
        finally {
            db.close();
        }
    }

    /**
     * Function returning every User following the specified user
     * @param userID
     * @return ArrayList of User
     */
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
            db.close();

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    //Check if not pending
                    if (cursor.getInt(2) == 0){
                        followingList.add( userDAO.get( cursor.getInt(0 )) );
                    }
                    cursor.moveToNext();
                }
            }

            return followingList;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return null;
        }
        finally {
            db.close();
        }
    }
}
