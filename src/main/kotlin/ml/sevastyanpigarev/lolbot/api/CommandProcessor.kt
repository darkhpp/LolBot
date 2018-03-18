package ml.sevastyanpigarev.lolbot.api

import com.github.sevastyandark.logio.LoggerFactory
import com.google.gson.JsonArray
import ml.sevastyanpigarev.lolbot.VK.VKApi

object CommandProcessor
{
    private val logger = LoggerFactory.getLog("lolbot:commandprocessor")
    fun process(args: JsonArray)
    {
        if(args[0].asInt != 4)
            return

        if(!args[5].asString.startsWith("!")) // TODO
            return
        if(args[2].asInt == 513)
            return

        logger.info("Пользователь с id ${args[3]} написал ${args[5]}")

        ModuleRegistry.moduleInstances.forEach {
            var text = it.value.process_commands(args)
            if(text != "")
            {
                text = text.replace(" ", "%20")
                VKApi.method("messages.send?user_id=${args[3]}&message=$text")
            }
        }
    }

}