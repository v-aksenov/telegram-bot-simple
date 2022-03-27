package org.example

import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

fun main() {
    val botToken = System.getProperty("botToken")
    val botUsername = System.getProperty("botUsername")
    val bot = Bot(botToken, botUsername, ::handleMessageFun)
    TelegramBotsApi(DefaultBotSession::class.java).registerBot(bot)
    println("bot started")
}

private val pokemonClient = PokemonClient()

fun handleMessageFun(update: Update): String = try {
    val info = pokemonClient.getPokemonInfo(update.message.text)
    "Вес: ${info.weight}, тип: ${info.types.joinToString(", ") { it.type.name }}"
} catch (e: Exception) {
    println(e.toString())
    "Не щмогла"
}
