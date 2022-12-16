package com.github.dzkoirn.adventofcode2022.day11

import java.util.Scanner
import java.util.function.BinaryOperator

data class Operation(
    val leftOperand: Operand,
    val arithmeticOperation: ArithmeticOperation,
    val rightOperand: Operand
) {
    fun invoke(v: Int): Int = arithmeticOperation.apply(leftOperand.invoke(v), rightOperand.invoke(v))
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Operation

        if (leftOperand != other.leftOperand) return false
        if (arithmeticOperation != other.arithmeticOperation) return false
        if (rightOperand != other.rightOperand) return false

        return true
    }

    override fun hashCode(): Int {
        var result = leftOperand.hashCode()
        result = 31 * result + arithmeticOperation.hashCode()
        result = 31 * result + rightOperand.hashCode()
        return result
    }

    override fun toString(): String {
        return "Operation(leftOperand=$leftOperand, arithmeticException=$arithmeticOperation, rightOperand=$rightOperand)"
    }

    companion object {

        /*
           Operation: new = old + 6
           Operation: new = old * old
           Operation: new = old * 19
           Operation: new = old + 3
         */
        fun parseString(input: String): Operation {
            val (left, operator, right) = Scanner(input).tokens().skip(3).toList()
            return return Operation(
                leftOperand = Operand.createFromString(left),
                arithmeticOperation = ArithmeticOperation.fromString(operator),
                rightOperand = Operand.createFromString(right),
            )
        }

        sealed interface Operand: Function1<Int, Int> {
            object OldOperand: Operand {
                override fun invoke(a: Int): Int = a
            }

            data class ConstOperand(
                private val constant: Int
            ) : Operand {
                override fun invoke(a: Int): Int = constant
            }

            companion object {
                fun createFromString(str: String): Operand =
                    if (str == "old") {
                        OldOperand
                    } else {
                        ConstOperand(str.toInt())
                    }
            }
        }

        enum class ArithmeticOperation : BinaryOperator<Int> {
            PLUS {
                override fun apply(t: Int, u: Int): Int = t + u
            }, MUL {
                override fun apply(t: Int, u: Int): Int = t * u
            }, MINUS {
                override fun apply(t: Int, u: Int): Int = t - u
            }, DIV {
                override fun apply(t: Int, u: Int): Int = t / u
            };

            companion object {
                fun fromString(input: String): ArithmeticOperation =
                    when(input) {
                        "+" -> PLUS
                        "-" -> MINUS
                        "*" -> MUL
                        "/" -> DIV
                        else -> throw IllegalArgumentException("No such operation defined $input")
                    }
            }
        }
    }
}