package com.felipeporge.cleanbootstrap.rxkotlin.domain.mappers


/**
 * This class represents a mapper that allows uni-directional transformations.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    12/06/2017
 */
abstract class SingleMapper<FROM, TO> {

    /**
     * Transforms a value.
     * @param value Value to transform.
     * @return Transformed value.
     */
    abstract fun transform(value: FROM): TO

    /**
     * Transforms an array of values.
     * @param value Values to transform.
     * @return Transformed values.
     */
    fun transform(value: List<FROM>) : List<TO> = value.map { transform(it) }
}