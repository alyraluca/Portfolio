package com.example.myapplication3.calculadorakotlin

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class HowToUseVideoActivity : AppCompatActivity() {
    //Clase para manejar la parte de 'Como utilizar la app' del menu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to_use_video)

        val videoView: VideoView = findViewById(R.id.videoView)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.stock_video
        val uri = Uri.parse(videoPath)

        // Crear un controlador multimedia para habilitar reproducción, pausa, etc.
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)

        // Establecer el controlador multimedia en la vista de video
        videoView.setMediaController(mediaController)


        // Establecer la URI del video en la vista de video
        videoView.setVideoURI(uri)

        // Iniciar la reproducción del video
        videoView.start()
    }
}