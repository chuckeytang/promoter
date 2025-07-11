package com.haomai.promotor.feature.promotor.ui


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.haomai.promotor.accessibility.GestureService
import com.haomai.promotor.accessibility.actions.ClickAction
import com.haomai.promotor.common.launcher.AppLauncher
import com.haomai.promotor.common.state.UiState
import com.haomai.promotor.feature.promotor.databinding.FragmentAiTaskBinding
import com.haomai.promotor.feature.promotor.viewmodel.PromotorViewModel
import kotlinx.coroutines.launch

class AITaskFragment : Fragment() {

    private var _binding: FragmentAiTaskBinding? = null
    private val binding get() = _binding!!

    private val WECHAT_PACKAGE_NAME = "com.tencent.mm"
    private val viewModel: PromotorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAiTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Observe the uiState from the ViewModel
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Idle -> {
                            // Hide progress bar, show initial UI
                        }
                        is UiState.Loading -> {
                            // Show a progress bar
                        }
                        is UiState.Success -> {
                            // Hide progress bar, show the data
                            val data = state.data
                            // myTextView.text = data
                        }
                        is UiState.Error -> {
                            // Hide progress bar, show an error message
                            val errorMessage = state.message
                            // Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        // Trigger the data fetch
        viewModel.fetchData()

        // Set up the listener for our new button
        binding.wechatClickButton.setOnClickListener {
            handleWeChatClick()
        }
    }

    private fun handleWeChatClick() {
        if (!isAccessibilityServiceEnabled()) {
            // If the service is not enabled, prompt the user to enable it
            Toast.makeText(requireContext(), "请先在设置中开启应用的无障碍服务", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        } else {
            // If the service is enabled, launch WeChat and perform the click
            launchWeChatAndPerformClick()
        }
    }

    private fun launchWeChatAndPerformClick() {
        // 1. Use the new AppLauncher utility
        val launched = AppLauncher.launchByPackageName(requireContext(), WECHAT_PACKAGE_NAME)

        if (launched) {
            // Wait for WeChat to launch before clicking
            Handler(Looper.getMainLooper()).postDelayed({
                // 2. Use the new ClickAction class
                val clickAction = ClickAction()
                clickAction.performAtCenter()
            }, 2000) // 2-second delay
        }
    }

    /**
     * Checks if our accessibility service is enabled in the system settings.
     */
    private fun isAccessibilityServiceEnabled(): Boolean {
        val service = "${requireContext().packageName}/${GestureService::class.java.canonicalName}"
        val enabledServices = Settings.Secure.getString(
            requireContext().contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
        return enabledServices?.contains(service) == true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}