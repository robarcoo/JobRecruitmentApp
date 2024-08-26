package com.example.hhrutest.ui.vacancy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.Vacancy
import com.example.hhrutest.databinding.FragmentDashboardBinding
import com.example.hhrutest.databinding.FragmentVacancyBinding
import com.example.hhrutest.ui.dashboard.DashboardViewModel
import com.example.hhrutest.ui.dashboard.OfferAdapter
import com.example.hhrutest.ui.dashboard.VacancyAdapter
import io.ktor.http.CacheControl
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class VacancyFragment(val id : String = "") : Fragment() {

    private var _binding: FragmentVacancyBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lateinit var buttonAdapter: ButtonAdapter
        val dashboardViewModel : DashboardViewModel by activityViewModel()
        val vacancyList = dashboardViewModel.getVacancy(arguments?.getString("vacancy_id"))

        if (vacancyList.isNotEmpty()) {
            val vacancy = vacancyList.first()
            binding.fullVacancyTitle.text = vacancy.title
            binding.fullVacancySalary.text = vacancy.salary.full
            binding.fullVacancyAddress.text = vacancy.address.toString()
            binding.fullVacancyCompanyName.text = vacancy.company
            binding.fullVacancyCompanyDescription.text = vacancy.description
            binding.fullVacancyWorkday.text = vacancy.schedules.toString()
            if (vacancy.appliedNumber == null) {
                binding.fullVacancyAlreadyAppliedCard.visibility = View.GONE
            } else {
                binding.fullVacancyAlreadyApplied.text =
                    "${vacancy.appliedNumber} человек уже откликнулись"
            }
            if (vacancy.lookingNumber == null) {
                binding.fullVacancyAlreadyAppliedCard.visibility = View.GONE
            } else {
                binding.fullVacancyLookingRightNow.text =
                    "${vacancy.lookingNumber} человек сейчас смотрят"
            }
            binding.fullVacancyExperience.text = vacancy.experience.text
            binding.fullVacancyResponsibilities.text = vacancy.responsibilities
            with(binding.recyclerViewButtons) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                buttonAdapter = ButtonAdapter(vacancy.questions)
                adapter = buttonAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
