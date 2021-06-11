package exercises

object Pimp extends App {
    implicit class SuperString(value:String) {
        def asInt:Int={
            value.toInt
        }
        def encrypt(n:Int):String = {
            value.map(x=> (x+n).toChar)
        }
    }
    implicit class RichInt(value:Int) {
        def times[T](arg:() => Unit):Unit ={
            (1 to value).foreach(_=>arg())
        }
    }

    println("asdf".encrypt(1))
    println(3.times(()=>List(1,2,3)))
}
