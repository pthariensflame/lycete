package com.pthariensflame.lycete.execution

import com.oracle.truffle.api.TruffleFile
import org.jetbrains.annotations.Contract
import java.nio.charset.Charset

public class LyceteFileDetector : TruffleFile.FileTypeDetector {
    public companion object {
        public const val MIME_TYPE: String = "text/x-lycete"
    }

    override fun findMimeType(file: TruffleFile): String? {
        // TODO
        return null
    }

    @Contract(pure = true)
    override fun findEncoding(file: TruffleFile): Charset =
        Charsets.UTF_8
}