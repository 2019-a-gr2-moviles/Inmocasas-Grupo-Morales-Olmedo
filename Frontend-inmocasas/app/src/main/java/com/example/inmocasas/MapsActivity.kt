package com.example.inmocasas
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.location.Address
import android.location.Geocoder
import android.os.Build
//import android.support.v4.app.FragmentActivity
import androidx.appcompat.app.AppCompatActivity

import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.location.LocationServices
import android.location.Location
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
//import android.support.v4.content.ContextCompat
import androidx.core.content.ContextCompat
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.IOException


class MapsActivity(): AppCompatActivity(), OnMapReadyCallback, LocationListener,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private var mMap: GoogleMap? = null
    internal lateinit var mLastLocation: Location
    internal var mCurrLocationMarker: Marker? = null
    internal var mGoogleApiClient: GoogleApiClient? = null
    internal lateinit var mLocationRequest: LocationRequest
    private var tienePermisosLocalizacion = false
    protected var latitudSeleccionada:Double = 0.0
    protected var longitudSeleccionada:Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btn_hecho.setOnClickListener {
            devolverRespuesta()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        solicitarPermisosLocalizacion()

        val foch = LatLng(-0.202760,-78.490813)
        val titulo = "Plaza Foch"
        val zoom = 17f
        aniadirMarcador(foch,titulo)
        moverCamaraConZoom(foch,zoom)
        mMap!!.uiSettings.isZoomControlsEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient()
                mMap!!.isMyLocationEnabled = true

            }
        } else {
            buildGoogleApiClient()
            mMap!!.isMyLocationEnabled = true
        }

    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API).build()
        mGoogleApiClient!!.connect()
    }

    override fun onConnected(bundle: Bundle?) {

        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 1000
        mLocationRequest.fastestInterval = 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(this)
        }
    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onLocationChanged(location: Location) {

        mLastLocation = location
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker!!.remove()
        }
        //Place current location marker
        val latLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("Current Position")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        mCurrLocationMarker = mMap!!.addMarker(markerOptions)

        //move map camera
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.getFusedLocationProviderClient(this)
        }

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    fun searchLocation(view: View) {
        val locationSearch:EditText = findViewById<EditText>(R.id.editTextMap)
        lateinit var location: String
        location = locationSearch.text.toString()
        var addressList: List<Address>? = null

        if (location == null || location == "") {
            Toast.makeText(applicationContext,"ingrese una busqueda",Toast.LENGTH_SHORT).show()
        }
        else{
            val geoCoder = Geocoder(this)
            try {
                addressList = geoCoder.getFromLocationName(location, 1)

            } catch (e: IOException) {
               // e.printStackTrace()
                Toast.makeText(applicationContext, "NO SE ENCONTRO LA UBICACIÓN", Toast.LENGTH_LONG).show()
            }
            if(addressList != null){
                val address = addressList!![0]
                val latLng = LatLng(address.latitude, address.longitude)
                mMap!!.addMarker(MarkerOptions().position(latLng).title(location))
                mMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                Toast.makeText(applicationContext, address.latitude.toString() + " " + address.longitude, Toast.LENGTH_LONG).show()
                this.latitudSeleccionada = address.latitude
                this.longitudSeleccionada = address.longitude
            }
            else{
                Toast.makeText(applicationContext, "NO SE ENCONTRO LA UBICACIÓN", Toast.LENGTH_LONG).show()

            }



        }
    }

    fun solicitarPermisosLocalizacion(){
        val permisoFineLocation = ContextCompat
            .checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        val tienePermiso = permisoFineLocation == PackageManager.PERMISSION_GRANTED

        if(tienePermiso){
            Log.i("mapa","Tiene permisos de FINE_LOCATION")
            this.tienePermisosLocalizacion = true
        }else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1  // Codigo que vamos a esperar
            )
        }


    }

    fun aniadirMarcador(latLng: LatLng, title:String){
        mMap!!.addMarker(MarkerOptions().position(latLng).title(title))
    }
    fun moverCamaraConZoom(latLng:LatLng, zoom: Float = 10f){
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom))
    }

    fun devolverRespuesta(){

        val intentRespuesta = Intent()

        intentRespuesta.putExtra("longitud",this.longitudSeleccionada)
        intentRespuesta.putExtra("latitud",this.latitudSeleccionada)

        this.setResult(
            RESULT_OK,intentRespuesta //Podemos Result_Ok o Result_canceled
        )
        this.finish()
    }
}