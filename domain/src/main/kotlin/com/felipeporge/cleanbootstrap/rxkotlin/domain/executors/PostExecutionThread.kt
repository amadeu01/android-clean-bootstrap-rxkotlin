package com.felipeporge.cleanbootstrap.rxkotlin.domain.executors

import rx.Scheduler


/**
 * This class represents a post execution thread.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    15/05/2017
 */
interface PostExecutionThread {
    val scheduler: Scheduler
}