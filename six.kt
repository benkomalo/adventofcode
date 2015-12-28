import java.util.HashMap
import kotlin.text.Regex

enum class Action {
    ON,
    OFF,
    TOGGLE
}

fun setRange(lights: MutableMap<Pair<Int, Int>, Boolean>,
             topLeft: Pair<Int, Int>,
             bottomRight: Pair<Int, Int>,
             action: Action) {
    (topLeft.first..bottomRight.first).forEach(fun(x) {
        (topLeft.second..bottomRight.second).forEach(fun(y) {
            val coordinate = Pair(x, y)
            when (action) {
                Action.ON -> lights.set(coordinate, true)
                Action.OFF -> lights.set(coordinate, false)
                Action.TOGGLE -> lights.set(coordinate, !(lights.get(coordinate) ?: false))
                else -> throw IllegalStateException("Unknown action ${action}")
            }
        })
    })
}

fun initializeOff(lights: MutableMap<Pair<Int, Int>, Boolean>) {
    setRange(lights, Pair(0, 0), Pair(999, 999), Action.OFF)
}

fun main(args: Array<String>) {
    val lights : MutableMap<Pair<Int, Int>, Boolean> = HashMap(1000000)
    initializeOff(lights)

    val commandRegex = Regex("(turn on|turn off|toggle) (\\d+),(\\d+) through (\\d+),(\\d+)")
    while (true) {
        val line = readLine() ?: break
        val match = commandRegex.matchEntire(line)!!
        val command : Action = when(match.groups[1]!!.value) {
            "turn on" -> Action.ON
            "turn off" -> Action.OFF
            "toggle" -> Action.TOGGLE
            else -> throw IllegalArgumentException("Unexpected command ${match}")
        }
        val topLeft = Pair(match.groups[2]!!.value.toInt(), match.groups[3]!!.value.toInt())
        val bottomRight = Pair(match.groups[4]!!.value.toInt(), match.groups[5]!!.value.toInt())
        setRange(lights, topLeft, bottomRight, command)
    }

    val numOn = lights.values.filter { it }.map { 1 }.sum()
    println("Num total: ${lights.size}")
    println("Num on: ${numOn}")
}