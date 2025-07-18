package dev.converta.bot.converter

import net.dv8tion.jda.api.interactions.commands.Command.Choice

object VolumeConverter : Converter {

    private val choiceUnits = listOf(
        Choice("Liters (L)", "l"),
        Choice("Milliliters (mL)", "ml"),
        Choice("Cubic meters (m³)", "m3"),
        Choice("Cubic centimeters (cm³)", "cm3"),
        Choice("Gallons (gal)", "gal"),
        Choice("Quarts (qt)", "qt"),
        Choice("Pints (pt)", "pt")
    )

    override fun name(): String {
        return "Volume"
    }

    override fun convert(value: Double, from: String, to: String): Double? {
        val conversionFactors = mapOf(
            "l" to 1.0,          // Liters
            "ml" to 0.001,       // Milliliters
            "m3" to 1000.0,      // Cubic meters
            "cm3" to 0.001,      // Cubic centimeters
            "gal" to 3.78541,    // Gallons
            "qt" to 0.946353,    // Quarts
            "pt" to 0.473176      // Pints
        )

        val fromFactor = conversionFactors[from] ?: return null
        val toFactor = conversionFactors[to] ?: return null

        val valueInLiters = value * fromFactor
        return valueInLiters / toFactor
    }

    override fun getChoices(): List<Choice> {
        return choiceUnits
    }
}