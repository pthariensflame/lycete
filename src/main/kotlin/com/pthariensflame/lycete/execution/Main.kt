package com.pthariensflame.lycete.execution

import com.github.ajalt.clikt.core.CliktCommand

public fun main(args: Array<String>): Unit = Main().main(args)

private class Main : CliktCommand(
    name = "lycete",
    help = """""",
    epilog = """""",
    printHelpOnEmptyArgs = true,
    treatUnknownOptionsAsArgs = false,
    autoCompleteEnvvar = "LYCETE_GENERATE_SHELL_AUTOCOMPLETE",
) {
    override fun run() {
        TODO("Not yet implemented")
    }
}

