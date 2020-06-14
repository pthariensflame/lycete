package com.pthariensflame.lycete.ast

import com.oracle.truffle.api.dsl.ReportPolymorphism
import com.oracle.truffle.api.instrumentation.InstrumentableNode
import com.oracle.truffle.api.instrumentation.ProbeNode
import com.oracle.truffle.api.interop.TruffleObject
import com.oracle.truffle.api.library.ExportMessage
import com.oracle.truffle.api.nodes.NodeInfo
import com.pthariensflame.lycete.language.LyceteLanguage
import org.jetbrains.annotations.ApiStatus
import org.jetbrains.annotations.Contract

@ReportPolymorphism
@NodeInfo(
    language = "Lycete",
)
@ApiStatus.NonExtendable
public interface LyceteNodeInterface : InstrumentableNode, TruffleObject {
    @Contract("_ -> new")
    override fun createWrapper(probe: ProbeNode): InstrumentableNode.WrapperNode

    @Contract(pure = true)
    override fun isInstrumentable(): Boolean

    @JvmDefault
    @Contract("-> this", pure = true)
    override fun getNodeObject(): LyceteNodeInterface

    @Contract("-> true", pure = true)
    public fun hasLanguage(): Boolean

    public fun toDisplayString(allowSideEffects: Boolean): Any

    @get:Contract(pure = true)
    public val language: Class<LyceteLanguage>
}
