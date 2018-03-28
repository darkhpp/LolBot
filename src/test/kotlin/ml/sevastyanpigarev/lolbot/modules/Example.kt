package ml.sevastyanpigarev.lolbot.modules

import ml.sevastyanpigarev.lolbot.api.BotModule
import ml.sevastyanpigarev.lolbot.api.Module

@Module("Example", arrayOf("тест"))
class Example : BotModule
{
        override fun process_commands(command: String, cmdArgs: Array<String>, sender: String): String
        {
            if(cmdArgs.size == 0)
                return "Команда $command без аргументов"
            else
            {
                val sb = StringBuilder()
                sb.append("Команда: $command с аргументами: ")
                for (arg in cmdArgs)
                    sb.append("$arg, ")
                return sb.toString()
            }
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
