package project.mc.dal.easyeatsapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import project.mc.dal.easyeatsapplication.sqlitePackage.DBAdapter;

public class MywishlistActivity extends AppCompatActivity
{
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    LinearLayoutManager llm;
    RecyclerView.Adapter mAdapter;
    RecyclerView.Adapter mListAdapter;
    String subCat_products_url;
    List<HashMap> dataSet = new ArrayList<HashMap>();
    boolean isGridDisplaying =true;
    ProgressBar progressBar;
    int sortByCurrentItem=0;
    AlertDialog alert=null;
    ImageButton gridOrListBtn,sortBtn;
    private Toolbar toolbar; // Declaring the Toolbar Object

    public static TextView wishListCountTv;
    public  static int  wishListCount=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywishlist);
        setTitle(null);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        gridOrListBtn=(ImageButton)findViewById(R.id.gridListButton);
       // sortBtn=(ImageButton)findViewById(R.id.shortButton);
        sortBtn.setVisibility(View.GONE);

        // Calling the RecyclerView
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        //Gridview arrangements
        // The number of Columns
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);


      /*  //Handling Click listner
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(ProductActivity.this,"Normal click on Pos"+position,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(ProductActivity.this,"Long click on Pos"+position,Toast.LENGTH_LONG).show();
            }
        }));


*/
       /* Intent intent=getIntent();
        String selectedProductSlug=intent.getStringExtra("slug");
        subCat_products_url="http://myflowershoppe.com/wc-api/v2/products?consumer_key=ck_645b0b0c9cda4fd434d434a5550512b2&consumer_secret=cs_5d027855c7c716fb07fdbe7be9a4121e&filter[product_cat]="+selectedProductSlug;
        checkInternetAndStartService();
*/
        DBAdapter adapter = new DBAdapter(this);
        dataSet=adapter.getProductList();

        if(dataSet.size()>0) {
            mAdapter = new GridAdapter(MywishlistActivity.this, dataSet);
            mRecyclerView.setAdapter(mAdapter);
            gridOrListBtn.setVisibility(View.VISIBLE);
            sortBtn.setVisibility(View.VISIBLE);
            gridOrListBtn.setEnabled(true);
            sortBtn.setEnabled(true);
        }
        else
        {
            Toast.makeText(MywishlistActivity.this,"No product(s) in the wishList.",Toast.LENGTH_LONG).show();
        }
        // setProgressBarIndeterminateVisibility(false);
        progressBar.setVisibility(View.GONE);

    }
    protected void onResume() {
        super.onResume();
        CommonListActivity.isWishListClicked = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wishlist, menu);
    /*    getMenuInflater().inflate(R.menu.menu_main, menu);

        RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(R.id.wish_list_with_badge).getActionView();
        wishListCountTv = (TextView) badgeLayout.findViewById(R.id.actionbar_notifcation_textview);

        DBAdapter adapter = new DBAdapter(this);
        List wishListProductIds=adapter.getIdsOfAllSelectedProduct();
        ProductActivity.wishListCount= wishListProductIds.size();

        wishListCountTv.setText(String.valueOf(ProductActivity.wishListCount));
        MenuItemCompat.getActionView(menu.findItem(R.id.wish_list_with_badge)).findViewById(R.id.icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked");
                Toast.makeText(WishListActivity.this, "Heart Clicked onCreateOptionsMenu", Toast.LENGTH_SHORT).show();

            }
        });


        RelativeLayout badgeLayout_cart = (RelativeLayout) menu.findItem(R.id.cart_with_badge).getActionView();
        TextView tv1 = (TextView) badgeLayout_cart.findViewById(R.id.actionbar_notifcation_textview);
        tv1.setText("99");
        MenuItemCompat.getActionView(menu.findItem(R.id.cart_with_badge)).findViewById(R.id.icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked");
                Toast.makeText(WishListActivity.this, "cart Clicked onCreateOptionsMenu", Toast.LENGTH_SHORT).show();

            }
        });*/

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id==android.R.id.home)
        {
          //  setVariable();
            this.finish();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.wish_list_with_badge) {
            Toast.makeText(MywishlistActivity.this, "Heart Clicked onOptionsItemSelected", Toast.LENGTH_SHORT).show();


            return true;
        }

        if (id == R.id.cart_with_badge) {
            Toast.makeText(MywishlistActivity.this, "Cart Clicked onOptionsItemSelected", Toast.LENGTH_SHORT).show();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeArrangement(View v)
    {
        if(isGridDisplaying) {
            isGridDisplaying=false;
            llm = new LinearLayoutManager(this);
            // use a linear layout manager to show items like listview
            gridOrListBtn.setBackgroundResource(R.drawable.list_red_light);
            mRecyclerView.setLayoutManager(llm);
            mListAdapter = new ListAdapterClass(MywishlistActivity.this, dataSet);
            mRecyclerView.setAdapter(mListAdapter);
        }
        else
        {
            isGridDisplaying=true;
            mLayoutManager = new GridLayoutManager(this, 2);
            gridOrListBtn.setBackgroundResource(R.drawable.grid_red_light);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new GridAdapter(MywishlistActivity.this, dataSet);
            mRecyclerView.setAdapter(mAdapter);

        }

    }

//    public void performShorting(View v)
//    {
//
//        final CharSequence[] items = {"Default shorting", "Short by popularity", "Short by average rating","Short by newness","Short By Price:low to high","Short by price:high to low"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Short By:");
//        builder.setSingleChoiceItems(items, sortByCurrentItem, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int item) {
//                // Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
//
//                if (items[item].equals("Default shorting")) {
//                    Toast.makeText(getApplicationContext(), "Work under progress", Toast.LENGTH_SHORT).show();
//                    sortByCurrentItem=0;
//                    alert.dismiss();
//                } else if (items[item].equals("Short by popularity"))//Italiano
//                {
//                    Toast.makeText(getApplicationContext(), "Work under progress", Toast.LENGTH_SHORT).show();
//                    sortByCurrentItem=1;
//                    alert.dismiss();
//                } else if (items[item].equals("Short by average rating")) {
//                    Toast.makeText(getApplicationContext(), "Work under progress", Toast.LENGTH_SHORT).show();
//                    sortByCurrentItem=2;
//                    alert.dismiss();
//                } else if (items[item].equals("Short by newness")) {
//                    Toast.makeText(getApplicationContext(), "Work under progress", Toast.LENGTH_SHORT).show();
//                    sortByCurrentItem=3;
//                    alert.dismiss();
//                } else if (items[item].equals("Short By Price:low to high")) {
//                    Toast.makeText(getApplicationContext(), "Work under progress", Toast.LENGTH_SHORT).show();
//                    sortByCurrentItem=4;
//                    alert.dismiss();
//                } else if (items[item].equals("Short by price:high to low")) {
//                    Toast.makeText(getApplicationContext(), "Work under progress", Toast.LENGTH_SHORT).show();
//                    sortByCurrentItem=5;
//                    alert.dismiss();
//
//                }
//
//
//            }
//        });
//        alert = builder.create();
//        alert.show();
//
//
//    }

    //@Override
//    public void onBackPressed() {
//        setVariable();
//    }
//    public void setVariable(){
//        .isWishListClicked=false;
//        ProductActivity.backFromWishListActivity=true;
//        if(ProductActivity.isGridDisplaying)
//        {
//            ProductActivity.isGridDisplaying=false;
//        }
//        else
//        {
//            ProductActivity.isGridDisplaying=true;
//        }
//        super.onBackPressed();
//
//    }
}
