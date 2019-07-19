package com.example.inmocasas.NavegacionUsuario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inmocasas.R
import kotlinx.android.synthetic.main.activity_usuario_main.*

class UsuarioMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_main)
        img_publicaciones.setImageResource(R.drawable.c2)

btn_publicaiones.setOnClickListener {
    irAPublicacionesActivity()
}
        btn_publicar.setOnClickListener {
            irAPublicarActivity()
        }
        btn_buscar.setOnClickListener {
            irAVozActivity()
        }
        btn_eliminar.setOnClickListener {
            irAEliminarActivity()
        }

    }
    fun irAEliminarActivity(){
        val intentExplicito = Intent(
            this,
            EliminarPublicacionActivity::class.java
        )

        startActivity(intentExplicito)
    }
    fun irAVozActivity(){
        val intentExplicito = Intent(
            this,
            VozActivity::class.java
        )

        startActivity(intentExplicito)

    }
    fun irAPublicacionesActivity(){

        val intentExplicito = Intent(
            this,
            PublicacionesActivity::class.java
        )

        startActivity(intentExplicito)


    }
    fun irAPublicarActivity(){

        val intentExplicito = Intent(
            this,
            PublicarActivity::class.java
        )

        startActivity(intentExplicito)


    }


}
