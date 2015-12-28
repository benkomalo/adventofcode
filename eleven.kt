val letters = "+abcdefghijklmnopqrstuvwxyz"

fun isGood(s: String) : Boolean {
    val len = s.length

    if (s.indexOf('i') > -1 || s.indexOf('l') > -1 || s.indexOf('o') > -1) {
        return false
    }

    var foundConsecutive = false
    for ((index, value) in s.withIndex()) {
        if (index < len - 2) {
            if ((s[index + 2] - s[index + 1] == 1) && (s[index + 1] - s[index] == 1)) {
                foundConsecutive = true
                break
            }
        }
    }

    if (!foundConsecutive) {
        return false
    }

    var i = 0
    var foundPairs = 0
    while (i < len - 1) {
        if (s[i] == s[i + 1]) {
            foundPairs += 1
            i += 1
        }

        i += 1
    }

    return foundPairs >= 2
}

fun inc(s: String) : String {
    var value : Long = 0
    s.forEach {
        val charValue = letters.indexOf(it)
        value = value * 26 + charValue
    }

    value += 1

    val result = StringBuilder()
    while (value > 0) {
        val charValue = when (value % 26) {
            0L -> 26
            else -> (value % 26).toInt()
        }
        val char = letters[charValue]
        value = (value - 1) / 26;
        result.insert(0, char)
    }
    return result.toString()
}

fun next(s: String) : String {
    var cur = inc(s)
    while (!isGood(cur)) {
        cur = inc(cur)
    }

    return cur
}

fun main(args: Array<String>) {
    println(next("hxbxwxba"))
}