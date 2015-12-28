import java.util.ArrayList

class Ingredient(val name: String,
                 val capacity: Int,
                 val durability: Int,
                 val flavor: Int,
                 val texture: Int) {
}

fun getRecipeValue(ingredients: Map<Ingredient, Int>) : Int {
    var capacity = 0
    var durability = 0
    var flavor = 0
    var texture = 0
    for (entry in ingredients.entries) {
        val ingredient = entry.key
        val amount = entry.value
        capacity += ingredient.capacity * amount
        durability += ingredient.durability * amount
        flavor += ingredient.flavor * amount
        texture += ingredient.texture * amount
    }
    if (capacity < 0 || durability < 0 || flavor < 0 || texture < 0) {
        return 0
    }
    return capacity * durability * flavor * texture
}

class Splits(val total: Int, val numItems: Int) : Iterator<List<Int>> {
    var exhausted = false
    val counters: IntArray
    init {
        counters = IntArray(numItems - 1)
    }

    override operator fun hasNext() : Boolean {
        return !exhausted
    }

    private fun advance() {
        var found = false
        for (i in (counters.size - 1 downTo 0)) {
            if (counters[i] < total) {
                counters[i] += 1
                for (j in (i + 1..counters.size - 1)) {
                    counters[j] = counters[i]
                }
                found = true
                break
            }
        }
        exhausted = !found
    }

    override operator fun next() : List<Int> {
        val result = arrayListOf<Int>()
        var current = 0
        for (i in counters) {
            result.add(i - current)
            current = i
        }
        result.add(total - current)
        advance()
        return result
    }
}

fun main(args: Array<String>) {
    val ingredients = ArrayList<Ingredient>()
    while (true) {
        val line = readLine() ?: break
        val (name, rest) = line.split(": ")
        val parts = rest.split(", ")
        var capacity = parts[0].split(" ")[1].toInt()
        var durability = parts[1].split(" ")[1].toInt()
        var flavor = parts[2].split(" ")[1].toInt()
        var texture = parts[3].split(" ")[1].toInt()
        ingredients.add(Ingredient(name, capacity, durability, flavor, texture))
    }

    val total = 100
    val splits = Splits(total, ingredients.size)
    var best = Int.MIN_VALUE
    while (splits.hasNext()) {
        val itemSplits = splits.next()
        val recipe = hashMapOf<Ingredient, Int>()
        for (i in ingredients.indices) {
            recipe.set(ingredients[i], itemSplits[i])
        }
        val value = getRecipeValue(recipe)
//        println("${itemSplits} has ${value}")
        if (value > best) {
            best = value
        }
    }
    println("Best is ${best}")
}