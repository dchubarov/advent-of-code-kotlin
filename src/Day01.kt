fun main() {
    val digits = mapOf(
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun calibrationValue(s: String): Int {
        val (_, l) = s.findAnyOf(digits.keys) ?: return 0
        val (_, r) = s.findLastAnyOf(digits.keys, s.length)
            ?: throw IllegalStateException()
        return digits[l]!! * 10 + digits[r]!!
    }

    fun calibrationSum(input: List<String>): Int =
        input.map(::calibrationValue).sum()

    val testInput = listOf(
        "1abc2",
        "pqr3stu8vwx",
        "a1b2c3d4e5f",
        "treb7uchet"
    )
    check(calibrationSum(testInput) == 142)

    val testInput2 = listOf(
        "two1nine",
        "eightwothree",
        "abcone2threexyz",
        "xtwone3four",
        "4nineeightseven2",
        "zoneight234",
        "7pqrstsixteen"
    )
    check(calibrationSum(testInput2) == 281)

    calibrationSum(readInput("Day01_test")).println()
}
