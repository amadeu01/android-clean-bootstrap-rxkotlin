package com.felipeporge.cleanbootstrap.rxkotlin.presentation.presenters

import com.felipeporge.cleanbootstrap.rxkotlin.presentation.views.MvpView

/**
 * This class represents a presenter.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    14/05/2017
 */
abstract class MvpPresenter<VIEW: MvpView> {

<<<<<<< HEAD
    var view: VIEW? = null
=======
    var view: VIEW?
>>>>>>> 37d05ec0b54ab67491b77ae18b8dbce009a34ef1

    /**
     * Resumes the presenter, because the view was resumed.
     */
    abstract fun resume()

    /**
     * Pauses the presenter, because the view was paused.
     */
<<<<<<< HEAD
    abstract fun pause()
=======
    fun pause()
>>>>>>> 37d05ec0b54ab67491b77ae18b8dbce009a34ef1

    /**
     * Destroys the presenter, because the view was destroyed.
     */
    abstract fun destroy()
}
