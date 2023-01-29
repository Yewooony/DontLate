package com.example.dontlate

import android.app.Application

class textApplication : Application() {
    private var textSize: Float = 16F
    private var progressBar : Int = 30
    private var magnification : String = "x 1.0"

    fun getSize(): Float {
        return textSize
    }

    fun setSize(textSize: Float) {
        this.textSize = textSize
    }

    fun getProgress() : Int {
        return progressBar
    }

    fun setProgress(progress : Int) {
        this.progressBar = progress
    }

    fun getMagnification() : String {
        return magnification
    }

    fun setMagnification(magnification : String) {
        this.magnification = magnification
    }
}