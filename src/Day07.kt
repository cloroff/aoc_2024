fun main() {
    fun calculate(result: Long, calc: Long, numbers: List<Int>): Boolean {
        var correct = false
        var newCalc = calc + numbers[0]
        if (newCalc == result && numbers.size == 1) {
            correct = true
        }
        else {
            if (newCalc <= result && numbers.size > 1) {
                correct = calculate(result, newCalc, numbers.filterIndexed { index, _ -> index > 0 })
            }
            if (!correct) {
                newCalc = calc * numbers[0]
                if (newCalc == result && numbers.size == 1) {
                    correct = true
                }
                else {
                    if (newCalc <= result && numbers.size > 1) {
                        correct = calculate(result, newCalc, numbers.filterIndexed { index, _ -> index > 0 })
                    }
                }
            }
        }
        return correct
    }

    fun calculateWithConcat(result: Long, calc: Long, numbers: List<Long>): Boolean {
        var correct = false
        var newCalc = calc + numbers[0]
        if (newCalc == result && numbers.size == 1) {
            correct = true
        }
        else {
            if (newCalc <= result && numbers.size > 1) {
                correct = calculateWithConcat(result, newCalc, numbers.filterIndexed { index, _ -> index > 0 })
            }
            if (!correct) {
                newCalc = calc * numbers[0]
                if (newCalc == result && numbers.size == 1) {
                    correct = true
                }
                else {
                    if (newCalc <= result && numbers.size > 1) {
                        correct = calculateWithConcat(result, newCalc, numbers.filterIndexed { index, _ -> index > 0 })
                    }
                    if (!correct) {
                        newCalc = "${calc}${numbers[0]}".toLong()
                        if (newCalc == result && numbers.size == 1) {
                            correct = true
                        }
                        else {
                            if (newCalc <= result && numbers.size > 1) {
                                correct = calculateWithConcat(result, newCalc, numbers.filterIndexed { index, _ -> index > 0 })
                            }
                        }
                    }
                }
            }
        }
        return correct
    }

    fun part1(input: List<String>): Long {
        val equationList = input.map { line -> Pair (
            line.split(": ")
                .first()
                .toLong(),
            line.split(": ")
                .last()
                .split(' ')
                .map{ it.toInt() }
        )}

        val equationSum = equationList.filter {
            val result = it.first
            val numbers = it.second
            val calc = numbers.first().toLong()
            calculate(result, calc, numbers.filterIndexed { index, _ -> index > 0 })
        }.sumOf { it.first }
        return equationSum
    }

    fun part2(input: List<String>): Long {
        val equationList = input.map { line -> Pair (
            line.split(": ")
                .first()
                .toLong(),
            line.split(": ")
                .last()
                .split(' ')
                .map{ it.toLong() }
        )}

        val equationSum = equationList.filter {
            val result = it.first
            val numbers = it.second
            val calc = numbers.first().toLong()
            calculateWithConcat(result, calc, numbers.filterIndexed { index, _ -> index > 0 })
        }.sumOf { it.first }
        return equationSum
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)

    val input = readInput("Day07")

    var startTime = System.currentTimeMillis()
    part1(input).println()
    println("Time: ${System.currentTimeMillis() - startTime}")

    check(part2(testInput) == 11387L)

    startTime = System.currentTimeMillis()
    part2(input).println()
    println("Time: ${System.currentTimeMillis() - startTime}")
}
