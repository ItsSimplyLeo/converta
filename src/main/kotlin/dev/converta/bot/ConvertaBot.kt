package dev.converta.bot

import dev.converta.bot.listener.SlashCommandListener
import io.github.cdimascio.dotenv.dotenv
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.interactions.commands.Command.Choice
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
        val dataUnits = listOf(
            Choice("Bit (b)", "b"),
            Choice("Byte (B)", "B"),
            Choice("Kilobit (kb)", "kb"),
            Choice("Kilobyte (kB)", "kB"),
            Choice("Megabit (Mb)", "Mb"),
            Choice("Megabyte (MB)", "MB"),
            Choice("Gigabit (Gb)", "Gb"),
            Choice("Gigabyte (GB)", "GB"),
            Choice("Terabit (Tb)", "Tb"),
            Choice("Terabyte (TB)", "TB")
        )

        val lengthUnits = listOf(
            Choice("Meters (m)", "m"),
            Choice("Kilometers (km)", "km"),
            Choice("Miles (mi)", "mi"),
            Choice("Feet (ft)", "ft"),
            Choice("Inches (in)", "in"),
            Choice("Centimeters (cm)", "cm"),
            Choice("Millimeters (mm)", "mm")
        )

        val speedUnits = listOf(
            Choice("Meters per second (m/s)", "m/s"),
            Choice("Kilometers per hour (km/h)", "km/h"),
            Choice("Miles per hour (mi/h)", "mi/h"),
            Choice("Feet per second (ft/s)", "ft/s"),
            Choice("Knots", "knots")
        )

        val temperatureUnits = listOf(
            Choice("Celsius (°C)", "c"),
            Choice("Fahrenheit (°F)", "f"),
            Choice("Kelvin (K)", "k")
        )

        val dataCommand = SubcommandData("data", "Convert between common digital storage units")
            .addOptions(
                OptionData(OptionType.NUMBER, "value", "The data value to convert", true),
                OptionData(OptionType.STRING, "from", "The unit to convert from", true).addChoices(dataUnits),
                OptionData(OptionType.STRING, "to", "The unit to convert to", true).addChoices(dataUnits)
            )

        val lengthCommand = SubcommandData("length", "Convert between common length units")
            .addOptions(
                OptionData(OptionType.NUMBER, "value", "The length value to convert", true),
                OptionData(OptionType.STRING, "from", "The unit to convert from", true).addChoices(lengthUnits),
                OptionData(OptionType.STRING, "to", "The unit to convert to", true).addChoices(lengthUnits)
            )

        val speedCommand = SubcommandData("speed", "Convert between common speed units")
            .addOptions(
                OptionData(OptionType.NUMBER, "value", "The speed value to convert", true),
                OptionData(OptionType.STRING, "from", "The unit to convert from", true).addChoices(speedUnits),
                OptionData(OptionType.STRING, "to", "The unit to convert to", true).addChoices(speedUnits)
            )

        val temperatureCommand = SubcommandData("temperature", "Convert between Celsius, Fahrenheit, and Kelvin")
            .addOptions(
                OptionData(OptionType.NUMBER, "value", "The temperature value to convert", true),
                OptionData(OptionType.STRING, "from", "The unit to convert from", true).addChoices(temperatureUnits),
                OptionData(OptionType.STRING, "to", "The unit to convert to", true).addChoices(temperatureUnits)
            )

        jda.updateCommands().addCommands(
            Commands.slash("convert", "Convert temperature, length, data and more")
                .addSubcommands(dataCommand, lengthCommand, speedCommand, temperatureCommand),
            Commands.slash("convertabot", "Converta bot commands")
                .addSubcommands(SubcommandData("about", "Information about the Converta bot"))
        ).queue()
    }
}