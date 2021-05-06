package exercises

import scala.annotation.tailrec

abstract class MySet[A] extends (A=>Boolean){
    override def apply(elem:A):Boolean={
        contains(elem)
    }
    def contains(elem:A):Boolean
    def +(elem:A):MySet[A]
    def ++(anotherSet:MySet[A]):MySet[A]
    def map[B](f: A => B):MySet[B]
    def flatMap[B](f:A=>MySet[B]):MySet[B]
    def filter[B](p:A=>Boolean):MySet[A]
    def foreach(f: A=>Unit):Unit
    def -(elem:A):MySet[A] = filter(_!=elem)
    def &(anotherSet:MySet[A]): MySet[A] = filter(anotherSet)
    def --(anotherSet:MySet[A]): MySet[A] = filter(x=> ! anotherSet(x))
    def unary_! : MySet[A]
}
class EmptySet[A] extends MySet[A]{
    def contains(elem: A): Boolean = false

    override def +(elem: A): MySet[A] = new NonEmptySet(elem,new EmptySet)

    override def ++(anotherSet: MySet[A]): MySet[A] = anotherSet

    override def map[B](f: A => B): MySet[B] = new EmptySet[B]

    override def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]

    override def filter[B](p: A => Boolean): MySet[A] = this

    override def foreach(f: A => Unit): Unit = ()

    override def unary_! : MySet[A] = new PropertyBasedSet[A](x=> true)
}
class NonEmptySet[A](head:A, tail:MySet[A]) extends MySet[A] {
    override def contains(elem: A): Boolean = (head==elem) || tail.contains(elem)

    override def +(elem: A): MySet[A] = if (this.contains(elem)) this else new NonEmptySet[A](elem, this)

    override def ++(anotherSet: MySet[A]): MySet[A] = tail ++ anotherSet + head

    override def map[B](f: A => B): MySet[B] = tail.map(f)+ f(head)

    override def flatMap[B](f: A => MySet[B]): MySet[B] = tail.flatMap(f) ++ f(head)

    override def filter[B](p: A => Boolean): MySet[A] = {
        val tailFiltered = tail.filter(p)
        if(p(head)) tailFiltered +  head
        else tailFiltered
    }

    override def foreach(f: A => Unit): Unit = {
        f(head)
        tail.foreach(f)
    }

    override def unary_! : MySet[A] = new PropertyBasedSet[A](x=> ! this.contains(x))
}

class PropertyBasedSet[A](property: A => Boolean) extends MySet[A] {
    override def contains(elem: A): Boolean = property(elem)

    override def +(elem: A): MySet[A] = new PropertyBasedSet((e:A) => property(e)||e==elem)

    override def ++(anotherSet: MySet[A]): MySet[A] = new PropertyBasedSet((e:A) => property(e)||anotherSet(e))

    override def map[B](f: A => B): MySet[B] = politelyFail

    override def flatMap[B](f: A => MySet[B]): MySet[B] = politelyFail

    override def filter[B](p: A => Boolean): MySet[A] = new PropertyBasedSet[A](x => property(x) && p(x))

    override def unary_! : MySet[A] = new PropertyBasedSet[A](x=> !property(x))

    override def foreach(f: A => Unit): Unit = politelyFail

    def politelyFail = throw new IllegalArgumentException("Really deep rabbit hole")
}


object MySet {
    def apply[A](values: A*):MySet[A]={
        @tailrec
        def buildSet(valSeq:Seq[A], acc:MySet[A]):MySet[A] = {
            if (valSeq.isEmpty) acc
            else buildSet(valSeq.tail, acc+valSeq.head)
        }
        val input = values.toSeq
        buildSet(input, new EmptySet[A])
    }
}

object MySetPlayGround extends App{
    val s = MySet(1,2,3)
    s + 5 + 3 ++ MySet(-1,1) flatMap (x=> MySet(x+1, x-1)) filter (_%2==0) foreach println
    println("asdf")
}