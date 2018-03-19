package ml.sevastyanpigarev.lolbot.api


@Retention(AnnotationRetention.RUNTIME)
annotation class Module(val name: String, val commands: Array<String>)