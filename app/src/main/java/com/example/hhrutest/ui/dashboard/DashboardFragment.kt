package com.example.hhrutest.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hhrutest.databinding.FragmentDashboardBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel by viewModel<DashboardViewModel>()
        lateinit var vacancyAdapter: VacancyAdapter
        lateinit var offerAdapter: OfferAdapter
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        lifecycleScope.launch {
            dashboardViewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.loadingPanel.visibility = View.VISIBLE
                } else {
                    with(binding.recyclerViewVertical) {
                        layoutManager = LinearLayoutManager(context)
                        vacancyAdapter = VacancyAdapter(dashboardViewModel.response.value)
                        adapter = vacancyAdapter
                    }
                    with(binding.recyclerViewHorizontal) {
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        offerAdapter = OfferAdapter(dashboardViewModel.response.value)
                        adapter = offerAdapter
                    }
                    binding.loadingPanel.visibility = View.GONE
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

