package com.example.inmocasas

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.beust.klaxon.Klaxon
import com.example.inmocasas.dto.Usuario
import com.example.inmocasas.services.http.HttpUsuario
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import com.example.inmocasas.NavegacionUsuario.UsuarioMainActivity

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
            Toast.makeText(this, "El usuario ingresado no existe", Toast.LENGTH_SHORT).show()
        }
        else{
            var usuario:Usuario? = jsonParse(response)
            if (usuario != null){
                currentUser = usuario
                Log.i("User","User: ${currentUser?.nombre}")
                rolValidate(currentUser)
                irAMain(rolUser)


            }
            else{
                Log.i("Error Parse","errorP Parseee del Logiiin!!!")
                Toast.makeText(this, "El usuario ingresado no existe", Toast.LENGTH_SHORT).show()
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


    fun rolValidate(usuario:Usuario?){
        if(usuario!=null){

            Log.i("DENTRO DEL ROL","USUARIO: ${usuario.nombre}")

            if(usuario.rolesDeUsuario.isNotEmpty()){
              var  rol:Int? = usuario?.rolesDeUsuario?.component1()?.fkRol
                when(rol){
                    1 ->{rolUser = "administrador"}
                    2 ->{rolUser = "usuario"}
                    else ->{rolUser = null}
                }
                Log.i("El usuario es ","El usuario es: ${rolUser}")

            }
            else{
                Log.i("ROLES NO ","Usuario sin roles")
            }

        }

    }
    fun irAMain(rol:String?){
        if(rol == "usuario"){
            irAUsuarioMainActivity()
        }
        else if (rol == "administrador"){
            irAAdministradorMainActivity()
        }else{
            Log.i("Error en el rol","error en el rol")
        }
    }

    fun irAAdministradorMainActivity(){
        val intentExplicito = Intent(
            this,
            AdministradorMainActivity::class.java
        )

        startActivity(intentExplicito)

    }
    fun irAUsuarioMainActivity(){
        val intentExplicito = Intent(
            this,
            UsuarioMainActivity::class.java
        )

        startActivity(intentExplicito)

    }


    companion object {

        var currentUser:Usuario?  = null
        var rolUser:String? = null

    }

}









