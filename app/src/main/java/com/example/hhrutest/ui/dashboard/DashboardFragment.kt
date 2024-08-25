package com.example.hhrutest.ui.dashboard

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hhrutest.databinding.FragmentDashboardBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
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
        lateinit var dashboardAdapter: DashboardAdapter
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        lifecycleScope.launch {
            dashboardViewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.loadingPanel.visibility = View.VISIBLE
                } else {
                    with(binding.recyclerViewVertical) {
                        layoutManager = LinearLayoutManager(context)
                        DividerItemDecoration(
                            context,
                            (layoutManager as LinearLayoutManager).orientation
                        ).apply {
                            addItemDecoration(this)
                        }
                        dashboardAdapter = DashboardAdapter(dashboardViewModel.response.value)
                        adapter = dashboardAdapter
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

