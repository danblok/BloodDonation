package com.example.blooddonation

import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

typealias QRListener = (applicationId: String) -> Unit

@ExperimentalGetImage
class QRCodeAnalyzer(
    private val listener: QRListener
    ) : ImageAnalysis.Analyzer {

    private val TAG: String = "QRCodeAnalyzer"
    private var isFirstCall: Boolean = true

    override fun analyze(imageProxy: ImageProxy) {

        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_QR_CODE)
                .build()

            val scanner = BarcodeScanning.getClient(options)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        if (isFirstCall) {
                            isFirstCall = false
                            val barcode = barcodes.last()
                            if (barcode != null) {
                                val rawValue = barcode.rawValue
                                if (rawValue != null) {
                                    Log.d(TAG, rawValue)
                                    listener.invoke(rawValue)
                                    scanner.close()
                                }
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    Log.e(TAG, "QRAnalysis fail: " + it.stackTraceToString())
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }
}