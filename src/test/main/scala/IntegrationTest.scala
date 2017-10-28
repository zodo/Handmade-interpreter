import org.scalacheck.Gen
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.prop.{Checkers, GeneratorDrivenPropertyChecks}

/**
  * Created by egor on 28.10.17.
  */
class IntegrationTest extends FunSuite with GeneratorDrivenPropertyChecks with Matchers {

  private def interpret(program: String, input: String = "") = SimpleFacade.run(program, input)

  test("input-output") {
    forAll { value: Int =>
      interpret("ВВОД test;ВЫВОД test;", value.toString) shouldBe Right(value.toString)
    }
  }

  test("conditional") {
    forAll { value: Int =>
      val result = interpret("ВВОД test; ЕСЛИ (test > 100) ВЫВОД 1; ИНАЧЕ ВЫВОД 0;", value.toString) shouldBe Right(if (value > 100) "1" else "0")
      result
    }
  }

  test("loop") {
    forAll(Gen.choose(1, 1000)) { value =>
      interpret("ВВОД value;result=0; ПОКА (result < value) result = result + 1; ВЫВОД result;", value.toString) shouldBe Right(value.toString)
    }
  }

  test("blocks") {
    interpret("ЕСЛИ (1=1) {ВЫВОД 1; ВЫВОД 2;}") shouldBe Right("1 2")
  }

  test("operator +") {
    forAll(Gen.choose(0, 1000), Gen.choose(0, 1000)) { (left, right) =>
      interpret(s"t=$left + $right; ВЫВОД t;") shouldBe Right((left + right).toString)
    }
  }

  test("operator -") {
    forAll(Gen.choose(0, 1000), Gen.choose(0, 1000)) { (left, right) =>
      interpret(s"t=$left - $right; ВЫВОД t;") shouldBe Right((left - right).toString)
    }
  }

  test("operator *") {
    forAll(Gen.choose(0, 1000), Gen.choose(0, 1000)) { (left, right) =>
      interpret(s"t=$left * $right; ВЫВОД t;") shouldBe Right((left * right).toString)
    }
  }

  test("operator /") {
    forAll(Gen.choose(0, 1000), Gen.choose(1, 1000)) { (left, right) =>
      interpret(s"t=$left / $right; ВЫВОД t;") shouldBe Right((left / right).toString)
    }
  }

  test("operator >") {
    forAll(Gen.choose(0, 1000), Gen.choose(0, 1000)) { (left, right) =>
      interpret(s"l=$left; r=$right; ЕСЛИ (l > r) ВЫВОД 1;") shouldBe Right(if (left > right) "1" else "")
    }
  }

  test("operator =") {
    forAll(Gen.choose(0, 1000), Gen.choose(0, 1000)) { (left, right) =>
      interpret(s"l=$left; r=$right; ЕСЛИ (l = r) ВЫВОД 1;") shouldBe Right(if (left == right) "1" else "")
    }
  }

  test("operator <") {
    forAll(Gen.choose(0, 1000), Gen.choose(0, 1000)) { (left, right) =>
      interpret(s"l=$left; r=$right; ЕСЛИ (l < r) ВЫВОД 1;") shouldBe Right(if (left < right) "1" else "")
    }
  }

  test("operator precedence") {
    interpret(s"t=2+2*2-12/6; ВЫВОД t;") shouldBe Right("4")
  }

  test("arrays") {
    forAll { numbers: Seq[Int] =>
      val program =
        """
           |ВВОД n;
           |i=0;
           |ПОКА(i < n) {
           |  ВВОД arr.i;i=i+1;
           |}
           |
           |i=0;
           |ПОКА(i < n) {
           |  ВЫВОД arr.i;i=i+1;
           |}
      """.stripMargin
      interpret(program, s"${numbers.length} ${numbers.mkString(" ")}") shouldBe Right(numbers.mkString(" "))
    }
  }

  test("arrays numeric index") {
    forAll(Gen.choose(0, 1000), Gen.choose(0, 1000)) { (index, value) =>
      interpret(s"i.$index=$value; ВЫВОД i.$index;") shouldBe Right(value.toString)
    }
  }

  test("gnome sort") {
    val program =
      """
        |ВВОД n;
        |
        |i = 0;
        |ПОКА(i < n) {
        |  ВВОД arr.i;
        |  i = i + 1;
        |}
        |
        |i = 0;
        |ПОКА (i < n) {
        |  ЕСЛИ (i=0) i = i + 1;
        |  ИНАЧЕ {
        |    prevI = i - 1;
        |    ЕСЛИ (arr.prevI < arr.i) i = i + 1;
        |    ИНАЧЕ ЕСЛИ (arr.prevI = arr.i) i = i + 1;
        |    ИНАЧЕ {
        |      t = arr.i;
        |      arr.i = arr.prevI;
        |      arr.prevI = t;
        |      i = i - 1;
        |    }
        |  }
        |}
        |
        |i = 0;
        |ПОКА(i < n) {
        |  ВЫВОД arr.i;
        |  i = i + 1;
        |}
      """.stripMargin

    forAll { unsorted: Seq[Int] =>
      interpret(program, s"${unsorted.length} ${unsorted.mkString(" ")}") shouldBe Right(unsorted.sorted.mkString(" "))
    }
  }
}
