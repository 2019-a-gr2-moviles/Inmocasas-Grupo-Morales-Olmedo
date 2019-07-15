package com.example.inmocasas.services.http

import android.util.Log
import com.example.inmocasas.dto.Inmueble

class HttpInmueble(url:String, modelo:String):HttpEntity<Inmueble>(url,modelo) {

    init {
        Log.i("Saludo","hola soy un Inmueble con url ${url}${modelo}")
    }




}