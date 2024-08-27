package com.example.hhrutest.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.hhrutest.R
import com.example.hhrutest.databinding.FragmentCodeBinding
import com.example.hhrutest.databinding.FragmentHomeBinding
import com.example.hhrutest.ui.home.HomeViewModel

class CodeFragment : Fragment() {
    private var _binding: FragmentCodeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCodeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sentCodeTitle.text = "Отправили код на ${arguments?.getString("login")}"
        binding.otp.addTextChangedListener(textWatcher)
        binding.codeButton.setOnClickListener {
            val navHostFragment = (activity as AppCompatActivity)
                .supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.navigation_dashboard)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val button = binding.codeButton
        if (s != null) {
            if (s.length == 4) {
                button.isEnabled = true
                button.setBackgroundResource(R.drawable.button_shape_enabled)
                button.setTextColor(ContextCompat.getColor(context!!, R.color.white))
            } else {
                button.isEnabled = false
                button.setBackgroundResource(R.drawable.buttonshape)
                button.setTextColor(ContextCompat.getColor(context!!, R.color.grey_9f))
            }
        }
    }
}
}