package dev.converta.bot.converter

import net.dv8tion.jda.api.interactions.commands.Command.Choice

object WeightConverter : Converter {

    private val choiceUnits = listOf(
        Choice("Kilograms (kg)", "kg"),
        Choice("Grams (g)", "g"),
        Choice("Pounds (lbs)", "lbs"),
        Choice("Ounces (oz)", "oz"),
        Choice("Stones (st)", "st")
    )

    override fun name(): String {
        return "Weight"
    }

    override fun convert(value: Double, from: String, to: String): Double? {
        val conversionFactors = mapOf(
            "kg" to 1.0,          // Kilograms
            "g" to 0.001,         // Grams
            "lbs" to 0.453592,    // Pounds
            "oz" to 0.0283495,    // Ounces
            "st" to 6.35029       // Stones
        )

        val fromFactor = conversionFactors[from] ?: return null
        val toFactor = conversionFactors[to] ?: return null

        val valueInKg = value * fromFactor
        return valueInKg / toFactor
    }

    override fun getChoices(): List<Choice> {
        return choiceUnits
    }
}