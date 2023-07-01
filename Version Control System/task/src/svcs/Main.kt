package svcs

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun main(args: Array<String>) {
        val helpString = """>These are SVCS commands:
        >config     Get and set a username.
        >add        Add a file to the index.
        >log        Show commit logs.
        >commit     Save changes.
        >checkout   Restore a file.""".trimMargin(">")

    val commitsFolder = File("vcs/commits")
    val logFile = File("vcs/log.txt")
    val indexFile = File("vcs/index.txt")
    val configFile = File("vcs/config.txt")
    if (!File("vcs").exists()) File("vcs").mkdir()
    if (!commitsFolder.exists()) commitsFolder.mkdir()
    logFile.createNewFile()
    indexFile.createNewFile()
    configFile.createNewFile()

    val arg1 = if (args.size == 2) args[1] else null

    when (if (args.isEmpty()) "" else args[0]) {
        "--help", "" -> println(helpString)
        "config" -> println(config(arg1, configFile))
        "add" -> println(add(arg1, indexFile))
        "log" -> println(log(logFile))
        "commit" -> println(commit(arg1?.substring(0, arg1.length), indexFile, logFile, configFile.readText()))
        "checkout" -> println("Restore a file.")
        else -> println("'${args[0]}' is not a SVCS command.")
    }
}

fun config(username: String?, configFile: File): String {
    if (!username.isNullOrEmpty()) configFile.writeText(username)
    val nameInFile = configFile.readText()

    return if (nameInFile.isNotEmpty()) "The username is $nameInFile." else "Please, tell me who you are."
}

fun add(filename: String?, indexFile: File): String {
    return if (!filename.isNullOrEmpty()) {
        if (File(filename).exists()) {
            val indexedFiles = buildList() {
                addAll(indexFile.readLines())
                add(filename)
            }
            indexFile.writeText(indexedFiles.joinToString("\n"))
            "The file '$filename' is tracked."
        } else {
            "Can't find '$filename'."
        }
    } else if (indexFile.exists()) {
        val trackedFiles = indexFile.readLines()
        if (trackedFiles.isNotEmpty()) "Tracked files:\n${trackedFiles.joinToString("\n")}" else "Add a file to the index."

    } else {
        "Add a file to the index."
    }
}

fun commit(message: String?, indexFile: File, logFile: File, username: String): String {
    if (message.isNullOrEmpty()) return "Message was not passed."
    val checksums = checksumsIndexFile(indexFile)
    val checksum = checksumString(checksums.joinToString())
    val logItems = getLogItems(logFile)
    if (logItems.isEmpty() || checksum != logItems.first().checksum) {
        val commitFolder = File("vcs/commits/$checksum")
        val indexedFiles = indexFile.readLines().map { File(it) }
        indexedFiles.forEach {it.copyTo(File(commitFolder.absolutePath + "//${it.name}"))}
        val logItem = LogItem(checksum, username, message)
        logFile.writeText(logItem.toString())
        if (logItems.isNotEmpty()) logFile.appendText(logItems.joinToString("\n\n", prefix="\n\n") { it.toString() })
        return "Changes are committed."
    }

    return "Nothing to commit."
}

fun checksumsIndexFile(indexFile: File): List<String> {
    val messageDigest = MessageDigest.getInstance("SHA-256")

    return indexFile.readLines()
        .map { File(it).readBytes() }
        .map { messageDigest.digest(it) }
        .map { BigInteger(1, it) }
        .map { it.toString(16) }
}

fun checksumString(str: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    return BigInteger(1, messageDigest.digest(str.toByteArray())).toString(16)
}

data class LogItem(val checksum: String, val author: String, val message: String) {
    override fun toString(): String {
        return "commit $checksum\nAuthor: $author\n$message"
    }
}

fun createFromLogFile(checksumLine: String, authorLine: String, messageLine: String): LogItem {
    return LogItem(checksumLine.substringAfter("commit "),
        authorLine.substringAfter("Author: "),
        messageLine)
}

fun getLogItems(logFile: File): List<LogItem> {
    val lines = logFile.readLines()

    return lines.mapIndexed { index, s ->
        if (s.startsWith("commit")) createFromLogFile(s, lines[index + 1], lines[index + 2]) else null
    }
    .filterNotNull()
}

fun log(logFile: File): String {
    val logItems = getLogItems(logFile)
    if (logItems.isEmpty()) return "No commits yet."

    return logItems.joinToString("\n\n") { it.toString() }.trim()
}