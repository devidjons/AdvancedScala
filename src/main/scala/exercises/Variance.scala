package exercises

object Variance extends App{
    class ParkingI[T](things:List[T]) {
        def park(vehicle:T):Unit = ???
        def impound(vehicles:List[T]):Unit = ???
        def checkVehicles(conditions:String):List[T] = ???
    }
    class ParkingCov[+T](things:List[T]) {
        def park[S>:T](vehicle:S):Unit = ???
        def impound[S>:T](vehicles:List[S]):Unit = ???
        def checkVehicles(conditions:String):List[T] = ???
    }

    class ParkingCon[-T](things:List[T]) {
        def park[T](vehicle:T):Unit = ???
        def impound[T](vehicles:List[T]):Unit = ???
        def checkVehicles[S<:T](conditions:String):List[S] = ???
    }



}

object Variance2 extends App {
    class ParkingI[T](things:Array[T]) {
        def park(vehicle:T):Unit = ???
        def impound(vehicles:Array[T]):Unit = ???
        def checkVehicles(conditions:String):Array[T] = ???
    }
    class ParkingCov[+T](things:Array[T]) {
        def park[S>:T](vehicle:S):Unit = ???
        def impound[S>:T](vehicles:Array[S]):Unit = ???
        def checkVehicles[S>:T](conditions:String):Array[S] = ???
    }

    class ParkingCon[-T](things:Array[T]) {
        def park[T](vehicle:T):Unit = ???
        def impound[T](vehicles:Array[T]):Unit = ???
        def checkVehicles[S<:T](conditions:String):Array[S] = ???
    }
}

object Variance3 extends App{
    class ParkingI[T](things:List[T]) {
        def park(vehicle:T):Unit = ???
        def impound(vehicles:List[T]):Unit = ???
        def checkVehicles(conditions:String):List[T] = ???
        def flatMap[B](f:List[T]=>ParkingI[B]):ParkingI[B]= ???
    }
    class ParkingCov[+T](things:List[T]) {
        def park[S>:T](vehicle:S):Unit = ???
        def impound[S>:T](vehicles:List[S]):Unit = ???
        def checkVehicles(conditions:String):List[T] = ???
        def flatMap[B](f:List[T]=>ParkingCov[B]):ParkingCov[B]= ???
    }

    class ParkingCon[-T](things:List[T]) {
        def park[T](vehicle:T):Unit = ???
        def impound[T](vehicles:List[T]):Unit = ???
        def checkVehicles[S<:T](conditions:String):List[S] = ???
        def flatMap[S<:T,B](f:List[S]=>ParkingCon[B]):ParkingCon[B]= ???
    }



}



