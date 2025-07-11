package com.example.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class ActionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_action, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Example click logic for first card
        val markReturnedBtn1 = view.findViewById<Button>(R.id.btn_mark_returned1)
        val markDiscountedBtn1 = view.findViewById<Button>(R.id.btn_mark_discounted1)

        markReturnedBtn1.setOnClickListener {
            Toast.makeText(requireContext(), "Batch marked as returned!", Toast.LENGTH_SHORT).show()
        }

        markDiscountedBtn1.setOnClickListener {
            Toast.makeText(requireContext(), "Batch marked as discounted!", Toast.LENGTH_SHORT).show()
        }

        // Example for second card
        val markReturnedBtn2 = view.findViewById<Button>(R.id.btn_mark_returned2)
        val markDiscountedBtn2 = view.findViewById<Button>(R.id.btn_mark_discounted2)

        markReturnedBtn2.setOnClickListener {
            Toast.makeText(requireContext(), "Batch marked as returned!", Toast.LENGTH_SHORT).show()
        }

        markDiscountedBtn2.setOnClickListener {
            Toast.makeText(requireContext(), "Batch marked as discounted!", Toast.LENGTH_SHORT).show()
        }
    }
}
