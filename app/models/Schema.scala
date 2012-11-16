package models

import org.squeryl.Schema
import org.squeryl.PrimitiveTypeMode._


object BulletinBoardSchema extends Schema {

    val posts = table[Post]("Post")

    val postToReplies = oneToManyRelation(posts, posts).via((pA, pB) => pA.id === pB.refPostId)

}

