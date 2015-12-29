enum class Register {
    a, b
}

sealed class Instruction {
    class Half(val reg: Register) : Instruction()
    class Triple(val reg: Register) : Instruction()
    class Inc(val reg: Register) : Instruction()
    class Jump(val offset: Int) : Instruction()
    class JumpIfEven(val reg: Register, val offset: Int) : Instruction()
    class JumpIfOne(val reg: Register, val offset: Int) : Instruction()
}

fun main(args: Array<String>) {
    val instructions = arrayListOf<Instruction>()
    while (true) {
        val line = readLine() ?: break
        var (opCode, rest) = line.split(" ", limit=2)
        instructions.add(when (opCode) {
            "hlf" -> Instruction.Half(Register.valueOf(rest))
            "tpl" -> Instruction.Triple(Register.valueOf(rest))
            "inc" -> Instruction.Inc(Register.valueOf(rest))
            "jmp" -> Instruction.Jump(rest.toInt())
            "jie", "jio" -> {
                var (reg, offset) = rest.split(", ")
                if (opCode.equals("jie")) {
                    Instruction.JumpIfEven(Register.valueOf(reg), offset.toInt())
                } else {
                    Instruction.JumpIfOne(Register.valueOf(reg), offset.toInt())
                }
            }
            else -> throw java.lang.IllegalArgumentException("guh? ${opCode}")
        })
    }

    var pointer = 0
    val values = hashMapOf(Register.a to 0, Register.b to 0)
    while (pointer >= 0 && pointer < instructions.size) {
        val instruction = instructions[pointer]
        println("${values.get(Register.a)} ${values.get(Register.b)} ${pointer} Executing ${instruction}")
        when (instruction) {
            is Instruction.Half -> {
                values.put(instruction.reg, values[instruction.reg]!! / 2)
                pointer += 1
            }
            is Instruction.Triple -> {
                values.put(instruction.reg, values[instruction.reg]!! * 3)
                pointer += 1
            }
            is Instruction.Inc -> {
                values.put(instruction.reg, values[instruction.reg]!! + 1)
                pointer += 1
            }
            is Instruction.Jump -> {
                pointer = pointer + instruction.offset
            }
            is Instruction.JumpIfEven -> {
                if (values.get(instruction.reg)!! % 2 == 0) {
                    pointer = pointer + instruction.offset
                } else {
                    pointer += 1
                }
            }
            is Instruction.JumpIfOne -> {
                if (values.get(instruction.reg)!! == 1) {
                    pointer = pointer + instruction.offset
                } else {
                    pointer += 1
                }
            }
        }
    }
    println("FINAL : ${values.get(Register.a)} ${values.get(Register.b)}")
}