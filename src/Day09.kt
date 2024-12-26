fun main() {
    fun initDiskMap(input: List<String>): MutableList<Int> {
        val diskMap = input[0].map { it.digitToInt() }
        val completeDisk = mutableListOf<Int>()
        diskMap.forEachIndexed { index, it ->
            if (index % 2 == 0) {
                for (i in 1..it) {
                    completeDisk.add(index / 2)
                }
            } else {
                for (i in 1..it) {
                    completeDisk.add(-1)
                }
            }
        }
        return completeDisk
    }

    fun part1(input: List<String>): Long {
        val completeDisk = initDiskMap(input)
        var lastIndex = completeDisk.lastIndex
        val compressedDisk = completeDisk.toList().mapIndexed { index, it ->
            var result = it
            if (lastIndex > index) {
                if (it == -1) {
                    while (completeDisk[lastIndex] == -1) {
                        lastIndex -= 1
                    }
                    result = completeDisk[lastIndex--]
                }
            }
            result
        }.filterIndexed { index, _ -> index in 0.. lastIndex }
        return compressedDisk.foldIndexed(0) { index, acc, i -> acc + i * index }
    }

    fun part2(input: List<String>): Long {
        val diskMap = input[0].map { it.digitToInt() }
        var pos = 0
        var completeDiskMap = diskMap.mapIndexed { index, it ->
            val result: Triple<Int, Int, Int>
            if (index % 2 == 0) {
                result = Triple(index/2, pos, it)
                pos += it
            }
            else {
                result = Triple(-1, pos, it)
                pos += it
            }
            result
        }
        for (id in completeDiskMap.maxOf { it.first } downTo 0) {
            val source = completeDiskMap.find { it.first == id }
            if (source != null) {
                val target = completeDiskMap.find {
                    it.first == -1 && it.second < source.second && it.third >= source.third }
                if (target != null) {
                    completeDiskMap = completeDiskMap.map {
                        var result = it
                        if (it == target) {
                            result = Triple(target.first, target.second + source.third, target.third - source.third)
                        }
                        if (it == source) {
                            result = Triple(source.first, target.second, source.third)
                        }
                        result
                    }
                }
            }
        }
        return completeDiskMap
            .filter { it.first >= 0 }
            .sumOf { it.first.toLong() * (it.second * 2 + it.third - 1) * it.third / 2 }
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 1928L)

    val input = readInput("Day09")

    var startTime = System.currentTimeMillis()
    part1(input).println()
    println("Time: ${System.currentTimeMillis() - startTime}")

    check(part2(testInput) == 2858L)

    startTime = System.currentTimeMillis()
    part2(input).println()
    println("Time: ${System.currentTimeMillis() - startTime}")
}

