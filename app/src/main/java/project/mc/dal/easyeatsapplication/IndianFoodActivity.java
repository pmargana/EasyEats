package project.mc.dal.easyeatsapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import project.mc.dal.easyeatsapplication.CommonListActivity;
import project.mc.dal.easyeatsapplication.GridAdapter;
import project.mc.dal.easyeatsapplication.ListAdapterClass;
import project.mc.dal.easyeatsapplication.R;
import project.mc.dal.easyeatsapplication.Services.GetService;


/**
 * Created by harika93 on 2017-11-02.
 */

public class IndianFoodActivity extends Fragment {

    String subCat_products_url="https://jsonblob.com/api/jsonBlob/527c4106-c708-11e7-b06a-99db826ae8c6";
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    LinearLayoutManager llm;
    RecyclerView.Adapter mAdapter;
    RecyclerView.Adapter mListAdapter;
    ArrayList<HashMap> cat_subCat_Products_List = new ArrayList<HashMap>();
    public static boolean isGridDisplaying =true;
    //ProgressBar progressBar;
    int sortByCurrentItem=0;
    AlertDialog alert=null;
    ImageButton gridOrListBtn,sortBtn;
    private Toolbar toolbar; // Declaring the Toolbar Object

    public static TextView wishListCountTv;
    public  static int  wishListCount=0;
    public static boolean isWishListClicked=false;
    public static boolean backFromWishListActivity=false;
    public static TextView cartCountTv;

    public IndianFoodActivity() {
        // Required empty public constructor
        setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("onCreateView of cake");
        View view= inflater.inflate(R.layout.common_list_layout, container, false);
       // progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
        gridOrListBtn=(ImageButton)view.findViewById(R.id.gridListButton);

        gridOrListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeArrangement();
            }
        });

        // Calling the RecyclerView
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        //Gridview arrangements
        // The number of Columns
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        CommonListActivity.isWishListClicked = false;
        System.out.println("onResume of cake");
        Bundle mySavedInstanceState = getArguments();
        ArrayList<HashMap> exitingCakeData = (ArrayList<HashMap>)mySavedInstanceState.getSerializable("cakeData");
        if(exitingCakeData!=null && exitingCakeData.size()>0)
        {
            isGridDisplaying = true;
            gridOrListBtn.setBackgroundResource(R.drawable.grid_red_light);
            mLayoutManager = new GridLayoutManager(getActivity(), 2);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new GridAdapter(getActivity(), cat_subCat_Products_List);
            mRecyclerView.setAdapter(mAdapter);
            gridOrListBtn.setVisibility(View.VISIBLE);
            //sortBtn.setVisibility(View.VISIBLE);
            gridOrListBtn.setEnabled(true);
           // sortBtn.setEnabled(true);
           // progressBar.setVisibility(View.GONE);
            System.out.println("exitingCakeData"+exitingCakeData);
        }else {
            checkInternetAndStartService();
            System.out.println("else checkInternetAndStartService");
        }
    }

    public void checkInternetAndStartService()
    {

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            startService();

        } else {
            // display error
            getActivity().setProgressBarIndeterminateVisibility(false);
            AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
            ab.setTitle("No Internet!!");
            ab.setMessage("No Internet Connection Available.Do you want to try again?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }
    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    checkInternetAndStartService();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    //finish();
                    dialog.dismiss();
                    break;
            }
        }
    };

    public void startService()
    {
        new AsyncHttpTask().execute(subCat_products_url);
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params)
        {
            String response=null;
            GetService getServiceObj = new GetService();
            response=getServiceObj.getResponseGET(params[0]);
            return response;
        }


        @Override
        protected void onPostExecute(String response) {
            /* Download complete. Lets update UI */

            if(response != null)
            {
                // System.out.println("Response=" + response);

                try{
                    cat_subCat_Products_List.clear();
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray cat_SubCat_Products = jsonResponse.getJSONArray("products");
                    for(int i=0; i< cat_SubCat_Products.length();i++ ){
                        HashMap singleProduct= new HashMap();
                        JSONObject cat_SubCat_Product = cat_SubCat_Products.optJSONObject(i);
                        String title = cat_SubCat_Product.getString("title");
                        int id = cat_SubCat_Product.getInt("id");
                        int price = cat_SubCat_Product.getInt("price");
                        String imageUrl = cat_SubCat_Product.getString("imageUrl");
                        System.out.println("title"+title);
                        System.out.println("ID:"+id);
                        singleProduct.put("title", title);
                        singleProduct.put("id", id);
                        singleProduct.put("price",price);
                        singleProduct.put("imageUrl", imageUrl);
                        cat_subCat_Products_List.add(singleProduct);
                    }
                    if(cat_subCat_Products_List.size()>0) {
                        if(getActivity()!=null) {
                            System.out.println("Inside if of getActivity");
                            mAdapter = new GridAdapter(getActivity(), cat_subCat_Products_List);
                            mRecyclerView.setAdapter(mAdapter);
                            gridOrListBtn.setVisibility(View.VISIBLE);
//                            sortBtn.setVisibility(View.VISIBLE);
                            gridOrListBtn.setEnabled(true);
//                            sortBtn.setEnabled(true);
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Opps!!!Product(s) out of stock.", Toast.LENGTH_LONG).show();
                    }
                    // setProgressBarIndeterminateVisibility(false);
                   // progressBar.setVisibility(View.GONE);


                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

        }
    }

    public void changeArrangement() {
        if (isGridDisplaying) {
            isGridDisplaying = false;
            llm = new LinearLayoutManager(getActivity());
            // use a linear layout manager to show items like listview
           gridOrListBtn.setBackgroundResource(R.drawable.list_red_light);
            mRecyclerView.setLayoutManager(llm);
            mListAdapter = new ListAdapterClass(getActivity(), cat_subCat_Products_List);
            mRecyclerView.setAdapter(mListAdapter);
        } else {
            isGridDisplaying = true;
            gridOrListBtn.setBackgroundResource(R.drawable.grid_red_light);
            mLayoutManager = new GridLayoutManager(getActivity(), 2);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new GridAdapter(getActivity(), cat_subCat_Products_List);
            mRecyclerView.setAdapter(mAdapter);

        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("onAttach of Cake");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate of Cake");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated of Cake");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("onStart of Cake");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("onPause of Cake");
        getArguments().putSerializable("cakeData", cat_subCat_Products_List);
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("onStop of Cake");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        System.out.println("onDestroyView of Cake");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy of Cake");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("onDetach of Cake");
    }

}
