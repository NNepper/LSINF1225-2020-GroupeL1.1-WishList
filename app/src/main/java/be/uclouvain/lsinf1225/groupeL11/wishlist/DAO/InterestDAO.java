package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Interest;

public class InterestDAO extends MyDatabaseHelper{
    public InterestDAO(Context context) {
        super(context);
    }

    /**
     * Utility function for DAO
     * @param interestID
     * @return Interest
     */
    public Interest getInterest(int interestID){
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        try {
            String getQuery = "SELECT * FROM Interest i WHERE i.interestID == '" + interestID + "'";
            Cursor cursor = db.rawQuery(getQuery, null);

            cursor.moveToFirst();
            Interest interest = new Interest(cursor.getString(0));
            interest.setId(cursor.getInt(1));
            //TODO: rating si time

            return interest;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return null;
        }
        finally {
            db.close();
        }
    }

    /**
     * Function returning every interest of the specified User
     * @param userID
     * @return ArrayList of interests
     */
    public ArrayList<Interest> getInterests(int userID){
        ArrayList<Interest> interestList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            String getQuery = String.format(
                    "SELECT * FROM User_has_Interests uhi " +
                            "WHERE uhi.userID == '%s'", userID);

            Cursor cursor = db.rawQuery(getQuery, null);
            db.close();

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    interestList.add( getInterest( cursor.getInt(1) ) );
                    cursor.moveToNext();
                }
            }

            return interestList;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return null;
        }
        finally {
            db.close();
        }
    }


    /**
     * Function for creating
     * @param interest
     * @return
     */
    public boolean create(Interest interest){
        SQLiteDatabase db = getWritableDatabase();

        Boolean noError = true;
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();

            values.put(INT_NAME, interest.interestname);

            // First try to update the user in case the user already exists in the database
            int rows = (int) db.insert(INT_TABLE, null, values);
            interest.setId(rows);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            noError = false;
        } finally {
            db.endTransaction();
            db.close();
            return noError;
        }
    }

    /**
     * Function
     * @return
     */
    public ArrayList<Interest> getAllInterests(){
        ArrayList<Interest> interestList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            String getQuery = String.format(
                    "SELECT * FROM Interests uhi");

            Cursor cursor = db.rawQuery(getQuery, null);
            db.close();

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    interestList.add( getInterest( cursor.getInt(1) ) );
                    cursor.moveToNext();
                }
            }

            return interestList;
        } catch (Exception e) {
            Log.d("SQL", e.getMessage());
            return null;
        }
        finally {
            db.close();
        }
    }
}
