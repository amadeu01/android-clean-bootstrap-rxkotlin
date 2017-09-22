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

    /**
     * Gets base activity [View].
     * @return [View] instance.
     */
    var baseView : View?

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
     * @param error         ErrorModel to show.
     * @param requestCode   Request code to retry.
     * @param onRetry       On retry function.
     */
    fun showError(error: ErrorModel, requestCode: Int = 0, onRetry: ((requestCode: Int) -> Unit)? = null)

    /**
     * Shows an error.
     * @param throwable Exception to show.
     * @param requestCode   Request code to retry.
     * @param onRetry       On retry function.
     */
    fun showError(throwable: Throwable?, requestCode: Int = 0, onRetry: ((requestCode: Int) -> Unit)? = null)

    /**
     * Shows the keyboard.
     * @param view  View to focus on.
     */
    fun showSoftKeyboard(view: View)

    /**
     * Hides the keyboard.
     */
    fun hideSoftKeyboard()
}
