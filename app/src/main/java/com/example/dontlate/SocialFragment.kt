package com.example.dontlate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dontlate.databinding.FragmentMyPageBinding
import com.example.dontlate.databinding.FragmentSocialBinding

class SocialFragment : Fragment() {
    lateinit var binding : FragmentSocialBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSocialBinding.inflate(inflater, container, false)
        return binding.root
    }



}