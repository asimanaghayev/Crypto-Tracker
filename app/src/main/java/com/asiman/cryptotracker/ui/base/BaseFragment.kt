package com.asiman.cryptotracker.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.asiman.cryptotracker.R
import com.asiman.cryptotracker.support.tools.NavigationCommand
import com.example.module_ui_kit.view.showSnackbar

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(
    private val vb: Inflate<VB>,
    private val vm: Class<VM>,
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    protected lateinit var viewModel: VM

    val baseActivity by lazy { requireActivity() as BaseActivity }

    private var isObserving = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[vm]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (_binding == null)
            _binding = vb.invoke(inflater, container, false)

        bindUi()
        if (!isObserving) {
            isObserving = true
            observeBaseData()
        }

        return binding.root
    }

    private fun observeBaseData() {
        bindNavController()
        bindInfo()
        bindError()
        bindLoading()
        bindObservers()
    }

    abstract fun bindUi()
    abstract fun bindObservers()

    private fun bindNavController() {
        viewModel.navigationCommands.observe(viewLifecycleOwner) { command ->
            when (command) {
                is NavigationCommand.To -> {
                    command.extras?.let { extras ->
                        findNavController().navigate(
                            command.directions, extras
                        )
                    } ?: run {
                        findNavController().navigate(
                            command.directions
                        )
                    }
                }
                is NavigationCommand.BackTo -> findNavController().getBackStackEntry(command.destinationId)
                is NavigationCommand.Back -> findNavController().popBackStack()
            }
        }
    }

    private fun bindInfo() {
        viewModel.info.observe(viewLifecycleOwner) {
            binding.root.showSnackbar(it)
        }
    }

    private fun bindError() {
        viewModel.error.observe(viewLifecycleOwner) {
            binding.root.showSnackbar(it, R.drawable.bg_error)
        }
    }

    protected open fun showBackEndError() {
        BaseBottomSheetDialog.build(action = {
            it.dismiss()
        }).show("general-error-dialog")
    }

    protected open fun showError(
        message: Int,
        title: Int? = R.string.error_unknown_title,
        image: Int? = R.drawable.ic_error,
    ) {
        BaseBottomSheetDialog.build(text = message,
            title = title ?: R.string.error_unknown_title,
            image = image ?: R.drawable.ic_error,
            action = {
                it.dismiss()
            }).show("show-error-dialog")
    }

    private fun bindLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) showLoading()
            else hideLoading()
        }
    }

    protected open fun showLoading() {
        baseActivity.showLoading()
    }

    protected open fun hideLoading() {
        baseActivity.hideLoading()
    }

    protected fun <T : DialogFragment> T.show(tag: String? = null): T {
        this.show(this@BaseFragment.parentFragmentManager, tag)
        return this
    }
}