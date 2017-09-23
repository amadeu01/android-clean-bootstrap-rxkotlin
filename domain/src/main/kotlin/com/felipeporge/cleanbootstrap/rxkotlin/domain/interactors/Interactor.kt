package com.felipeporge.cleanbootstrap.rxkotlin.domain.interactors


/**
 * This interface describes an interactor.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    21/09/2017
 */
internal interface Interactor<in PARAMS, out RESULT> {

    /**
     * Executes the interactor.
     * @param params    Interactor params.
     * @param onSubscribe   Function to run on observer subscribed.
     * @param onNext    Function to run on next result received.
     * @param onError   Function to run on error.
     * @param onComplete    Function to run on complete.
     */
    fun execute(
            params: PARAMS,
            onSubscribe: (() -> Unit)? = null,
            onNext: ((result: RESULT) -> Unit)? = null,
            onError: ((exception: Throwable?) -> Unit)? = null,
            onComplete: (() -> Unit)? = null
    )

    /**
     * Disposes this interactor.
     */
    fun dispose()
}