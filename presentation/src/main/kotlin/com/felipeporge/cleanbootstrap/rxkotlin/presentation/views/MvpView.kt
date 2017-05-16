package com.felipeporge.cleanbootstrap.rxkotlin.presentation.views

import android.support.v4.app.DialogFragment
import android.view.View
import com.felipeporge.cleanbootstrap.rxkotlin.presentation.models.ErrorModel

/**
 * This class represents a view on MVP pattern.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    14/05/2017
 */
interface MvpView {

    val baseView: View?

    /**
     * Shows the loading view.
     * @param message Message to show.
     */
    fun showLoading(message: String? = null)

    /**
     * Hides the loading view.
     */
    fun hideLoading()

    /**
     * Shows a dialog.
     * @param dialog - [DialogFragment] instance.
     */
    fun showDialog(dialog: DialogFragment)


    /**
     * Shows a toast message.
     * @param message Toast message.
     */
    fun showToast(message: String)

    /**
     * Shows an error.
     * @param error         Error to show.
     * @param retryListener On retry listener.
     * @param requestCode   Request code to retry.
     */
    fun showError(error: ErrorModel, retryListener: OnRetryListener? = null, requestCode: Int = 0)


    /**
     * Hides the keyboard.
     */
    fun hideSoftKeyboard()
}
