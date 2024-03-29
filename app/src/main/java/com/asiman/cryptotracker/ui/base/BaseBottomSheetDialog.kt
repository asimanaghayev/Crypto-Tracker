package com.asiman.cryptotracker.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.asiman.cryptotracker.R
import com.asiman.cryptotracker.databinding.FragmentBottomSheetDialogBaseBinding
import com.asiman.cryptotracker.support.util.StringUtils.updateBoldSpanWithBoldFont
import com.example.module_ui_kit.support.delegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheetDialog : BottomSheetDialogFragment() {

    val binding by viewBinding(FragmentBottomSheetDialogBaseBinding::bind)

    private var title: Int = R.string.error_unknown_title
    private var text: Int = R.string.error_unknown_text
    private var image: Int = R.drawable.ic_info

    private var scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER
    private var canCancel: Boolean = true
    private var buttonText: Int = R.string.btn_close
    private var action: (BaseBottomSheetDialog) -> Unit = {}
    private var imageBg: Int = com.example.module_ui_kit.R.color.white
    private var buttonTextColor: Int = com.example.module_ui_kit.R.color.gray_454A50

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = inflater.inflate(R.layout.fragment_bottom_sheet_dialog_base, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.setText(title)
        binding.text.text = updateBoldSpanWithBoldFont(
            getText(text),
            ContextCompat.getColor(requireContext(), com.example.module_ui_kit.R.color.gray_454A50)
        )
        binding.image.setBackgroundResource(imageBg)
        binding.image.setImageResource(image)
        binding.image.scaleType = scaleType

        binding.button.setText(buttonText)
        binding.button.setTextColor(ContextCompat.getColor(requireContext(), buttonTextColor))
        binding.button.setOnClickListener {
            action(this)
        }
        isCancelable = canCancel
    }

    companion object {
        fun build(
            title: Int = R.string.error_unknown_title,
            text: Int = R.string.error_unknown_text,
            image: Int = R.drawable.ic_info,
            scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER,
            cancelable: Boolean = true,
            buttonText: Int = R.string.btn_close,
            buttonTextColor: Int = com.example.module_ui_kit.R.color.gray_454A50,
            action: (BaseBottomSheetDialog) -> Unit = {},
            imageBg: Int = com.example.module_ui_kit.R.color.white,
        ): BaseBottomSheetDialog {
            return BaseBottomSheetDialog().apply {
                this.title = title
                this.text = text
                this.image = image
                this.scaleType = scaleType
                this.canCancel = cancelable
                this.buttonText = buttonText
                this.buttonTextColor = buttonTextColor
                this.action = action
                this.imageBg = imageBg
            }
        }
    }
}