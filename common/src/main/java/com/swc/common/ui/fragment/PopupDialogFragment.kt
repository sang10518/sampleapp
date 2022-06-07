package com.swc.common.ui.fragment

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swc.common.R
import com.swc.common.databinding.FragmentPopupDialogBinding
import com.swc.common.databinding.FragmentScrollingPopupDialogBinding

//import kotlinx.android.synthetic.main.fragment_popup_dialog.*


/**
Created by sangwn.choi on2020-06-29

 **/
open class PopupDialogFragment : BaseDialogFragment<FragmentPopupDialogBinding>() {
//    override val layoutId: Int =
//        R.layout.fragment_popup_dialog

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPopupDialogBinding = {
            inflater, container, attachToParent -> FragmentPopupDialogBinding.inflate(inflater, container, attachToParent)
    }

    private var mTitle: CharSequence? = null
    internal var mImage: Any? = null
    private var mContent: CharSequence? = null
    private var mDesc: CharSequence? = null
    private var mOkText: CharSequence? = null
    private var mCancelText: CharSequence? = null
    internal var mOkAction: (() -> Unit)? = {
    }
    internal var mCancelAction: (() -> Unit)? = {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isCancelable = false

        arguments?.run {
            //PopupDialogFragment components
            mTitle = getCharSequence(KEY_POPUP_TITLE)

            if (containsKey(KEY_POPUP_IMAGE_RES)) {
                mImage = getInt(KEY_POPUP_IMAGE_RES)
            } else if (containsKey(KEY_POPUP_IMAGE_URI)) {
                mImage = getString(KEY_POPUP_IMAGE_URI)
            }

            mContent = getCharSequence(KEY_POPUP_CONTENT)

            mDesc = getCharSequence(KEY_POPUP_DESC)

            mOkText = if (containsKey(KEY_POPUP_OK_TEXT)) {
                getCharSequence(KEY_POPUP_OK_TEXT)
            } else {
                getString(R.string.action_ok)
            }

            mCancelText = if (containsKey(KEY_POPUP_CANCEL_TEXT)) {
                getCharSequence(KEY_POPUP_CANCEL_TEXT)
            } else {
                getString(R.string.action_cancel)
            }

        }
        setStyle(STYLE_NORMAL,
            R.style.PopupWindowCustom
        )
//        setStyle(STYLE_NORMAL, 0)

    }

    override fun setLayout() {
        setTitle(mTitle)
        setImage(mImage)
        setContent(mContent)
        setDesc(mDesc)
        setOkText(mOkText)
        setCancelText(mCancelText)
        setOkAction(mOkAction)
        setCancelAction(mCancelAction)
    }

    private fun setTitle(title: CharSequence?) {
        title?.run {
            binding.tvPopupTitle.text = this
        } ?: run {
            binding.tvPopupTitle.visibility = View.GONE
            binding.vBorder.visibility = View.GONE
        }
    }

    open fun setImage(image: Any?) {
        image?.run {
            when (this) {
                is String -> {
                    //url? or could be local path..
                    //필요할 경우 Glide 추가
                    binding.ivPopupIcon.setImageURI(Uri.parse(this))
                }

                is Int -> {
                    //res id
                    binding.ivPopupIcon.setImageResource(this)
                }

                is Drawable -> {
                    //drawable
                    binding.ivPopupIcon.setImageDrawable(this)
                }

                else -> {
                    //다른 경우..추후 추가
                }
            }
        }?: run {
            binding.ivPopupIcon.visibility = View.GONE
        }

    }

    private fun setContent(content: CharSequence?) {
        content?.run {
            binding.tvPopupContent.text = this
        } ?: run {
            binding.tvPopupContent.visibility = View.GONE
        }
    }

    private fun setDesc(desc: CharSequence?) {
        desc?.run {
            binding.tvPopupDesc.text = this
        } ?: run {
            binding.tvPopupDesc.visibility = View.GONE
        }
    }

    private fun setOkText(okText: CharSequence?) {
        okText?.run {
            binding.btnOk.text = this
        } ?: run {
            binding.btnOk.visibility = View.GONE
        }
    }

    private fun setCancelText(cancelText: CharSequence?) {
        cancelText?.run {
            binding.btnCancel.text = this
        } ?: run {
            binding.btnCancel.visibility = View.GONE
        }
    }

    private fun setOkAction(action: (() -> Unit)?) {
        action?.run {
            binding.btnOk.setOnClickListener {
                this.invoke()
                dismiss()
            }
        }
    }

    private fun setCancelAction(action: (() -> Unit)?) {
        action?.run {
            binding.btnCancel.setOnClickListener {
                this.invoke()
                dismiss()
            }
        }
    }


    companion object {
        fun newInstance(bundle: Bundle): PopupDialogFragment {
            return PopupDialogFragment()
                .apply {
                arguments = bundle
            }
        }

        const val KEY_POPUP_TITLE = "kyppttl"
        const val KEY_POPUP_IMAGE_RES = "kyppimg"
        const val KEY_POPUP_IMAGE_URI = "kyppimguri"
        const val KEY_POPUP_CONTENT = "kyppctnt"
        const val KEY_POPUP_DESC = "kyppdsc"
        const val KEY_POPUP_OK_TEXT = "kyppkktxt"
        const val KEY_POPUP_CANCEL_TEXT = "kyppczxlctx"
        const val KEY_POPUP_OK_ACTION = "kyppctntact"
        const val KEY_POPUP_CANCEL_ACTION = "kyppctcacxntact"


    }


    open class PopupDialogBuilder {
        protected val mBundle = Bundle()
        protected var mBuilderOkAction: (() -> Unit)? = null
        protected var mBuilderCancelAction: (() -> Unit)? = null
        protected var mBuilderDrawable: Drawable? = null

        fun setTitle(title: CharSequence?): PopupDialogBuilder {
            mBundle.putCharSequence(KEY_POPUP_TITLE, title)
            return this
        }

        fun setImageResId(resId: Int): PopupDialogBuilder {
            mBundle.putInt(KEY_POPUP_IMAGE_RES, resId)
            return this
        }

        fun setImageUri(uri: String): PopupDialogBuilder {
            mBundle.putString(KEY_POPUP_IMAGE_URI, uri)
            return this
        }

        fun setImageDrawable(drawable: Drawable): PopupDialogBuilder {
            mBuilderDrawable = drawable
            return this
        }

        fun setContent(content: CharSequence?): PopupDialogBuilder {
            mBundle.putCharSequence(KEY_POPUP_CONTENT, content)
            return this
        }

        fun setDesc(desc: CharSequence?): PopupDialogBuilder {
            mBundle.putCharSequence(KEY_POPUP_DESC, desc)
            return this
        }

        fun setOkText(okText: CharSequence?): PopupDialogBuilder {
            mBundle.putCharSequence(KEY_POPUP_OK_TEXT, okText)
            return this
        }

        fun setCancelText(cancelText: CharSequence?): PopupDialogBuilder {
            mBundle.putCharSequence(KEY_POPUP_CANCEL_TEXT, cancelText)
            return this
        }

        fun setOkAction(action: (() -> Unit)?): PopupDialogBuilder {
            mBuilderOkAction = action
            return this
        }

        fun setCancelAction(action: (() -> Unit)?): PopupDialogBuilder {
            mBuilderCancelAction = action
            return this
        }

        open fun build(): PopupDialogFragment {
            return newInstance(
                mBundle
            ).apply {
                mBuilderOkAction?.let {
                    mOkAction = it
                }
                mBuilderCancelAction?.let {
                    mCancelAction = it
                }

                mBuilderDrawable?.let {
                    mImage = it
                }
            }
        }
    }

}