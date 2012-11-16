package controllers

import play.api._
import play.api.mvc._

import models.{Post, BulletinBoardSchema}
import java.sql.Timestamp
import org.squeryl.PrimitiveTypeMode.{inTransaction, transaction}

object Application extends Controller {
  
    def index = Action {
        Ok(views.html.index(Post.all))
    }

    def newPostForm(refPostId: Option[Long] = None) = Action { request =>
        val refPost = refPostId match {
            case Some(id) => Post(id)
            case None => None
        }
        Ok(views.html.newPost(refPost))
    }

    def processNewPost = Action { request =>
        val formData = request.body.asFormUrlEncoded.head

        def getParam(name: String): String = {
            val result = formData.get(name).flatMap(seq => seq.headOption)
            result match {
                case Some(value) => value
                case None => throw new Exception("Missing required parameter: " + name)
            }
        }

        val refPostId = formData.get("refPostId").flatMap(seq => seq.headOption) match {
            case Some("") => None
            case None => None
            case Some(value) => Some(value.toLong)
        }

        val message = Post(
            created=new Timestamp(System.currentTimeMillis),
            author=formData.get("author").flatMap(seq => seq.headOption).getOrElse("Anonymous"),
            subject=getParam("subject"),
            body=getParam("body"),
            refPostId=refPostId
        )

        transaction {
            BulletinBoardSchema.posts.insert(message)
        }

        Redirect("/")
    }

    def displayPost(postId: Long) = Action { request =>
        Ok(views.html.displayPost(Post(postId).head))
    }
}
