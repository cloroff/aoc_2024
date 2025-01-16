import kotlin.math.min

fun main() {
    var totalCalculations = 0L

    fun calculateMinTokens(target: Target, button1: Button, button2: Button, maxPresses: Long): Long {
        var result = 0L
        val divisorX = target.x / button1.x
        val divisorY = target.y / button1.y
        var multiplier1 = min(maxPresses, min(divisorX, divisorY))
        var multiplier2 = 0L
        var remainderX: Long
        var remainderY: Long
        var calculations = 0L
        var step = 1L
        var lastMinus = 0L
        do {
            calculations++
            remainderX = target.x - multiplier2 * button2.x - multiplier1 * button1.x
            remainderY = target.y - multiplier2 * button2.y - multiplier1 * button1.y
            if (remainderX == 0L && remainderY == 0L) {
                result = multiplier2 * button2.cost + multiplier1 * button1.cost
                break
            }

            if (remainderX < 0 || remainderY < 0) {
                val step1 = min(remainderX/button1.x, remainderY/button1.y) - 1
                multiplier1 += min(step1, -1)
                step = calculations - lastMinus
                lastMinus = calculations
            } else {
                if (remainderX + remainderY < step * (button2.x + button2.y)) {
                    step = 1
                }
                if (remainderX + remainderY > 1000000 * step * (button2.x + button2.y)) {
                    step *= 1000000
                }
                multiplier2 += step
            }
        } while (multiplier2 <= maxPresses && multiplier1 >= 0 && (remainderX > 0 || remainderY > 0))
        totalCalculations += calculations
        return result
    }

    fun part1(input: List<String>): Long {
        var tokens = 0L
        val clawMachines = mutableListOf<Triple<Button, Button, Target>>()
        for (line in input.indices step 4) {
            val regex = Regex(""".+: X[+=](?<xvalue>\d+), Y[+=](?<yvalue>\d+)""")
            var matchResult = regex.find(input[line])!!
            val buttonA = Button(matchResult.groupValues[1].toInt(), matchResult.groupValues[2].toInt(), 3)
            matchResult = regex.find(input[line+1])!!
            val buttonB = Button(matchResult.groupValues[1].toInt(), matchResult.groupValues[2].toInt(), 1)
            matchResult = regex.find(input[line+2])!!
            val target = Target(matchResult.groupValues[1].toLong(), matchResult.groupValues[2].toLong())
            clawMachines.add(Triple(buttonA, buttonB, target))
        }
        clawMachines.forEach {
            val buttonA = it.first
            val buttonB = it.second
            val target = it.third
            var currentTokens: Long
            if (buttonB.cost * (buttonB.x + buttonB.y) > buttonA.cost * (buttonA.x + buttonA.y)) {
                currentTokens = calculateMinTokens(target, buttonA, buttonB, 100)
                if (currentTokens == 0L) {
                    currentTokens = calculateMinTokens(target, buttonB, buttonA, 100)
                }
            }
            else {
                currentTokens = calculateMinTokens(target, buttonB, buttonA, 100)
                if (currentTokens == 0L) {
                    currentTokens = calculateMinTokens(target, buttonA, buttonB, 100)
                }
            }

            tokens += currentTokens
        }
        return tokens
    }

    fun part2(input: List<String>): Long {
        var tokens = 0L
        val clawMachines = mutableListOf<Triple<Button, Button, Target>>()
        for (line in input.indices step 4) {
            val regex = Regex(""".+: X[+=](?<xvalue>\d+), Y[+=](?<yvalue>\d+)""")
            var matchResult = regex.find(input[line])!!
            val buttonA = Button(matchResult.groupValues[1].toInt(), matchResult.groupValues[2].toInt(), 3)
            matchResult = regex.find(input[line+1])!!
            val buttonB = Button(matchResult.groupValues[1].toInt(), matchResult.groupValues[2].toInt(), 1)
            matchResult = regex.find(input[line+2])!!
            val target = Target(matchResult.groupValues[1].toLong()+10000000000000, matchResult.groupValues[2].toLong()+10000000000000)
            clawMachines.add(Triple(buttonA, buttonB, target))
        }
        clawMachines.forEach {
            val buttonA = it.first
            val buttonB = it.second
            val target = it.third
            var currentTokens = calculateMinTokens(target, buttonB, buttonA, Long.MAX_VALUE)
            if (currentTokens == 0L) {
                currentTokens = calculateMinTokens(target, buttonA, buttonB, Long.MAX_VALUE)
            }
            tokens += currentTokens
        }
        return tokens
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 480L)

    val input = readInput("Day13")

    var startTime = System.currentTimeMillis()
    part1(input).println()
    println("Time: ${System.currentTimeMillis() - startTime}")

    check(part2(testInput) == 875318608908)

    startTime = System.currentTimeMillis()
    part2(input).println()
    println("Time: ${System.currentTimeMillis() - startTime}")
    println("$totalCalculations total calculations")
}

data class Button (val x: Int, val y: Int, val cost: Int)

data class Target (val x: Long, val y: Long)


