package com.example.dontlate

import android.R
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import com.example.dontlate.databinding.FragmentSocialBinding
import kotlinx.android.synthetic.main.fragment_social.view.*


class SocialFragment : Fragment() {

    lateinit var binding : FragmentSocialBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSocialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonOnClick()
    }

    private fun initButtonOnClick() {
        binding.root.run {
            binding.s13Fab.setOnClickListener {
                // FAB Click 이벤트 처리 구간
                val intent = Intent(getActivity(), social14::class.java)
                startActivity(intent)
            }

            binding.s13NotiBtn.setOnClickListener {
                val intent = Intent(getActivity(), social41::class.java)
                startActivity(intent)
            }

            binding.s13ListView1.setOnClickListener {
                val intent = Intent(getActivity(), social31::class.java)
                startActivity(intent)
            }

            binding.s13ListView2.setOnClickListener {
                val intent = Intent(getActivity(), social35::class.java)
                startActivity(intent)
            }

            binding.keyword1.setOnClickListener {
                binding.keyword1.setClickable(true)
                keyword1.setTextColor(Color.parseColor("#E88B58"))
                binding.keyword2.setClickable(false)
                binding.keyword3.setClickable(false)
                binding.keyword4.setClickable(false)
                binding.keyword5.setClickable(false)
            }

            binding.keyword2.setOnClickListener {
                binding.keyword1.setClickable(false)
                binding.keyword2.setClickable(true)
                binding.keyword3.setClickable(false)
                binding.keyword4.setClickable(false)
                binding.keyword5.setClickable(false)
            }

            binding.keyword3.setOnClickListener {
                binding.keyword1.setClickable(false)
                binding.keyword2.setClickable(false)
                binding.keyword3.setClickable(true)
                binding.keyword4.setClickable(false)
                binding.keyword5.setClickable(false)
            }

            binding.keyword4.setOnClickListener {
                binding.keyword1.setClickable(false)
                binding.keyword2.setClickable(false)
                binding.keyword3.setClickable(false)
                binding.keyword4.setClickable(true)
                binding.keyword5.setClickable(false)
            }

            binding.keyword5.setOnClickListener {
                binding.keyword1.setClickable(false)
                binding.keyword2.setClickable(false)
                binding.keyword3.setClickable(false)
                binding.keyword4.setClickable(false)
                binding.keyword5.setClickable(true)
            }

        }
    }

}