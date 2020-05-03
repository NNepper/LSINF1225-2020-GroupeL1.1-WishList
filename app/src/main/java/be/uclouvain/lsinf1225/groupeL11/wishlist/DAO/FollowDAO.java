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


    public ArrayList<User> getFollowing(int userID){
        UserDAO userDAO = new UserDAO(context);
        ArrayList<User> followingList = new ArrayList<>();

        SQLiteDatabase db = getDB();
        db.beginTransaction();

        try {
            String getQuery = String.format(
                    "SELECT * FROM User_has_Friends uhf " +
                            "WHERE uhf.userID == '%s'", userID);

            Cursor cursor = db.rawQuery(getQuery, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    //Check if not pending
                    if (cursor.getInt(2) == 0){
                        followingList.add( userDAO.get(cursor.getInt(1 )) );
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

    public ArrayList<User> getFollowers(int userID){
        SQLiteDatabase db = getDB();
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
                        followingList.add( userDAO.get( cursor.getInt(0 )) );
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
}
