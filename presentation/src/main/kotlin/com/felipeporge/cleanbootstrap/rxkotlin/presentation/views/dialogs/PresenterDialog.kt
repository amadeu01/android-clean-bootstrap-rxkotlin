package com.felipeporge.cleanbootstrap.rxkotlin.presentation.views.dialogs


import android.support.v4.app.DialogFragment
import com.felipeporge.cleanbootstrap.rxkotlin.presentation.presenters.MvpPresenter
import com.felipeporge.cleanbootstrap.rxkotlin.presentation.views.MvpView
import javax.inject.Inject

/**
 * This class represents a presenter dialog.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    14/05/2017
 */
abstract class PresenterDialog<PRESENTER: MvpPresenter<VIEW>, VIEW: MvpView> : DialogFragment(), MvpView {

    @Inject
    lateinit var presenter: PRESENTER

    @Suppress("UNCHECKED_CAST")
    override fun onStart() {
        super.onStart()
        presenter.view = (this as? VIEW)
        presenter.onViewCreated()
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}