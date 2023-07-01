fun main() {
    val (a, b, c) = List(3) { readln().toDouble() }
    println((c - b) / a)
}