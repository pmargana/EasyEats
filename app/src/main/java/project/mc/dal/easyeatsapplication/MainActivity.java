package project.mc.dal.easyeatsapplication;

import android.content.Context;
import android.content.Intent;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper mViewFlipper;
    private Animation.AnimationListener mAnimationListener;
    private Context mContext;
    private static int swipe_count = 0;
    @SuppressWarnings("deprecation")
    private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());
    ImageView indian,italian,mexican,chinese,american,local,beverages;
    ImageView starters,soups,salads,maincourse,deserts;
    Toolbar toolbar;
    TextView callTv, emailTv, userName;
    private DrawerLayout mDrawerLayout;

    //Mobileservices variable -- ESK
    private MobileServiceClient mClient;
    private MobileServiceTable<TodoItem> mTodoTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        mContext = this;
        mViewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        mViewFlipper.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(final View view, final MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });

        //Now added
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(4000);
        mViewFlipper.startFlipping();

        //animation listener
        mAnimationListener = new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                //animation started event
                System.out.print("onAnimationStart");
            }

            public void onAnimationRepeat(Animation animation) {
                System.out.print("onAnimationRepeat");
            }

            public void onAnimationEnd(Animation animation) {
                //TODO animation stopped event
                System.out.print("onAnimationEnd");
            }
        };

        init();

        try {
            mClient = new MobileServiceClient(
                    "https://eazyeats.azurewebsites.net",
                    this
            );
            Log.e("Success", "Connection passes");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        final TodoItem item = new TodoItem();
        item.Text = "Awesome item";
          //  mClient.getTable(Category.class).execute("select * from ")
//        mClient.getTable(TodoItem.class).insert(item, new TableOperationCallback<item>()
//        {
//            public void onCompleted(TodoItem entity, Exception exception, ServiceFilterResponse response)
//            {
//                if (exception == null) {
//                    // Insert succeeded
//                } else {
//                    // Insert failed
//                }
//            }
//        });


//        Log.e("output", mClient.getTable(TodoItem.class).execute("Select * from TodoItem"));

    }


    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    swipe_count = swipe_count + 1;
                    //  mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
                    // mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
                    // controlling animation
                    mViewFlipper.getInAnimation().setAnimationListener(mAnimationListener);
                    mViewFlipper.showNext();
                  /*  if(swipe_count==3) {
                        swipe_count=0;
                        goTOHomeActivity();
                        return false;
                    }
                    else {
                        return true;
                    }*/
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    if (swipe_count > 0) {
                        swipe_count = swipe_count - 1;
//                        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
                        //                      mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,R.anim.right_out));
                        // controlling animation
                        mViewFlipper.getInAnimation().setAnimationListener(mAnimationListener);
                        mViewFlipper.showPrevious();
                        return true;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//       toolbar.setNavigationIcon(R.drawable.back_arrow_a7);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        italian=(ImageView)findViewById(R.id.italian);
        italian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        indian=(ImageView)findViewById(R.id.indian);
        indian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        american=(ImageView)findViewById(R.id.american);
        american.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        local=(ImageView)findViewById(R.id.local);
        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        chinese=(ImageView)findViewById(R.id.chinese);
        chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mexican=(ImageView)findViewById(R.id.mexican);
        mexican.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        beverages=(ImageView)findViewById(R.id.beverages);
        beverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        starters=(ImageView)findViewById(R.id.starters);
        starters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        soups=(ImageView)findViewById(R.id.soups);
        soups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        salads=(ImageView)findViewById(R.id.salads);
        salads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        deserts=(ImageView)findViewById(R.id.deserts);
        deserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        maincourse=(ImageView)findViewById(R.id.maincourse);
        maincourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.help) {
            Intent i = new Intent(this, HelpActivity.class);
            startActivity(i);
        } else if (id == R.id.call) {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" +"9966820378"));
            startActivity(dialIntent);
        } else if (id == R.id.logout) {

        }
        else if (id == R.id.login) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
        else if (id == R.id.myaccount) {
            Intent i = new Intent(this, MyaccountActivity.class);
            startActivity(i);
        } else if (id == R.id.basket) {
            Intent i = new Intent(this, MybasketActivity.class);
            startActivity(i);
        }
        else if (id == R.id.wishlist) {
      Intent i = new Intent(this, MywishlistActivity.class);
            startActivity(i);

        }
        else if (id == R.id.restaurants) {
            Intent i = new Intent(this, RestaurantsActivity.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
