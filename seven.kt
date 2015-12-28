import java.util.HashMap
import kotlin.text.Regex

interface Node {
    val value: Int

    val unsignedValue: Int
        get() = when {
            (value >= 0) -> value
            else -> -1 * value - 1
        }
}

class Literal(val _value: Int): Node {
    override val value: Int
        get() = _value
}

class AndGate(val left: Node, val right: Node): Node {
    override val value: Int
        get() = left.value and right.value
}

class OrGate(val left: Node, val right: Node): Node {
    override val value: Int
        get() = left.value or right.value
}

class LShift(val operand: Node, val amount: Int): Node {
    override val value: Int
        get() = operand.value shl amount
}

class RShift(val operand: Node, val amount: Int): Node {
    override val value: Int
        get() = operand.value shr amount
}

class Not(val operand: Node): Node {
    override val value: Int
        get() = operand.value.inv()
}

class NamedInput(val name: String, val context: Map<String, Node>): Node {
    override val value: Int by lazy {
        context.get(name)!!.value
    }
}

class Parser(val context: Map<String, Node>) {
    fun parse(expression: String): Node {
        return when {
            expression.indexOf("AND") > -1 -> {
                val (left, right) = expression.split(" AND ")
                AndGate(parse(left), parse(right))
            }
            expression.indexOf("OR") > -1 -> {
                val (left, right) = expression.split(" OR ")
                OrGate(parse(left), parse(right))
            }
            expression.indexOf("LSHIFT") > -1 -> {
                val (left, right) = expression.split(" LSHIFT ")
                LShift(parse(left), right.toInt())
            }
            expression.indexOf("RSHIFT") > -1 -> {
                val (left, right) = expression.split(" RSHIFT ")
                RShift(parse(left), right.toInt())
            }
            expression.indexOf("NOT") > -1 -> {
                Not(parse(expression.substring(4)))
            }
            else -> {
                try {
                    Literal(expression.toInt())
                } catch (e: NumberFormatException) {
                    NamedInput(expression, context)
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    val symbolMap = HashMap<String, Node>()
    val parser = Parser(symbolMap)

    while (true) {
        val line = readLine() ?: break
        var (expression, symbol) = line.split(" -> ")
        val node = parser.parse(expression)
        symbolMap.put(symbol, node)
    }

    println("whoa")
    println(symbolMap.get("a")!!.unsignedValue)
}