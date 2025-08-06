package com.example.dashboard

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.api.RetrofitClient
import com.example.api.StatusUpdateRequest
import com.example.api.MedicineResponse
import com.example.utils.TokenManager
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QRScannerFragment : Fragment() {

    private lateinit var barcodeView: DecoratedBarcodeView
    private var isScanning = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_qr_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        barcodeView = view.findViewById(R.id.barcode_scanner)

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startScanning()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                101
            )
        }
    }

    private fun startScanning() {
        isScanning = true
        barcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                if (!isScanning) return

                result?.text?.let { scannedId ->
                    isScanning = false
                    barcodeView.pause()
                    updateStatus(scannedId)
                }
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
        })
        barcodeView.resume()
    }

    private fun updateStatus(medicineId: String) {
        val token = TokenManager.getToken(requireContext())
        if (token.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
            return
        }

        val request = StatusUpdateRequest(medicineId, "returned")

        RetrofitClient.getInstance(requireContext()).markStatus("Bearer $token", request)
            .enqueue(object : Callback<MedicineResponse> {
                override fun onResponse(
                    call: Call<MedicineResponse>,
                    response: Response<MedicineResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Status updated for ${response.body()?.name}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to update status",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<MedicineResponse>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "Scan failed: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 101 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startScanning()
        } else {
            Toast.makeText(requireContext(), "Camera permission is required", Toast.LENGTH_SHORT).show()
        }
    }
}
