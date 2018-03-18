package ml.sevastyanpigarev.lolbot.modules

import com.google.gson.JsonArray
import ml.sevastyanpigarev.lolbot.api.BotModule

class Hello : BotModule
{
    override fun process_commands(args: JsonArray): String {
        if(args[5].asString.toLowerCase() in arrayOf("!qq", "!привет", "!ку", "!здравствуйте"))
            return "Здравствуйте, LOLBot вас слушает."
        return ""
    }

    override fun getModuleName(): String {
        return "HelloModule"
    }

    override fun onEnable(): Boolean {
        return true
    }

}