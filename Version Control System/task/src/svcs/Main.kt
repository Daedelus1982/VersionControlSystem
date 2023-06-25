package svcs

fun main(args: Array<String>) {
    val helpString = """>These are SVCS commands:
        >config     Get and set a username.
        >add        Add a file to the index.
        >log        Show commit logs.
        >commit     Save changes.
        >checkout   Restore a file.""".trimMargin(">")

    when (if (args.isEmpty()) "" else args[0]) {
        "--help", "" -> println(helpString)
        "config" -> println("Get and set a username.")
        "add" -> println("Add a file to the index.")
        "log" -> println("Show commit logs.")
        "commit" -> println("Save changes.")
        "checkout" -> println("Restore a file.")
        else -> println("'${args[0]}' is not a SVCS command.")
    }
}