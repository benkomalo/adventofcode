fun process(s: String): String {
    var curChar : Char? = null
    var curCount = 0
    val result = StringBuilder()
    for (c in s) {
        if (c != curChar) {
            if (curChar != null) {
                result.append(curCount).append(curChar)
            }
            curChar = c
            curCount = 1
        } else {
            curCount += 1
        }
    }

    if (curCount > 0) {
        result.append(curCount).append(curChar)
    }
    return result.toString()
}

fun main(args: Array<String>) {
    var line = readLine() ?: return
    println(line)
    (0..39).forEach {
        line = process(line)
        println(line)
    }
    println(line.length)
}