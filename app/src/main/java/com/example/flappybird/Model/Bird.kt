package com.example.flappybird.Model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.flappybird.R


class Bird (res : Resources){
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
    val maxFrame : Int =3
    var currentFrame : Int = 0
        get() = field
        set(value) {
            field = value
        }
    var birdList : ArrayList<Bitmap>
            init {
                birdList = arrayListOf()
                birdList.add(BitmapFactory.decodeResource(res, R.drawable.bluebird_downflap))
                birdList.add(BitmapFactory.decodeResource(res, R.drawable.bluebird_midflap))
                birdList.add(BitmapFactory.decodeResource(res, R.drawable.bluebird_upflap))
                birdList.add(BitmapFactory.decodeResource(res, R.drawable.bluebird_midflap))

                x = ScreenSize.SCREEN_WIDTH/4 - birdList[0].width/2
                y = ScreenSize.SCREEN_HEIGHT/2 - birdList[0].width/2
              //  birdList.add(BitmapFactory.decodeResource(res, R.drawable.frame_4))
             //   birdList.add(BitmapFactory.decodeResource(res, R.drawable.frame_5))
             //   birdList.add(BitmapFactory.decodeResource(res, R.drawable.frame_6))
             //   birdList.add(BitmapFactory.decodeResource(res, R.drawable.frame_7))
              //  birdList.add(BitmapFactory.decodeResource(res, R.drawable.frame_8))
            //    birdList.add(BitmapFactory.decodeResource(res, R.drawable.frame_9))

            }
    fun getBird(current: Int) : Bitmap{
        return birdList.get(current)
    }

}