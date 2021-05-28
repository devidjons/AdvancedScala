package exercises
import scala.math.Numeric
object Monads extends App{
    class LazyM[A](value: => A) {
        private lazy val internalValue = value
        def flatMap[B](f: (=>A) => LazyM[B]):LazyM[B]={
            f(internalValue)
        }
        def use:A = internalValue
    }
    object LazyM {
        def unit[A](input: => A): LazyM[A] ={
            new LazyM(input)
        }
    }

    val r1 = LazyM.unit({println("create instance"); 42})
    val f = (x:Int)=>{println("function call"); LazyM.unit(x+1)}
    println("init finished")
    val r2 = r1.flatMap(x => f(x))
    println(r2.use)
    val r3 = List[Double](1,2,3)
    r3.sum



}
