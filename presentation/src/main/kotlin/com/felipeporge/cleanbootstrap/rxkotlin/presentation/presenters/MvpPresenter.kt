package com.felipeporge.cleanbootstrap.rxkotlin.presentation.presenters

import com.felipeporge.cleanbootstrap.rxkotlin.presentation.views.MvpView

/**
 * This class represents a presenter.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    14/05/2017
 */
interface MvpPresenter<VIEW: MvpView> {

    var view: VIEW?

    /**
     * Starts the presenter, because the view was started.
     */
    fun start()

    /**
     * Resumes the presenter, because the view was resumed.
     */
    fun resume()

    /**
     * Pauses the presenter, because the view was paused.
     */
    fun pause()

    /**
     * Destroys the presenter, because the view was destroyed.
     */
    fun destroy()
}
