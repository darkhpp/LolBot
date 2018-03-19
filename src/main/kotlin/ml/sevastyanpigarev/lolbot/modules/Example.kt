package ml.sevastyanpigarev.lolbot.modules

import ml.sevastyanpigarev.lolbot.api.BotModule
import ml.sevastyanpigarev.lolbot.api.Module

@Module("Example", arrayOf("тест"))
class Example : BotModule
{
        override fun process_commands(command: String, cmdArgs: Array<String>, sender: String): String
        {
            if(command.toLowerCase() == "!тест")
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
