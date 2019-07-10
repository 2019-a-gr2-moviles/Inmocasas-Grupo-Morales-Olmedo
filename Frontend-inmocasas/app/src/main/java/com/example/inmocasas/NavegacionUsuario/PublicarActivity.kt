package com.example.inmocasas.NavegacionUsuario

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.inmocasas.R
import kotlinx.android.synthetic.main.activity_publicar.*

class PublicarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicar)
        img_publicar.setImageResource(R.drawable.c1)
    }
}
