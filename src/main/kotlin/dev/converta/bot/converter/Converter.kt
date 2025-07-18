package dev.converta.bot.converter

import net.dv8tion.jda.api.interactions.commands.Command.Choice

interface Converter {

    /**
     * Returns the name of the converter.
     *
     * @return The name of the converter.
     */
    fun name(): String

    /**
     * Converts a value from one unit to another.
     *
     * @param value The numeric value to convert.
     * @param from The unit of the input value.
     * @param to The unit to convert the value to.
     * @return The converted value, or null if the conversion is not possible.
     */
    fun convert(value: Double, from: String, to: String): Double?

    /**
     * Returns a list of choices for the command options.
     *
     * @return A list of Command.Choice objects representing the available units.
     */
    fun getChoices(): List<Choice>
}