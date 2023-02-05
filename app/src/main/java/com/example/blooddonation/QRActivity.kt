package com.example.blooddonation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.JsonWriter
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.widget.Toast
import androidx.camera.core.*
import com.example.blooddonation.databinding.ActivityQractivityBinding
import androidx.camera.lifecycle.ProcessCameraProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executor

@ExperimentalGetImage
class QRActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityQractivityBinding
    private lateinit var camera: Camera

    private val qrListener: QRListener = {
        data -> Log.d(TAG, data.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityQractivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        camera = Camera(this).setPreviewView(viewBinding.viewFinder)

        if (allPermissionsGranted()) {
            camera.startCamera(qrListener)
            //                        TODO
            // ---------------------------------------------------- //
            /* Receive information about a client: id and application_id
            *  start activity on a doctor's behalf where he can
            *  change the status of the application (opened, closed, ip).
            *  Then the application is closed, notify the client about
            *  a successful blood donation and give him advice on health
            *  (how to behave after the donation, what drink, eat, what
            *  drugs to take and etc)*/
            // ---------------------------------------------------- //
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        camera.setCameraExecutor(Executors.newSingleThreadExecutor())
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val TAG = "QRActivity"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()

        @JvmStatic
        fun newIntent(context: MainActivity): Intent {
            return Intent(context, QRActivity::class.java)
        }
    }

    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                camera.startCamera(qrListener)
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        camera.shutdownCameraExecutor()
    }
}