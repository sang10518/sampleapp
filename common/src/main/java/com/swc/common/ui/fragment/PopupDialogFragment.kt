package com.swc.common.ui.fragment

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.swc.common.R
import kotlinx.android.synthetic.main.fragment_popup_dialog.*


/**
Created by sangwn.choi on2020-06-29

 **/
open class PopupDialogFragment : BaseDialogFragment() {
    override val layoutId: Int =
        R.layout.fragment_popup_dialog

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
            tvPopupTitle?.text = this
        } ?: run {
            tvPopupTitle?.visibility = View.GONE
            vBorder?.visibility = View.GONE
        }
    }

    open fun setImage(image: Any?) {
        image?.run {
            when (this) {
                is String -> {
                    //url? or could be local path..
                    //필요할 경우 Glide 추가
                    ivPopupIcon?.setImageURI(Uri.parse(this))
                }

                is Int -> {
                    //res id
                    ivPopupIcon?.setImageResource(this)
                }

                is Drawable -> {
                    //drawable
                    ivPopupIcon?.setImageDrawable(this)
                }

                else -> {
                    //다른 경우..추후 추가
                }
            }
        }?: run {
            ivPopupIcon?.visibility = View.GONE
        }

    }

    private fun setContent(content: CharSequence?) {
        content?.run {
            tvPopupContent?.text = this
        } ?: run {
            tvPopupContent?.visibility = View.GONE
        }
    }

    private fun setDesc(desc: CharSequence?) {
        desc?.run {
            tvPopupDesc?.text = this
        } ?: run {
            tvPopupDesc?.visibility = View.GONE
        }
    }

    private fun setOkText(okText: CharSequence?) {
        okText?.run {
            btnOk?.text = this
        } ?: run {
            btnOk?.visibility = View.GONE
        }
    }

    private fun setCancelText(cancelText: CharSequence?) {
        cancelText?.run {
            btnCancel?.text = this
        } ?: run {
            btnCancel?.visibility = View.GONE
        }
    }

    private fun setOkAction(action: (() -> Unit)?) {
        action?.run {
            btnOk?.setOnClickListener {
                this.invoke()
                dismiss()
            }
        }
    }

    private fun setCancelAction(action: (() -> Unit)?) {
        action?.run {
            btnCancel?.setOnClickListener {
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