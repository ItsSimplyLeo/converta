package dev.converta.bot

import io.github.cdimascio.dotenv.dotenv
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

fun main() {
    val dotenv = dotenv()
    val token = dotenv["DISCORD_TOKEN"] ?: error("DISCORD_TOKEN not found in .env")

    val jda = JDABuilder.createDefault(token)
        .setActivity(Activity.playing("Converting units with Converta!"))
        .addEventListeners(MessageListener())
        .build()

    jda.awaitReady()
    println("Converta is now online!")
}

class MessageListener : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) return

        val message = event.message.contentRaw.lowercase()

        if (message.startsWith("!convert ")) {
            val args = message.removePrefix("!convert ").trim()

            event.channel.sendMessage("You asked to convert: `$args`").queue()
        }
    }
}