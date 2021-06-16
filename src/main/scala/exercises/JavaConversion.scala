package exercises

import java.util.Optional
//import scala.language.implicitConversions

object JavaConversion extends App{
    implicit class JavaToScala[T](value:Optional[T]){
        def asScala:Option[T] = if (value.isEmpty) None else Some(value.get())
    }
    val r1 = Optional.of(25)
    println(r1.asScala)
//    implicit def ToInt1(value:String):Int =value.toInt
    implicit def ToInt1(value:String) =augmentString(value).toInt
//    println("13" / 4)
    1 to 3

}
