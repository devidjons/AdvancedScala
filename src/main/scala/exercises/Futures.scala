package exercises

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future, Promise}
import scala.util.Success

object Futures extends App{
    val promise = Promise[Int]()
    val future = promise.future
    future.onComplete{
        case Success(r)=> {
            println("value obtained")
            Thread.sleep(500)
            println(s" [consumer] I `ve receivend $r")
        }
    }
    println("before value sent")
    promise.success(42)
    println("after value sent")
    Thread.sleep(1000)
    def inSeq[A,B](fa:Future[A], fb: => Future[B]):Future[B]={
        fa.flatMap(x=> fb)
    }
    def f1:Future[Int] = Future {
        println(s"start future 1")
        Thread.sleep(1000)
        println("end future 1")
        1
    }
    def f2:Future[Int] = Future{
        println(s"start future 2")
        Thread.sleep(1200)
        println("end future 2")
        2
    }
    def first[A](fa:Future[A], fb:Future[A]):Future[A]={
        val result = Promise[A]()
        fa.onComplete(x=> {
            if (result.future.value.isEmpty){
                result.complete(x)
            }

        })
        fb.onComplete(x=> {
            if (result.future.value.isEmpty){
                result.complete(x)
            }

        })
        result.future
    }
    def second[A](fa:Future[A], fb:Future[A]):Future[A]={
        val result = Promise[A]()
        fa.onComplete(x=> {
            if (fb.value.isDefined){
                result.complete(x)
            }

        })
        fb.onComplete(x=> {
            if (fa.value.isDefined){
                result.complete(x)
            }

        })
        result.future
    }
    def retryUntil[T](action:()=>Future[T], condition:T=>Boolean):Future[T]={
        val result = Promise[T]()
        val testFuture = action()
        testFuture.onComplete({
            case Success(r) if condition(r) => result.success(r)
            case _ => {
                retryUntil(action, condition).onComplete(result.complete)
            }
        })
        result.future
    }
    var state:Int = 1
    val act = ()=>{
        state+=1
        Future{
            println(s"action $state start")
            Thread.sleep(100)
            println(s"action $state end")
            state
        }
    }
    val r1 = retryUntil(act, {x:Int=> x > 10})
    Await.result(r1, 10.seconds)

    println(r1.value)
    Thread.sleep(5000)



}
