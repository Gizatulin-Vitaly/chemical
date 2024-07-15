package com.example.pet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pet.databinding.AjkLayoutBinding
import com.example.pet.ui.dashboard.AJKViewModel

class AJKFragment : Fragment() {

    private var _binding: AjkLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Используем lazy для ленивой инициализации ViewModel
        val AJKViewModel: AJKViewModel by lazy {
            ViewModelProvider(this)[AJKViewModel::class.java]
        }

        _binding = AjkLayoutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
