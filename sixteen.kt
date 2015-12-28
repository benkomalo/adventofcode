val fingerprint = hashMapOf<String, Int>(
    "children" to 3,
    "cats" to 7,
    "samoyeds" to 2,
    "pomeranians" to 3,
    "akitas" to 0,
    "vizslas" to 0,
    "goldfish" to 5,
    "trees" to 3,
    "cars" to 2,
    "perfumes" to 1
)

fun main(args: Array<String>) {
    var counter = 1
    val matches = arrayListOf<String>()
    while (true) {
        val line = readLine() ?: break
        val parts = line.split(": ", limit=2)[1].split(", ")

        var allMatches = true
        parts.map { it.split(": ") }.forEach {
            if ((fingerprint.get(it[0]) ?: 0) != it[1].toInt()) {
                allMatches = false
            }
        }
//        println(line)
//        println(detected)

        if (allMatches) {
            matches.add(line)
//            break
        }

        counter++
    }

    for (match in matches) {
        println("Matched: ${match}")
    }
}