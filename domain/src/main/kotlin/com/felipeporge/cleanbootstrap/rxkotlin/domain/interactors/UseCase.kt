package com.felipeporge.cleanbootstrap.rxkotlin.domain.interactors

import com.felipeporge.cleanbootstrap.rxkotlin.domain.executors.PostExecutionThread
import com.felipeporge.cleanbootstrap.rxkotlin.domain.executors.TaskExecutorThread
import rx.Observable
import rx.Subscriber
import rx.functions.Action0
import rx.functions.Action1
import rx.schedulers.Schedulers
import rx.subscriptions.Subscriptions


/**
 * This class represents an Use Case.
 */
abstract class UseCase<RESULT> protected constructor (val taskExecutorThread: TaskExecutorThread, val postExecutorThread: PostExecutionThread) {

    private var subscription = Subscriptions.empty()

    /**
     * Builds an [Observable] which will be used when executing the current [UseCase].
     */
    protected abstract fun buildObservable(): Observable<RESULT>

    /**
     * Executes the use case.
     * @param subscriber    [Subscriber] that will observe the use case [Observable].
     */
    fun execute(subscriber: Subscriber<RESULT>) {
        this.subscription = buildObservable()
                .subscribeOn(Schedulers.from(taskExecutorThread))
                .observeOn(postExecutorThread.scheduler)
                .subscribe(subscriber)
    }

    /**
     * Executes the use case.
     * @param onNext    [Action1] that handles on next data.
     * @param onError   [Action1] that handles occurred errors ([Throwable] instance).
     */
    fun execute(onNext: Action1<RESULT>, onError: Action1<Throwable>) {
        this.subscription = buildObservable()
                .subscribeOn(Schedulers.from(taskExecutorThread))
                .observeOn(postExecutorThread.scheduler)
                .subscribe(onNext, onError)
    }

    /**
     * Executes the use case.
     * @param onNext    [Action1] that handles on next data.
     * @param onError   [Action1] that handles occurred errors ([Throwable] instance).
     * @param onCompleted   [Action0] that handles on completed state.
     */
    fun execute(onNext: Action1<RESULT>, onError: Action1<Throwable>, onCompleted: Action0) {
        this.subscription = buildObservable()
                .subscribeOn(Schedulers.from(taskExecutorThread))
                .observeOn(postExecutorThread.scheduler)
                .subscribe(onNext, onError, onCompleted)
    }


    /**
     * Unsubscribes from current [Subscription].
     */
    fun unsubscribe() {
        if (!subscription.isUnsubscribed) {
            subscription.unsubscribe()
        }
    }

}
