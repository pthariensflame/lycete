package com.pthariensflame.lycete

import com.oracle.truffle.api.utilities.AssumedValue
import kotlin.reflect.KProperty

public object AssumedValues {
    @JvmStatic
    @Suppress("NOTHING_TO_INLINE")
    public inline operator fun <T> AssumedValue<T>.getValue(r: Any?, p: KProperty<*>): T =
        this.get()!!

    @JvmStatic
    @Suppress("NOTHING_TO_INLINE")
    public inline operator fun <T> AssumedValue<T>.setValue(r: Any?, p: KProperty<*>, v: T): Unit =
        this.set(v)
}