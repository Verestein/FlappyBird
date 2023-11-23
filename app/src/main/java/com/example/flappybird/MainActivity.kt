package com.example.flappybird


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.flappybird.Model.ScreenSize
import com.example.flappybird.UI.Jugando

class MainActivity : AppCompatActivity() {
    val Tag = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ScreenSize.getScreenSize(this)

        val inicio : ImageButton = findViewById(R.id.btnPlay)

        inicio.setOnClickListener{
            val iPlayGame = Intent(this@MainActivity,Jugando::class.java)
            startActivity(iPlayGame)
            finish()
            Log.d(Tag,"Boton jugar presionado")

        }


    }
}