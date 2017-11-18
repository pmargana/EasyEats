package project.mc.dal.easyeatsapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MyaccountActivity extends AppCompatActivity
{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserManager.db";
    private static final String TABLE_USER = "user";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_MOBILENUMBER = "user_mobilenumber";
    private static final String COLUMN_USER_LOCATION = "user_location";

    EditText fullnameed,emailed,mobileed;
    TextView nametx,emailtx;
    SharedPreferences loginpreference,registerpreference;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);
        loginpreference=getSharedPreferences(ConstantString.loginpreference,MODE_PRIVATE);
        registerpreference=getSharedPreferences(ConstantString.registerpreference,MODE_PRIVATE);
        if(loginpreference!=null);
        String username=loginpreference.getString("username",null);
        if(registerpreference!=null);
        String name=registerpreference.getString("name",null);
        String mnum=registerpreference.getString("mnum",null);
        nametx=(TextView)findViewById(R.id.name);
        nametx.setText(name);
        emailtx=(TextView)findViewById(R.id.Email);
        emailtx.setText(username);
        fullnameed=(EditText)findViewById(R.id.fullNameEdit);
        fullnameed.setText(name);
        emailed=(EditText)findViewById(R.id.userEmailId);
        emailed.setText(username);
        mobileed=(EditText)findViewById(R.id.mobileNumberEdit);
        mobileed.setText(mnum);
        setTitle(null);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setdetails()
    {

    }
}
