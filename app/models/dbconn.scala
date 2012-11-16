package models

import org.squeryl.{Session, SessionFactory}
import org.squeryl.adapters.PostgreSqlAdapter
import play.Logger
import java.sql.DriverManager

class NewAgePostgreSqlAdapter extends PostgreSqlAdapter {
    override val usePostgresSequenceNamingScheme: Boolean = true
}

object DbPool {
    Class forName "org.postgresql.Driver"

    SessionFactory.concreteFactory = Some(() =>
        Session.create(
            DriverManager.getConnection("jdbc:postgresql://localhost:5432/squeryl"),
            new NewAgePostgreSqlAdapter
        )
    )

    Logger.info("DB Pool initialized")
}
