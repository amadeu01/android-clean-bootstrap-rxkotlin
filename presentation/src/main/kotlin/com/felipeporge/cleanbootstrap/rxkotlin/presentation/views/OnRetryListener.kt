package com.felipeporge.cleanbootstrap.rxkotlin.presentation.views

/**
 * This class represents an interface in which is described how to handle retry events.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    14/05/2017
*/
interface OnRetryListener {

    /**
     * On retry action requested.
     * @param requestCode   Request code.
     */
    fun onRetry(requestCode: Int)
}
