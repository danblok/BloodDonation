package com.example.blooddonation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.blooddonation.databinding.ActivityQractivityBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.Executors

@ExperimentalGetImage
class QRActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityQractivityBinding
    private lateinit var camera: Camera
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val qrListener: QRListener = {
        applicationId ->
            startActivity(ApplicationManagementActivity.newIntent(this, applicationId))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityQractivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        camera = Camera(this).setPreviewView(viewBinding.viewFinder)

        if (allPermissionsGranted()) {
            camera.startCamera(qrListener)
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        camera.setCameraExecutor(Executors.newSingleThreadExecutor())

        viewBinding.fabLogSignOut.setOnClickListener {
            auth.signOut();
            startActivity(LoginActivity.newIntent(this))
        }
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
        fun newIntent(context: AppCompatActivity): Intent {
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