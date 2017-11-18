package project.mc.dal.easyeatsapplication.sqlitePackage;

/**
 * Created by harika93 on 2017-11-11.
 */

public class SingleProductCart {
    // Labels table name
    public static final String TABLE = "ProductInCart";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_image = "imageurl";
    public static final String KEY_title = "title";
    public static final String KEY_price = "price";
    public static final String KEY_quatity = "quantity";
    public static final String KEY_RESTAURANT = "restaurantName";
    public static final String KEY_totalamount="total";


    //Bitmap and discription(optional)

    // property help us to keep data
    public int product_ID;
    public byte[] imagebitmap;
    public String title;
    public String price;
    public int quantity;
    public String total;
    public String restaurantName;
}
