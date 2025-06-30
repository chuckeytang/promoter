package com.example.promotor


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.promotor.databinding.FragmentAiTaskBinding // Assuming you'll create this binding

class AITaskFragment : Fragment() {

    private var _binding: FragmentAiTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAiTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize your chat RecyclerView, adapters, send button logic here
        // For example:
        // binding.chatRecyclerView.layoutManager = LinearLayoutManager(context)
        // binding.chatRecyclerView.adapter = yourChatAdapter
        // binding.sendButton.setOnClickListener { /* send message logic */ }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}