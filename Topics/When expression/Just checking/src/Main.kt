fun main() {
    val choice = readln().toInt()
    println(
        when (choice) {
            1, 3, 4 -> "No!"
            2 -> "Yes!"
            else -> "Unknown number"
        }
    )
}