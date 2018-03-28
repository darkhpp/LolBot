package ml.sevastyanpigarev.lolbot.api

import com.github.sevastyandark.logio.LoggerFactory
import org.reflections.Reflections


object ModuleRegistry
{
    private val logger = LoggerFactory.getLog("lolbot:moduleregistry")

    var moduleInstances: MutableMap<String, BotModule> = mutableMapOf()

    fun register()
    {
        logger.info("Загружаю модули...")
        val reflections = Reflections("ml.sevastyanpigarev.lolbot")
        val classes = reflections.getSubTypesOf(BotModule::class.java)
        classes.forEach {
            moduleInstances[it.name] = it.newInstance()
        }

        moduleInstances.forEach {
            logger.info("* -> ${it.value.getModuleName()}...")
            it.value.onEnable()
        }

        logger.info("Всего загружено ${moduleInstances.size} модуля/модулей.")
    }
}