fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        val regex = Regex("""mul\((?<multiplier>\d{1,3}),(?<multiplicand>\d{1,3})\)""")
        input.forEach {
            line -> regex.findAll(line).forEach {
                result += (it.groupValues[1].toInt() * it.groupValues[2].toInt()) } }
        return result
    }

    fun part2(input: List<String>): Int {
        var completeInput = ""
        var filteredInput = ""
        input.forEach{ completeInput = completeInput.plus(it) }

        val regexStart = Regex("""^(.*?)don't\(\)""")
        val regexMid = Regex("""do\(\)(.*?)don't\(\)""")
        val regexEndDo = Regex(""".*do\(\)(.*?)$""")
        val regexEndDont = Regex(""".*don't\(\)(.*?)$""")

        filteredInput += regexStart.find(completeInput)?.groupValues?.get(1)
        regexMid.findAll(completeInput).forEach { filteredInput += it.groupValues[1] }

        val endDo = regexEndDo.find(completeInput)?.groupValues?.get(1)
        val endDont = regexEndDont.find(completeInput)?.groupValues?.get(1)
        if (endDo != null && endDont != null) {
            if (endDo.length < endDont.length) {
                filteredInput += endDo
            }
        }
        return part1(listOf(filteredInput))
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")

    check(part1(testInput) == 161)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")

    part1(input).println()

    val testInput2 = readInput("Day03_test2")

    check(part2(testInput2) == 48)

    part2(input).println()
}
