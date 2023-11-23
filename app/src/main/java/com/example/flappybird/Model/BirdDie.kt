package com.example.flappybird.Model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.flappybird.R

class BirdDie (res : Resources) {
    var x : Int = 0
        get() = field
        set(value) {
            field = value
        }
    var y : Int = 0
        get() = field
        set(value) {
            field = value
        }
    var maxFrame = 7
    var currentFrame = 0
        get() = field
        set(value) {
            field = value
        }
    var dieArr : ArrayList<Bitmap>
    init {
        dieArr = arrayListOf()
        dieArr.add(BitmapFactory.decodeResource(res, R.drawable.exp_0))
        dieArr.add(BitmapFactory.decodeResource(res, R.drawable.exp_1))
        dieArr.add(BitmapFactory.decodeResource(res, R.drawable.exp_2))
        dieArr.add(BitmapFactory.decodeResource(res, R.drawable.exp_3))
        dieArr.add(BitmapFactory.decodeResource(res, R.drawable.exp_4))
        dieArr.add(BitmapFactory.decodeResource(res, R.drawable.exp_5))
        dieArr.add(BitmapFactory.decodeResource(res, R.drawable.exp_6))

        x = ScreenSize.SCREEN_WIDTH/4 - dieArr[0].width/2
        y = ScreenSize.SCREEN_HEIGHT/2 - dieArr[0].width/2
    }
    fun getBirdDie(i: Int) : Bitmap{
        return dieArr.get(i)
    }
}