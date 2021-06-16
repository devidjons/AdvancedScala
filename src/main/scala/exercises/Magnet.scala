package exercises

object Magnet extends App {
    trait MathP {
        def add1(x:Int):Int = x+1
        def add1(x:String):Int = x.toInt + 1
    }



    def add1(magnet: Magnet):Int = magnet()

    trait Magnet {
        def apply():Int
    }

    implicit class IntMath(value: => Int) extends Magnet{
        override def apply(): Int = value+1
    }

    implicit class StringMath(value: => String) extends Magnet{
        override def apply(): Int = value.toInt +1
    }
    println(add1(1))
    println(add1("2"))
}
