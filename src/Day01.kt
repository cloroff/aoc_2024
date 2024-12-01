import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>): List<List<String>> {
        val parsedInput = input.map { it.split("   ") }
        return parsedInput
    }

    fun part1(input: List<String>): Int {
        val parsedInput = parseInput(input)
        val list1 = parsedInput.map{ it.first().toInt() }.sorted()
        val list2 = parsedInput.map{ it.last().toInt() }.sorted()
        val sortedMap = list1.zip(list2)
        val distance = sortedMap.sumOf{ abs(it.first - it.second) }
        return distance
    }

    fun part2(input: List<String>): Int {
        val parsedInput = parseInput(input)
        val list1 = parsedInput.map{ it.first().toInt() }
        val list2 = parsedInput.map{ it.last().toInt() }
        val resultList = list1.map{ location -> location * list2.count { it == location } }
        return resultList.sum()
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()

    part2(input).println()
}
