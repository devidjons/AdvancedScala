package lectures.part2s

object CurryingPAF extends App{

    def PAF(n:Int):Int =
        n match {
            case 1=>2
            case 3=>4
        }
    def simpleAddMethod(x:Int, y:Int):Int = x+y

    def byName(n: => Int):Int =  n+1
    def byFunction(f:()=>Int) = f()+1

    def method:Int = 42
    def parenMethod():Int = 42

    val r1 = simpleAddMethod(_,7)

    val formatNumber = (n:Double)=> (f:String) => f.format(n)
    println(List(1.0,2.0,3.0).map(formatNumber(_)("%4.2f")))

    byName(42)
//    byFunction(42)
    byName(method)
//    byFunction(method)
    byName(parenMethod)
//    byFunction(parenMethod)
//    byName(()=>42)
    byFunction(()=>42)
    byName(PAF(1))
    byFunction(()=>PAF(1))
}
