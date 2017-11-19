package project.mc.dal.easyeatsapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NearbyRestaurantsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_restaurants);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        mMap.setMyLocationEnabled(true);

        // Add a marker in Taste of India
        LatLng ToI = new LatLng(44.6501095, -63.5765253);
        mMap.addMarker(new MarkerOptions().position(ToI).title("Taste of India"));


        // Add a marker in Freeman's Little New York
        LatLng FLNY = new LatLng(44.6463508, -63.5951649);
        mMap.addMarker(new MarkerOptions().position(FLNY).title("Freeman's Little New York"));

        // Add a marker on Mirchi Tandoor
        LatLng MirchiTandoor = new LatLng(44.6449058, -63.5761106);
        mMap.addMarker(new MarkerOptions().position(MirchiTandoor).title("Mirchi Tandoor"));

        // Add a marker on Blue Olive Greek Taverna
        LatLng BlueOlive = new LatLng(44.6457226, -63.5956364);
        mMap.addMarker(new MarkerOptions().position(BlueOlive).title("Blue Olive"));

        // Add a marker on Pete's ToGoGo
        LatLng ToGoGo = new LatLng(44.6369563, -63.5911345);
        mMap.addMarker(new MarkerOptions().position(ToGoGo).title("Pete's ToGoGo"));

        // Add a marker on Tawa Grill
        LatLng TawaGrill = new LatLng(44.6444525, -63.572487);
        mMap.addMarker(new MarkerOptions().position(TawaGrill).title("Tawa Grill"));

        // Add a marker on Dhaba Express
        LatLng DhabaEx = new LatLng(44.6470817, -63.6777885);
        mMap.addMarker(new MarkerOptions().position(DhabaEx).title("Dhaba Express"));

        CameraPosition cameraPosition = CameraPosition.builder().target(ToGoGo).zoom(14).bearing(0).tilt(0).build();


        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}
