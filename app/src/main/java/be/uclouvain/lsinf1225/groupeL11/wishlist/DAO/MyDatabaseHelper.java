package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DB.sqlite";
    private static int DATABASE_VERSION = 2;
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


    //Wishlist Table Tags
    protected static final String WISHLIST_TABLE = "Wishlist";
    protected static final String WISHLIST_ID = "wishlistID";
    protected static final String WISHLIST_NAME = "name";
    protected static final String WISHLIST_DESC = "description";


    //User_has_Friends
    protected static final String UHF_TABLE = "User_has_Friends";
    protected static final String UHF_ID = "userID";
    protected static final String UHF_FRIEND = "frienduserID";
    protected static final String UHF_PENDING = "pending";


    //User_has_Interests
    protected static final String UHI_TABLE = "User_has_Interests";
    protected static final String UHI_USERID = "userID";
    protected static final String UHI_ID = "interestID";
    protected static final String UHI_RATING = "rating";


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
            "    shoe_size    INTEGER     ," +
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
            "    purchased   INTEGER," +
            "    position    INTEGER," +
            "    quantity    INTEGER," +
            "    wishlistID  INTEGER," +
            "    PRIMARY KEY (" +
            "        productID" +
            "    )" +
            ");";

    private static final String CREATE_INTERESTS = "CREATE TABLE Interests (" +
            "    interestname VARCHAR (45)             UNIQUE" +
            "                                          NOT NULL," +
            "    interestID   [INTEGER AUTO_INCREMENT]," +
            "    PRIMARY KEY (" +
            "        interestID" +
            "    )" +
            ");";

    private static final String CREATE_USER_HAS_FRIENDS = "CREATE TABLE User_has_Friends (" +
            "    userID       INT     NOT NULL," +
            "    frienduserID INT     NOT NULL," +
            "    pending      INTEGER NOT NULL," +
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

    private static final String CREATE_USER_HAS_INTERESTS = "CREATE TABLE User_has_Interests (\n" +
            "    userID     INT     NOT NULL,\n" +
            "    interestID INT     NOT NULL,\n" +
            "    rating     INTEGER NOT NULL,\n" +
            "    PRIMARY KEY (\n" +
            "        userID,\n" +
            "        interestID\n" +
            "    ),\n" +
            "    CONSTRAINT fk_User_has_Interests_User1 FOREIGN KEY (\n" +
            "        userID\n" +
            "    )\n" +
            "    REFERENCES User (userID) ON DELETE CASCADE\n" +
            "                             ON UPDATE NO ACTION,\n" +
            "    CONSTRAINT fk_User_has_Interests_Interests1 FOREIGN KEY (\n" +
            "        interestID\n" +
            "    )\n" +
            "    REFERENCES Interests (interestID) ON DELETE CASCADE\n" +
            "                                      ON UPDATE NO ACTION\n" +
            ");\n ";

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
            database.execSQL(CREATE_INTERESTS);
            database.execSQL(CREATE_PRODUCTS);
            database.execSQL(CREATE_WISHLIST);
            database.execSQL(CREATE_USER_HAS_FRIENDS);
            database.execSQL(CREATE_USER_HAS_INTERESTS);
            database.execSQL(CREATE_USER_HAS_WISHLIST);

            for (int i = 0; i < Populate.length; i++){
                database.execSQL(Populate[i]);
            }
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


    protected static final String[] Populate = new String[]{
            "INSERT INTO Interests (interestname, interestID) VALUES ('Cinema', 9);",
            "INSERT INTO Interests (interestname, interestID) VALUES ('Sport', 10);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (1, 'Samsung Galaxy 20', 'Smartphone Android 5G', 'https://www.samsung.com/be_fr/smartphones/galaxy-s20/buy/', 1, 1, 2, 1);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (2, 'Cable ethernet', 'Cable ethernet de 10m', 'https://www.amazon.fr/Ethernet-Blindage-Compatible-Nintendo-Routeur/dp/B07DDH9RLK/ref=sr_1_3?keywords=cable+ethernet+10m&qid=1583517691&s=electronics&sr=1-3', 0, 1, 3, 2);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (3, 'Ballon', 'Ballon de Basket signé par Kobe Bryant', 'https://www.ebay.com/itm/Kobe-Bryant-Signed-Full-Size-Basketball-Autograph-Case-Beckett-BAS-LOA-Lakers/383441285983?hash=item5946e1b35f:g:R6IAAOSw1~BeWXDK', 0, 2, 4, 1);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (1, 'corentin', 'bruce', 'clingier', 'new_address@gmail.com', '1234', 'rue il', 'Blue', 42, 'M', 'S', 0);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (2, 'Bruce', 'lingier', 'blingier', 'corentin', 'password', 'Place des Wallons 70, 1348 Louvain-la-Neuve', 'Red', 42, 'M', 'M', 0);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (26, 'test', 'user', 'testuser', 'testuser@email.com', 'none', 'address', 'Green', 42, 'M', 'M', 1);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (3, 'firstnameDemo', 'lastnameDemo', 'usernameDemo', 'emailDemo', 'demo', 'Street, ZIP, City', 'Red', 41, 'S', 'M', 0);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (4, 'Jules', 'Lesuisse', 'jules', 'jul', 'jul', 'Neuville, LLN', 'Red', 41, 'S', 'S', 0);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (5, 'Jean', 'Dupont', 'jeandupont', 'jeandupont@email.com', 'jean', 'Chez Dupont, 01', 'Blue', 42, 'M', 'M', 1);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (6, 'Jeanne', 'Dupont', 'jeannedupont', 'jeannedupont@email.com', 'jeanne', 'Chez Dupont, 01', 'Green', 36, 'S', 'M', 0);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (7, 'Barack', 'Obama', 'barackoba', 'barackobama@email.com', 'barack', 'Street, 9999, Miami', 'Blue', 42, 'M', 'M', 1);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (1, 2, 1);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (2, 1, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (26, 1, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (1, 26, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (5, 6, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (6, 5, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (4, 7, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (4, 3, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (3, 5, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (5, 7, 1);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (7, 3, 0);\n",

            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (1, 9, 4);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (2, 10, 2);\n",
            "INSERT INTO User_has_Wishlist (userID, wishlistID) VALUES (1, 1);\n",
            "INSERT INTO User_has_Wishlist (userID, wishlistID) VALUES (2, 2);\n",
            "INSERT INTO User_has_Wishlist (userID, wishlistID) VALUES (26, 11);\n",
            "INSERT INTO Wishlist (wishlistID, name, description) VALUES (1, 'High-Tech', 'List des objets connectes qui m interesse');\n",
            "INSERT INTO Wishlist (wishlistID, name, description) VALUES (2, 'Sport', 'Objets de sports');\n",
            "INSERT INTO Wishlist (wishlistID, name, description) VALUES (3, 'Test1', 'Wishlist test 1');\n",
            "INSERT INTO Wishlist (wishlistID, name, description) VALUES (4, 'Test2', 'Wishlist_test2');\n",
            "INSERT INTO Wishlist (wishlistID, name, description) VALUES (7, 'Test', 'Test Wishlist');\n",
            "INSERT INTO Wishlist (wishlistID, name, description) VALUES (8, 'Test', 'Test Wishlist');\n",
            "INSERT INTO Wishlist (wishlistID, name, description) VALUES (11, 'Test update', 'Test Wishlist');\n"};

}

