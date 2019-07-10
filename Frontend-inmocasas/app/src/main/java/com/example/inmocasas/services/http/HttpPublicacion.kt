package com.example.inmocasas.services.http

import android.util.Log
import com.example.inmocasas.dto.Publicacion


class HttpPublicacion(url:String, modelo:String):HttpEntity<Publicacion>(url,modelo) {

    init {
        Log.i("Saludo","hola soy una Empresa con url ${url}${modelo}")
    }




}