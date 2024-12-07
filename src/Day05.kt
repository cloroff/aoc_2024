fun main() {
    fun parseRules(input: List<String>): List<List<Int>> {
        val rules = input.filter { it.contains('|') }
            .map{ line -> line.split('|').map{ it.toInt() } }
        return rules
    }

    fun parsePages(input: List<String>): List<List<Int>> {
        val pages = input.filter { it.contains(',') }
            .map{ line -> line.split(',').map{ it.toInt() } }
        return pages
    }

    fun part1(input: List<String>): Int {
        val rules = parseRules(input)
        val pageLines = parsePages(input)
        val middlePageSum = pageLines.filter { line ->
            line.filterIndexed { pageIndex, page ->
                val pagesAfter = line.filterIndexed { index, _ -> index > pageIndex }
                val pageRules = rules.filter { it.last() == page }.map { it.first() }
                pageRules.any {it in pagesAfter}
            }.isEmpty()
        }.sumOf { it[it.size/2] }
        return middlePageSum
    }

    fun part2(input: List<String>): Int {
        val rules = parseRules(input)
        val pageLines = parsePages(input)
        val orderList = mutableListOf<Triple<Int, List<Int>, List<Int>>>()

        rules.flatten().distinct().forEach { page ->
            orderList.add(Triple(page, rules.filter{ it.last() == page }.map { it.first() }, rules.filter{ it.first() == page }.map { it.last() } ))
        }
        val unorderedPageLines = pageLines.filter { line ->
            line.filterIndexed { pageIndex, page ->
                val pagesAfter = line.filterIndexed { index, _ -> index > pageIndex }
                val pageRules = rules.filter { it.last() == page }.map { it.first() }
                pageRules.any {it in pagesAfter}
            }.isNotEmpty()
        }
        val middlePageSum = unorderedPageLines.map { line ->
            orderList.filter { order -> order.first in line }
                .map { triple -> Pair(triple.first, triple.second.filter { it in line }.size)}
                .sortedBy { it.second } }
            .sumOf { it[it.size/2].first }

        return middlePageSum
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)

    val input = readInput("Day05")
    part1(input).println()

    check(part2(testInput) == 123)

    part2(input).println()
}
