package com.example.inmocasas.services.http

import android.util.Log
import com.example.inmocasas.dto.Usuario

class HttpUsuario(url:String, modelo:String):HttpEntity<Usuario>(url,modelo) {

    init {
        Log.i("Saludo","hola soy un Usuario con url ${url}${modelo}")
    }




}