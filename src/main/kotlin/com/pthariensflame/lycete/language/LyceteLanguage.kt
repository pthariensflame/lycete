package com.pthariensflame.lycete.language

import com.oracle.truffle.api.TruffleLanguage
import com.oracle.truffle.api.instrumentation.ProvidedTags
import com.oracle.truffle.api.instrumentation.StandardTags
import com.oracle.truffle.api.utilities.AssumedValue
import com.pthariensflame.lycete.AssumedValues.getValue
import com.pthariensflame.lycete.AssumedValues.setValue
import com.pthariensflame.lycete.execution.LyceteFileDetector
import org.jetbrains.annotations.Contract

@TruffleLanguage.Registration(
    id = "lycete",
    name = "Lycete",
    implementationName = "Reference Truffle Lycete",
    version = "0.0.1",
    defaultMimeType = LyceteFileDetector.MIME_TYPE,
    characterMimeTypes = [
        LyceteFileDetector.MIME_TYPE,
    ],
    byteMimeTypes = [],
    interactive = false,
    internal = false,
    dependentLanguages = [],
    fileTypeDetectors = [
        LyceteFileDetector::class,
    ],
    services = [],
    contextPolicy = TruffleLanguage.ContextPolicy.EXCLUSIVE,
)
@ProvidedTags(
    StandardTags.RootTag::class,
    StandardTags.RootBodyTag::class,
    StandardTags.ExpressionTag::class,
    StandardTags.CallTag::class,
    StandardTags.ReadVariableTag::class,
)
public class LyceteLanguage : TruffleLanguage<LyceteLanguage.Context>() {
    public class Context(
        env: Env
    ) {
        public var environment: Env by AssumedValue<Env>(env)
            internal set

    }

    @Contract("_ -> new", pure = true)
    override fun createContext(env: Env): Context =
        Context(env)

    @Contract("_ -> true", mutates = "param1")
    override fun patchContext(context: Context, newEnv: Env): Boolean {
        context.environment = newEnv
        return true
    }
}