package project.mc.dal.easyeatsapplication.sqlitePackage;

/**
 * Created by harika93 on 2017-11-11.
 */

public class SingleProductWishList {
    // Labels table name
    public static final String TABLE = "ProductInWishList";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_image = "imageurl";
    public static final String KEY_title = "title";
    public static final String KEY_price = "price";
    public static final String KEY_Description = "description";
    public static final String KEY_RESTAURANT = "restaurantName";
    public static final String KEY_selected = "selected";

    //Bitmap and discription(optional)

    // property help us to keep data
    public String product_ID;
    public String imageurl;
    public String title;
    public int price;
    public int selected;
    public String restaurantName;
    public String description;
}
