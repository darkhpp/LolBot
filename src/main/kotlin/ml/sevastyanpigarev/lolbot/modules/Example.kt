package ml.sevastyanpigarev.lolbot.modules

import com.github.sevastyandark.logio.LoggerFactory
import com.google.gson.JsonArray
import ml.sevastyanpigarev.lolbot.api.BotModule

class Example : BotModule
{
        override fun process_commands(args: JsonArray): String
        {
            if(args[5].asString == "!тест")
                return "Проверка прошла успешно"
            return ""
        }

        override fun onEnable(): Boolean
        {
            getModuleLog().info("123")
            return true
        }

        override fun getModuleName(): String
        {
            return "Example"
        }
}
