package com.example.hhrutest.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.Vacancy
import com.example.hhrutest.R
import com.example.hhrutest.databinding.FragmentDashboardBinding
import com.example.hhrutest.ui.vacancy.VacancyFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel : DashboardViewModel by activityViewModel()
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

                        vacancyAdapter = VacancyAdapter(dashboardViewModel.response.value) { vacancy ->
                            onItemClicked(vacancy)
                        }
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

    private fun onItemClicked(id : String) {
        val bundle = Bundle()
        bundle.putString("vacancy_id", id)
        val navHostFragment = (activity as AppCompatActivity)
            .supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.vacancyFragment, bundle)
    }
}

