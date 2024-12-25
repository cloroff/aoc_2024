fun main() {
    fun initMaps(input: List<String>): Pair<List<CharArray>, MutableMap<Char, MutableList<Position>>> {
        val cityMap = input.map { it.toCharArray() }
        val antennaMap = mutableMapOf<Char, MutableList<Position>>()

        cityMap.forEachIndexed { y, space ->
            space.forEachIndexed { x, it ->
                if (it != '.') {
                    antennaMap.getOrPut(it) { mutableListOf() }.add(Position(x, y))
                }
            }
        }
        return Pair(cityMap, antennaMap)
    }

    fun part1(input: List<String>): Int {
        val (cityMap, antennaMap) = initMaps(input)
        antennaMap.println()

        val frequencyMap = mutableSetOf<Position>()
        antennaMap.forEach {
            val positionList = it.value
            positionList.forEachIndexed { index, _ ->
                for (i in index+1 .. positionList.lastIndex) {
                    val xDif = positionList[index].x - positionList[i].x
                    val yDif = positionList[index].y - positionList[i].y
                    frequencyMap.add(Position(positionList[index].x+xDif, positionList[index].y+yDif))
                    frequencyMap.add(Position(positionList[i].x-xDif, positionList[i].y-yDif))
                }
            }
        }
        return frequencyMap.filter { it.x in cityMap[0].indices && it.y in cityMap.indices }.size
    }

    fun part2(input: List<String>): Int {
        val (cityMap, antennaMap) = initMaps(input)
        antennaMap.println()

        val frequencyMap = mutableSetOf<Position>()
        antennaMap.forEach {
            val positionList = it.value
            positionList.forEachIndexed { index, _ ->
                for (i in index+1 .. positionList.lastIndex) {
                    val xDif = positionList[index].x - positionList[i].x
                    val yDif = positionList[index].y - positionList[i].y
                    var xPos = positionList[index].x
                    var yPos = positionList[index].y
                    do {
                        frequencyMap.add(Position(xPos, yPos))
                        xPos += xDif
                        yPos += yDif
                    } while (xPos in cityMap[0].indices && yPos in cityMap.indices)
                    xPos = positionList[i].x
                    yPos = positionList[i].y
                    do {
                        frequencyMap.add(Position(xPos, yPos))
                        xPos -= xDif
                        yPos -= yDif
                    } while (xPos in cityMap[0].indices && yPos in cityMap.indices)
                }
            }
        }
        return frequencyMap.size
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)

    val input = readInput("Day08")

    var startTime = System.currentTimeMillis()
    part1(input).println()
    println("Time: ${System.currentTimeMillis() - startTime}")

    check(part2(testInput) == 34)

    startTime = System.currentTimeMillis()
    part2(input).println()
    println("Time: ${System.currentTimeMillis() - startTime}")
}

