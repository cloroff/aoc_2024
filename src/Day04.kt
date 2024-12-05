fun main() {
    fun part1(input: List<String>): Int {
        val regexRight = Regex("XMAS")
        val regexLeft = Regex("SAMX")
        var sum = 0
        sum += input.sumOf { regexRight.findAll(it).count() }
        sum += input.sumOf { regexLeft.findAll(it).count() }

        for (i in input[0].indices) {
            val vertical = String(input.map { it[i] }.toCharArray())
            sum += regexRight.findAll(vertical).count()
            sum += regexLeft.findAll(vertical).count()
            var index = i
            val diagonalLeft = String(input.map {
                if (index in input[0].indices) it[index--] else ' ' }.toCharArray()).trim()
            sum += regexRight.findAll(diagonalLeft).count()
            sum += regexLeft.findAll(diagonalLeft).count()
            index = i
            val diagonalRight = String(input.map {
                if (index in input[0].indices) it[index++] else ' ' }.toCharArray()).trim()
            sum += regexRight.findAll(diagonalRight).count()
            sum += regexLeft.findAll(diagonalRight).count()
        }

        for (i in 1..input.lastIndex) {
            var startIndex = input[0].lastIndex
            val diagonalLeft = String(input
                .mapIndexed { index, it -> if (index >=i && startIndex in input[0].indices) it[startIndex--] else ' ' }
                .toCharArray()).trim()
            sum += regexRight.findAll(diagonalLeft).count()
            sum += regexLeft.findAll(diagonalLeft).count()
            startIndex = 0
            val diagonalRight = String(input
                .mapIndexed { index, it -> if (index >=i && startIndex in input[0].indices) it[startIndex++] else ' ' }
                .toCharArray()).trim()
            sum += regexRight.findAll(diagonalRight).count()
            sum += regexLeft.findAll(diagonalRight).count()
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val regex = Regex("A")
        var sum = 0
        input.forEachIndexed { index, s ->
            if (index in 1..<input.lastIndex) {
                regex.findAll(s).forEach {
                    val x = it.range.first
                    if (x in 1..<input[0].lastIndex) {
                        if (input[index-1][x-1] == 'M'
                            && input[index-1][x+1] == 'M'
                            && input[index+1][x-1] == 'S'
                            && input[index+1][x+1] == 'S') {
                            sum++
                        }
                        else if (input[index-1][x-1] == 'S'
                            && input[index-1][x+1] == 'S'
                            && input[index+1][x-1] == 'M'
                            && input[index+1][x+1] == 'M') {
                            sum++
                        }
                        else if (input[index-1][x-1] == 'S'
                            && input[index-1][x+1] == 'M'
                            && input[index+1][x-1] == 'S'
                            && input[index+1][x+1] == 'M') {
                            sum++
                        }
                        else if (input[index-1][x-1] == 'M'
                            && input[index-1][x+1] == 'S'
                            && input[index+1][x-1] == 'M'
                            && input[index+1][x+1] == 'S') {
                            sum++
                        }
                    }
                }
            }
        }
        return sum
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)

    val input = readInput("Day04")
    part1(input).println()

    check(part2(testInput) == 9)

    part2(input).println()
}
