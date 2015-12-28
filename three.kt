import java.util.HashSet

fun main(args: Array<String>) {
    val contents = readLine()
    contents?.let {
        var location = Pair(0, 0)
        val visited = HashSet<Pair<Int, Int>>()
        visited.add(location)
        for (c in contents) {
            when (c) {
                '<' -> location = Pair(location.first - 1, location.second)
                '^' -> location = Pair(location.first, location.second - 1)
                '>' -> location = Pair(location.first + 1, location.second)
                'v' -> location = Pair(location.first, location.second + 1)
                else -> throw IllegalArgumentException("eh? $c")
            }
            visited.add(location)
        }
        println("OK then ${visited.size}")
    }
}
