package dev.converta.bot.listener

import dev.converta.bot.ConvertaBot
import dev.converta.bot.converter.DataConverter
import dev.converta.bot.converter.LengthConverter
import dev.converta.bot.converter.TemperatureConverter
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.math.round

class SlashCommandListener(private val convertaBot: ConvertaBot) : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        when (event.name) {
            "convert" -> {
                when (event.subcommandName) {
                    "data" -> handleData(event)
                    "temperature" -> handleTemperature(event)
                    "length" -> handleLength(event)
                }
            }
            "convertabot" -> {
                if (event.subcommandName == "about") {
                    handleAbout(event)
                }
            }
        }
    }

    private fun handleAbout(event: SlashCommandInteractionEvent) {
        val uptime = Duration.between(convertaBot.startupTime, Instant.now())
        val formattedUptime = formatDuration(uptime)
        val guildCount = convertaBot.jda.guilds.size
        val githubUrl = "https://github.com/ItsSimplyLeo/converta"
        val inviteUrl = "https://discord.com/oauth2/authorize?client_id=YOUR_CLIENT_ID&scope=bot%20applications.commands&permissions=8"

        val embed = EmbedBuilder()
            .setTitle("🤖 About Converta")
            .setDescription("A smart Discord bot that helps you convert values between units — temperature, length, and more.")
            .addField("Version", "v1.0.0", true)
            .addField("Developer", "ItsSimplyLeo", true)
            .addField("Uptime", formattedUptime, true)
            .addField("Guilds", guildCount.toString(), true)
            .addField("Supported Conversions", "🌡 Temperature\n📏 Length", false)
            .addField("GitHub", "[ItsSimplyLeo/converta]($githubUrl)", true)
            .addField("Invite", "[Click here to invite me!]($inviteUrl)", true)
            .setFooter("Made with ❤️ using Kotlin + JDA")
            .setColor(0x00BFFF)
            .build()

        event.replyEmbeds(embed).queue()
    }

    private fun handleData(event: SlashCommandInteractionEvent) {
        val value = event.getOption("value")?.asDouble
        val from = event.getOption("from")?.asString
        val to = event.getOption("to")?.asString
        if (value == null || from == null || to == null) {
            event.reply("Missing required options.").setEphemeral(true).queue()
            return
        }
        val result = DataConverter.convert(value, from, to)
        if (result == null) {
            event.reply("Invalid unit selection.").setEphemeral(true).queue()
            return
        }
        event.reply("${value.format()} $from = ${result.format()} $to").queue()
    }


    private fun formatDuration(duration: Duration): String {
        val days = duration.toDays()
        val hours = duration.minus(days, ChronoUnit.DAYS).toHours()
        val minutes = duration.minus(days, ChronoUnit.DAYS).minus(hours, ChronoUnit.HOURS).toMinutes()
        val seconds = duration.minus(days, ChronoUnit.DAYS).minus(hours, ChronoUnit.HOURS).minus(minutes, ChronoUnit.MINUTES).seconds

        return buildString {
            if (days > 0) append("${days}d ")
            if (hours > 0) append("${hours}h ")
            if (minutes > 0) append("${minutes}m ")
            append("${seconds}s")
        }.trim()
    }

    private fun handleLength(event: SlashCommandInteractionEvent) {
        val value = event.getOption("value")?.asDouble ?: run {
            event.reply("Please provide a valid number for value.").setEphemeral(true).queue()
            return
        }
        val fromUnit = event.getOption("from")?.asString ?: run {
            event.reply("Please specify the unit to convert from.").setEphemeral(true).queue()
            return
        }
        val toUnit = event.getOption("to")?.asString ?: run {
            event.reply("Please specify the unit to convert to.").setEphemeral(true).queue()
            return
        }

        val result = LengthConverter.convert(value, fromUnit.lowercase(), toUnit.lowercase())
        if (result == null) {
            event.reply("Something went wrong with the conversion. Supported units: meters, kilometers, miles, feet, inches, cm, mm, etc.").setEphemeral(true).queue()
            return
        }

        val rounded = (round(result * 100)) / 100.0
        event.reply(":white_check_mark: `$value ${fromUnit.uppercase()}` is equal to `$rounded ${toUnit.uppercase()}`").queue()
    }

    private fun handleTemperature(event: SlashCommandInteractionEvent) {
        val value = event.getOption("value")?.asDouble ?: run {
            event.reply("Please provide a valid number for value.").setEphemeral(true).queue()
            return
        }
        val from = event.getOption("from")?.asString?.lowercase() ?: run {
            event.reply("Please specify the unit to convert from.").setEphemeral(true).queue()
            return
        }
        val to = event.getOption("to")?.asString?.lowercase() ?: run {
            event.reply("Please specify the unit to convert to.").setEphemeral(true).queue()
            return
        }

        val result = TemperatureConverter.convert(value, from, to)
        if (result == null) {
            event.reply("Invalid conversion path. Supported units: Celsius, Fahrenheit, Kelvin (c, f, k)").setEphemeral(true).queue()
            return
        }

        val rounded = (round(result * 100)) / 100.0
        event.reply(":white_check_mark: `$value°${from.uppercase()}` is equal to `$rounded°${to.uppercase()}`").queue()
    }

    // helper for formatting numbers
    private fun Double.format(): String =
        if (this == this.toLong().toDouble()) "%d".format(this.toLong())
        else "%.6f".format(this).trimEnd('0').trimEnd('.')

}
