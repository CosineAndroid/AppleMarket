package kr.cosine.applemarket.listener

import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView

class ScrollListener(
    private val scrollButton: ImageButton
) : RecyclerView.OnScrollListener() {

    private var isTop = true

    private val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 300 }
    private val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 300 }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (!recyclerView.canScrollVertically(-1) &&
            newState == RecyclerView.SCROLL_STATE_IDLE
        ) {
            scrollButton.startAnimation(fadeOut)
            scrollButton.visibility = View.GONE
            isTop = true
        } else if (isTop) {
            scrollButton.visibility = View.VISIBLE
            scrollButton.startAnimation(fadeIn)
            isTop = false
        }
    }
}