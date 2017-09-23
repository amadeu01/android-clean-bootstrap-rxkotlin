package com.felipeporge.cleanbootstrap.rxkotlin.domain.interactors.pagination

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription


/**
 * This class represents a paginated source. This source requests data until receives an empty list.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    21/09/2017
 */
class PaginatedPublisher<T>(
        private val requestBlock: (page: Int) -> Observable<List<T>>
) : Publisher<List<T>>, Disposable {

    var currPage = 1
        private set

    var hasNext = true
        private set

    private var compositeDisposable = CompositeDisposable()

    private var subscribeOn: Scheduler = AndroidSchedulers.mainThread()
    private var observeOn: Scheduler = AndroidSchedulers.mainThread()

    private var doOnSubscribe: (() -> Unit)? = null
    private var onSubscribe: ((Subscription) -> Unit)? = null
    private var onNext: ((List<T>) -> Unit)? = null
    private var onComplete: (() -> Unit)? = null
    private var onError: ((Throwable) -> Unit)? = null

    private var subscription = object : Subscription {

        override fun cancel() {
            compositeDisposable.dispose()
            compositeDisposable.clear()
        }

        override fun request(n: Long) {
            if (!hasNext)
                return

            compositeDisposable.add(
                    Observable
                            .concat(
                                    (1..n).map { requestBlock(currPage++) }
                            )
                            .subscribeOn(subscribeOn)
                            .observeOn(observeOn)
                            .subscribeBy(
                                    onNext = { result ->
                                        hasNext = result.isNotEmpty()
                                        if (hasNext) {
                                            onNext?.invoke(result)
                                        } else {
                                            onComplete?.invoke()
                                            cancel()
                                        }
                                    },
                                    onError = { exception ->
                                        onError?.invoke(exception)
                                    }
                            )
            )

        }
    }

    /**
     * Does something on subscribe.
     * @param block Block to execute.
     */
    fun doOnSubscribe(block: () -> Unit): PaginatedPublisher<T> {
        doOnSubscribe = block
        return this
    }

    /**
     * Defines the scheduler to subscribe on.
     * @return scheduler    Scheduler to use.
     */
    fun subscribeOn(scheduler: Scheduler): PaginatedPublisher<T> {
        subscribeOn = scheduler
        return this
    }

    /**
     * Defines the scheduler to observe on.
     * @param scheduler Scheduler to use.
     */
    fun observeOn(scheduler: Scheduler): PaginatedPublisher<T> {
        observeOn = scheduler
        return this
    }

    /**
     * Subscribes with a subscriber and returns a disposable.
     * @param subscriber    Subscriber instance.
     * @return  Disposable.
     */
    fun subscribeWith(subscriber: Subscriber<in List<T>>?): Disposable {
        onSubscribe = { sub -> subscriber?.onSubscribe(sub) }
        onNext = { items -> subscriber?.onNext(items) }
        onError = { errors -> subscriber?.onError(errors) }
        onComplete = { subscriber?.onComplete() }

        onSubscribe?.invoke(subscription)
        doOnSubscribe?.invoke()

        return compositeDisposable
    }

    /**
     * Resets this publisher.
     */
    fun reset() {
        currPage = 1
        hasNext = true
    }

    override fun subscribe(subscriber: Subscriber<in List<T>>?) {
        subscribeWith(subscriber)
    }


    override fun isDisposed(): Boolean {
        return compositeDisposable.isDisposed
    }

    override fun dispose() {
        compositeDisposable.dispose()
    }
}