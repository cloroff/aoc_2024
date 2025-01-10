import kotlin.math.abs

fun main() {
    var regionIndexer = 0

    fun findArea(
        areaMap: MutableMap<Position, Triple<Char, Int, List<Direction>>>,
        visitedPlots: MutableList<Position>,
        plotPosition: Position
    ) {
        val plot = areaMap[plotPosition]!!
        var region = plot.second
        if (region == 0) {
            region = ++regionIndexer
        }

        val neighbourMap = areaMap.filter {
            abs(plotPosition.x - it.key.x) + abs(plotPosition.y - it.key.y) == 1 && plot.first == it.value.first
        }
        val fenceList = mutableListOf<Direction>()
        for (dir in Direction.entries) {
            if (plotPosition.neighbour(dir) !in neighbourMap)
                fenceList.add(dir)
        }
        areaMap[plotPosition] = Triple(plot.first, region, fenceList)
        visitedPlots.add(plotPosition)
        neighbourMap
            .filter { it.key !in visitedPlots }
            .forEach {
                areaMap[it.key] = Triple(it.value.first, region, it.value.third)
                findArea(areaMap, visitedPlots, it.key)
            }
    }

    fun part1(input: List<String>): Int {
        val gardenMap = input.map { row -> row.toCharArray() }
        val areaMap = mutableMapOf<Position, Triple<Char, Int, List<Direction>>>()

        gardenMap.forEachIndexed { y, row ->
            row.forEachIndexed { x, plot ->
                areaMap[Position(x, y)] = Triple(plot, 0, listOf())
            }
        }

        val visitedPlots = mutableListOf<Position>()
        for (y in gardenMap.indices) {
            for (x in gardenMap[0].indices) {
                val currentPosition = Position(x, y)
                findArea(areaMap, visitedPlots, currentPosition)
            }
        }
        var price = 0
        for (region in 1..regionIndexer) {
            val regionMap = areaMap.filter { it.value.second == region }
            price += regionMap.size * regionMap.map { it.value.third.size }.sum()
        }
        return price
    }

    fun findBorders(regionMap: Map<Position, Triple<Char, Int, List<Direction>>>): Int {
        var borders = 0
        regionMap
            .filterValues { it.third.isNotEmpty() }
            .forEach {
                if (Direction.NORTH in it.value.third) {
                    val neighbour = regionMap[it.key.neighbour(Direction.WEST)]
                    if (neighbour == null || !neighbour.third.contains(Direction.NORTH)) {
                        borders++
                    }
                }
                if (Direction.EAST in it.value.third) {
                    val neighbour = regionMap[it.key.neighbour(Direction.NORTH)]
                    if (neighbour == null || !neighbour.third.contains(Direction.EAST)) {
                        borders++
                    }
                }
                if (Direction.SOUTH in it.value.third) {
                    val neighbour = regionMap[it.key.neighbour(Direction.EAST)]
                    if (neighbour == null || !neighbour.third.contains(Direction.SOUTH)) {
                        borders++
                    }
                }
                if (Direction.WEST in it.value.third) {
                    val neighbour = regionMap[it.key.neighbour(Direction.SOUTH)]
                    if (neighbour == null || !neighbour.third.contains(Direction.WEST)) {
                        borders++
                    }
                }
            }
        return borders
    }

    fun part2(input: List<String>): Int {
        val gardenMap = input.map { row -> row.toCharArray() }
        val areaMap = mutableMapOf<Position, Triple<Char, Int, List<Direction>>>()

        gardenMap.forEachIndexed { y, row ->
            row.forEachIndexed { x, plot ->
                areaMap[Position(x, y)] = Triple(plot, 0, listOf())
            }
        }

        val visitedPlots = mutableListOf<Position>()
        for (y in gardenMap.indices) {
            for (x in gardenMap[0].indices) {
                val currentPosition = Position(x, y)
                findArea(areaMap, visitedPlots, currentPosition)
            }
        }
        var price = 0
        for (region in 1..regionIndexer) {
            val regionMap = areaMap.filter { it.value.second == region }
            price += regionMap.size * findBorders(regionMap)
        }
        return price
    }

    val testInput1 = readInput("Day12_test1")
    check(part1(testInput1) == 140)

    regionIndexer = 0
    val testInput2 = readInput("Day12_test2")
    check(part1(testInput2) == 772)

    regionIndexer = 0
    val testInput3 = readInput("Day12_test3")
    check(part1(testInput3) == 1930)

    regionIndexer = 0
    val input = readInput("Day12")

    var startTime = System.currentTimeMillis()
    part1(input).println()
    println("Time: ${System.currentTimeMillis() - startTime}")

    regionIndexer = 0
    check(part2(testInput1) == 80)

    regionIndexer = 0
    check(part2(testInput2) == 436)

    regionIndexer = 0
    check(part2(testInput3) == 1206)

    startTime = System.currentTimeMillis()
    part2(input).println()
    println("Time: ${System.currentTimeMillis() - startTime}")
}

