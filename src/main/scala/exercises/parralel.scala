package exercises

object parralel extends App{
    var r1 = 2
    var r2 = 3

    val user1 = new Thread(()=> r1.synchronized({
        Thread.sleep(2000)
        r2.synchronized({
            Thread.sleep(2000)
            r2 += 1
        })
        r1 += 1
    }))
    val user2 = new Thread({()=> r2.synchronized({
        println(s"user2 into r2")
        Thread.sleep(2000)
        r1.synchronized({
            Thread.sleep(2000)
            r1 += 1
        })
        r2 += 1
    })})
    user1.start()
    user1.join()
    user2.start()

}
