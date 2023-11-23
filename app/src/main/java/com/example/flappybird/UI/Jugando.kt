package com.example.flappybird.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Jugando : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jugar = Jugar(this)
        setContentView(jugar)
    }
}