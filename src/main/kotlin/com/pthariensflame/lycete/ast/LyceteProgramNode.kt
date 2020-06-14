package com.pthariensflame.lycete.ast

import com.oracle.truffle.api.dsl.*
import com.oracle.truffle.api.frame.FrameDescriptor
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.instrumentation.*
import com.oracle.truffle.api.interop.InteropLibrary
import com.oracle.truffle.api.library.ExportLibrary
import com.oracle.truffle.api.library.ExportMessage
import com.oracle.truffle.api.nodes.NodeInfo
import com.oracle.truffle.api.nodes.RootNode
import com.pthariensflame.lycete.language.LyceteLanguage
import com.pthariensflame.lycete.language.LyceteRuntimeTypeSystem
import org.jetbrains.annotations.ApiStatus
import org.jetbrains.annotations.Contract

@GenerateNodeFactory
@GenerateWrapper
@Introspectable
@NodeInfo(
    shortName = "program",
    description = """A Lycete program."""
)
@TypeSystemReference(LyceteRuntimeTypeSystem::class)
@ExportLibrary(InteropLibrary::class)
@ApiStatus.NonExtendable
@NodeChildren(
    NodeChild(
        "body",
        type = LyceteProgramBodyNode::class,
        executeWith = [],
    )
)
@NodeFields(
    NodeField(
        name = "lyceteLanguage",
        type = LyceteLanguage::class,
    )
)
public abstract class LyceteProgramNode
@JvmOverloads protected constructor(
    lang: LyceteLanguage,
    frameDesc: FrameDescriptor? = null,
) : RootNode(lang, frameDesc), LyceteNodeInterface {

    protected constructor(other: LyceteProgramNode) : this(
        other.lyceteLanguage,
        other.frameDescriptor,
    )

    public companion object {
        @Contract("_, _, _ -> new")
        @JvmStatic
        @JvmOverloads
        public fun create(
            lang: LyceteLanguage,
            frameDesc: FrameDescriptor? = null,
            body: LyceteProgramBodyNode
        ): LyceteProgramNode =
            LyceteProgramNodeFactory.create(lang, frameDesc, body, lang)
    }

    @Contract("_ -> new")
    override fun createWrapper(probe: ProbeNode): InstrumentableNode.WrapperNode =
        LyceteProgramNodeWrapper(this, this, probe)

    @Contract("-> true", pure = true)
    override fun isInstrumentable(): Boolean = true

    @Contract("-> this", pure = true)
    override fun getNodeObject(): LyceteProgramNode = this

    @Contract("_ -> null")
    @ExportMessage.Ignore
    abstract override fun execute(frame: VirtualFrame): Nothing?

    override fun hasTag(tag: Class<out Tag>): Boolean =
        tag.kotlin == StandardTags.RootTag::class || super.hasTag(tag)

    protected abstract val body: LyceteProgramBodyNode

    public abstract val lyceteLanguage: LyceteLanguage

    @Specialization
    protected fun exec(@Suppress("UNUSED_PARAMETER") bodyResult: Nothing?): Nothing? = null

    @ExportMessage
    override fun hasLanguage(): Boolean = true

    @ExportMessage
    override fun toDisplayString(allowSideEffects: Boolean): Any = this

    @get:ExportMessage
    override val language: Class<LyceteLanguage>
        get() = LyceteLanguage::class.java
}
