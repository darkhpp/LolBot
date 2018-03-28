@file:JvmName("bootstrapper")
package ml.sevastyanpigarev.lolbot

import com.github.sevastyandark.logio.LoggerFactory
import com.google.gson.JsonObject
import ml.sevastyanpigarev.lolbot.VK.VKApi
import ml.sevastyanpigarev.lolbot.utils.ConfigUtils
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File
import java.lang.NullPointerException
import java.net.MalformedURLException


class LBTest
{
    private val logger = LoggerFactory.getLog("lolbot-test:bootstrapper")

    @Test
    @DisplayName("Test 1: Config read without data")
    fun test1()
    {
        logger.info("+--------------------------------------------------+")
        logger.info("+                     LOLBot                       +")
        logger.info("+            Author: @SevastyanDark                +")
        logger.info("+--------------------------------------------------+")
        logger.info("Test 1: Config read without data")
        assertThrows(NullPointerException::class.java, {ConfigUtils.loadConfig(File("C:/"), JsonObject())})
    }

    @Test
    @DisplayName("Test 2: VK Auth with group")
    fun test2()
    {
        logger.info("Test 2: VK Auth with group")
        assertThrows(NotImplementedError::class.java, {VKApi.auth()})
    }

    @Test
    @DisplayName("Test 3: VK Auth without api server")
    fun test3()
    {
        logger.info("Test 3: VK Auth without api server")
        Config.auth_isGroup = false
        assertThrows(MalformedURLException::class.java, {VKApi.auth()})
    }
    @Test
    @DisplayName("Test 4: VK Auth without token")
    fun test4()
    {
        logger.info("Test 4: VK Auth without token")
        Config.auth_isGroup = false
        Config.vk_vkApi = "https://api.vk.com/method"
        assertThrows(IllegalStateException::class.java, {VKApi.auth()})
    }
}