package com.example.inmocasas

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.beust.klaxon.Klaxon
import com.example.inmocasas.dto.RolUsuario
import com.example.inmocasas.dto.Usuario
import com.example.inmocasas.services.http.HttpUsuario
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var url = "http://192.168.56.1:1337/"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_sign_in.setOnClickListener {
            login()
        }

    }

    fun login(){
        //http://localhost:1337/Usuario?email=nika@hotmail.com&contrasenia=12345678
        var usuario:HttpUsuario = HttpUsuario(url,"Usuario")
        var response = usuario.getByQuery("email=${edit_email.text}&contrasenia=${edit_password.text}")
        Log.i("login","El uSER ES ${response}")
        if(response == "[]"){
            Log.i("ERROR","ERRORRRR")
        }
        else{
            var usuario:Usuario? = jsonParse(response)
            if (usuario != null){
                Log.i("User","User: ${usuario.nombre}")
            }
            else{
                Log.i("Error Parse","errorP Parseee!!!")
            }


        }


    }

    fun jsonParse(data:String):Usuario?{
        try{
            val usuarioParseado = Klaxon()
                .parseArray<Usuario>(data)

            return usuarioParseado?.component1()

        }
        catch(err: Exception){
            Log.i("Exception","Exception")

        }

        return null
    }










}








