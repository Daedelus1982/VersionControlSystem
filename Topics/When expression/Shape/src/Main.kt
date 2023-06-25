fun main() {
    val choice = "You have chosen a"
    println(
        when (readln().toInt()) {
            1 -> "$choice square"
            2 -> "$choice circle"
            3 -> "$choice triangle"
            4 -> "$choice rhombus"
            else -> "There is no such shape!"
        }
    )
}