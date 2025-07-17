package dev.converta.bot.listener

import dev.converta.bot.converter.LengthConverter
import dev.converta.bot.converter.TemperatureConverter
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import kotlin.math.round

class SlashCommandListener : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name != "convert") return

        when (event.subcommandName) {
            "temperature" -> handleTemperature(event)
            "length" -> handleLength(event)
        }
    }

    private fun handleLength(event: SlashCommandInteractionEvent) {
        val value = event.getOption("value")?.asDouble ?: return
        val fromUnit = event.getOption("from")?.asString ?: return
        val toUnit = event.getOption("to")?.asString ?: return

        val result = LengthConverter.convert(value, fromUnit, toUnit)
        if (result == null) {
            event.reply("Something went wrong with the conversion.").setEphemeral(true).queue()
            return
        }

        val rounded = (round(result * 100)) / 100.0
        event.reply(":white_check_mark: `$value ${fromUnit.uppercase()}` is equal to `$rounded ${toUnit.uppercase()}`").queue()
    }

    private fun handleTemperature(event: SlashCommandInteractionEvent) {
        val value = event.getOption("value")?.asDouble ?: return
        val from = event.getOption("from")?.asString ?: return
        val to = event.getOption("to")?.asString ?: return

        val result = TemperatureConverter.convert(value, from, to)
        if (result == null) {
            event.reply("Invalid conversion path.").setEphemeral(true).queue()
            return
        }

        val rounded = (round(result * 100)) / 100.0
        event.reply(":white_check_mark: `$value°${from.uppercase()}` is equal to `$rounded°${to.uppercase()}`").queue()
    }
}