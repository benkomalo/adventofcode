enum class Spell(val cost: Int) {
    MAGIC_MISSILE(53) {
        override fun canCast(state: Game) = canAfford(state)

        override fun apply(state: Game) : Game {
            return Game(
                    state.myHp,
                    state.myMana - cost,
                    state.enemyHp - 4,
                    state.enemyAttack,
                    state.effects
            )
        }
    },

    DRAIN(73) {
        override fun canCast(state: Game) = canAfford(state)

        override fun apply(state: Game) : Game {
            return Game(
                    state.myHp + 2,
                    state.myMana - cost,
                    state.enemyHp - 2,
                    state.enemyAttack,
                    state.effects
            )
        }
    },

    SHIELD(113) {
        override fun canCast(state: Game) : Boolean {
            return canAfford(state) && state.effects.find { it is Effect.Shield } == null
        }

        override fun apply(state: Game) : Game {
            return Game(
                    state.myHp,
                    state.myMana - cost,
                    state.enemyHp,
                    state.enemyAttack,
                    state.effects + arrayListOf(Effect.Shield())
            )
        }
    },

    POISON(173) {
        override fun canCast(state: Game) : Boolean {
            return canAfford(state) && state.effects.find { it is Effect.Poison } == null
        }

        override fun apply(state: Game) : Game {
            return Game(
                    state.myHp,
                    state.myMana - cost,
                    state.enemyHp,
                    state.enemyAttack,
                    state.effects + arrayListOf(Effect.Poison())
            )
        }
    },

    RECHARGE(229) {
        override fun canCast(state: Game) : Boolean {
            return canAfford(state) && state.effects.find { it is Effect.Recharge } == null
        }

        override fun apply(state: Game) : Game {
            return Game(
                    state.myHp,
                    state.myMana - cost,
                    state.enemyHp,
                    state.enemyAttack,
                    state.effects + arrayListOf(Effect.Recharge())
            )
        }
    };

    abstract fun apply(state: Game) : Game
    abstract fun canCast(state: Game) : Boolean
    fun canAfford(state: Game) = state.myMana >= cost
}

sealed class Effect(val turns: Int) {
    class Shield(turns: Int = 6): Effect(turns)
    class Poison(turns: Int = 6): Effect(turns)
    class Recharge(turns: Int = 5): Effect(turns)

    fun tick() : Effect {
        return when(this) {
            is Shield -> Shield(turns - 1)
            is Poison -> Poison(turns - 1)
            is Recharge -> Recharge(turns - 1)
        }
    }

    fun isFinished() = turns <= 0
}

class Game(val myHp: Int = 50,
           val myMana: Int = 500,
           val enemyHp: Int = 55,
           val enemyAttack: Int = 8,
           val effects: List<Effect> = arrayListOf()) {

    fun isWin() = enemyHp <= 0
    fun isLoss() = enemyHp > 0 && myHp <= 0

    fun applyBeginEffects() : Game {
        val hasPoison = effects.find { it is Effect.Poison } != null
        val hasRecharge = effects.find { it is Effect.Recharge } != null

        return Game(
                myHp,
                if (hasRecharge) (myMana + 101) else myMana,
                if (hasPoison) (enemyHp - 3) else enemyHp,
                enemyAttack,
                effects.map { it.tick() }.filter { !it.isFinished() }
        )
    }

    fun applyEnemyTurn() : Game {
        val damage = Math.max(1, enemyAttack - myArmor)
        return Game(myHp - damage, myMana, enemyHp, enemyAttack, effects)
    }

    val myArmor : Int
        get() {
            if (effects.find { it is Effect.Shield } != null) {
                return 7
            } else {
                return 0
            }
        }
}

fun attemptRound(startState: Game,
                 manaExpended: Int = 0,
                 bestSoFar: Int = Int.MAX_VALUE,
                 step: Int = 0) : Int {
    if (manaExpended > bestSoFar) {
        return bestSoFar
    }

//    println()
//    println("${step} -- Player turn --")
//    println("${step} - Player has ${startState.myHp} hp, ${startState.myMana} mana")
//    println("${step} - Boss has ${startState.enemyHp}")
    var state = startState.applyBeginEffects()
    if (state.isWin()) {
        return manaExpended
    }

    var newBest = bestSoFar
    for (spell in Spell.values()) {
        if (!spell.canCast(state)) {
            continue
        }
//        println("${step} Player casts ${spell}")

        val newExpended = manaExpended + spell.cost
        var potentialState = spell.apply(state)

//        println()
//        println("${step} -- Enemy turn --")
//        println("${step} - Player has ${potentialState.myHp} hp, ${potentialState.myMana} mana")
//        println("${step} - Boss has ${potentialState.enemyHp}")
        potentialState = potentialState.applyBeginEffects()
        potentialState = potentialState.applyEnemyTurn()
        if (potentialState.isWin()) {
            if (newExpended < newBest) {
                newBest = newExpended
            }
        } else if (!potentialState.isLoss()) {
            newBest = Math.min(
                    newBest,
                    attemptRound(potentialState, newExpended, newBest, step + 1)
            )
        }
    }

    return newBest
}

fun main(args: Array<String>) {
    println(attemptRound(Game()))
}
