package com.example.flappybird.Model

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.flappybird.R

class Obstaculo(res : Resources) {
        val ObsTop = BitmapFactory.decodeResource(res, R.drawable.ej_cot_top)
            get() = field
        val ObsBot = BitmapFactory.decodeResource(res, R.drawable.ej_cot_bottom)
            get() = field

    val w = ObsTop.width
        val h = ObsTop.height

        var x : Int = 0
            get() = field
            set(value) {
                field = value
            }

    var ccY : Int = 0
        get() = field
        set(value) {
            field = value
        }
    fun getTopY () : Int{
        return ccY - h
    }

    fun  getBotY () : Int {
        return ccY + 550
    }


}