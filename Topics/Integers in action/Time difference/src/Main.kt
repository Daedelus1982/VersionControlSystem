const val MINS_IN_HOUR = 60
const val SECS_IN_MIN = 60

fun main() {
    val time1 = listOf<Int>(readln().toInt(), readln().toInt(), readln().toInt())
    val time2 = listOf<Int>(readln().toInt(), readln().toInt(), readln().toInt())

    val secs = (time2[0] - time1[0]) * MINS_IN_HOUR * SECS_IN_MIN +
            (time2[1] - time1[1]) * SECS_IN_MIN +
            (time2[2] - time1[2])

    println(secs)
}