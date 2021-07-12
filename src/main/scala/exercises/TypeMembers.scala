package exercises

object TypeMembers extends App{
    trait MyList {
        type T
        def head:T
        def tail:MyList
    }

    class MSS(hd:String, tl:MSS) extends MyList{
        type T = String
        def head: T = hd
        def tail: MyList = tl
    }

    trait MyList1 extends MyList {
        
    }
    class MSS1(hd:Int, tl:MSS) extends MyList1{
        type T = Int
        def head: T = hd
        def tail: MyList = tl
    }
}
