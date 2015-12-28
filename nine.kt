import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet

fun findBest(cities: Set<String>,
             paths: Map<Pair<String, String>, Int>,
             pathSoFar: List<String>,
             costSoFar: Int = 0): Int {
    if (pathSoFar.size == cities.size) {
        return costSoFar
    }

    var best = Int.MAX_VALUE
    val currentCity = pathSoFar[pathSoFar.size - 1]
    cities.forEach {
        if (!it.equals(currentCity) && !pathSoFar.contains(it)) {
            val targetCity = it
            val distance = paths.get(Pair(currentCity, targetCity))
            distance?.let {
                val candidateSolution = findBest(
                        cities, paths, pathSoFar + listOf(targetCity), costSoFar + distance
                )
                if (candidateSolution < best) {
                    best = candidateSolution
                }
            }
        }
    }
    return best
}

fun main(args: Array<String>) {
    val cities: MutableSet<String> = HashSet()
    val paths: MutableMap<Pair<String, String>, Int> = HashMap()
    while (true) {
        val line = readLine() ?: break
        val (rawPath, rawValue) = line.split(" = ")
        val (from, to) = rawPath.split(" to ")
        cities.add(from)
        cities.add(to)
        paths.put(Pair(from, to), rawValue.toInt())
        paths.put(Pair(to, from), rawValue.toInt())
    }

    val start = "__START__"
    cities.forEach {
        paths.put(Pair(start, it), 0)
    }
    cities.add(start)

    println(findBest(cities, paths, listOf(start), 0))
}