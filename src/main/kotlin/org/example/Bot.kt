package org.example

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class Bot(
    private val botToken: String,
    private val botUsername: String,
    private val handleMessage: (u: Update) -> String
) : TelegramLongPollingBot() {

    override fun getBotUsername() = botUsername

    override fun getBotToken() = botToken

    override fun onUpdateReceived(update: Update) {
        sendApiMethod(SendMessage(update.message.chatId.toString(), handleMessage(update)))
    }
}