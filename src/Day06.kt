fun main() {
    var currentDirection = Direction.NORTH

    fun initStartValues(input: List<String>): Pair<MutableList<Position>, Position> {
        val labMap = input.map { it.toCharArray() }
        val startObstacles = mutableListOf<Position>()
        var startPosition = Position(0, 0)
        labMap.forEachIndexed { y, space ->
            if (space.contains('^')) startPosition = Position(space.indexOf('^'), y)
            if (space.contains('#')) space.forEachIndexed { x, it -> if (it == '#') startObstacles.add(Position(x, y)) }
        }
        return Pair(startObstacles, startPosition)
    }

    fun processMove(currentPosition: Position, obstacles: List<Position>): Position {
        var nextPosition = when(currentDirection) {
            Direction.NORTH -> Position(currentPosition.x, currentPosition.y-1)
            Direction.EAST -> Position(currentPosition.x+1, currentPosition.y)
            Direction.SOUTH -> Position(currentPosition.x, currentPosition.y+1)
            Direction.WEST -> Position(currentPosition.x-1, currentPosition.y)
        }
        if (nextPosition in obstacles) {
            currentDirection = currentDirection.turnRight()
            nextPosition = processMove(currentPosition, obstacles)
        }
        return nextPosition
    }

    fun part1(input: List<String>): Set<Position> {
        val pair = initStartValues(input)
        val startObstacles = pair.first
        val startPosition = pair.second
        val visitedPositions = mutableSetOf<Position>()

        var currentPosition = startPosition
        currentDirection = Direction.NORTH
        do {
            visitedPositions.add(currentPosition)
            currentPosition = processMove(currentPosition, startObstacles)
        } while (currentPosition.x in input[0].indices && currentPosition.y in input.indices)

        return visitedPositions.toSet()
    }

    fun part2(input: List<String>): Int {
        val startValues = initStartValues(input)
        val startObstacles = startValues.first
        val startPosition = startValues.second
        val visitedPositions = part1(input)
        var loopObstructions = 0

        visitedPositions.filter{ it != startPosition }
            .forEach {
                val mutableObstacles = startObstacles.toMutableList()
                mutableObstacles.add(it)
                val obstacles = mutableObstacles.toList()
                var currentPosition = startPosition
                currentDirection = Direction.NORTH
                val path = mutableListOf<PathPosition>()
                do {
                    val currentPathPosition = PathPosition(currentPosition, currentDirection)
                    if (currentPathPosition in path) {
                        loopObstructions++
                        break
                    }
                    else {
                        path.add(currentPathPosition)
                        currentPosition = processMove(currentPosition, obstacles)
                    }
                } while (currentPosition.x in input[0].indices && currentPosition.y in input.indices)
            }

        return loopObstructions
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput).size == 41)

    val input = readInput("Day06")

    var startTime = System.currentTimeMillis()
    part1(input).size.println()
    println("Time: ${System.currentTimeMillis() - startTime}")

    check(part2(testInput) == 6)

    startTime = System.currentTimeMillis()
    part2(input).println()
    println("Time: ${System.currentTimeMillis() - startTime}")
}
