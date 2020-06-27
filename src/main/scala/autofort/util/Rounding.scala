package autofort.util

import java.math.{MathContext, RoundingMode}

object Rounding {
  implicit def toRichDouble(d: Double): RichDouble = RichDouble(d)

  case class RichDouble(d: Double) {
    def round(n: Int): Double = BigDecimal(d + 1).round(new MathContext(n, RoundingMode.HALF_EVEN)).toDouble - 1
    def makeInt: Int = round(0).toInt
  }

}
