package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DB.sqlite";
    private static final int DATABASE_VERSION = 2;
    protected Context context;

    //User Table Tags
    protected static final String USER_TABLE = "User";
    protected static final String USER_ID = "userID";
    protected static final String FIRSTNAME = "firstname";
    protected static final String LASTNAME = "lastname";
    protected static final String USERNAME = "username";
    protected static final String EMAIL = "email";
    protected static final String PASSWORD = "password";
    protected static final String ADDRESS = "address";
    protected static final String COLOR = "fav_color";
    protected static final String SHOES = "shoe_size";
    protected static final String TROUSERS = "trouser_size";
    protected static final String TSHIRT = "tshirt_size";
    protected static final String PRIVACY = "privacy";


    //Product Table Tags
    protected static final String PRODUCT_TABLE = "Product";
    protected static final String PRODUCT_ID = "productID";
    protected static final String PROD_NAME = "name";
    protected static final String PROD_DESC = "description";
    protected static final String PROD_LINK = "link";
    protected static final String PROD_PURCHASSED = "purchased";
    protected static final String PROD_POSITION = "position";
    protected static final String PROD_QUANTITY = "quantity" ;
    protected static final String PROD_WISHLIST = "wishlistID";


    //Interests Table Tags
    protected static final String INT_TABLE = "Interests";
    protected static final String INT_NAME = "interestname";
    protected static final String INT_ID = "interestID";
    protected static final String RATING = "rating";


    //Wishlist Table Tags
    protected static final String WISHLIST_TABLE = "Wishlist";
    protected static final String WISHLIST_ID = "wishlistID";
    protected static final String WISHLIST_NAME = "name";
    protected static final String WISHLIST_DESC = "description";


    //User_has_Friends
    protected static final String UHF_TABLE = "User_has_Friends";
    protected static final String UHF_ID = "userID";
    protected static final String UHF_FRIEND = "frienduserID";
    protected static final String PENDING = "pending";


    //User_has_Interests
    protected static final String UHI_TABLE = "User_has_Interests";
    protected static final String UHI_USERID = "userID";
    protected static final String UHI_ID = "interestID";


    //User_has_Wishlist
    protected static final String UHW_TABLE = "User_has_Wishlist";
    protected static final String UHW_USERID = "userID";
    protected static final String UHW_ID = "wishlistID";


    // Database creation sql statement
    private static final String CREATE_USER = "CREATE TABLE User (" +
            "    userID       INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
            "    firstname    VARCHAR (16)  NOT NULL," +
            "    lastname     VARCHAR (16)  NOT NULL," +
            "    username     VARCHAR (16)  NOT NULL," +
            "    email        VARCHAR (255) NOT NULL," +
            "    password     VARCHAR (32)  NOT NULL," +
            "    address      VARCHAR (255) NOT NULL," +
            "    fav_color    VARCHAR (45)," +
            "    shoe_size    VARCHAR (45)," +
            "    trouser_size VARCHAR (45)," +
            "    tshirt_size  VARCHAR (45)," +
            "    privacy      INTEGER       NOT NULL," +
            "    UNIQUE (" +
            "        username," +
            "        email" +
                ")" +
            ");";

    private static final String CREATE_WISHLIST = "CREATE TABLE Wishlist (" +
            "    wishlistID  INTEGER      PRIMARY KEY AUTOINCREMENT," +
            "    name        VARCHAR (45) NOT NULL," +
            "    description MEDIUMTEXT" +
            ");";

    private static final String CREATE_PRODUCTS = "CREATE TABLE Products (" +
            "    productID   [INTEGER AUTO_INCREMENT]," +
            "    name        VARCHAR (45)," +
            "    description VARCHAR (45)," +
            "    link        VARCHAR (45)," +
            "    purchased   BOOLEAN," +
            "    position    INTEGER," +
            "    quantity," +
            "    wishlistID  INTEGER," +
            "    PRIMARY KEY (" +
            "        productID" +
            "    )" +
            ");";

    private static final String CREATE_INTERESTS = "CREATE TABLE Interests (" +
            "    interestname VARCHAR (45)             UNIQUE" +
            "                                          NOT NULL," +
            "    interestID   [INTEGER AUTO_INCREMENT]," +
            "    rating       INTEGER," +
            "    PRIMARY KEY (" +
            "        interestID" +
            "    )" +
            ");";

    private static final String CREATE_USER_HAS_FRIENDS = "CREATE TABLE User_has_Friends (" +
            "    userID       INT     NOT NULL," +
            "    frienduserID INT     NOT NULL," +
            "    pending      BOOLEAN NOT NULL," +
            "    CONSTRAINT fk_User_has_User_User1 FOREIGN KEY (" +
            "        userID" +
            "    )" +
            "    REFERENCES User (userID) ON DELETE CASCADE" +
            "                             ON UPDATE NO ACTION," +
            "    CONSTRAINT fk_User_has_User_User2 FOREIGN KEY (" +
            "        frienduserID" +
            "    )" +
            "    REFERENCES User (userID) ON DELETE CASCADE" +
            "                             ON UPDATE NO ACTION" +
            ");";

    private static final String CREATE_USER_HAS_INTERESTS = "CREATE TABLE User_has_Interests (" +
            "    userID     INT NOT NULL," +
            "    interestID INT NOT NULL," +
            "    PRIMARY KEY (" +
            "        userID," +
            "        interestID" +
            "    )," +
            "    CONSTRAINT fk_User_has_Interests_User1 FOREIGN KEY (" +
            "        userID" +
            "    )" +
            "    REFERENCES User (userID) ON DELETE CASCADE" +
            "                             ON UPDATE NO ACTION," +
            "    CONSTRAINT fk_User_has_Interests_Interests1 FOREIGN KEY (" +
            "        interestID" +
            "    )" +
            "    REFERENCES Interests (interestID) ON DELETE CASCADE" +
            "                                      ON UPDATE NO ACTION" +
            ");";

    private static final String CREATE_USER_HAS_WISHLIST = "CREATE TABLE User_has_Wishlist (" +
            "    userID     INT NOT NULL," +
            "    wishlistID INT NOT NULL," +
            "    PRIMARY KEY (" +
            "        userID," +
            "        wishlistID" +
            "    )," +
            "    CONSTRAINT fk_User_has_Wishlist_User1 FOREIGN KEY (" +
            "        userID" +
            "    )" +
            "    REFERENCES User (userID) ON DELETE CASCADE" +
            "                             ON UPDATE NO ACTION," +
            "    CONSTRAINT fk_User_has_Wishlist_Wishlist1 FOREIGN KEY (" +
            "        wishlistID" +
            "    )" +
            "    REFERENCES Wishlist ON DELETE CASCADE" +
            "                        ON UPDATE NO ACTION" +
            ");";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        try {
            database.execSQL(CREATE_USER);
            database.execSQL(CREATE_WISHLIST);
            database.execSQL(CREATE_PRODUCTS);
            database.execSQL(CREATE_INTERESTS);
            database.execSQL(CREATE_USER_HAS_FRIENDS);
            database.execSQL(CREATE_USER_HAS_INTERESTS);
            database.execSQL(CREATE_USER_HAS_WISHLIST);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
        Log.w(MyDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + WISHLIST_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + INT_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + UHF_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + UHI_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + UHW_TABLE);

        onCreate(database);
    }
}