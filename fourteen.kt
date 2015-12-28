import java.util.ArrayList
import kotlin.text.Regex

class Reindeer(val name: String, val speed: Int, val flyTime: Int, val restTime: Int) {
    fun distanceTravelled(time: Int) : Int {
        val period = flyTime + restTime
        val wholeCycles = time / period
        val partFlyTime = intArrayOf(time % period, flyTime).min() ?: 0

        return speed * flyTime * wholeCycles + partFlyTime * speed
    }
}

val RE = Regex("(\\w+) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds.")
fun main(args: Array<String>) {
    val reindeers = ArrayList<Reindeer>()
    while (true) {
        val line = readLine() ?: break
        val match = RE.matchEntire(line)!!
        val name = match.groups[1]!!.value
        val speed = match.groups[2]!!.value.toInt()
        val stamina = match.groups[3]!!.value.toInt()
        val restTime = match.groups[4]!!.value.toInt()
        reindeers.add(Reindeer(name, speed, stamina, restTime))
    }

    val time = 2503
    for (reindeer in reindeers) {
        println("${reindeer.name} has travelled ${reindeer.distanceTravelled(time)}")
    }
    println((reindeers.map { it.distanceTravelled(time) }.max()))
}
