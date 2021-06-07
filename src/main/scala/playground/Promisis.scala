package playground

import scala.concurrent.Promise
import scala.util.Success
import scala.concurrent.ExecutionContext.Implicits.global

object Promisis extends App{
    val promise = Promise[Int]()
    val future = promise.future
    future.onComplete{
        case Success(r)=> println(s" [consumer] I `ve receivend $r")
    }

    val producer = new Thread (()=> {
        println("producer crunching number")
        Thread.sleep(500)
        promise.success(42)
        println("producer done")

    })
    producer.start()
    Thread.sleep(1000)
}
