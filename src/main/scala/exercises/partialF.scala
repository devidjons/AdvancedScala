package exercises


object partialF extends App{
    val pf1 = new PartialFunction[Int, Int] {
        override def isDefinedAt(x: Int): Boolean = Set(1,2,5).contains(x)

        override def apply(v1: Int): Int =
            v1 match {
                case 1 => 42
                case 2 => 44
                case 5 => 55
            }
    }
    val CBanswer:PartialFunction[String, String]={
        case "Hi"=> "hi"
        case "question"=> "answer"
        case "bye"=> "bye"
    }
    scala.io.Source.stdin.getLines().foreach(CBanswer(_))
}
