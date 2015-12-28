sealed class Item(val cost: Int, val damage: Int, val armor: Int) {
    class Weapon(cost: Int, damage: Int) : Item(cost, damage, 0)
    class Armor(cost: Int, armor: Int) : Item(cost, 0, armor)
    class Ring(cost: Int, damage: Int, armor: Int) : Item(cost, damage, armor)

    override fun toString() : String {
        return "[${javaClass} ${cost} ${damage} ${armor}]"
    }
}

class ItemSet(val weapon: Item.Weapon,
              val armor: Item.Armor?,
              val ring1: Item.Ring?,
              val ring2: Item.Ring?) {
    val attack: Int
        get() = weapon.damage + (ring1?.damage ?: 0) + (ring2?.damage ?: 0)

    val defense: Int
        get() = (armor?.armor ?: 0) + (ring1?.armor ?: 0) + (ring2?.armor ?: 0)

    val cost: Int
        get() = weapon.cost + (armor?.cost ?: 0) + (ring1?.cost ?: 0) + (ring2?.cost ?: 0)

    override fun toString() : String {
        return "ItemSet ${weapon} ${armor} ${ring1} ${ring2}"
    }
}

val weapons = arrayOf(
        intArrayOf(8, 4),
        intArrayOf(10, 5),
        intArrayOf(25, 6),
        intArrayOf(40, 7),
        intArrayOf(74, 8)
).map { Item.Weapon(it[0], it[1]) }

val armors = arrayOf(
        null,
        intArrayOf(13, 1),
        intArrayOf(31, 2),
        intArrayOf(53, 3),
        intArrayOf(75, 4),
        intArrayOf(102, 5)
).map { if (it != null) Item.Armor(it[0], it[1]) else null }

val rings = arrayOf(
        null,
        null,
        intArrayOf(25, 1, 0),
        intArrayOf(50, 2, 0),
        intArrayOf(100, 3, 0),
        intArrayOf(20, 0, 1),
        intArrayOf(40, 0, 2),
        intArrayOf(80, 0, 3)
).map { if (it != null) Item.Ring(it[0], it[1], it[2]) else null }

fun willWin(itemSet: ItemSet,
            myHp: Int = 100,
            enemyHp: Int = 100,
            enemyAttack: Int = 8,
            enemyArmor: Int = 2) : Boolean {
    val myDamage = Math.max(1, itemSet.attack - enemyArmor)
    val enemyDamage = Math.max(1, enemyAttack - itemSet.defense)

    var myHpCurrent = myHp
    var enemyHpCurrent = enemyHp
    while (enemyHpCurrent > 0 && myHpCurrent > 0) {
        enemyHpCurrent -= myDamage
        myHpCurrent -= enemyDamage
    }

    return enemyHpCurrent <= 0
}

fun main(args: Array<String>) {
    var cheapest = Int.MAX_VALUE
    for (weapon in weapons) {
        for (armor in armors) {
            for (i in rings.indices) {
                for (j in rings.indices) {
                    if (i == j) {
                        continue
                    }
                    val itemSet = ItemSet(weapon, armor, rings[i], rings[j])
                    if (willWin(itemSet)) {
                        val cost = itemSet.cost
                        if (cost < cheapest) {
//                            println("Yay ${itemSet}")
                            cheapest = cost
                        }
                    }
                }
            }
        }
    }
    println("Cheapest is ${cheapest}")
}