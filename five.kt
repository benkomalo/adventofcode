import java.util.HashSet

val vowels = hashSetOf('a', 'e', 'i', 'o', 'u')
fun containsThreeVowels(s : String) : Boolean {
//    var vowelCount = 0
//    for (c in s) {
//        if (vowels.containsRaw(c)) {
//            vowelCount += 1
//            if (vowelCount >= 3) {
//                return true
//            }
//        }
//    }
    return s.filter { vowels.contains(it) }.map { 1 }.sum() >= 3
}

fun containsDoubleLetter(s : String) : Boolean {
    for ((i, c) in s.withIndex()) {
        if (i < s.length - 1 && s[i + 1] == c) {
            return true
        }
    }
    return false
}

val badStrings = arrayOf("ab", "cd", "pq", "xy")
fun doesNotContainBadString(s : String) : Boolean {
    for (badString in badStrings) {
        if (s.indexOf(badString) > -1) {
            return false
        }
    }
    return true
}

fun main(args: Array<String>) {
    var numGood = 0
    while (true) {
        val line = readLine() ?: break
//        println(line)
        if (containsThreeVowels(line) && containsDoubleLetter(line) && doesNotContainBadString(line)) {
            numGood += 1
//            println("GOOD")
        }
    }
    println(numGood)
}
