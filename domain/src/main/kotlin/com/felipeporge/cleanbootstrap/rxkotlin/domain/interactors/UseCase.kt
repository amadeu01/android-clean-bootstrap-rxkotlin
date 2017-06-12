package com.felipeporge.cleanbootstrap.rxkotlin.domain.interactors

import com.felipeporge.cleanbootstrap.rxkotlin.domain.executors.PostExecution
import com.felipeporge.cleanbootstrap.rxkotlin.domain.executors.TaskExecutor
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


/**
 * This class represents an use case.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    27/05/2017
 */
abstract class UseCase<in PARAMS, RESULT>(val taskExecutor: TaskExecutor, val postExecutor: PostExecution) {

    val compDisposables = CompositeDisposable()

    /**
     * Builds an use case observer.
     * @param params    Use case parameters.
     * @return  Built observable for this use case.
     */
    abstract fun buildObservable(params: PARAMS): Observable<RESULT>

    /**
     * Executes this use case.
     * @param observer  Disposable observer instance.
     * @param params    use case parameters.
     */
    fun execute(observer: DisposableObserver<RESULT>, params: PARAMS) {
        val disposable = buildObservable(params)
                .subscribeOn(Schedulers.from(taskExecutor))
                .observeOn(postExecutor.scheduler)
                .subscribeWith(observer)

        compDisposables.add(disposable)
    }


    /**
     * Disposes the composite disposables
     */
    fun dispose() {
        if(!compDisposables.isDisposed)
            compDisposables.dispose()
    }
}