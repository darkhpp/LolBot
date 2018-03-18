@file:JvmName("bootstrapper")
package ml.sevastyanpigarev.lolbot

import com.github.sevastyandark.logio.LoggerFactory
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import ml.sevastyanpigarev.lolbot.VK.VKApi
import ml.sevastyanpigarev.lolbot.api.ModuleRegistry
import ml.sevastyanpigarev.lolbot.utils.ConfigUtils
import java.io.File
import org.apache.commons.io.FileUtils



fun main(args: Array<String>)
{
    val startUpTime = System.nanoTime()
    /* Получаем логгер */
    val logger = LoggerFactory.getLog("lolbot:bootstrapper")
    /* Выводим приветствие */
    logger.info("+--------------------------------------------------+")
    logger.info("+                     LOLBot                       +")
    logger.info("+            Author: @SevastyanDark                +")
    logger.info("+--------------------------------------------------+")
    logger.info("LOLBot запускается...")
    logger.info("Загружаю конфиг...")
    /* Подгружаем конфиг */
    /* Рабочая директория (где лежит jar или exe файл) */
    val path = System.getProperty("user.dir")
    /* Пытаемся открыть файл */
    val config = File(path + "/config.json")
    if(config.exists())
    {
        /* Если существует, то проверяем валидность и загружаем данные */
        if(!ConfigUtils.validateJson(config.readText()))
        {
            logger.critical("JSON не валидный!")
            System.exit(1)
        }
        val parser = JsonParser()
        val jsonElement = parser.parse(config.readText())
        /* Получаем главный объект */
        val rootObject = jsonElement.asJsonObject
        /* Загружаем конфиг */
        ConfigUtils.loadConfig(config, rootObject)
        /* Авторизуемся в ВК */
        VKApi.auth()
        /* Загружаем модули */
        ModuleRegistry.register()
        logger.info("Загрузился в ${(System.nanoTime() - startUpTime) / 1000000000} секунд(у/ы).")
        /* Запускаем LongPoll */
        VKApi.initLongPoll()
    }
    else
    {
        /* Создвем конфиг */
        val inputUrl = Config.javaClass.getResource("/config_template.json")
        val dest = File("$path/config.json")
        FileUtils.copyURLToFile(inputUrl, dest)
        logger.info("Создан файл config.json, впишите туда свои данные.")
    }
}