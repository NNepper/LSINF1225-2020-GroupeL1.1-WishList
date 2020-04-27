import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTableLockedException;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.MyDatabaseHelper;

public class UserDB extends MyDatabaseHelper {

    public UserDB(Context context) {
        super(context);
    }

    private static final String USER_TABLE = "User";
    private static final String USER_ID = "_id";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String ADDRESS = "address";
    private static final String COLOR = "fav_color";
    private static final String SHOES = "shoe_size";
    private static final String TROUSERS = "trouser_size";
    private static final String TSHIRT = "tshirt_size";
    private static final String PRIVACY = "privacy";


    public void updateUser(User user){
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

        db.update(USER_TABLE, values, USER_ID + "=" + user.id, null);
        db.close();
    }

    /**
     * Add a user to the Database and set his ID
     * @param user
     */
    public boolean addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (checkExistance("User", "email", user.email)){
            return false;
        }
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

        long x = db.insert(USER_TABLE, null, values);
        user.id = (int) x;
        db.close();
        return true;
    }

    /**
     * Getter to access to the User given the email address
     * @param email
     */
    public void getUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String getQuery = "SELECT * FROM User WHERE email == " + email;
        Cursor cursor = db.rawQuery(getQuery, null);
        try {
            User user = new User(-1);

        } finally {
            db.close();
        }

    }

    public boolean checkExistance(String TableName,
                                         String Field, String Value) {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "Select * from " + TableName + " where " + Field + " = " + Value;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }



}