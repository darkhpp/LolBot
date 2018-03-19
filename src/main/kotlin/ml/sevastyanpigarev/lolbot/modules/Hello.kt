package ml.sevastyanpigarev.lolbot.modules

import ml.sevastyanpigarev.lolbot.api.BotModule
import ml.sevastyanpigarev.lolbot.api.Module

@Module("HelloModule", arrayOf("qq", "привет"))
class Hello : BotModule
{
    override fun process_commands(command: String, cmdArgs: Array<String>, sender: String): String {
        if(command.toLowerCase() in arrayOf("qq", "привет", "ку", "здравствуйте"))
            return "Здравствуйте, LOLBot вас слушает."
        return ""
    }

    override fun getModuleName(): String {
        return "HelloModule"
    }

    override fun onEnable(): Boolean {
        return false
    }

}