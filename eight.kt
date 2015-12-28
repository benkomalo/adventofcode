fun main(args: Array<String>) {
    var delta = 0
    while (true) {
        val line = readLine() ?: break
        val inCode = line.length
        var inMemory = 0
        var i = 1
        while (i < line.length - 1) {
            val c = line[i]
            when (c) {
                '\\' -> {
                    when (line[i + 1]) {
                        '\\', '"' -> {
                            i += 2
                            inMemory += 1
                        }
                        'x' -> {
                            i += 4
                            inMemory += 1
                        }
                        else -> throw java.lang.IllegalArgumentException("guh?")
                    }
                }
                else -> {
                    inMemory += 1
                    i += 1
                }
            }
        }
        delta += inCode - inMemory
    }
    println(delta)
}