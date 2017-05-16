package com.felipeporge.cleanbootstrap.rxkotlin.presentation.models

/**
 * This class represents an error on presentation layer.
 * @author Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @since 04/05/17
 */
class ErrorModel private constructor(){

    var isAdvice = true
        private set

    var isRetry = false
        private set

    var runnable: Runnable? = null
        private set

    var message: String? = null
        private set

    /**
     * Constructor method for an error that allows retry.
     * @param message   Error message.
     */
    constructor(isAdvice: Boolean, retry: Boolean, message: String) : this() {
        this.isAdvice = isAdvice
        this.isRetry = retry
        this.message = message
    }

    /**
     * Constructor method for an error that does not allows retry.
     * @param isAdvice  Is this error only an advice?
     * @param message   Error message.
     * @param runnable  Runnable to execute after show.
     */
    constructor(isAdvice: Boolean, message: String, runnable: Runnable): this() {
        this.isAdvice = isAdvice
        this.isRetry = false
        this.message = message
        this.runnable = runnable
    }

    /**
     * Executes the runnable.
     */
    fun execute() {
        runnable?.run()
    }
}
