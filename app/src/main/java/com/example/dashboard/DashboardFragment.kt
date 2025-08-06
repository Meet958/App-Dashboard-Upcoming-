package com.example.dashboard

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.example.api.RetrofitClient
import com.example.api.StatusUpdateResponse
import com.example.utils.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pieChart = view.findViewById<PieChart>(R.id.pieChart)

        // ✅ Get token from TokenManager
        val token = TokenManager.getToken(requireContext())

        if (token != null) {
            RetrofitClient.getInstance(requireContext()).getStats("Bearer $token")
                .enqueue(object : Callback<StatusUpdateResponse> {
                    override fun onResponse(
                        call: Call<StatusUpdateResponse>,
                        response: Response<StatusUpdateResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { stats ->
                                val entries = arrayListOf(
                                    PieEntry(stats.totalStock.toFloat(), "Total"),
                                    PieEntry(stats.nearExpiry.toFloat(), "Near Expiry"),
                                    PieEntry(stats.expired.toFloat(), "Expired"),
                                    PieEntry(if (stats.returned) 1f else 0f, "Returned") // ✅ Convert Boolean to Float
                                )

                                val dataSet = PieDataSet(entries, "")
                                dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
                                val data = PieData(dataSet)
                                data.setValueTextSize(14f)
                                data.setValueTextColor(android.graphics.Color.WHITE)

                                pieChart.data = data
                                pieChart.description.isEnabled = false
                                pieChart.isDrawHoleEnabled = true
                                pieChart.centerText = "Batches"
                                pieChart.animateY(1000)
                                pieChart.invalidate()
                            }
                        } else {
                            Toast.makeText(requireContext(), "Error loading stats", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<StatusUpdateResponse>, t: Throwable) {
                        Toast.makeText(requireContext(), "Failed: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        } else {
            Toast.makeText(requireContext(), "Token not found", Toast.LENGTH_SHORT).show()
        }
    }
}
