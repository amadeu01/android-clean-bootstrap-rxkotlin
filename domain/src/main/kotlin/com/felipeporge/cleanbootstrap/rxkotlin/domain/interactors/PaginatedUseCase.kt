package com.felipeporge.cleanbootstrap.rxkotlin.domain.interactors

import com.felipeporge.cleanbootstrap.rxkotlin.domain.executors.PostExecution
import com.felipeporge.cleanbootstrap.rxkotlin.domain.executors.TaskExecutor
import com.felipeporge.cleanbootstrap.rxkotlin.domain.interactors.pagination.PaginatedPublisher
import com.felipeporge.cleanbootstrap.rxkotlin.domain.interactors.pagination.PaginatedSubscriber
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * This class represents a paginated use case.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    21/09/2017
 */
abstract class PaginatedUseCase<in PARAMS, RESULT>(
        private val taskExecutor: TaskExecutor,
        private val postExecutor: PostExecution
) {

    private val compDisposables = CompositeDisposable()

    /**
     * Builds the publisher for this use case.
     * @param params    Use case parameters.
     * @return  Built publisher for this use case.
     */
    abstract fun buildPublisher(params: PARAMS): PaginatedPublisher<RESULT>

    /**
     * Executes this use case.
     * @param params    use case parameters.
     * @param requestNextOnExecute Request first on execute.
     * @param onSubscribe   Function to run on observer subscribed.
     * @param onNext    Function to run on next result received.
     * @param onError   Function to run on error.
     * @param onComplete    Function to run on complete.
     */
    fun executeWith(
            params: PARAMS,
            requestNextOnExecute: Boolean = true,
            onSubscribe: (() -> Unit)? = null,
            onNext: ((result: List<RESULT>) -> Unit)? = null,
            onError: ((exception: Throwable?) -> Unit)? = null,
            onComplete: (() -> Unit)? = null
    ): PaginatedSubscriber<RESULT> {

        val subscriber = object : PaginatedSubscriber<RESULT>(requestFirstOnSubscribe = requestNextOnExecute) {

            override fun onError(throwable: Throwable?) {
                onError?.invoke(throwable)
            }

            override fun onComplete() {
                onComplete?.invoke()
            }

            override fun onNext(result: List<RESULT>) {
                onNext?.invoke(result)
            }
        }

        val disposable = buildPublisher(params).apply { reset() }
                .subscribeOn(Schedulers.from(taskExecutor))
                .observeOn(postExecutor.scheduler)
                .doOnSubscribe { onSubscribe?.invoke() }
                .subscribeWith(subscriber)

        compDisposables.add(disposable)

        return subscriber
    }

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
            onSubscribe: (() -> Unit)?,
            onNext: ((result: List<RESULT>) -> Unit)?,
            onError: ((exception: Throwable?) -> Unit)?,
            onComplete: (() -> Unit)?
    ) {
        executeWith(
                params = params,
                onSubscribe = onSubscribe,
                onNext = onNext,
                onError = onError,
                onComplete = onComplete
        )
    }

    /**
     * Disposes this interactor.
     */
    fun dispose() {
        if (!compDisposables.isDisposed)
            compDisposables.dispose()
    }
}