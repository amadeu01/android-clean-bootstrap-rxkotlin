package com.felipeporge.cleanbootstrap.rxkotlin.presentation.views.activities

import android.support.v7.app.AppCompatActivity
import android.view.View
import com.felipeporge.cleanbootstrap.rxkotlin.presentation.presenters.MvpPresenter
import com.felipeporge.cleanbootstrap.rxkotlin.presentation.views.MvpView
import javax.inject.Inject

/**
 * This class represents a presenter activity.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    14/05/2017
 */
abstract class PresenterActivity<PRESENTER: MvpPresenter<in MvpView>> : AppCompatActivity(), MvpView {

    @Inject
    lateinit var presenter: PRESENTER

    override fun setContentView(view: View?) {
        super.setContentView(view)

        presenter.view = this
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}
