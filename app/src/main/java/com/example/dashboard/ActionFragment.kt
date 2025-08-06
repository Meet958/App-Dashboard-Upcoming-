package com.example.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.api.MedicineApi
import com.example.api.MedicineResponse
import com.example.api.RetrofitClient
import com.example.api.StatusUpdateRequest
import com.example.utils.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActionFragment : Fragment() {

    // Replace with actual batch numbers (these are examples)
    private val batchNo1 = "BATCH001"
    private val batchNo2 = "BATCH002"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_action, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val markReturnedBtn1 = view.findViewById<Button>(R.id.btn_mark_returned1)
        val markDiscountedBtn1 = view.findViewById<Button>(R.id.btn_mark_discounted1)
        val markReturnedBtn2 = view.findViewById<Button>(R.id.btn_mark_returned2)
        val markDiscountedBtn2 = view.findViewById<Button>(R.id.btn_mark_discounted2)

        markReturnedBtn1.setOnClickListener {
            updateBatchStatus(batchNo1, "returned")
        }

        markDiscountedBtn1.setOnClickListener {
            updateBatchStatus(batchNo1, "discounted")
        }

        markReturnedBtn2.setOnClickListener {
            updateBatchStatus(batchNo2, "returned")
        }

        markDiscountedBtn2.setOnClickListener {
            updateBatchStatus(batchNo2, "discounted")
        }
    }

    private fun updateBatchStatus(batchNo: String, status: String) {
        val token = TokenManager.getToken(requireContext())
        if (token.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "⚠️ User not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        val request = StatusUpdateRequest(medicineId = batchNo, newStatus = status)
        val api = RetrofitClient.getInstance(requireContext())  // ✅ Already returns MedicineApi

        api.markStatus("Bearer $token", request)
            .enqueue(object : Callback<MedicineResponse> {
                override fun onResponse(
                    call: Call<MedicineResponse>,
                    response: Response<MedicineResponse>
                ) {
                    if (response.isSuccessful) {
                        val medicine = response.body()
                        Toast.makeText(
                            requireContext(),
                            "✅ Status updated for ${medicine?.name}",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(requireContext(), "❌ Failed to update status", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<MedicineResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "⚠️ Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
