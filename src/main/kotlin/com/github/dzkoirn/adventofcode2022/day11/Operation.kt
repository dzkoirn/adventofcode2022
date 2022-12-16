package com.github.dzkoirn.adventofcode2022.day11

import java.util.Scanner
import java.util.function.BinaryOperator

data class Operation(
    val leftOperand: Operand,
    val arithmeticOperation: ArithmeticOperation,
    val rightOperand: Operand
) {
    fun invoke(v: Long): Long = arithmeticOperation.apply(leftOperand.invoke(v), rightOperand.invoke(v))

    companion object {
        /*
           Operation: new = old + 6
           Operation: new = old * old
           Operation: new = old * 19
           Operation: new = old + 3
         */
        fun parseString(input: String): Operation {
            val (left, operator, right) = Scanner(input).tokens().skip(3).toList()
            return Operation(
                leftOperand = Operand.createFromString(left),
                arithmeticOperation = ArithmeticOperation.fromString(operator),
                rightOperand = Operand.createFromString(right),
            )
        }

        sealed interface Operand: Function1<Long, Long> {
            object OldOperand: Operand {
                override fun invoke(a: Long): Long = a
            }

            data class ConstOperand(
                private val constant: Long
            ) : Operand {
                override fun invoke(a: Long): Long = constant
            }

            companion object {
                fun createFromString(str: String): Operand =
                    if (str == "old") {
                        OldOperand
                    } else {
                        ConstOperand(str.toLong())
                    }
            }
        }

        enum class ArithmeticOperation : BinaryOperator<Long> {
            PLUS {
                override fun apply(t: Long, u: Long): Long = t + u
            }, MUL {
                override fun apply(t: Long, u: Long): Long = t * u
            }, MINUS {
                override fun apply(t: Long, u: Long): Long = t - u
            }, DIV {
                override fun apply(t: Long, u: Long): Long = t / u
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