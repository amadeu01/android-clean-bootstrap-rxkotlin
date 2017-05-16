package com.felipeporge.cleanbootstrap.rxkotlin.di


/**
 * This interface describes a class that has a component.
 * @author  Felipe Porge Xavier - <a href="http://www.felipeporge.com" target="_blank">www.felipeporge.com</a>
 * @date    15/05/2017
 */
interface HasComponent<COMPONENT> {

    val component: COMPONENT
}