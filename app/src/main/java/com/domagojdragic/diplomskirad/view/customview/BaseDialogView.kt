package com.domagojdragic.diplomskirad.view.customview

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding

abstract class BaseDialogView<T : ViewBinding>(protected val binding: T) : DefaultLifecycleObserver {

    protected val popupWindow = PopupWindow(binding.root, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

    fun show(parent: View) {
        parent.post {
            popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, 0, 0)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        popupWindow.dismiss()
    }
}
