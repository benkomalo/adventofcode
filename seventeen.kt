fun main(args: Array<String>) {
    val containers = intArrayOf(
        11,
        30,
        47,
        31,
        32,
        36,
        3,
        1,
        5,
        3,
        32,
        36,
        15,
        11,
        46,
        26,
        28,
        1,
        19,
        3
    )
    val sum = 150
    val DP = Array<IntArray>(size = containers.size, init = { IntArray(size=sum + 1) })

    for (i in containers.indices) {
        DP[i][0] = 0
    }

    for ((i, size) in containers.withIndex()) {
        for (j in (0..sum)) {
            if (j < size) {
                if (i > 0) {
                    DP[i][j] = DP[i - 1][j]
                }
            } else if (j == size) {
                if (i > 0) {
                    DP[i][j] = 1 + DP[i - 1][j]
                } else {
                    DP[i][j] = 1
                }
            } else {
                if (i > 0) {
                    DP[i][j] = DP[i - 1][j] + DP[i - 1][j  - size]
                } else {
                    DP[i][j] = 0
                }
            }
        }
    }
    for (i in containers.indices) {
        print("${containers[i]}:\t")
        for (j in (0..sum)) {
            print("${DP[i][j]} ")
        }
        println()
    }
    println("Whoa. Done. ${DP[containers.size - 1][sum]}")
}