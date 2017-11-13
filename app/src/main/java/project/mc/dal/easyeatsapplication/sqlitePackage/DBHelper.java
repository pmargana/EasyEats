package project.mc.dal.easyeatsapplication.sqlitePackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by harika93 on 2017-11-11.
 */

public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "product.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_WISHLIST = "CREATE TABLE " + SingleProductWishList.TABLE  + "("
                + SingleProductWishList.KEY_ID  + " INTEGER PRIMARY KEY ,"
                + SingleProductWishList.KEY_image + " TEXT, "
                + SingleProductWishList.KEY_title + " TEXT, "
                + SingleProductWishList.KEY_price + " INTEGER, "
                +SingleProductWishList.KEY_RESTAURANT + " TEXT, "
                + SingleProductWishList.KEY_selected + " INTEGER )";

        sqLiteDatabase.execSQL(CREATE_TABLE_WISHLIST);

        String CREATE_TABLE_CART = "CREATE TABLE " + SingleProductCart.TABLE  + "("
                + SingleProductCart.KEY_ID  + " INTEGER PRIMARY KEY ,"
                + SingleProductCart.KEY_image + " TEXT, "
                + SingleProductCart.KEY_title + " TEXT, "
                + SingleProductCart.KEY_price + " TEXT, "
                + SingleProductCart.KEY_quatity + " INTEGER, "
                +SingleProductCart.KEY_RESTAURANT + " TEXT, "
                + SingleProductCart.KEY_totalamount + " TEXT )";

        sqLiteDatabase.execSQL(CREATE_TABLE_CART);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed, all data will be gone!!!
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SingleProductWishList.TABLE);

        // Create tables again
        onCreate(sqLiteDatabase);

    }
}
