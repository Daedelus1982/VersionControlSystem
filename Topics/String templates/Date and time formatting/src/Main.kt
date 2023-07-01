fun main() {
    val (hrs, mins, secs) = readln().split(' ')
    val (day, month, yr) = readln().split(' ')

    println("$hrs:$mins:$secs $day/$month/$yr")
}