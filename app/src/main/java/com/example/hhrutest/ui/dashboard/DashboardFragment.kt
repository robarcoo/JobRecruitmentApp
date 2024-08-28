package com.example.hhrutest.ui.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
import com.example.hhrutest.util.vacancyUtil
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dashboardViewModel : DashboardViewModel by activityViewModel()
        lateinit var vacancyAdapter: VacancyAdapter
        lateinit var offerAdapter: OfferAdapter

        lifecycleScope.launch {
            dashboardViewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.loadingPanel.visibility = View.VISIBLE
                } else {
                    with(binding.recyclerViewVertical) {
                        layoutManager = LinearLayoutManager(context)

                        vacancyAdapter = VacancyAdapter(dashboardViewModel.response.value.vacancies, onFavoriteButtonClicked =
                        { dashboardViewModel.favoriteButtonClick(it) } ) { vacancy ->
                            onItemClicked(vacancy)
                        }
                        adapter = vacancyAdapter
                    }
                    with(binding.recyclerViewHorizontal) {
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        offerAdapter = OfferAdapter(dashboardViewModel.response.value) { link ->
                            onOfferClicked(link)
                        }
                        adapter = offerAdapter
                    }
                    binding.loadingPanel.visibility = View.GONE
                }
            }
        }
        val moreVacancies = dashboardViewModel.response.value.vacancies.size - 3
        val allVacancies = dashboardViewModel.response.value.vacancies.size
        binding.showMoreVacanciesButton.text =
            getString(R.string.more_vacancies_text, moreVacancies.toString(), vacancyUtil(moreVacancies))
        binding.overallVacancies.text =
            getString(R.string.overall_vacancies_text, allVacancies.toString(), vacancyUtil(allVacancies))
        binding.showMoreVacanciesButton.setOnClickListener {
            vacancyAdapter.updateMaxItems(Int.MAX_VALUE)
            vacancyAdapter.notifyDataSetChanged()
            binding.recyclerViewHorizontal.visibility = View.GONE
            binding.allVacanciesBar.visibility = View.VISIBLE
            binding.vacanciesForYou.visibility = View.GONE
            it.visibility = View.GONE
        }
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

    private fun onOfferClicked(url : String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context?.let { ContextCompat.startActivity(it, browserIntent, null) }
    }
}

