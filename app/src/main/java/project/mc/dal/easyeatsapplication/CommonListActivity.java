package project.mc.dal.easyeatsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.mc.dal.easyeatsapplication.sqlitePackage.DBAdapter;

/**
 * Created by harika93 on 2017-11-02.
 */

public class CommonListActivity extends AppCompatActivity {

    // private DrawerLayout mDrawerLayout;
    public static TextView wishListCountTv;
    public static int  wishListCount=0;
    public static boolean isWishListClicked=false;
    public static boolean backFromWishListActivity=false;
    public static TextView cartCountTv;
    public  static ArrayList cakeIds=new ArrayList();

    Intent myIntent;

    //Need to add onCreateOptionsMenu menu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        System.out.println("onCreate of HomeActivity");
        //setTitle("Shop by Cusine");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        getSupportActionBar().setTitle("Shop by Cusine");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
    }
    private void setupViewPager(ViewPager viewPager) {

        myIntent=getIntent();
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new IndianFoodActivity(), "Indian Food");
      /*  adapter.addFragment(new FlowerArrangementsFragment(), "Flower Arrangements");
        adapter.addFragment(new ExoticFlowerFragment(), "Exotic Flowers");
        adapter.addFragment(new ReadyCombosFragment(), "Ready Combos");
        adapter.addFragment(new CakeFragment(), "Cakes");
        adapter.addFragment(new ChocolateFragment(), "Chocolates");
        adapter.addFragment(new PlantFragment(), "Plants");*/
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(myIntent.getIntExtra("index",0));
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_other_pages, menu);

        RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(R.id.wish_list_with_badge).getActionView();
        wishListCountTv = (TextView) badgeLayout.findViewById(R.id.actionbar_notifcation_textview);

        DBAdapter adapter = new DBAdapter(this);
        List wishListProductIds=adapter.getIdsOfAllSelectedProduct();
        CommonListActivity.wishListCount= wishListProductIds.size();

        wishListCountTv.setText(String.valueOf(CommonListActivity.wishListCount));
        MenuItemCompat.getActionView(menu.findItem(R.id.wish_list_with_badge)).findViewById(R.id.icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // System.out.println("wishListTv clicked");
                isWishListClicked = true;
                // Toast.makeText(ProductActivity.this, "Heart Clicked onCreateOptionsMenu", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CommonListActivity.this, MywishlistActivity.class);
                startActivity(intent);
            }
        });


        RelativeLayout badgeLayout_cart = (RelativeLayout) menu.findItem(R.id.cart_with_badge).getActionView();
        cartCountTv = (TextView) badgeLayout_cart.findViewById(R.id.actionbar_notifcation_textview);
        int itemCountinCart=adapter.getIdsOfAllCartProduct().size();
        cartCountTv.setText(String.valueOf(itemCountinCart));
        MenuItemCompat.getActionView(menu.findItem(R.id.cart_with_badge)).findViewById(R.id.icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // System.out.println("cartTv clicked");
                //Toast.makeText(ProductActivity.this, "cart Clicked onCreateOptionsMenu", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CommonListActivity.this, MycartActivity.class);
                startActivity(intent);

            }
        });
                return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("onStart of HomeActivity");
    }
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume of HomeActivity");
        //fetching cart data
        DBAdapter adapter1 = new DBAdapter(CommonListActivity.this);
/*        int itemCountinCart=adapter1.getIdsOfAllCartProduct().size();
        if(cartCountTv!=null)
            cartCountTv.setText(String.valueOf(itemCountinCart));*/

    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("onPause of HomeActivity");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("onStop of HomeActivity");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy of HomeActivity");
    }

}