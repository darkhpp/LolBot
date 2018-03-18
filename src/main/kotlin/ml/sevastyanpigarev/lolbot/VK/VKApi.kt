package ml.sevastyanpigarev.lolbot.VK

import com.github.sevastyandark.logio.LoggerFactory
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import khttp.responses.Response
import ml.sevastyanpigarev.lolbot.Config
import ml.sevastyanpigarev.lolbot.api.CommandProcessor

object VKApi
{
    private val parser = JsonParser()
    private var accessToken = ""
    private var longPollServer = ""
    private var ts = 0
    private var longPollKey = ""
    private val logger = LoggerFactory.getLog("lolbot:vkapi")
    fun auth(): Boolean
    {
        this.accessToken = Config.auth_access_token
        if(!Config.auth_isGroup)
        {
            logger.info("Авторизация в ВК...")
            //***********************************
            val info = method("account.getProfileInfo")
            val respObj = info.get("response").asJsonObject
            val firstName = respObj.get("first_name").asString
            val lastName = respObj.get("last_name").asString
            val screenName = respObj.get("screen_name").asString
            logger.info("Вошли как: $firstName $lastName(vk.com/$screenName)")
            //***********************************
            logger.info("Инициализирую LongPoll...")
            val lp = method("messages.getLongPollServer?need_pts=0&lp_version=2")
            val resp = lp.get("response").asJsonObject
            this.longPollKey = resp.get("key").asString
            this.longPollServer = "https://" + resp.get("server").asString
            this.ts = resp.get("ts").asInt
            logger.info("Тестирую подключение к LongPoll...")
            val testResp = khttp.get(url = longPollServer,
                    params = mapOf("act" to "a_check",
                            "key" to longPollKey,
                            "ts" to ts.toString(),
                            "wait" to "1",
                            "mode" to "2",
                            "version" to "2"))
            if(testResp.text != "")
            {
                logger.info("Успешно!")
                return true
            }
        }
        else
        {
            throw NotImplementedError("Not implemented yet.")
        }
        return false
    }
    fun initLongPoll()
    {
        logger.info("Запускаю поток longpoll...")
        kotlin.concurrent.thread {
            longPollLoop()
        }
    }
    fun longPollLoop()
    {
        logger.info("Приступаю к прослушке")
        val parser = JsonParser()
        lateinit var updates: JsonArray

        while(true)
        {
            val longPoll = khttp.get(url = longPollServer,
                    params = mapOf("act" to "a_check",
                            "key" to longPollKey,
                            "ts" to ts.toString(),
                            "wait" to "25",
                            "mode" to "0",
                            "version" to "2"))
            val rootObject = parser.parse(longPoll.text).asJsonObject

            try
            {
                updates = rootObject.get("updates").asJsonArray
                for(update in updates)
                {

                    val json = parser.parse(update.toString()).asJsonArray
                        kotlin.concurrent.thread { CommandProcessor.process(json) }

                }

            }
            catch(ex: JsonParseException)
            {
                if(rootObject.get("failed").asInt == 1)
                {
                    this.ts = rootObject.get("ts").asInt
                }

                if(rootObject.get("failed").asInt == 2 || rootObject.get("failed").asInt == 3)
                {
                    val lp = method("messages.getLongPollServer?need_pts=0&lp_version=2")
                    println(lp)
                    val resp = lp.get("response").asJsonObject
                    this.longPollKey = resp.get("key").asString
                    this.longPollServer = "https://" + resp.get("server").asString
                    this.ts = resp.get("ts").asInt
                }
            }
            this.ts = rootObject.get("ts").asInt
        }
    }

    fun method(nameAndParams: String): JsonObject
    {
        lateinit var respond: Response
        if(nameAndParams.contains("?"))
        {
            respond = khttp.get("${Config.vk_vkApi}/$nameAndParams&access_token=${this.accessToken}&v=5.73")
        }
        else
        {
            respond = khttp.get("${Config.vk_vkApi}/$nameAndParams?access_token=${this.accessToken}&v=5.73")
        }
        if(respond.statusCode != 200)
            throw RuntimeException("VKApi's respond status code not 200, it is ${respond.statusCode}")
        return parser.parse(respond.text).asJsonObject
    }
}