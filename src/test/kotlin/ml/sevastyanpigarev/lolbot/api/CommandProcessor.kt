package ml.sevastyanpigarev.lolbot.api

import com.github.sevastyandark.logio.LoggerFactory
import com.google.gson.JsonArray
import ml.sevastyanpigarev.lolbot.VK.VKApi
import java.net.URLEncoder
import java.util.*

object CommandProcessor {
    private val logger = LoggerFactory.getLog("lolbot:commandprocessor")
    fun process(args: JsonArray) {
        if (args[0].asInt != 4)
            return

        var realCmd = ""
        val cmd = args[5].asString
        lateinit var cmdArgs: Array<String>

        if (cmd[0] == '!' && cmd[1] == ' ') {
            val tmpCmd = cmd.substring(2)
            cmdArgs = tmpCmd.split(" ").toTypedArray()
            realCmd = cmdArgs[0]
            println("case 1: $realCmd")
            cmdArgs = Arrays.copyOfRange(cmdArgs, 1, cmdArgs.size)
        }
        else if (cmd[0] == '!' && cmd[1] != ' ')
        {
            val tmpCmd = cmd.substring(1)
            cmdArgs = tmpCmd.split(" ").toTypedArray()
            realCmd = cmdArgs[0]
            println("case 2: $realCmd")
            cmdArgs = Arrays.copyOfRange(cmdArgs, 1, cmdArgs.size)

            // cmdArgs.forEach { println(it) }
        }
            logger.info("Пользователь с id ${args[3]} написал ${args[5]}")

            ModuleRegistry.moduleInstances.forEach {
                val annotation = it.value.javaClass.getAnnotation(Module::class.java)
                if (annotation != null)
                {
                    if (annotation.commands.contains(realCmd))
                    {
                        println(realCmd)
                        val resp = it.value.process_commands(realCmd, cmdArgs, args[3].asString).replace(" ", "%20")
                        if(resp != "")
                            VKApi.method("messages.send?user_id=${args[3]}&message=${URLEncoder.encode(resp, "UTF-8")}")
                    }
                }
            }
        }
}
