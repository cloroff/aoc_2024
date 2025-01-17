fun main() {
    fun robotMotion(robot: Position, velocity: Position, space: Position, seconds: Int): Position {
        val targetPosition = Position(
            ((robot.x + seconds * velocity.x) % space.x + space.x) % space.x,
            ((robot.y + seconds * velocity.y) % space.y + space.y) % space.y)
        return targetPosition
    }

    fun robotMotionPrint(robotMap: List<Pair<Position, Position>>, space: Position, seconds: Int): Int {
        var motionMap = robotMap
        for (i in 1..seconds) {
            motionMap = motionMap.map { Pair(Position((it.first.x + it.second.x + space.x) % space.x, (it.first.y + it.second.y + space.y) % space.y), it.second) }
            if (i == seconds)
            for (y in 0..space.y) {
//                if (motionMap.count {it.first.y == y} > 30 ) {
                    println(i)
                    for (x in 0..space.x) {
                        if (motionMap.any { it.first.x == x && it.first.y == y }) {
                            print('#')
                        }
                        else {
                            print(' ')
                        }
                    }

//                }
            }
            motionMap = motionMap.map { Pair(Position((it.first.x + it.second.x + space.x) % space.x, (it.first.y + it.second.y + space.y) % space.y), it.second) }
        }
        return 0
    }

    fun part1(input: List<String>, space: Position): Int {
        val robotMap = input.map {
            val regex = Regex("""p=(?<xpos>\d+),(?<ypos>\d+) v=(?<xvel>-?\d+),(?<yvel>-?\d+)""")
            val matchResult = regex.find(it)!!
            val position = Position(matchResult.groupValues[1].toInt(), matchResult.groupValues[2].toInt())
            val velocity = Position(matchResult.groupValues[3].toInt(), matchResult.groupValues[4].toInt())
            Pair(position, velocity)
        }
        val finalMap = robotMap.map { robotMotion(it.first, it.second, space, 100) }
        val safetyFactor = finalMap.count { it.x < space.x / 2 && it.y < space.y / 2 } *
                finalMap.count { it.x > space.x / 2 && it.y < space.y / 2 } *
                finalMap.count { it.x > space.x / 2 && it.y > space.y / 2 } *
                finalMap.count { it.x < space.x / 2 && it.y > space.y / 2 }
        return safetyFactor
    }

    fun part2(input: List<String>, space: Position): Int {
        val robotMap = input.map {
            val regex = Regex("""p=(?<xpos>\d+),(?<ypos>\d+) v=(?<xvel>-?\d+),(?<yvel>-?\d+)""")
            val matchResult = regex.find(it)!!
            val position = Position(matchResult.groupValues[1].toInt(), matchResult.groupValues[2].toInt())
            val velocity = Position(matchResult.groupValues[3].toInt(), matchResult.groupValues[4].toInt())
            Pair(position, velocity)
        }
        robotMotionPrint(robotMap, space, 6398)

        return 0
    }

    val testInput = readInput("Day14_test")

    check(part1(testInput, Position(11, 7)) == 12)

    val input = readInput("Day14")

    var startTime = System.currentTimeMillis()
    part1(input, Position(101, 103)).println()
    println("Time: ${System.currentTimeMillis() - startTime}")

//    check(part2(testInput) == 1)

    startTime = System.currentTimeMillis()
    part2(input, Position(101, 103)).println()
    println("Time: ${System.currentTimeMillis() - startTime}")
}

