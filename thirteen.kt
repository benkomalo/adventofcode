import java.util.HashMap
import java.util.HashSet
import kotlin.text.Regex

class Permutations<T>(val items: Set<T>) : Iterator<List<T>> {
    val underlyingIter = items.iterator()
    var current : T? = null
    var rest : Permutations<T>? = null

    fun setupNextHeadItem() : Boolean {
        if (underlyingIter.hasNext()) {
            current = underlyingIter.next()
            if (items.size > 1) {
                rest = Permutations(HashSet<T>(items.filter { it != current }))
            }
            return true
        } else {
            return false
        }
    }

    fun isSingleItem() : Boolean {
        return current != null && rest == null
    }

    fun ensureHeadIsSetup() {
        if (current == null) {
            setupNextHeadItem()
        }
        rest?.let {
            if (!it.hasNext()) {
                setupNextHeadItem()
            }
        }
    }

    override operator fun hasNext() : Boolean {
        return underlyingIter.hasNext()
                || (isSingleItem() && current != null)
                || (rest?.hasNext() ?: false)
    }

    override operator fun next() : List<T> {
        ensureHeadIsSetup()

        if (isSingleItem()) {
            val result = current!!
            current = null
            return arrayListOf<T>(result)
        } else {
            val result = arrayListOf<T>(current!!) + rest!!.next()
            return result
        }
    }
}

fun computeCost(list: List<String>, costs: Map<Pair<String, String>, Int>) : Int {
    var sum = 0
    for (i in list.indices) {
        val nextI = if (i == list.size - 1) 0 else (i + 1)
        sum += costs.get(Pair(list[i], list[nextI]))!!
        sum += costs.get(Pair(list[nextI], list[i]))!!
    }

//    println("Tried ${list} : ${sum}")
    return sum
}

val RE = Regex("(\\w+) would (lose|gain) (\\d+) happiness units by sitting next to (\\w+).")
fun main(args: Array<String>) {
    val names = HashSet<String>()
    val costs = HashMap<Pair<String, String>, Int>()
    while (true) {
        val line = readLine() ?: break
        val match = RE.matchEntire(line)!!
        val subject = match.groups[1]!!.value
        val neighbour = match.groups[4]!!.value
        val cost = when (match.groups[2]!!.value) {
            "lose" -> -1 * match.groups[3]!!.value.toInt()
            "gain" -> match.groups[3]!!.value.toInt()
            else -> throw java.lang.IllegalArgumentException("Unexpected ${match.groups[2]}")
        }
        names.add(subject)
        names.add(neighbour)
        costs.put(Pair(subject, neighbour), cost)
    }

//    println(costs)
    val first = names.iterator().next()
    val rest = Permutations(names.subtract(hashSetOf(first)))
    var bestCost = Int.MIN_VALUE
    while (rest.hasNext()) {
        val cost = computeCost(arrayListOf(first) + rest.next(), costs)
        if (cost > bestCost) {
            bestCost = cost
        }
    }

    println("Best cost is ${bestCost}")
}