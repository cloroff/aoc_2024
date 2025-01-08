private const val BLINKS_PART1 = 25
private const val BLINKS_PART2 = 75

fun main() {

    var stoneCounter = 0L
    var maxBlinks: Int
    val repo = mutableMapOf<Pair<Long, Int>, Long>()

    fun transform(stone: Long, blinksLeft: Int) {
        val initialStones = stoneCounter
        if (blinksLeft > 0) {
            val stones = repo[Pair(stone, blinksLeft)]
            if (stones != null) {
                stoneCounter += stones
            }
            else {
                if (stone == 0L) {
                    transform(1, blinksLeft-1)
                }
                else {
                    val stoneString = stone.toString()
                    if (stoneString.length % 2 == 0) {
                        stoneCounter++
                        transform(stoneString.substring(0, stoneString.length/2).toLong(), blinksLeft-1)
                        transform(stoneString.substring(stoneString.length/2, stoneString.length).toLong(), blinksLeft-1)
                    }
                    else {
                        transform(stone*2024, blinksLeft-1)
                    }
                }
                repo[Pair(stone, blinksLeft)] = stoneCounter-initialStones
            }

        }
    }

    fun part1(input: List<String>): Long {
        val parsedInput = input.map { line -> line.split(" ").map{ it.toLong() } }
        val stoneList = parsedInput[0]
        stoneCounter = stoneList.size.toLong()
        maxBlinks = BLINKS_PART1
        stoneList.forEach {
            transform(it, maxBlinks)
        }
        return stoneCounter
    }

    fun part2(input: List<String>): Long {
        val parsedInput = input.map { line -> line.split(" ").map{ it.toLong() } }
        val stoneList = parsedInput[0]
        stoneCounter = stoneList.size.toLong()
        maxBlinks = BLINKS_PART2
        stoneList.forEach {
            transform(it, maxBlinks)
        }
        return stoneCounter
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 55312L)

    val input = readInput("Day11")

    var startTime = System.currentTimeMillis()
    part1(input).println()
    println("Time: ${System.currentTimeMillis() - startTime}")

//    no separate test for part2 available
//    check(part2(testInput) == 1)

    startTime = System.currentTimeMillis()
    part2(input).println()
    println("Time: ${System.currentTimeMillis() - startTime}")
}

