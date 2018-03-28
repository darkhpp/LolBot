package ml.sevastyanpigarev.lolbot.utils

import com.github.sevastyandark.logio.LoggerFactory
import com.google.gson.Gson
import com.google.gson.JsonObject
import ml.sevastyanpigarev.lolbot.Config
import java.io.File

object ConfigUtils
{
    private val logger = LoggerFactory.getLog("ConfigUtils")
    fun validateJson(json: String): Boolean
    {
        val gson = Gson()
        try
        {
            gson.fromJson(json, Any::class.java)
            return true
        }
        catch (ex: com.google.gson.JsonSyntaxException)
        {
            return false
        }
    }
    fun loadConfig(config: File, rootObject: JsonObject)
    {
        val authObj = rootObject.getAsJsonObject("auth")
        val botObj = rootObject.getAsJsonObject("bot")
        val vkObj = rootObject.getAsJsonObject("vk")
        /* Читаем данные */
        /* Auth */
        Config.auth_access_token = authObj.get("access_token").asString
        Config.auth_isGroup = authObj.get("isGroup").asBoolean
        /* Bot */
        Config.bot_logSettings = botObj.get("logSettings").asBoolean
        Config.bot_sendLogsToUser = botObj.get("sendLogsToUser").asBoolean
        /* VK */
        Config.vk_vkApi = vkObj.get("vkApi").asString
        //*************************************
        /* Если включено логгирование настроек, то пишем */
        if(!Config.bot_logSettings)
            return
        // TODO: логгирование

    }
}