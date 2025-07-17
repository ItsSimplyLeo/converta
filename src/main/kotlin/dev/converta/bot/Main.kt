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

fun main() {
    val dotenv = dotenv()
    val token = dotenv["DISCORD_TOKEN"] ?: error("DISCORD_TOKEN not found in .env")

    val jda = JDABuilder.createDefault(token)
        .setActivity(Activity.playing("Converting units with Converta!"))
        .addEventListeners(SlashCommandListener())
        .build()

    registerSlashCommands(jda)

    jda.awaitReady()
    println("Converta is now online!")
}


fun registerSlashCommands(jda: JDA) {
    val temperatureUnits = listOf(
        Choice("Celsius (°C)", "c"),
        Choice("Fahrenheit (°F)", "f"),
        Choice("Kelvin (K)", "k")
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

    val temperatureCommand = SubcommandData("temperature", "Convert between Celsius, Fahrenheit, and Kelvin")
        .addOptions(
            OptionData(OptionType.NUMBER, "value", "The temperature value to convert", true),
            OptionData(OptionType.STRING, "from", "The unit to convert from", true).addChoices(temperatureUnits),
            OptionData(OptionType.STRING, "to", "The unit to convert to", true).addChoices(temperatureUnits)
        )

    val lengthCommand = SubcommandData("length", "Convert between common length units")
        .addOptions(
            OptionData(OptionType.NUMBER, "value", "The length value to convert", true),
            OptionData(OptionType.STRING, "from", "The unit to convert from", true).addChoices(lengthUnits),
            OptionData(OptionType.STRING, "to", "The unit to convert to", true).addChoices(lengthUnits)
        )

    jda.updateCommands().addCommands(
        Commands.slash("convert", "Convert temperature, length, and more")
            .addSubcommands(temperatureCommand, lengthCommand)
    ).queue()
}