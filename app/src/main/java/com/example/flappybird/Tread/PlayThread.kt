package com.example.flappybird.Tread


import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder
import com.example.flappybird.Model.BackgroundImage
import com.example.flappybird.Model.Bird
import com.example.flappybird.Model.BirdDie
import com.example.flappybird.Model.Obstaculo
import com.example.flappybird.Model.ScreenSize
import com.example.flappybird.R
import java.util.Random


class PlayThread : Thread {

    private val Tag : String = "PlayThread"
    private var holder : SurfaceHolder
    private var resources : Resources
    var isRunning: Boolean = false
        get() = field
        set(value) {
            field = value
        }
    private val FPS: Int = (1000.0/60.0).toInt()
    private val backgroundImage = BackgroundImage()
    private var bitmapImage : Bitmap? = null
    private var startTime : Long = 0
    private var FrameTime : Long = 0
    private val Velocity = 3
    private val bird : Bird    //modelo de ave
    private var state : Int = 0 // estado 0 = espera, 1 = jugando, 2 = fn del jeugo
    private var velocityBird : Int = 0

    var obs : Obstaculo? = null
    val numObs = 2
    val velObs = 17
    val minY = 350
    val maxY = ScreenSize.SCREEN_HEIGHT - minY - 500
    val kc = ScreenSize.SCREEN_WIDTH * 3/4
    var obsArray : ArrayList<Obstaculo> = arrayListOf()
    var ran : Random = Random()

    var iObs = 0
    var birdDie : BirdDie
    var isDie = false

    constructor(holder : SurfaceHolder, resources : Resources){
        this.holder = holder
        this.resources = resources
        isRunning = true
        //ave
        bird = Bird(resources)
        //backgroun
        bitmapImage = BitmapFactory.decodeResource(resources, R.drawable.run_background)
        bitmapImage = bitmapImage?.let {ScaleResize(it)  }
        //obstaculo
        obs = Obstaculo(resources)
        createObs(resources)
        //aver muere
        birdDie = BirdDie(resources)
    }

    private fun createObs(resources: Resources) {
        for (i in 0 until numObs){
            val obs = Obstaculo(resources)
            obs.x = ScreenSize.SCREEN_WIDTH + kc*i
            obs.ccY = ran.nextInt( maxY- minY) + minY
            obsArray.add(obs)
        }
    }

    override fun run() {
        Log.d(Tag, "Thread Started")

        while (isRunning){
            if (holder == null) return

            startTime = System.nanoTime()
            val canvas = holder.lockCanvas()
            if(canvas != null){
                try {
                    synchronized(holder){
                        //dibujar background
                        render(canvas)
                        //dibujar ave
                        RenderBird(canvas)
                        // dibujar obstaculo
                        renderObs(canvas)
                        //dibujar explosion
                        RenderDie(canvas)
                    }
                }
                finally {
                    holder.unlockCanvasAndPost(canvas)
                }
            }
            FrameTime = (System.nanoTime() - startTime)/1000000
            if (FrameTime<FPS){
                try {
                    Thread.sleep(FPS - FrameTime)
                }catch (e : InterruptedException){
                    Log.e("Interrupted", "Thread Sleep Error")
                }
            }
        }
        Log.d(Tag,"Thread Finish")
    }

    private fun RenderDie(canvas: Canvas) {
        if (isDie){
            var i : Int = birdDie.currentFrame
            canvas!!.drawBitmap(birdDie.getBirdDie(i),birdDie.x.toFloat(),birdDie.y.toFloat(), null)
            i++
            birdDie.currentFrame = i
            if (i == birdDie.maxFrame){
                isRunning = false
            }
        }
    }

    private fun renderObs(canvas: Canvas?) {
        if (state == 1 ){  //jugando
            if(obsArray.get(iObs).x < bird.x - obs!!.w){
                iObs++
                if (iObs > numObs -1){
                    iObs = 0
                }
            }
            else if (((obsArray.get(iObs).x) <bird.x +  bird.getBird(0).width) &&
                        (obsArray.get(iObs).ccY > bird.y || obsArray.get(iObs).getBotY() < bird.y + bird.getBird(0).height)){
                    isDie = true
                }
        for (i in 0 until numObs){// 0 1 2

                if (obsArray.get(i).x < -obs!!.w){
                    //crear nuevo obstaculo cuando x = kc + anterior obstaculo
                    obsArray.get(i).x = obsArray.get(i).x + numObs*kc
                    obsArray.get(i).ccY = ran.nextInt(maxY - minY) + minY
                }
                // mover obstaculos
                obsArray.get(i).x = obsArray.get(i).x - velObs
                //graficar obstaculo superior
                canvas!!.drawBitmap(obs!!.ObsTop, obsArray.get(i).x.toFloat(), obsArray.get(i).getTopY().toFloat(), null)
                //dibujar obstaculo inferior
                canvas!!.drawBitmap(obs!!.ObsBot, obsArray.get(i).x.toFloat(), obsArray.get(i).getBotY().toFloat(), null)
            }
        }

    }


    private fun RenderBird(canvas: Canvas?) {
            if (state == 1){
                if(!isDie) {


                    if (bird.y < (ScreenSize.SCREEN_HEIGHT - bird.getBird(0).height) || velocityBird < 0) {
                        velocityBird = velocityBird + 2 // bajar
                        bird.y = bird.y + velocityBird //subir
                    }
                }
            }
            if (!isDie){


            var current : Int = bird.currentFrame

            canvas!!.drawBitmap(bird.getBird(current),bird.x.toFloat(), bird.y.toFloat(), null)
            current ++
                if (current > bird.maxFrame)
                    current = 0
                    bird.currentFrame = current

            }

    }

    //dibujar el fondo
    private fun render(canvas: Canvas?){
        Log.d(Tag, "Render canvas")

        backgroundImage.x = backgroundImage.x - Velocity
        if(backgroundImage.x< -bitmapImage!!.width){
            backgroundImage.x = 0
        }
        bitmapImage?.let { canvas!!.drawBitmap(it, (backgroundImage.x).toFloat(),(backgroundImage.y).toFloat(), null) }

        //creando el bucle
        if(backgroundImage.x < -(bitmapImage!!.width - ScreenSize.SCREEN_WIDTH)){
            bitmapImage?.let { canvas!!.drawBitmap(it,(backgroundImage.x + bitmapImage!!.width).toFloat(),(backgroundImage.y).toFloat(), null) }
        }

    }

    private fun ScaleResize(bitmap: Bitmap): Bitmap {


        var ratio : Float = (bitmap.width / bitmap.height).toFloat()
        val scaleWidth : Int = (ratio * ScreenSize.SCREEN_HEIGHT).toInt()
        return Bitmap.createScaledBitmap(bitmap,scaleWidth,ScreenSize.SCREEN_HEIGHT, false)
    }

    fun Jump() {
        state = 1
        //limite de vuelo
          if(bird.y > 0 )
              velocityBird = -37
    }
    //ajustar las dimenciones

}

/*
* private fun ScaleResize(bitmap: Bitmap): Bitmap {
        var ratio : Float = (bitmap.width / bitmap.height).toFloat()
        val scaleWidth : Int = (ratio * ScreenSize.SCREEN_HEIGHT).toInt()
        return Bitmap.createScaledBitmap(bitmap,scaleWidth,ScreenSize.SCREEN_HEIGTH, false)
    }
* */