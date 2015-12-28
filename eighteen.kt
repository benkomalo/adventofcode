fun computeNeighboursOn(lights: List<List<Char>>, i : Int, j: Int) : Int {
    var results = 0
    for (ii in -1..1) {
        for (jj in -1..1) {
            if (ii == 0 && jj == 0) {
                continue
            }

            val ni = i + ii
            val nj = j + jj
            if (ni < 0 || ni >= lights.size || nj < 0 || nj >= lights[i].size) {
                continue
            }

            if (lights[ni][nj] == '#') {
                results++
            }
        }
    }
    return results
}

fun getNextStep(lights: List<List<Char>>) : List<List<Char>> {
    val results = arrayListOf<List<Char>>()
    for ((i, row) in lights.withIndex()) {
        val newRow = arrayListOf<Char>()
        for ((j, value) in row.withIndex()) {
            val numNeighboursOn = computeNeighboursOn(lights, i, j)
            val newValue = when(value) {
                '#' -> if (numNeighboursOn == 2 || numNeighboursOn == 3) '#' else '.'
                else -> if (numNeighboursOn == 3) '#' else '.'
            }
            newRow.add(newValue)
        }
        results.add(newRow)
    }
    return results
}

fun numOn(lights: List<List<Char>>) : Int {
    var results = 0
    for (row in lights) {
        results += row.filter { it == '#' }.map { 1 }.sum()
    }
    return results
}

fun printLights(lights: List<List<Char>>) {
    for (row in lights) {
        println(row)
    }
}

fun main(args: Array<String>) {
    val lights = arrayListOf<List<Char>>()
    while (true) {
        val line = readLine() ?: break
        lights.add(line.map { it })
    }

    var state : List<List<Char>> = lights
    for (i in 1..100) {
        state = getNextStep(state)
    }
    printLights(state)
    println(numOn(state))
}
