package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DB.sqlite";
    private static final int DATABASE_VERSION = 2;

    // Database creation sql statement
    private static final String CREATE_USER = "CREATE TABLE User (\n" +
            "    userID       INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
            "    firstname    VARCHAR (16)  NOT NULL,\n" +
            "    lastname     VARCHAR (16)  NOT NULL,\n" +
            "    username     VARCHAR (16)  NOT NULL,\n" +
            "    email        VARCHAR (255) NOT NULL,\n" +
            "    password     VARCHAR (32)  NOT NULL,\n" +
            "    address      VARCHAR (255) NOT NULL,\n" +
            "    fav_color    VARCHAR (45),\n" +
            "    shoe_size    VARCHAR (45),\n" +
            "    trouser_size VARCHAR (45),\n" +
            "    tshirt_size  VARCHAR (45),\n" +
            "    privacy      BOOLEAN       NOT NULL,\n" +
            "    UNIQUE (\n" +
            "        username,\n" +
            "        email\n" +
            "    )\n" +
            ");\n";

    private static final String CREATE_WISHLIST = "CREATE TABLE Wishlist (\n" +
            "    wishlistID  INTEGER      PRIMARY KEY AUTOINCREMENT,\n" +
            "    name        VARCHAR (45) NOT NULL,\n" +
            "    description MEDIUMTEXT\n" +
            ");\n";

    private static final String CREATE_PRODUCTS = "CREATE TABLE Products (\n" +
            "    productID   [INTEGER AUTO_INCREMENT],\n" +
            "    name        VARCHAR (45),\n" +
            "    description VARCHAR (45),\n" +
            "    link        VARCHAR (45),\n" +
            "    purchased   BOOLEAN,\n" +
            "    position    INTEGER,\n" +
            "    quantity,\n" +
            "    wishlistID  INTEGER,\n" +
            "    PRIMARY KEY (\n" +
            "        productID\n" +
            "    )\n" +
            ");\n";

    private static final String CREATE_INTERESTS = "CREATE TABLE Interests (\n" +
            "    interestname VARCHAR (45)             UNIQUE\n" +
            "                                          NOT NULL,\n" +
            "    interestID   [INTEGER AUTO_INCREMENT],\n" +
            "    rating       INTEGER,\n" +
            "    PRIMARY KEY (\n" +
            "        interestID\n" +
            "    )\n" +
            ");\n";

    private static final String CREATE_USER_HAS_FRIENDS = "CREATE TABLE User_has_Friends (\n" +
            "    userID       INT     NOT NULL,\n" +
            "    frienduserID INT     NOT NULL,\n" +
            "    pending      BOOLEAN NOT NULL,\n" +
            "    CONSTRAINT fk_User_has_User_User1 FOREIGN KEY (\n" +
            "        userID\n" +
            "    )\n" +
            "    REFERENCES User (userID) ON DELETE CASCADE\n" +
            "                             ON UPDATE NO ACTION,\n" +
            "    CONSTRAINT fk_User_has_User_User2 FOREIGN KEY (\n" +
            "        frienduserID\n" +
            "    )\n" +
            "    REFERENCES User (userID) ON DELETE CASCADE\n" +
            "                             ON UPDATE NO ACTION\n" +
            ");\n";

    private static final String CREATE_USER_HAS_INTERESTS = "CREATE TABLE User_has_Interests (\n" +
            "    userID     INT NOT NULL,\n" +
            "    interestID INT NOT NULL,\n" +
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
            ");";

    private static final String CREATE_USER_HAS_WISHLIST = "CREATE TABLE User_has_Wishlist (\n" +
            "    userID     INT NOT NULL,\n" +
            "    wishlistID INT NOT NULL,\n" +
            "    PRIMARY KEY (\n" +
            "        userID,\n" +
            "        wishlistID\n" +
            "    ),\n" +
            "    CONSTRAINT fk_User_has_Wishlist_User1 FOREIGN KEY (\n" +
            "        userID\n" +
            "    )\n" +
            "    REFERENCES User (userID) ON DELETE CASCADE\n" +
            "                             ON UPDATE NO ACTION,\n" +
            "    CONSTRAINT fk_User_has_Wishlist_Wishlist1 FOREIGN KEY (\n" +
            "        wishlistID\n" +
            "    )\n" +
            "    REFERENCES Wishlist ON DELETE CASCADE\n" +
            "                        ON UPDATE NO ACTION\n" +
            ");\n";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_USER);
        database.execSQL(CREATE_WISHLIST);
        database.execSQL(CREATE_PRODUCTS);
        database.execSQL(CREATE_INTERESTS);
        database.execSQL(CREATE_USER_HAS_FRIENDS);
        database.execSQL(CREATE_USER_HAS_INTERESTS);
        database.execSQL(CREATE_USER_HAS_WISHLIST);
    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
        Log.w(MyDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS User");
        database.execSQL("DROP TABLE IF EXISTS Products");
        database.execSQL("DROP TABLE IF EXISTS Wishlist");
        database.execSQL("DROP TABLE IF EXISTS Interests");
        database.execSQL("DROP TABLE IF EXISTS User_has_Friends");
        database.execSQL("DROP TABLE IF EXISTS User_has_Interests");
        database.execSQL("DROP TABLE IF EXISTS User_has_Wishlist");

        onCreate(database);
    }
}