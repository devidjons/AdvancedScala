package exercises

object InnerTypes extends App{
    trait Item {
        type keyType
    }
    trait IntItem extends Item {
        override type keyType = Int
    }
    trait StringItem extends Item {
        override type keyType = String
    }

    def get[ItemType <: Item](key:ItemType#keyType):ItemType= ???


    get[IntItem](42)
    get[StringItem]("asdf")
//    get[IntItem]("asdf")

}
