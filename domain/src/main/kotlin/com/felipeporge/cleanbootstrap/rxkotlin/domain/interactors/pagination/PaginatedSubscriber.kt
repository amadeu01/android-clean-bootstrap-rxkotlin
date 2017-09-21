package com.felipeporge.cleanbootstrap.rxkotlin.domain.interactors.pagination

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription


/**
 * This class represents a paginated subscriber that allows to request pages stopping the flow.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    21/09/2017
 */
abstract class PaginatedSubscriber<T>(
        private val requestFirstOnSubscribe: Boolean = true
) : Subscriber<List<T>> {

    private var subscription: Subscription? = null

    override fun onSubscribe(s: Subscription?) {
        subscription = s?.apply {
            if(requestFirstOnSubscribe) request(1)
        }
    }

    /**
     * Requests next page.
     * @param pagesCount    The quantity of pages to request.
     */
    fun requestNext(pagesCount: Long = 1) {
        subscription?.request(pagesCount)
    }
}