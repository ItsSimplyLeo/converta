package dev.converta.bot

import dev.converta.bot.converter.Converter
import dev.converta.bot.converter.DataConverter
import dev.converta.bot.converter.LengthConverter
import dev.converta.bot.converter.SpeedConverter
import dev.converta.bot.converter.TemperatureConverter
import dev.converta.bot.listener.SlashCommandListener
import io.github.cdimascio.dotenv.dotenv
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import java.time.Instant

class ConvertaBot {

    private val dotenv = dotenv()
    private val token = dotenv["DISCORD_TOKEN"] ?: error("DISCORD_TOKEN not found in .env")
    lateinit var startupTime: Instant
    lateinit var jda: JDA

    private var converters: List<Converter> = listOf(
        DataConverter,
        LengthConverter,
        SpeedConverter,
        TemperatureConverter
    )

    fun start() {
        jda = JDABuilder.createDefault(token)
            .setActivity(Activity.playing("Converting units with Converta!"))
            .addEventListeners(SlashCommandListener(this))  // Pass JDA if needed
            .build()

        registerSlashCommands()

        jda.awaitReady()
        startupTime = Instant.now()

        println("Converta is now online!")
    }

    private fun registerSlashCommands() {
        val converterSubcommands: MutableList<SubcommandData> = mutableListOf()

        converters.forEach { converter ->
            val choices = converter.getChoices()
            converterSubcommands.add(SubcommandData(converter.name().lowercase(), "Convert ${converter.name().lowercase()} units")
                .addOptions(
                    OptionData(OptionType.NUMBER, "value", "The value to convert", true),
                    OptionData(OptionType.STRING, "from", "The unit to convert from", true).addChoices(choices),
                    OptionData(OptionType.STRING, "to", "The unit to convert to", true).addChoices(choices)
                ))
        }

        jda.updateCommands().addCommands(
            Commands.slash("convert", "Convert temperature, length, data and more")
                .addSubcommands(converterSubcommands),
            Commands.slash("convertabot", "Converta bot commands")
                .addSubcommands(SubcommandData("about", "Information about the Converta bot"))
        ).queue()
    }
}