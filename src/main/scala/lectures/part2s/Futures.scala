package lectures.part2s
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object Futures extends App{
    def calculateMeaningOfLife:Int = {
        Thread.sleep(2000)
        42
    }

    def aFuture = Future {
        calculateMeaningOfLife
    }
    val r1 = aFuture.onComplete({
        case Success(value) => value
        case Failure(exception)=>0.0
    })
    Thread.sleep(3000)
    println(r1)
}
