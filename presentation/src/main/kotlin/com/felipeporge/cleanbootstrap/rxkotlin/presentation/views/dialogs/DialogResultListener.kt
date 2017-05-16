package com.felipeporge.cleanbootstrap.rxkotlin.presentation.views.dialogs

/**
 * This class represents the dialog result listener
 * @author Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @since 04/05/17
 */
interface DialogResultListener<in RESULT> {

    /**
     * Handles when the dialog is finishing.
     * @param result Result.
     */
    fun onResult(result: RESULT)
}
