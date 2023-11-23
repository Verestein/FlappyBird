package com.example.flappybird.UI

import android.content.Context
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.flappybird.Tread.PlayThread

class Jugar(context: Context?) : SurfaceView(context), SurfaceHolder.Callback {

    private val TAG = "PlayView"
    private var playThread : PlayThread? = null

    init {
        val holder = holder
        holder.addCallback(this)
        isFocusable = true
        playThread = PlayThread(holder,resources)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        if (!playThread!!.isRunning){
            playThread = p0.let{
                PlayThread(it!!, resources)
            }
        }else{
            playThread!!.start()
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        if(playThread!!.isRunning){
            playThread!!.isRunning = false
            var isCheck : Boolean = true
            while (isCheck){
                try {
                    playThread!!.join()
                }catch (e : InterruptedException){

                }
            }
        }
    }
    // vuelo del ave
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val ev = event!!.action
        if(ev == MotionEvent.ACTION_DOWN){
            playThread!!.Jump()
            }
        return true
        }
    }


