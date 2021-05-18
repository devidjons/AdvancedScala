package exercises

abstract class MyStream[+A] {
  def isEmpty:Boolean
  def head:A
  def tail:MyStream[A]
  def #::[B>:A](element: B):MyStream[B] // prepend operator
  def ++[B>:A](anotherStream: =>MyStream[B]):MyStream[B] // concatenate

  def foreach(f:A=>Unit):Unit
  def map[B](f: A => B):MyStream[B]
  def flatMap[B](f:A=>MyStream[B]):MyStream[B]
  def filter(p:A=> Boolean):MyStream[A]

  def take(n:Int):MyStream[A]
  def takeAsList(n:Int):List[A]

}
object MyStream {
  def from[A](start:A)(generator: A=>A):MyStream[A] = new NonEmptyStream[A]( start, from(generator(start))(generator))
}

object EmptyStream extends MyStream[Nothing] {

  override def isEmpty: Boolean = true

  override def head: Nothing = politelyFail

  override def tail: MyStream[Nothing] = politelyFail

  override def #::[B >: Nothing](element: B): MyStream[B] = new NonEmptyStream[B](element, EmptyStream)

  override def ++[B >: Nothing](anotherStream: =>MyStream[B]): MyStream[B] = anotherStream

  override def foreach(f: Nothing => Unit): Unit = ()

  override def map[B](f: Nothing => B): MyStream[B] = EmptyStream

  override def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = EmptyStream

  override def filter(p: Nothing => Boolean): MyStream[Nothing] = EmptyStream

  override def take(n: Int): MyStream[Nothing] = EmptyStream

  override def takeAsList(n: Int): List[Nothing] = Nil

  def politelyFail = throw new IllegalArgumentException(" Stream is empty")
}

class NonEmptyStream[+A](start:A, rest: => MyStream[A]) extends MyStream[A] {
  override def isEmpty: Boolean = false

  override val head: A = start

  override def tail: MyStream[A] = rest

  override def #::[B >: A](element: B): MyStream[B] = new NonEmptyStream[B](element, this)

  override def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] = new NonEmptyStream[B](head, tail ++ anotherStream )

  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }

  override def map[B](f: A => B): MyStream[B] = new NonEmptyStream[B](f(head), tail.map(f))

  override def flatMap[B](f: A => MyStream[B]): MyStream[B] = f(head)++tail.flatMap(f)

  override def filter(p: A => Boolean): MyStream[A] =
    if (p(head)) new NonEmptyStream(head, tail.filter(p)) else tail.filter(p)

  override def take(n: Int): MyStream[A] = n match {
    case 0 => EmptyStream
    case _ => new NonEmptyStream(head, tail.take(n-1))
  }

  override def takeAsList(n: Int): List[A] = n match {
    case 0 => Nil
    case _ => head :: tail.takeAsList(n-1)
  }
}

object test extends App {
  println(MyStream.from( 1)(_+2).flatMap(x=> new NonEmptyStream(x,new NonEmptyStream(x, EmptyStream))).take(2).foreach(println))
  val starts2 = MyStream.from(2)(_+1)
  val filterSet = new EmptySet[Int]()
  def primes(numbers:MyStream[Int]):MyStream[Int]={
    new NonEmptyStream(numbers.head, primes(numbers.tail.filter(_%numbers.head!=0)))
  }
  def fib(a1:Int,a2:Int):MyStream[Int]=new NonEmptyStream(a1, fib(a2, a1+a2))
  primes(starts2).take(100).foreach(println)
//  fib(1,1).take(100).foreach(println)
}
