package com.felipeporge.cleanbootstrap.rxkotlin.domain.interactors.pagination

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber


/**
 * This class represents a paginated source. This source requests data until receives an empty list.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    21/09/2017
 */
class PaginatedPublisher<T>(
        private val requestBlock: (page: Int) -> Observable<List<T>>
) : Publisher<List<T>> {

    var currPage = 1
        private set

    var hasNext = true
        private set

    private var flow =
            Flowable
                    .fromCallable {
                        requestBlock(currPage++)
                                .blockingFirst()
                    }
                    .repeat()
                    .map { list ->
                        hasNext = list.isNotEmpty()
                        list // return
                    }
                    .takeUntil { !hasNext }

    /**
     * Does something on subscribe.
     * @param block Block to execute.
     */
    fun doOnSubscribe(block: () -> Unit): PaginatedPublisher<T> {
        return this.apply { flow = flow.doOnSubscribe { block() } }
    }

    /**
     * Defines the scheduler to subscribe on.
     * @return scheduler    Scheduler to use.
     */
    fun subscribeOn(scheduler: Scheduler): PaginatedPublisher<T> {
        return this.apply { flow = flow.subscribeOn(scheduler) }
    }

    /**
     * Defines the scheduler to observe on.
     * @param scheduler Scheduler to use.
     */
    fun observeOn(scheduler: Scheduler): PaginatedPublisher<T> {
        return this.apply { flow = flow.observeOn(scheduler) }
    }

    /**
     * Subscribes with a subscriber and returns a disposable.
     * @param subscriber    Subscriber instance.
     * @return  Disposable.
     */
    fun subscribeWith(subscriber: Subscriber<in List<T>>?): Disposable {
        return flow.subscribe(
                { result -> subscriber?.onNext(result) }, // OnNext
                { exception -> subscriber?.onError(exception) }, // OnError
                { subscriber?.onComplete() }, // OnComplete
                { subscription -> subscriber?.onSubscribe(subscription) } // OnSubscribe
        )
    }

    /**
     * Resets this publisher.
     */
    fun reset() {
        currPage = 1
        hasNext = true
    }

    override fun subscribe(subscriber: Subscriber<in List<T>>?) {
        flow.subscribe(subscriber)
    }
}