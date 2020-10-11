package com.dmgdavid2109.btcusdorderbook.common.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.dmgdavid2109.btcusdorderbook.databinding.LoadingErrorViewBinding

class LoadingErrorView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultStyleAttributes: Int = 0
) : ConstraintLayout(context, attributeSet, defaultStyleAttributes) {

    enum class VisibilityState { LOADING, OFFLINE, GONE }

    private var retryListener: (() -> Unit)? = null
    private val binding: LoadingErrorViewBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = LoadingErrorViewBinding.inflate(inflater, this, true)

        binding.errorView.tryAgainButton.setOnClickListener {
            retryListener?.invoke()
        }
    }

    fun setRetryListener(listener: () -> Unit) {
        retryListener = listener
    }

    fun status(display: VisibilityState) {

        when (display) {
            VisibilityState.GONE -> {
                setViewsVisibility(baseViewVisibility = View.GONE)
            }

            VisibilityState.LOADING -> {
                setViewsVisibility(progressBarVisibility = View.VISIBLE)
            }

            VisibilityState.OFFLINE -> {
                setViewsVisibility(errorViewVisibility = View.VISIBLE)
            }
        }
    }

    private fun setViewsVisibility(
        baseViewVisibility: Int = View.VISIBLE,
        progressBarVisibility: Int = View.GONE,
        errorViewVisibility: Int = View.GONE
    ) {
        visibility = baseViewVisibility
        binding.progressBar.visibility = progressBarVisibility
        binding.errorView.root.visibility = errorViewVisibility
    }
}
