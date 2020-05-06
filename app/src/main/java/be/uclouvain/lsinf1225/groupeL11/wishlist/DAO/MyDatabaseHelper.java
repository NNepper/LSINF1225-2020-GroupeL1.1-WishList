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
    protected static final String PRODUCT_TABLE = "Products";
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


    //User_has_profil
    protected static final String UHP_TABLE = "User_has_Profil";
    protected static final String UHP_USERID = "userID";
    protected static final String UHP_IMAGE = "image";


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


    private static final String CREATE_USER_HAS_PROFIL = "CREATE TABLE User_has_Profil (" +
            "    userID INTEGER PRIMARY KEY" +
            "                   NOT NULL" +
            "                   UNIQUE," +
            "    image  BOOLEAN NOT NULL" +
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
            database.execSQL(CREATE_USER_HAS_PROFIL);

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
        database.execSQL("DROP TABLE IF EXISTS " + UHP_TABLE);

        onCreate(database);
    }


    protected static final String[] Populate = new String[]{
            "INSERT INTO Interests (interestname, interestID) VALUES ('Sport', 1);",
            "INSERT INTO Interests (interestname, interestID) VALUES ('Cinema', 2);",
            "INSERT INTO Interests (interestname, interestID) VALUES ('Computer science', 3);",
            "INSERT INTO Interests (interestname, interestID) VALUES ('Clothes', 4);",
            "INSERT INTO Interests (interestname, interestID) VALUES ('Video games', 5);",
            "INSERT INTO Interests (interestname, interestID) VALUES ('Books', 6);",
            "INSERT INTO Interests (interestname, interestID) VALUES ('Furniture', 7);\n",
            "INSERT INTO Interests (interestname, interestID) VALUES ('Cooking', 8);\n",


            "INSERT INTO Wishlist (wishlistID, name, description) VALUES (1, 'My sport wishes', 'All my favourite sport items that I would love to get !');\n",
            "INSERT INTO Wishlist (wishlistID, name, description) VALUES (2, 'My dreams', 'I listed here all my dreams');\n",
            "INSERT INTO Wishlist (wishlistID, name, description) VALUES (3, 'Cooking wishlist', 'As a huge fan of cooking I will update this wishlist with all funny things');\n",
            "INSERT INTO Wishlist (wishlistID, name, description) VALUES (4, 'So sweet things', 'These things are just amazing ! Id love to get then');\n",
            "INSERT INTO Wishlist (wishlistID, name, description) VALUES (5, 'Perfect gifts', 'If you want to make a gift choose in those things !');\n",
            "INSERT INTO Wishlist (wishlistID, name, description) VALUES (6, 'Dream gifts', 'There are so many little thingd Id never buy but they would be wonderful gifts');\n",
            "INSERT INTO Wishlist (wishlistID, name, description) VALUES (7, 'My wishlist', 'There are my wishes !!');\n",

            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (1, 'Samsung Galaxy 20', 'Smartphone Android 5G', 'https://www.samsung.com/be_fr/smartphones/galaxy-s20/buy/', 1, 1, 2, 1);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (2, 'Cable ethernet', 'Cable ethernet de 10m', 'https://www.amazon.fr/Ethernet-Blindage-Compatible-Nintendo-Routeur/dp/B07DDH9RLK/ref=sr_1_3?keywords=cable+ethernet+10m&qid=1583517691&s=electronics&sr=1-3', 0, 1, 3, 2);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (3, 'Ballon', 'Ballon de Basket signé par Kobe Bryant', 'https://www.ebay.com/itm/Kobe-Bryant-Signed-Full-Size-Basketball-Autograph-Case-Beckett-BAS-LOA-Lakers/383441285983?hash=item5946e1b35f:g:R6IAAOSw1~BeWXDK', 0, 2, 4, 1);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (4, 'Name', 'Description', 'webLink', 0, 1, 1, 1);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (5, 'Football shirt', 'Football shirt of Axel Witsel with Belgian Red Devils', null, 0, 1, 1, 1);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (6, 'Tennis raquet', 'A babolat tennis raquet of size 3', null, 0, 2, 1, 1);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (7, 'Football ball', 'The original Jabulani ball from South Africa World Cup 2008', null, 0, 3, 1, 1);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (8, 'Football shoes', 'This new shoes are so insane!', null, 0, 4, 1, 1);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (9, 'Computer', 'If you want to be my new best friend here is the perfect gift', null, 0, 1, 1,  2);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (10, 'Pencil box', 'A pencil box to put away the pens Id like.', null, 0, 2, 1, 2);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (11, 'Pencils', 'Really high quality pencils', null, 0, 3, 1, 2);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (12, 'Sheet pad', 'That is a great sheet pad', null, 0, 4, 1, 2);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (13, 'Soup ladle', 'This superb soup ladle seams really great', null, 0, 1, 1, 3);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (14, 'Set of cutlery', 'Set of cutlery to renew my old cutlery', null, 0, 2, 1, 3);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (15, 'Saucepan', 'A funny saucepan', null, 0, 3, 1, 3);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (16, 'Meat slicer', 'This is a meat slicer to slice my sausage', null, 0, 4, 1, 3);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (17, 'Sunglasses', 'Red sunglasses', null, 0, 1, 1, 4);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (18, 'Coat', 'Brown wool coat from Ireland', null, 0, 2, 1, 4);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (19, 'Controller', 'PC controller to play with my friends', null, 0, 3, 1, 4);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (20, 'Magnet', 'A true scitific magnet', null, 0, 4, 1, 4);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (21, 'Couch', 'If you want a make a great gift!', null, 0, 1, 1, 5);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (22, 'Wallet', 'Leather wallet', null, 0, 2, 1, 5);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (23, 'Leg warmers', 'For the cold nights of winter', null, 0, 3, 1, 5);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (24, 'Tissue box', 'A funny tissue box to but in my living room', null, 0, 4, 1, 5);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (25, 'Needle', 'I want to start sewing', null, 0, 1, 1, 6);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (26, 'Shovel', 'A great shovel I know this brand', null, 0, 2, 1, 6);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (27, '45-rpm player', 'Id love to listent to my old singles', null, 1, 3, 1, 6);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (28, 'A Beatles single', 'I just got a 45-rpm player.', null, 0, 4, 1, 6);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (29, 'Paint brush', 'A large brush please', null, 0, 1, 1, 7);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (30, 'Color pot', 'Famous brand it is a must have', null, 0, 2, 1, 7);\n",
            "INSERT INTO Products (productID, name, description, link, purchased, position, quantity, wishlistID) VALUES (31, 'Nail clippers', 'My nails are soooo long', null, 0, 3, 1, 7);\n",

            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (1, 'firstnameDemo', 'lastnameDemo', 'usernameDemo', 'user', 'user', 'Street, ZIP, City', 'Red', 41, 'S', 'M', 0);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (2, 'Gia', 'Cortes', 'giacortes', 'giacortes@mail.com', 'gia', 'Gia Street, 9464, LA', 'Red', 40, 'M', 'L', 0);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (3, 'Jon', 'Maxwell', 'jonmaxwell', 'jonmaxwell@mail.com', 'jon', 'Jon Stret, 6455, London', 'Red', 42, 'XL', 'XXL', 0);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (4, 'Terry', 'Kearns', 'terrykearns', 'terrykearns@mail.com', 'terry', '1st Avenue, 7532, NY', 'Blue', 40, 'M', 'L', 1);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (5, 'Jean', 'Dupont', 'jeandupont', 'jeandupont@mail.com', 'jean', 'Chez Dupont 01, 7852, Paris', 'Blue', 42, 'L', 'M', 1);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (6, 'Jeanne', 'Dupont', 'jeannedupont', 'jeannedupont@mail.com', 'jeanne', 'Chez Dupont 01, 7852, Paris', 'Green', 36, 'S', 'M', 0);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (7, 'Barack', 'Obama', 'barackobama', 'barackobama@mail.com', 'barack', 'My Great Street, 6568, Newtown', 'Blue', 42, 'XS', 'XS', 1);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (8, 'Corentin', 'Lengele', 'corentinlengele', 'corentinlengele@mail.com', 'corentin', 'Wallons, 1348, LLN', 'Blue', 41, 'S', 'M', 1);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (9, 'Gwendal', 'Laurent', 'gwendal', 'gwendal@mail.com', 'gwendal', 'Picardie, 1348, LLN', 'Blue', 39, 'M', 'M', 1);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (10, 'Jules', 'Lesuisse', 'jules', 'jules@mail.com', 'jules', 'Neuville, 1348, LLN', 'Red', 41, 'S', 'S', 1);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (11, 'Nathan', 'Nepper', 'nathan', 'nathan@mail.com', 'nathan', 'Grand Rue, 1348, LLN', 'Green', 44, 'M', 'M', 1);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (12, 'Corentin', 'Lingier', 'corentinlingier', 'corentinlingier@mail.com', 'corentin', 'Primevères, 1348, LLN', 'Green', 43, 'M', 'S', 0);\n",
            "INSERT INTO User (userID, firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size, privacy) VALUES (13, 'William', 'Ruan', 'williamruan', 'williamruan@mail.com', 'william', 'Marjolaine, 1348, LLN', 'Blue', 42, 'L', 'S', 0);\n",

            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (1, 2, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (1, 3, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (1, 4, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (1, 5, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (1, 6, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (1, 7, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (1, 8, 1);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (1, 9, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (1, 10, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (1, 11, 1);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (1, 12, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (1, 13, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (2, 3, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (2, 6, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (2, 8, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (2, 11, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (2, 12, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (2, 13, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (3, 1, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (3, 5, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (3, 9, 1);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (4, 3, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (4, 6, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (4, 8, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (4, 9, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (5, 3, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (5, 4, 1);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (5, 6, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (5, 7, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (5, 8, 1);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (6, 1, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (6, 3, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (7, 1, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (7, 2, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (7, 3, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (7, 4, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (7, 5, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (7, 6, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (8, 7, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (8, 9, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (8, 10, 1);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (8, 11, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (9, 8, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (9, 11, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (9, 12, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (9, 13, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (10, 8, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (10, 9, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (10, 11, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (11, 2, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (11, 3, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (11, 6, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (11, 7, 1);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (12, 1, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (12, 6, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (12, 8, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (13, 4, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (13, 6, 0);\n",
            "INSERT INTO User_has_Friends (userID, frienduserID, pending) VALUES (13, 8, 1);\n",

            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (1, 1, 4);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (1, 5, 2);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (1, 7, 5);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (2, 2, 2);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (2, 3, 1);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (2, 6, 2);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (3, 1, 4);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (3, 2, 2);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (3, 3, 1);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (4, 4, 3);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (5, 5, 4);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (6, 6, 5);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (7, 7, 2);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (8, 2, 1);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (8, 4, 4);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (8, 6, 2);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (9, 7, 3);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (9, 1, 1);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (9, 5, 2);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (10, 3, 5);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (10, 1, 2);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (11, 2, 3);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (12, 6, 4);\n",
            "INSERT INTO User_has_Interests (userID, interestID, rating) VALUES (13, 2, 1);\n",

            "INSERT INTO User_has_Wishlist (userID, wishlistID) VALUES (1, 1);\n",
            "INSERT INTO User_has_Wishlist (userID, wishlistID) VALUES (8, 2);\n",
            "INSERT INTO User_has_Wishlist (userID, wishlistID) VALUES (9, 3);\n",
            "INSERT INTO User_has_Wishlist (userID, wishlistID) VALUES (10, 4);\n",
            "INSERT INTO User_has_Wishlist (userID, wishlistID) VALUES (11, 5);\n",
            "INSERT INTO User_has_Wishlist (userID, wishlistID) VALUES (12, 6);\n",
            "INSERT INTO User_has_Wishlist (userID, wishlistID) VALUES (13, 7);\n"};

}

