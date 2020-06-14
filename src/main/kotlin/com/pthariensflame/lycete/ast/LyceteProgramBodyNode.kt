package com.pthariensflame.lycete.ast

import com.oracle.truffle.api.dsl.*
import com.oracle.truffle.api.frame.VirtualFrame
import com.oracle.truffle.api.instrumentation.*
import com.oracle.truffle.api.interop.InteropLibrary
import com.oracle.truffle.api.library.ExportLibrary
import com.oracle.truffle.api.library.ExportMessage
import com.oracle.truffle.api.nodes.BlockNode
import com.oracle.truffle.api.nodes.NodeInfo
import com.pthariensflame.lycete.language.LyceteLanguage
import org.jetbrains.annotations.ApiStatus
import org.jetbrains.annotations.Contract

@GenerateNodeFactory
@GenerateWrapper
@Introspectable
@NodeInfo(
    shortName = "program body",
    description = """The body of a Lycete program.""",
)
@ExportLibrary(InteropLibrary::class)
@ApiStatus.NonExtendable
@NodeChildren(
    NodeChild(
        "decls",
        type = BlockNode::class,
        executeWith = [],
    )
)
public abstract class LyceteProgramBodyNode
protected constructor(
    lang: LyceteLanguage,
) : LyceteExecutableNode(lang) {
    protected constructor(other: LyceteProgramBodyNode) : this(
        (other.rootNode as LyceteProgramNode).lyceteLanguage,
    )

    public companion object {
        @Contract("_ -> new")
        @JvmStatic
        public fun create(
            lang: LyceteLanguage,
        ): LyceteProgramBodyNode =
            LyceteProgramBodyNodeFactory.create(lang)
    }

    @Contract("_ -> new")
    override fun createWrapper(probe: ProbeNode): InstrumentableNode.WrapperNode =
        LyceteProgramBodyNodeWrapper(this, this, probe)

    @Contract("-> this", pure = true)
    override fun getNodeObject(): LyceteProgramBodyNode = this

    @Contract("_ -> null")
    @ExportMessage.Ignore
    abstract override fun execute(frame: VirtualFrame): Nothing?

    override fun hasTag(tag: Class<out Tag>): Boolean =
        tag.kotlin == StandardTags.RootBodyTag::class || super.hasTag(tag)

    @Specialization
    protected fun exec(blockResult: Nothing?): Nothing? {
        // TODO
        return null
    }
}