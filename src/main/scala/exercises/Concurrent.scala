package exercises

object Concurrent extends App{
    val lst1 = List.empty
    val lstThr = List.newBuilder[Thread]
    for (i <- 1 to 50) lstThr+=new Thread(()=>println("threan n " + i))
    lstThr.result().foldLeft(new Thread(()=>()))((acc, el)=>new Thread(()=>{
        el.start()
        el.join()
        acc.start()
        acc.join()
    })).start()


    def inceptionThreads(maxT:Int = 50, i:Int = 1):Thread = new Thread(()=>{
        if (i<=maxT){
            val nextThr = inceptionThreads(maxT, i+1)
            nextThr.start()
            nextThr.join()
            println("thread number "+i)
        }

    })
//    inceptionThreads().start()
}
