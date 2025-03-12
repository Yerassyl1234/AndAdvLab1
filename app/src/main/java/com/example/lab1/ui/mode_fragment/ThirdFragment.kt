package com.example.lab1.ui.mode_fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lab1.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!

    private lateinit var airplaneModeReceiver: BroadcastReceiver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAirplaneModeReceiver()
        updateAirplaneModeStatus()
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(
            airplaneModeReceiver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(airplaneModeReceiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupAirplaneModeReceiver() {
        airplaneModeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                    updateAirplaneModeStatus()
                }
            }
        }
    }

    private fun updateAirplaneModeStatus() {
        val isAirplaneModeEnabled = Settings.Global.getInt(
            requireContext().contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0
        ) == 1

        val statusText = if (isAirplaneModeEnabled) {
            "Режим полёта: Включен"
        } else {
            "Режим полёта: Выключен"
        }

        binding.statusText.text = statusText
        Toast.makeText(requireContext(), statusText, Toast.LENGTH_SHORT).show()
    }
}
