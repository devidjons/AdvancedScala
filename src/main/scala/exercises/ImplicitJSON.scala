package exercises

import java.sql.Date

object ImplicitJSON extends App{
    trait JSONConverter {
        def stringify:String
    }

    case class StringJSON(value:String) extends JSONConverter{
        override def stringify: String = value
    }
    case class ListJSON(values:List[JSONConverter]) extends JSONConverter{
        override def stringify: String = values.map(_.stringify).mkString("{", ",", "}")
    }

    case class ObjectJSON(items:Map[String, JSONConverter]) extends JSONConverter{
        override def stringify: String = items.map({
            case (key, value)=> s"$key:${value.stringify}"
        }).mkString("{", ",", "}")
    }



    trait JSONImp[T] {
        def convert(value:T):JSONConverter
    }
    implicit object UserJSON extends JSONImp[User]{
        override def convert(user:User): JSONConverter = ObjectJSON(Map("name"->StringJSON(user.name), "age"->StringJSON(user.age.toString)))
    }
    implicit object PostJSON extends JSONImp[Post]{
        override def convert(post:Post): JSONConverter = ObjectJSON(Map("Date"-> StringJSON(post.date.toString), "Post"->StringJSON(post.text)))
    }
    implicit object FeedJSON extends JSONImp[Feed]{
        override def convert(value: Feed): JSONConverter = ObjectJSON(Map("user"->value.user.toJSON, "posts"->ListJSON(value.posts.map(_.toJSON))))
    }

    implicit class JSONEnrich[T](value:T) {
        def toJSON(implicit conv:JSONImp[T]):JSONConverter = conv.convert(value)
    }



    case class User(name:String, age:Int)
    case class Post(text:String, date:Date)
    case class Feed(user:User, posts:List[Post])





}
