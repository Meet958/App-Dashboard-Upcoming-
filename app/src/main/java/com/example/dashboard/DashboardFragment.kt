package com.example.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import android.util.Log

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pieChart = view.findViewById<PieChart>(R.id.pieChart)

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(120f, "Total"))
        entries.add(PieEntry(8f, "Near Expiry"))
        entries.add(PieEntry(2f, "Expired"))
        entries.add(PieEntry(3f, "Returned"))
        Log.d("PieChartDebug", "Entries size: ${entries.size}")

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
}
