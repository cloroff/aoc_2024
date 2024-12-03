
fun main() {
    fun parseInput(input: List<String>): List<List<Int>> {
        val parsedInput = input.map { line -> line.split(" ").map{ it.toInt() } }
        return parsedInput
    }

    fun safeAscending(level: List<Int>) = level.zipWithNext { a, b -> a < b && a > b - 4 }

    fun safeDescending(level: List<Int>) = level.zipWithNext { a, b -> a > b && a < b + 4 }

    fun part1(input: List<String>): Int {
        val parsedInput = parseInput(input)
        val ascending = parsedInput.count { level -> safeAscending(level).all { it } }
        val descending = parsedInput.count { level -> safeDescending(level).all { it } }
        return ascending + descending
    }

    fun part2(input: List<String>): Int {
        val parsedInput = parseInput(input)
        val filteredInput = parsedInput.filter { level -> safeAscending(level).any { !it } }
            .filter { level -> safeDescending(level).any { !it } }

        val failedIndexAscendingList = filteredInput.map { level -> safeAscending(level).indexOfFirst { !it } }
        val checkList1 = filteredInput.mapIndexed { index, levels -> safeAscending(levels.filterIndexed { ind, _ -> ind != failedIndexAscendingList[index] }).all { it } }
        val checkList2 = filteredInput.mapIndexed { index, levels -> safeAscending(levels.filterIndexed { ind, _ -> ind != failedIndexAscendingList[index] + 1 }).all { it } }

        val failedIndexDescendingList = filteredInput.map { level -> safeDescending(level).indexOfFirst { !it } }
        val checkList3 = filteredInput.mapIndexed { index, levels -> safeDescending(levels.filterIndexed { ind, _ -> ind != failedIndexDescendingList[index] }).all { it } }
        val checkList4 = filteredInput.mapIndexed { index, levels -> safeDescending(levels.filterIndexed { ind, _ -> ind != failedIndexDescendingList[index] + 1 }).all { it } }

        val checkList = checkList1.mapIndexed { index, b -> b || checkList2[index] || checkList3[index] || checkList4[index] }
        return input.size - filteredInput.size + checkList.count { it }
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 11)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()

    part2(input).println()
}
