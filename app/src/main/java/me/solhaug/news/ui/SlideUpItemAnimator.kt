package me.solhaug.news.ui

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.animation.TranslateAnimation

class SlideUpItemAnimator: SimpleItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        val anim = TranslateAnimation(0f, 0f, 1f, 0f)
        anim.duration = 5000

        holder?.itemView?.startAnimation(anim)

        return true
    }


    override fun animateMove(holder: RecyclerView.ViewHolder?, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
        val anim = TranslateAnimation(0f, 0f, 1f, 0f)
        anim.duration = 5000

        holder?.itemView?.startAnimation(anim)

        return false
    }

    override fun runPendingAnimations() {}

    override fun animateChange(oldHolder: RecyclerView.ViewHolder?, newHolder: RecyclerView.ViewHolder?, fromLeft: Int, fromTop: Int, toLeft: Int, toTop: Int): Boolean = false

    override fun isRunning(): Boolean = false

    override fun endAnimation(item: RecyclerView.ViewHolder?) {}

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean = false

    override fun endAnimations() {}

}