package exercises

object PatternExcercise extends App{
    object even{
        def unapply(n:Int):Option[Boolean]={
            if (n%2==0) Some(true) else None
        }
    }
    object singleDigit {
        def unapply(n: Int): Option[Boolean] = {
            if (n <10 && n > -10) Some(true) else None
        }
    }
    val x = 14
    val mathProperty = x match {
        case even(_) => "an number is even"
        case singleDigit(_) => "a single digit number"
        case _ => "no property"
    }
    println(mathProperty)

}
