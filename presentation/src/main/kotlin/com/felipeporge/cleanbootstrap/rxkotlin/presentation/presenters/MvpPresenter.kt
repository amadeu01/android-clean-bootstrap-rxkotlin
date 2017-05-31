package com.felipeporge.cleanbootstrap.rxkotlin.presentation.presenters

import com.felipeporge.cleanbootstrap.rxkotlin.presentation.views.MvpView

/**
 * This class represents a presenter.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    14/05/2017
 */
abstract class MvpPresenter<VIEW: MvpView> {

    var view: VIEW? = null

    /**
     * Resumes the presenter, because the view was resumed.
     */
    abstract fun resume()

    /**
     * Pauses the presenter, because the view was paused.
     */
    abstract fun pause()

    /**
     * Destroys the presenter, because the view was destroyed.
     */
    abstract fun destroy()
}
