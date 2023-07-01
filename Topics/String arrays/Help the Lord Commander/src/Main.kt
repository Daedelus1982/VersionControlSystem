fun main() {  
    val beyondTheWall = readln().split(',').map { it }.toTypedArray()
    val backFromTheWall = readln().split(',').map { it }.toTypedArray()
    // do not touch the lines above
    // write your code here
    println(beyondTheWall.contentEquals(backFromTheWall))

}