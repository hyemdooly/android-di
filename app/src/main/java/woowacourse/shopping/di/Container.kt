package woowacourse.shopping.di

import kotlin.reflect.KClass

object Container {
    private val instances = mutableMapOf<KClass<*>, Any>()
    fun addInstance(type: KClass<*>, instance: Any) {
        instances[type] = instance
    }

    fun getInstance(type: KClass<*>): Any =
        instances[type] ?: throw NoSuchElementException("Unknown Instance")

    fun clear() {
        instances.clear()
    }
}