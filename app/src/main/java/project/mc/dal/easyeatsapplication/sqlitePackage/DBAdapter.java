package project.mc.dal.easyeatsapplication.sqlitePackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by harika93 on 2017-11-11.
 */

public class DBAdapter {
    private DBHelper dbHelper;

    public DBAdapter(Context context)
    {
        dbHelper = new DBHelper(context);
    }

    public int insert(SingleProductWishList student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SingleProductWishList.KEY_ID, student.product_ID);
        values.put(SingleProductWishList.KEY_image, student.imageurl);
        values.put(SingleProductWishList.KEY_title, student.title);
        values.put(SingleProductWishList.KEY_price, student.price);
        values.put(SingleProductWishList.KEY_selected, student.selected);

        // Inserting Row
        long product_Id = db.insert(SingleProductWishList.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) product_Id;
    }

    public int delete(int product_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        int affectedrowCount=db.delete(SingleProductWishList.TABLE, SingleProductWishList.KEY_ID + "= ?", new String[]{String.valueOf(product_Id)});
        db.close(); // Closing database connection
        return affectedrowCount;
    }

    public void update(SingleProductWishList student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SingleProductWishList.KEY_image, student.imageurl);
        values.put(SingleProductWishList.KEY_title,student.title);
        values.put(SingleProductWishList.KEY_price, student.price);
        values.put(SingleProductWishList.KEY_selected, student.selected);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(SingleProductWishList.TABLE, values, SingleProductWishList.KEY_ID + "= ?", new String[]{String.valueOf(student.product_ID)});
        db.close(); // Closing database connection
    }

    public void updateSelectedCol(SingleProductWishList student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SingleProductWishList.KEY_selected, student.selected);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(SingleProductWishList.TABLE, values, SingleProductWishList.KEY_ID + "= ?", new String[]{String.valueOf(student.product_ID)});
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap> getProductList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                SingleProductWishList.KEY_ID + "," +
                SingleProductWishList.KEY_image + "," +
                SingleProductWishList.KEY_title + "," +
                SingleProductWishList.KEY_price + "," +
                SingleProductWishList.KEY_selected +
                " FROM " + SingleProductWishList.TABLE;

        ArrayList<HashMap> productList = new ArrayList<HashMap>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap product = new HashMap();
                product.put("id", cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_ID)));
                product.put("imageUrl", cursor.getString(cursor.getColumnIndex(SingleProductWishList.KEY_image)));
                product.put("title", cursor.getString(cursor.getColumnIndex(SingleProductWishList.KEY_title)));
                product.put("price", cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_price)));
                product.put("selected", cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_selected)));
                productList.add(product);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }
    public ArrayList getIdsOfAllSelectedProduct()
    {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                SingleProductWishList.KEY_ID +/* "," +
                SingleProductWishList.KEY_image + "," +
                SingleProductWishList.KEY_title + "," +
                SingleProductWishList.KEY_price + "," +
                SingleProductWishList.KEY_selected +*/
                " FROM " + SingleProductWishList.TABLE;

        ArrayList productIds = new ArrayList();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
               /* HashMap product = new HashMap();
                product.put("id", cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_ID)));
                product.put("imageUrl", cursor.getString(cursor.getColumnIndex(SingleProductWishList.KEY_image)));
                product.put("title", cursor.getString(cursor.getColumnIndex(SingleProductWishList.KEY_title)));
                product.put("price", cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_price)));
                product.put("selected", cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_selected)));
                productIds.add(product);*/

                productIds.add(cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_ID)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productIds;
    }
    public SingleProductWishList getProductById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                SingleProductWishList.KEY_ID + "," +
                SingleProductWishList.KEY_image + "," +
                SingleProductWishList.KEY_title + "," +
                SingleProductWishList.KEY_price + "," +
                SingleProductWishList.KEY_selected +
                " FROM " + SingleProductWishList.TABLE
                + " WHERE " +
                SingleProductWishList.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        SingleProductWishList product = new SingleProductWishList();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                product.product_ID =cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_ID));
                product.imageurl  =cursor.getString(cursor.getColumnIndex(SingleProductWishList.KEY_image));
                product.title  =cursor.getString(cursor.getColumnIndex(SingleProductWishList.KEY_title));
                product.price =cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_price));
                product.selected =cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_selected));


            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return product;
    }

    public  int readSelectedCol(int Id) {
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String selectQuery = "SELECT  " +
               /* SingleProductWishList.KEY_ID + "," +
                SingleProductWishList.KEY_image + "," +
                SingleProductWishList.KEY_title + "," +
                SingleProductWishList.KEY_price + "," +*/
                    SingleProductWishList.KEY_selected +
                    " FROM " + SingleProductWishList.TABLE
                    + " WHERE " +
                    SingleProductWishList.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

            //int iCount =0;
            // SingleProductWishList product = new SingleProductWishList();

            int selected = 0;

            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

       /* if (cursor.moveToFirst()) {
            do {*/
             /*   product.product_ID =cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_ID));
                product.imageurl  =cursor.getString(cursor.getColumnIndex(SingleProductWishList.KEY_image));
                product.title  =cursor.getString(cursor.getColumnIndex(SingleProductWishList.KEY_title));
                product.price =cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_price));*/
            selected = cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_selected));


         /*   } while (cursor.moveToNext());
        }*/
            cursor.close();
            db.close();
            return selected;
        }
    }
    public int insertToCart(SingleProductCart cart)  {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SingleProductCart.KEY_ID, cart.product_ID);
        values.put(SingleProductCart.KEY_image, cart.imagebitmap);
        values.put(SingleProductCart.KEY_title, cart.title);
        values.put(SingleProductCart.KEY_price, cart.price);
        values.put(SingleProductCart.KEY_quatity, cart.quantity);
        values.put(SingleProductCart.KEY_totalamount, cart.total);
        // Inserting Row
        long product_Id = db.insert(SingleProductCart.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) product_Id;
    }
    public int deleteFromCart(int product_Id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        int affectedrowCount=db.delete(SingleProductCart.TABLE, SingleProductCart.KEY_ID + "= ?", new String[]{String.valueOf(product_Id)});
        db.close(); // Closing database connection
        return affectedrowCount;
    }

    public void updateCartItem(SingleProductCart cart) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SingleProductCart.KEY_quatity,cart.quantity);
        values.put(SingleProductCart.KEY_totalamount,cart.total);
        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(SingleProductCart.TABLE, values, SingleProductCart.KEY_ID + "= ?", new String[]{String.valueOf(cart.product_ID)});
        //it returns no(int) of row effected/updated;
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap> getProductListFromCart() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                SingleProductCart.KEY_ID + "," +
                SingleProductCart.KEY_image + "," +
                SingleProductCart.KEY_title + "," +
                SingleProductCart.KEY_price + "," +
                SingleProductCart.KEY_quatity + "," +
                SingleProductCart.KEY_totalamount +
                " FROM " + SingleProductCart.TABLE;

        ArrayList<HashMap> productList = new ArrayList<HashMap>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap product = new HashMap();
                product.put("id", cursor.getInt(cursor.getColumnIndex(SingleProductCart.KEY_ID)));
                product.put("imageBitmap", cursor.getBlob(cursor.getColumnIndex(SingleProductCart.KEY_image)));
                product.put("title", cursor.getString(cursor.getColumnIndex(SingleProductCart.KEY_title)));
                product.put("price", cursor.getString(cursor.getColumnIndex(SingleProductCart.KEY_price)));
                product.put("quantity", cursor.getInt(cursor.getColumnIndex(SingleProductCart.KEY_quatity)));
                product.put("total", cursor.getString(cursor.getColumnIndex(SingleProductCart.KEY_totalamount)));
                productList.add(product);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }

    public ArrayList<JSONObject> getIdsAndQtyOfAllProductFromCart() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                SingleProductCart.KEY_ID + "," +
                /*SingleProductCart.KEY_image + "," +
                SingleProductCart.KEY_title + "," +
                SingleProductCart.KEY_price + "," +*/
                SingleProductCart.KEY_quatity +/* "," +
                SingleProductCart.KEY_totalamount +*/
                " FROM " + SingleProductCart.TABLE;

        ArrayList<JSONObject> productList = new ArrayList<JSONObject>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap product = new HashMap();
                product.put("product_id", cursor.getInt(cursor.getColumnIndex(SingleProductCart.KEY_ID)));
                // product.put("imageBitmap", cursor.getBlob(cursor.getColumnIndex(SingleProductCart.KEY_image)));
                // product.put("title", cursor.getString(cursor.getColumnIndex(SingleProductCart.KEY_title)));
                // product.put("price", cursor.getString(cursor.getColumnIndex(SingleProductCart.KEY_price)));
                product.put("quantity", cursor.getInt(cursor.getColumnIndex(SingleProductCart.KEY_quatity)));
                //  product.put("total", cursor.getString(cursor.getColumnIndex(SingleProductCart.KEY_totalamount)));


                productList.add(new JSONObject(product));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }

    public ArrayList get_Ids_Price_Qty_OfAllProductInWish()
    {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                SingleProductCart.KEY_ID +/*"," +
                SingleProductCart.KEY_price + "," +
                SingleProductCart.KEY_quatity + "," +
               /* SingleProductWishList.KEY_price + "," +
                SingleProductWishList.KEY_selected +*/
                " FROM " + SingleProductCart.TABLE;

        ArrayList productIds = new ArrayList();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
               /* HashMap product = new HashMap();
                product.put("id", cursor.getInt(cursor.getColumnIndex(SingleProductCart.KEY_ID)));
                product.put("price", cursor.getString(cursor.getColumnIndex(SingleProductCart.KEY_price)));
                product.put("quantity", cursor.getInt(cursor.getColumnIndex(SingleProductCart.KEY_quatity)));
               *//* product.put("price", cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_price)));
                product.put("selected", cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_selected)));*//*
                productIds.add(product);*/

                productIds.add(cursor.getInt(cursor.getColumnIndex(SingleProductCart.KEY_ID)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productIds;
    }

    public SingleProductCart getProductByIdFromCart(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                SingleProductCart.KEY_ID + "," +
              /*  SingleProductCart.KEY_image + "," +
                SingleProductCart.KEY_title + "," +*/
                SingleProductCart.KEY_price + "," +
                SingleProductCart.KEY_quatity +
                " FROM " + SingleProductCart.TABLE
                + " WHERE " +
                SingleProductCart.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        SingleProductCart product = new SingleProductCart();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                product.product_ID =cursor.getInt(cursor.getColumnIndex(SingleProductCart.KEY_ID));
               /* product.imagebitmap  =cursor.getBlob(cursor.getColumnIndex(SingleProductCart.KEY_image));
                product.title  =cursor.getString(cursor.getColumnIndex(SingleProductCart.KEY_title));*/
                product.price =cursor.getString(cursor.getColumnIndex(SingleProductCart.KEY_price));
                product.quantity =cursor.getInt(cursor.getColumnIndex(SingleProductCart.KEY_quatity));


            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return product;
    }

    public ArrayList getTotalPriceOfEachProduct()
    {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                SingleProductCart.KEY_totalamount +/* "," +
                SingleProductWishList.KEY_image + "," +
                SingleProductWishList.KEY_title + "," +
                SingleProductWishList.KEY_price + "," +
                SingleProductWishList.KEY_selected +*/
                " FROM " + SingleProductCart.TABLE +" WHERE "+SingleProductCart.KEY_totalamount +" NOT NULL ";

        ArrayList<String> productIds = new ArrayList<String>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
               /* HashMap product = new HashMap();
                product.put("id", cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_ID)));
                product.put("imageUrl", cursor.getString(cursor.getColumnIndex(SingleProductWishList.KEY_image)));
                product.put("title", cursor.getString(cursor.getColumnIndex(SingleProductWishList.KEY_title)));
                product.put("price", cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_price)));
                product.put("selected", cursor.getInt(cursor.getColumnIndex(SingleProductWishList.KEY_selected)));
                productIds.add(product);*/

                productIds.add(cursor.getString(cursor.getColumnIndex(SingleProductCart.KEY_totalamount)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productIds;
    }

    public ArrayList getIdsOfAllCartProduct()
    {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                SingleProductCart.KEY_ID +
                " FROM " + SingleProductCart.TABLE;

        ArrayList productIds = new ArrayList();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                productIds.add(cursor.getInt(cursor.getColumnIndex(SingleProductCart.KEY_ID)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productIds;
    }
}
