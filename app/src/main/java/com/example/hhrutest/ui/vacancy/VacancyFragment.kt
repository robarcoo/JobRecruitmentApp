package com.example.hhrutest.ui.vacancy

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.Vacancy
import com.example.hhrutest.R
import com.example.hhrutest.databinding.FragmentDashboardBinding
import com.example.hhrutest.databinding.FragmentVacancyBinding
import com.example.hhrutest.ui.dashboard.DashboardViewModel
import com.example.hhrutest.ui.dashboard.OfferAdapter
import com.example.hhrutest.ui.dashboard.VacancyAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
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
            binding.fullVacancyFavoriteButton.changeFavoriteButton(vacancy.isFavorite)
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
                buttonAdapter = ButtonAdapter(vacancy.questions) { _, question ->
                   openDialog(vacancy.title, question)
                }
                adapter = buttonAdapter
            }

            binding.goBackButton.setOnClickListener {
                val navHostFragment = (activity as AppCompatActivity)
                    .supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
                navHostFragment.navController.popBackStack()
            }
            binding.fullVacancyFavoriteButton.setOnClickListener {
                dashboardViewModel.favoriteButtonClick(vacancy.id)
                binding.fullVacancyFavoriteButton.changeFavoriteButton(vacancy.isFavorite)
            }
            openDialog(vacancy.title)

        }
    }

    private fun ImageButton.changeFavoriteButton(isFavorite : Boolean) {
        if (isFavorite) {
            this.setBackgroundResource(R.drawable.ic_favorite)
        } else {
            this.setBackgroundResource(R.drawable.ic_unfavorite)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openDialog(title : String, question: String = "") {
        val dialog = context?.let { BottomSheetDialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.apply_dialog)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.findViewById<TextView>(R.id.dialog_vacancy_title)?.text = title
        if (question != "") {
            dialog?.show()
        }
        binding.applyForVacancyButton.setOnClickListener {
            dialog?.show()
        }
        if (question.isEmpty()) {
            dialog?.findViewById<TextView>(R.id.add_cover_letter)?.setOnClickListener {
                dialog.findViewById<EditText>(R.id.write_cover_letter)?.visibility = View.VISIBLE
                it.visibility = View.GONE
            }
        } else {
            dialog?.findViewById<TextView>(R.id.add_cover_letter)?.visibility = View.GONE
            dialog?.findViewById<EditText>(R.id.write_cover_letter)?.visibility = View.VISIBLE
            dialog?.findViewById<EditText>(R.id.write_cover_letter)?.setText(question)
        }

        dialog?.findViewById<Button>(R.id.send_application_button)?.setOnClickListener {
            dialog.dismiss()
        }
    }

}
