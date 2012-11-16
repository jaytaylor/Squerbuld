
import play.{Application, GlobalSettings, Logger}

/**
 * @author Jay Taylor [@jtaylor]
 * @date 2012-11-15
 */

class Global extends GlobalSettings {

    /**
     * Touch lazy objects to wake them up.
     */
    override def onStart(app: Application) {
        import models.DbPool

        DbPool.getClass

        try {
            import org.squeryl.PrimitiveTypeMode.transaction
            import models.BulletinBoardSchema

            transaction {
                BulletinBoardSchema.create
            }

        } catch {
            case e: Exception => Logger.info("Global caught exception: " + e.getMessage)
        }
    }

}
