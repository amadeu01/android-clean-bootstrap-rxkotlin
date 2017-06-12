package com.felipeporge.cleanbootstrap.rxkotlin.domain.mappers


/**
 * This class represents a mapper.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    12/06/2017
 */
abstract class Mapper<FROM, TO> {

    /**
     * Transforms a value.
     * @param value Value to transform.
     * @return Transformed value.
     */
    abstract fun transform(value: FROM): TO

    /**
     * Parses back a value.
     * @param value Value to parse back.
     * @return Value parsed back.
     */
    abstract fun parseBack(value: TO): FROM

    /**
     * Transforms an array of values.
     * @param value Values to transform.
     * @return Transformed values.
     */
    fun transform(value: Array<FROM>) = value.map { transform(it) }

    /**
     * Parses an array back.
     * @param value Values to parse back.
     * @return Values parsed back.
     */
    fun parseBack(value: Array<TO>) = value.map { parseBack(it) }
}