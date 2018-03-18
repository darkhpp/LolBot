package ml.sevastyanpigarev.lolbot.api

import com.github.sevastyandark.logio.Logger
import com.github.sevastyandark.logio.LoggerFactory
import com.google.gson.JsonArray

interface BotModule
{

        fun process_commands(args: JsonArray): String
        fun getModuleName(): String
        fun onEnable(): Boolean
        fun getModuleLog(): Logger = LoggerFactory.getLog("lolbot:module/${getModuleName()}")

}
