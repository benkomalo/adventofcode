fun getValue(index: Int) : Long {
    val base = 20151125L
    val multiple = 252533L
    val divisor = 33554393L

    var exponent = index - 1
    var value = base
    while (exponent > 0) {
        value = (value * multiple) % divisor
        exponent -= 1
    }

    return value
}

fun getIndex(row: Int, col: Int) : Int{
    val rowStart = (1 + (row * (row - 1)) / 2)
    return rowStart + (row+1..(row+col-1)).sum()
}

fun Long.format(digits: Int) = java.lang.String.format("%${digits}d", this)

fun main(args: Array<String>) {
    val row = readLine()?.toInt() ?: 3010
    val col = readLine()?.toInt() ?: 3019

//    for (i in (1..6)) {
//        for (j in (1..6)) {
//            print("${getIndex(i, j)} ")
//        }
//        println()
//    }
//
//    for (i in (1..6)) {
//        for (j in (1..6)) {
//            print("${getValue(getIndex(i, j)).format(10)}")
//        }
//        println()
//    }

    val index = getIndex(row, col)
    println(index)
    println(getValue(index))
}