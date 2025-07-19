package dev.converta.bot.listener

import dev.converta.bot.ConvertaBot
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
                convertaBot.converters.filter { it.name().lowercase() == event.subcommandName }.getOrElse(0) {
                    event.reply("Unknown conversion type.").setEphemeral(true).queue()
                    return
                }.let { converter ->
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

                    val result = converter.convert(value, fromUnit, toUnit)
                    if (result == null) {
                        event.reply("Invalid conversion path. Supported units: ${converter.getChoices().joinToString(", ") { it.name }}").setEphemeral(true).queue()
                        return
                    }

                    val rounded = (round(result * 100)) / 100.0
                    event.reply(":white_check_mark: `$value ${fromUnit.uppercase()}` is equal to `$rounded ${toUnit.uppercase()}`").queue()
                }
            }
            "convertabot" -> {
                when (event.subcommandName) {
                    event.subcommandName -> {
                        handleAbout(event)
                    }
                    event.subcommandName -> {
                        handleInvite(event)
                    }
                    else -> {
                        event.reply("Unknown subcommand.").setEphemeral(true).queue()
                    }
                }
            }
        }
    }

    private fun handleAbout(event: SlashCommandInteractionEvent) {
        val uptime = Duration.between(convertaBot.startupTime, Instant.now())
        val formattedUptime = formatDuration(uptime)
        val guildCount = convertaBot.jda.guilds.size

        val embed = EmbedBuilder()
            .setTitle("🤖 About Converta")
            .setDescription("A smart Discord bot that helps you convert values between units — temperature, length, and more.")
            .addField("Version", "v1.0.0", true)
            .addField("Developer", "ItsSimplyLeo", true)
            .addField("Uptime", formattedUptime, true)
            .addField("Guilds", guildCount.toString(), true)
            .addField("Supported Conversions", "🌡️ Temperature, 📏 Length, 📊 Data, 🚀 Speed, 💧 Volume, ⚖️ Weight", false)
            .addField("GitHub", "[ItsSimplyLeo/converta](${convertaBot.githubUrl})", true)
            .addField("Invite", "[Click here to invite me!](${convertaBot.inviteUrl})", true)
            .setFooter("Made with ❤️ using Kotlin + JDA")
            .setColor(0x00BFFF)
            .build()

        event.replyEmbeds(embed).queue()
    }

    private fun handleInvite(event: SlashCommandInteractionEvent) {
        val embed = EmbedBuilder()
            .setTitle("Invite Converta to your server!")
            .setDescription("Click the button below to invite Converta to your server.")
            .addField("Invite Link", "[Click here to invite](${convertaBot.inviteUrl})", false)
            .setColor(0x00BFFF)
            .build()
        event.replyEmbeds(embed).setEphemeral(true).queue()
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
}
