package com.example.blooddonation

import android.util.Log
import android.util.Rational
import android.util.Size
import android.view.Surface.ROTATION_0
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.camera2.internal.annotation.CameraExecutor
import androidx.camera.core.*
import androidx.camera.core.AspectRatio.RATIO_4_3
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Camera(private val context: AppCompatActivity) {

    private val TAG: String = "Camera"
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

    private lateinit var previewView: PreviewView

    fun setPreviewView(previewView: PreviewView): Camera {
        this.previewView = previewView
        return this
    }

    fun setCameraExecutor(executorService: ExecutorService) {
        cameraExecutor = executorService
    }

    @ExperimentalGetImage
    fun startCamera(listener: QRListener) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, QRCodeAnalyzer(listener))
                }


            val useCaseGroup = UseCaseGroup.Builder()
                .addUseCase(preview)
                .addUseCase(imageAnalyzer)
                .addUseCase(imageCapture!!)
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    context, cameraSelector, useCaseGroup)
            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(context))
    }

    fun shutdownCameraExecutor() {
        cameraExecutor.shutdown()
    }
}