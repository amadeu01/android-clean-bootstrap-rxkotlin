package com.felipeporge.cleanbootstrap.rxkotlin.domain.interactors

import com.felipeporge.cleanbootstrap.rxkotlin.domain.executors.PostExecution
import com.felipeporge.cleanbootstrap.rxkotlin.domain.executors.TaskExecutor
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers


/**
 * This class represents a completable use case.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    27/05/2017
 */
abstract class CompletableUseCase<in PARAMS>(
        private val taskExecutor: TaskExecutor,
        private val postExecutor: PostExecution
) {

    private val compDisposables = CompositeDisposable()

    /**
     * Builds an use case completable.
     * @param params    Use case parameters.
     * @return  Built completable for this use case.
     */
    abstract fun buildCompletable(params: PARAMS): Completable

    /**
     * Executes the interactor.
     * @param params    Interactor params.
     * @param onSubscribe   Function to run on observer subscribed.
     * @param onError   Function to run on error.
     * @param onComplete    Function to run on complete.
     */
    fun execute(
            params: PARAMS,
            onSubscribe: (() -> Unit)?,
            onError: ((exception: Throwable?) -> Unit)?,
            onComplete: (() -> Unit)?
    ) {

        val disposable = buildCompletable(params)
                .subscribeOn(Schedulers.from(taskExecutor))
                .observeOn(postExecutor.scheduler)
                .doOnSubscribe { onSubscribe?.invoke() }
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        onComplete?.invoke()
                    }

                    override fun onError(exception: Throwable?) {
                        onError?.invoke(exception)
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