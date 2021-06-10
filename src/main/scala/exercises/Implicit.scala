package exercises

object Implicit extends App{
    case class Person(name:String, age: Int)
    val l1 = List(
        Person("asdf", 12),
        Person("bsdf", 15),
        Person("Amy", 16)
    )
    implicit val personOrder:Ordering[Person] = Ordering.fromLessThan(_.name < _.name)

    case class Purchase(Price:Int, nUnits:Int)
    object Purchase{
        implicit val commonSort:Ordering[Purchase] = Ordering.by(x=> x.nUnits*x.Price)
    }
    object PriceSorting{
        implicit val priceSorting:Ordering[Purchase] = Ordering.fromLessThan(_.Price< _.Price)
    }
    object UnitsSorting{
        implicit val unitsSorting:Ordering[Purchase] = Ordering.fromLessThan(_.nUnits < _.nUnits)
    }
}
