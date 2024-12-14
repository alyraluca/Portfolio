package com.example.myapplication3.calculadorakotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//Clase para manejar la parte de 'Sobre la la app' del menu
class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_dialog)

    }
}