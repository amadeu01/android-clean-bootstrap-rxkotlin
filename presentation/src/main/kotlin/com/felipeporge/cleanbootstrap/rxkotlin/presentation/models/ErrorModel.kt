package com.felipeporge.cleanbootstrap.rxkotlin.presentation.models


/**
 * This class represents an error.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    27/05/2017
 *
 * @param message           Error message.
 * @param allowRetry        True if this error allows retry.
 * @param isWarning         True if this error is only a warning.
 * @param requiredAction    Function describing a required action.
 */
<<<<<<< HEAD
class ErrorModel(val message: String, val allowRetry: Boolean, val isWarning: Boolean, val requiredAction: (() -> Unit)? = null)
=======
class ErrorModel(val message: String, val allowRetry: Boolean, val isWarning: Boolean, val requiredAction: (() -> Unit)? = null)
>>>>>>> 37d05ec0b54ab67491b77ae18b8dbce009a34ef1
