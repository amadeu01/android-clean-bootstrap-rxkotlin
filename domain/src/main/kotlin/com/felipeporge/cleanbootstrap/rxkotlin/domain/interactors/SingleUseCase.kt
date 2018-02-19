package com.felipeporge.cleanbootstrap.rxkotlin.domain.interactors

import com.felipeporge.cleanbootstrap.rxkotlin.domain.executors.PostExecution
import com.felipeporge.cleanbootstrap.rxkotlin.domain.executors.TaskExecutor
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


/**
 * This class represents a single use case.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    27/05/2017
 */
abstract class SingleUseCase<in PARAMS, RESULT>(
        private val taskExecutor: TaskExecutor,
        private val postExecutor: PostExecution
) {

    private val compDisposables = CompositeDisposable()

    /**
     * Builds an use case single.
     * @param params    Use case parameters.
     * @return  Built single for this use case.
     */
    abstract fun buildSingle(params: PARAMS): Single<RESULT>

    /**
     * Executes the interactor.
     * @param params    Interactor params.
     * @param onSubscribe   Function to run on observer subscribed.
     * @param onSuccess    Function to run on result received.
     * @param onError   Function to run on error.
     */
    fun execute(
            params: PARAMS,
            onSubscribe: (() -> Unit)? = null,
            onSuccess: ((result: RESULT) -> Unit)? = null,
            onError: ((exception: Throwable?) -> Unit)? = null
    ) {

        val disposable = buildSingle(params)
                .subscribeOn(Schedulers.from(taskExecutor))
                .observeOn(postExecutor.scheduler)
                .doOnSubscribe { onSubscribe?.invoke() }
                .subscribeWith(object : DisposableSingleObserver<RESULT>() {
                    override fun onError(exception: Throwable?) {
                        onError?.invoke(exception)
                    }

                    override fun onSuccess(result: RESULT) {
                        onSuccess?.invoke(result)
                    }
                })

        compDisposables.add(disposable)
    }

    /**
     * Disposes this interactor.
     */
    fun dispose() {
        if (!compDisposables.isDisposed)
            compDisposables.dispose()
    }
}