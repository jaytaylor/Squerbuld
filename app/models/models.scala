package models

import org.squeryl._
import org.squeryl.dsl._
import org.squeryl.PrimitiveTypeMode._
import java.sql.Timestamp

case class Post(
    val id: Long = 0L,
    val created: Timestamp,
    val author: String,
    val subject: String,
    val body: String,
    val refPostId: Option[Long] = None
) extends KeyedEntity[Long] {
    lazy val replies: OneToMany[Post] = BulletinBoardSchema.postToReplies.left(this)

    def getReplies: List[Post] =
        inTransaction {
            replies. toList
        }
}

object Post {
    def apply(id: Long): Option[Post] =
        inTransaction {
            from(BulletinBoardSchema.posts)(p => where(p.id === id) select(p)).headOption
        }

    def all: List[Post] =
        inTransaction {
            from(BulletinBoardSchema.posts)( p => select(p) orderBy(p.created desc)).toList
        }
}
