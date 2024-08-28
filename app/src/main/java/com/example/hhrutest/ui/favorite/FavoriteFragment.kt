package com.example.hhrutest.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hhrutest.R
import com.example.hhrutest.databinding.FragmentFavoriteBinding
import com.example.hhrutest.ui.dashboard.DashboardViewModel
import com.example.hhrutest.ui.dashboard.OfferAdapter
import com.example.hhrutest.ui.dashboard.VacancyAdapter
import com.example.hhrutest.util.vacancyUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lateinit var vacancyAdapter: VacancyAdapter
        val dashboardViewModel : DashboardViewModel by activityViewModel()
        lifecycleScope.launch {
                dashboardViewModel.isLoading.collect { isLoading ->
                    if (isLoading) {
                        binding.loadingPanel.visibility = View.VISIBLE
                    } else {
                        with(binding.favoriteVacancyRecyclerview) {
                            layoutManager = LinearLayoutManager(context)

                            vacancyAdapter = VacancyAdapter(
                                dashboardViewModel.getAllFavorite(),
                                onFavoriteButtonClicked =
                                { dashboardViewModel.favoriteButtonClick(it) })
                            { vacancy ->
                                onItemClicked(vacancy)
                            }
                            vacancyAdapter.updateMaxItems(Int.MAX_VALUE)
                            adapter = vacancyAdapter
                        }

                        binding.loadingPanel.visibility = View.GONE
                        val vacancySize = dashboardViewModel.getAllFavorite().size
                        binding.vacancyAmount.text = getString(
                            R.string.all_favorite_vacancies_text,
                            vacancySize.toString(),
                            vacancyUtil(vacancySize)
                        )


                    }
                }
        }
    }

    private fun onItemClicked(id : String) {
        val bundle = Bundle()
        bundle.putString("vacancy_id", id)
        val navHostFragment = (activity as AppCompatActivity)
            .supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.vacancyFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}