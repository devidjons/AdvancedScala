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

object TP extends App{
    case class User(name:String, age:Int)


    trait Equal[T] {
        def compare(a:T,b:T):Boolean
    }

    object Equal {
        def apply[T](implicit eq:Equal[T]):Equal[T] = eq
    }

    implicit object UserEqual extends Equal[User] {
        override def compare(a: User, b: User): Boolean = a.name==b.name && a.age==b.age
    }


    trait HTMLSerializer[T] {
        def serialize(value:T):String
    }

    object HTMLSerializer {
//        def serialize[T](value:T)(implicit serializer:HTMLSerializer[T]):String = serializer.serialize(value)
        def apply[T](implicit serializer:HTMLSerializer[T]):HTMLSerializer[T]=  serializer
    }



    implicit object UserSerializer extends HTMLSerializer[User]{
        override def serialize(value: User): String = s"${value.name}, ${value.age}"
    }

    implicit object IntSerializer extends HTMLSerializer[Int]{
        override def serialize(value: Int): String = s"number $value"
    }

    implicit class EqualEnrichment[T](value:T){
        def ===(other:T)(implicit equalize: Equal[T]):Boolean = equalize.compare(value, other)
        def !==(other:T)(implicit equalize: Equal[T]):Boolean = ! (value === other)
    }


//    println(HTMLSerializer.serialize(42))
//    println(HTMLSerializer[User].serialize(User("john",22)))
//    println(Equal[User].compare(User("john",32), User("john",33)))
    val john = User("John",25)
    println(john !== john)




}
