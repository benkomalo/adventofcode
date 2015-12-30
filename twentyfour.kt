import java.util.HashSet

data class Score(val numItems: Int, val entanglement: Long) : Comparable<Score> {
    override operator fun compareTo(other: Score) : Int {
        if (numItems < other.numItems) {
            return -1
        } else if (numItems > other.numItems) {
            return 1
        } else if (entanglement < other.entanglement) {
            return -1
        } else if (entanglement > other.entanglement) {
            return 1
        }
        return 0
    }

    companion object {
        val MAX = Score(Int.MAX_VALUE, Long.MAX_VALUE)
    }
}

data class Subset(val items: Set<Int>) : Comparable<Subset> {
    val score : Score by lazy {
        Score(items.size, items.map { it.toLong() }.reduce { a, b -> a * b })
    }

    override operator fun compareTo(other: Subset) = score.compareTo(other.score)
}

data class DPSubsets(val sets : Array<IntArray>) {
    companion object Factory {
        val EMPTY = DPSubsets(arrayOf())

        fun combine(prevWithout: DPSubsets, prevWith: DPSubsets, newValue: Int) : DPSubsets {
            val setsWith = if (prevWith == EMPTY) {
                arrayOf(intArrayOf(newValue))
            } else {
                Array(prevWith.sets.size, { prevWith.sets[it] + intArrayOf(newValue) })
            }

            return DPSubsets(prevWithout.sets + setsWith)
        }
    }

    override fun toString() : String {
        val builder = StringBuilder()
        for (set in sets) {
            for (i in set) {
                builder.append("${i}  ")
            }
            builder.appendln()
        }
        return builder.toString()
    }
}

fun findAllSubsets(items: List<Int>, sum: Int) : DPSubsets {
    val N = items.size
    val DP = Array<Array<DPSubsets>>(N, { Array<DPSubsets>(sum + 1, { DPSubsets.EMPTY }) })

    for (i in (0..N - 1)) {
        DP[i][0] = DPSubsets.EMPTY

        val size = items[i]
        for (s in (1..sum)) {
            val prevWithout = if (i > 0) DP[i - 1][s] else DPSubsets.EMPTY
            if (s >= size) {
                val prevWith = if (i > 0) {
                    DP[i - 1][s - size]
                } else {
                    DPSubsets.EMPTY
                }
                if (prevWith != DPSubsets.EMPTY || s == size) {
                    DP[i][s] = DPSubsets.combine(prevWithout, prevWith, size)
                } else {
                    DP[i][s] = prevWithout
                }
            } else {
                DP[i][s] = prevWithout
            }
        }
    }

//    for (row in DP) {
//        for (s in row) {
//            print("${s.sets.size} ")
//        }
//        println()
//    }

    return DP[N - 1][sum]
}

fun covers(a: Subset, b: Subset, c: Subset, total: Set<Int>) : Boolean{
    return total.all {
        ((if (a.items.contains(it)) 1 else 0)
          + (if (b.items.contains(it)) 1 else 0)
          + (if (c.items.contains(it)) 1 else 0)) == 1
    }
}

fun main(args: Array<String>) {
    val weights = arrayListOf<Int>()
    while (true) {
        val line = readLine() ?: break
        weights.add(line.toInt())
    }

    val target = weights.sum() / 3
    println("Target: ${target}")
    val subsets = findAllSubsets(weights, target).sets.map{ Subset(it.toSet()) }
    println("Found ${subsets.size} subsets that sum to the target weight")

    // Looks like sizes are unique? Problem doesn't say so. But *shrug* - makes it simpler
    var weightsSet : Set<Int> = HashSet(weights)
    assert(weightsSet.size == weights.size)

    // Take 1 - brute force - too slow
//    var best = Score.MAX
//    var bestCovers : Array<Subset>? = null
//    for (i in (0..subsets.size - 1)) {
//        for (j in (i + 1..subsets.size - 1)) {
//            for (k in (j + 1..subsets.size - 1)) {
//                if (covers(subsets[i], subsets[j], subsets[k], weightsSet)) {
//                    var score = arrayOf(subsets[i].score, subsets[j].score, subsets[k].score).min()!!
//                    if (score < best) {
//                        best = score
//                        bestCovers = arrayOf(subsets[i], subsets[j], subsets[k])
//                    }
//                }
//            }
//        }
//    }

    // Gross - same worst case but probably fast enough in practice?
    val sorted = subsets.sorted()
    var bestCovers : Array<Subset>? = null
    outer@ for ((i, candidate) in sorted.withIndex()) {
        for (j in sorted.indices) {
            for (k in sorted.indices) {
                if (i == j || i == k || j == k) {
                    continue
                }

                if (covers(candidate, sorted[j], sorted[k], weightsSet)) {
                    bestCovers = arrayOf(candidate, sorted[j], sorted[k])
                    break@outer
                }
            }
        }
    }

    println("==")
    println(bestCovers!![0])

    println("==")
    println(bestCovers[1])

    println("==")
    println(bestCovers[2])

    println("==")
    println(bestCovers[0].score)
}