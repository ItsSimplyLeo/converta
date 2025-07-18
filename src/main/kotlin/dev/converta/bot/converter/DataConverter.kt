package dev.converta.bot.converter

import net.dv8tion.jda.api.interactions.commands.Command.Choice


object DataConverter : Converter {

    private val choiceUnits = listOf(
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

    private val toBytes = mapOf(
        "b"  to 1.0 / 8,                            // 1 bit = 1/8 byte
        "B"  to 1.0,                                // 1 byte
        "kb" to 1024.0 / 8,                         // 1 kb = 128 bytes
        "kB" to 1024.0,                             // 1 kB = 1024 bytes
        "Mb" to 1024.0 * 1024 / 8,                  // 1 Mb = 131,072 bytes
        "MB" to 1024.0 * 1024,                      // 1 MB = 1,048,576 bytes
        "Gb" to 1024.0 * 1024 * 1024 / 8,           // 1 Gb = 134,217,728 bytes
        "GB" to 1024.0 * 1024 * 1024,               // 1 GB = 1,073,741,824 bytes
        "Tb" to 1024.0 * 1024 * 1024 * 1024 / 8,    // 1 Tb = 137,438,953,472 bytes
        "TB" to 1024.0 * 1024 * 1024 * 1024         // 1 TB = 1,099,511,627,776 bytes
    )

    override fun name(): String {
        return "Data"
    }

    override fun convert(value: Double, from: String, to: String): Double? {
        val f = from.trim()
        val t = to.trim()
        val fromBytes = toBytes[f] ?: return null
        val toBytes = toBytes[t] ?: return null
        val valueInBytes = value * fromBytes
        return valueInBytes / toBytes
    }

    override fun getChoices(): List<Choice> {
        return choiceUnits
    }
}