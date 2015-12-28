//fun genPrimesTo(n: Int) : List<Int> {
//    val candidates = BooleanArray(size=n + 1, init={
//        i -> when(i) {
//            0, 1 -> false
//            else -> true
//        }
//    })
//
//    for (i in (2..n)) {
//        var counter = 2 * i
//        while (counter <= n) {
//            candidates[counter] = false
//            counter += i
//        }
//    }
//    return candidates.withIndex().filter { it.value }.map { it.index }
//}

fun getFactors(n: Int) : List<Int> {
    return (1..(Math.floor(Math.sqrt(n * 1.0)).toInt()))
        .filter { n % it == 0 }
        .map { arrayListOf(it, n / it) }
        .flatten()
        .distinct()
}

fun main(args: Array<String>) {
    val input = 34000000
    val threshold = input / 10

    for (i in (2..threshold)) {
        val factors = getFactors(i)
        if (factors.sum() >= threshold) {
            println("Match! ${i}")
            break
        }
    }
}
