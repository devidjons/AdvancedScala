package lectures.part1s

import scala.util.Try

object DarkSugars extends App {
    def singleArgMethod(arg:Int):String  = s"$arg little ducks ..."
    val description = singleArgMethod{
        43
        42
    }
    val aTryInstance = Try {
        throw new RuntimeException
    }
    List(1,2,3).map{ x=> x+1}
    //syntax sugar 2
    trait Action{
        def act(x: Int):Int
    }

}
