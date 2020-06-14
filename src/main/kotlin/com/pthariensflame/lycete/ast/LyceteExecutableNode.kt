package com.pthariensflame.lycete.ast

import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.interop.InteropLibrary
import com.oracle.truffle.api.library.ExportLibrary
import com.oracle.truffle.api.library.ExportMessage
import com.oracle.truffle.api.nodes.ExecutableNode
import com.pthariensflame.lycete.language.LyceteLanguage
import org.jetbrains.annotations.ApiStatus
import org.jetbrains.annotations.Contract

@ExportLibrary(InteropLibrary::class)
@ApiStatus.NonExtendable
public abstract class LyceteExecutableNode
protected constructor(
    lang: LyceteLanguage,
) : ExecutableNode(lang), LyceteNodeInterface {
    @Contract("-> true", pure = true)
    override fun isInstrumentable(): Boolean = true

    @Contract("-> this", pure = true)
    abstract override fun getNodeObject(): LyceteExecutableNode

    @ExportMessage.Ignore
    abstract override fun execute(frame: VirtualFrame): Any?

    @ExportMessage
    override fun hasLanguage(): Boolean = true

    @ExportMessage
    override fun toDisplayString(allowSideEffects: Boolean): Any = this

    @get:ExportMessage
    override val language: Class<LyceteLanguage>
        get() = LyceteLanguage::class.java
}