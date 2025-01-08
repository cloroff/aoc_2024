private const val MAX_LEVEL = 9

fun main() {
    fun processHike(trailMap: List<List<Pair<Position, Int>>>, currentPosition: Position, level: Int, peaks: MutableList<Position>) {
        if (level < MAX_LEVEL) {
            var newPosition = Position(currentPosition.x, currentPosition.y-1)
            if (newPosition.x in trailMap[0].indices && newPosition.y in trailMap.indices && trailMap[newPosition.y][newPosition.x].second == level+1) {
                processHike(trailMap, newPosition, level+1, peaks)
            }
            newPosition = Position(currentPosition.x+1, currentPosition.y)
            if (newPosition.x in trailMap[0].indices && newPosition.y in trailMap.indices && trailMap[newPosition.y][newPosition.x].second == level+1) {
                processHike(trailMap, newPosition, level+1, peaks)
            }
            newPosition = Position(currentPosition.x, currentPosition.y+1)
            if (newPosition.x in trailMap[0].indices && newPosition.y in trailMap.indices && trailMap[newPosition.y][newPosition.x].second == level+1) {
                processHike(trailMap, newPosition, level+1, peaks)
            }
            newPosition = Position(currentPosition.x-1, currentPosition.y)
            if (newPosition.x in trailMap[0].indices && newPosition.y in trailMap.indices && trailMap[newPosition.y][newPosition.x].second == level+1) {
                processHike(trailMap, newPosition, level+1, peaks)
            }
        }
        else {
            peaks.add(currentPosition)
        }
    }

    fun part1(input: List<String>): Int {
        val trailMap = input.mapIndexed { y, line -> line.toCharArray().mapIndexed { x, column -> Pair(Position(x, y), column.digitToInt()) } }
        val trailHeads = trailMap.flatten().filter { it.second == 0 }.map { Triple(it.first, it.second, mutableListOf<Position>()) }
        trailHeads.forEach { processHike(trailMap, it.first, it.second, it.third) }
        return trailHeads.sumOf { it.third.distinct().size }
    }

    fun part2(input: List<String>): Int {
        val trailMap = input.mapIndexed { y, line -> line.toCharArray().mapIndexed { x, column -> Pair(Position(x, y), column.digitToInt()) } }
        val trailHeads = trailMap.flatten().filter { it.second == 0 }.map { Triple(it.first, it.second, mutableListOf<Position>()) }
        trailHeads.forEach { processHike(trailMap, it.first, it.second, it.third) }
        return trailHeads.sumOf { it.third.size }
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)

    val input = readInput("Day10")

    var startTime = System.currentTimeMillis()
    part1(input).println()
    println("Time: ${System.currentTimeMillis() - startTime}")

    check(part2(testInput) == 81)

    startTime = System.currentTimeMillis()
    part2(input).println()
    println("Time: ${System.currentTimeMillis() - startTime}")
}

