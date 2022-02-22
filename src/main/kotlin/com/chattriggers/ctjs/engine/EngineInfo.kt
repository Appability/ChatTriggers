package com.chattriggers.ctjs.engine

import java.util.Deque
import java.util.ArrayDeque
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

object EngineInfo {
    private val loaderStack = object : ThreadLocal<Deque<ILoader>>() {
        override fun initialValue() = ArrayDeque<ILoader>()
    }

    fun getLoader() = loaderStack.get().first

    @OptIn(ExperimentalContracts::class)
    fun <T> withLoader(loader: ILoader, block: () -> T): T {
        contract {
            callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        }

        val deque = this.loaderStack.get()
        deque.addLast(loader)
        return try {
            block()
        } finally {
            deque.removeLast()
        }
    }
}
