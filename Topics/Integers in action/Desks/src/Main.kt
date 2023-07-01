const val PLACES_PER_DESK = 2U

fun main() {
    val group1 = readln().toUInt()
    val group2 = readln().toUInt()
    val group3 = readln().toUInt()

    val group1Desks = group1 / PLACES_PER_DESK + group1 % PLACES_PER_DESK
    val group2Desks = group2 / PLACES_PER_DESK + group2 % PLACES_PER_DESK
    val group3Desks = group3 / PLACES_PER_DESK + group3 % PLACES_PER_DESK
    println(group1Desks + group2Desks + group3Desks)
}