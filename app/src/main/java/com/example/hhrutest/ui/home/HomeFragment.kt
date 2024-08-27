package com.example.hhrutest.ui.home

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.hhrutest.R
import com.example.hhrutest.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.loginButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("login", binding.email.text.toString())
            val navHostFragment = (activity as AppCompatActivity)
                .supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.codeFragment, bundle)
        }

        binding.email.addTextChangedListener(textWatcher)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val button = binding.loginButton
            if (s != null) {
                if (s.isEmpty()) {
                    button.isEnabled = false
                    binding.loginEditText.isErrorEnabled = false
                    binding.loginEditText.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(context!!, R.color.grey_34))
                    button.setBackgroundResource(R.drawable.buttonshape)
                    button.setTextColor(ContextCompat.getColor(context!!, R.color.grey_9f))
                } else if (android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    binding.loginEditText.isErrorEnabled = false
                    binding.loginEditText.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(context!!, R.color.grey_34))
                    binding.loginButton.isEnabled = true
                    button.setBackgroundResource(R.drawable.button_shape_enabled)
                    button.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                } else {
                    binding.loginEditText.isErrorEnabled = true
                    binding.loginEditText.setBoxStrokeColorStateList(AppCompatResources.getColorStateList(context!!, R.color.red))
                    binding.loginEditText.error = "Невалидный логин"
                    binding.loginButton.isEnabled = false
                    button.setBackgroundResource(R.drawable.buttonshape)
                    button.setTextColor(ContextCompat.getColor(context!!, R.color.grey_9f))
                }
            }
        }
    }
}