package dao

import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Inject
import models.User
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

class UserDao @Inject()(
                         protected val dbConfigProvider: DatabaseConfigProvider
                       )(
                         implicit executionContext: ExecutionContext
                       ) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val Users = TableQuery[UsersTable]

  def all(): Future[Seq[User]] = db.run(Users.result)

  def findById(userId: Long): Future[Seq[User]] = db.run(Users.filter(_.id === userId).result)

  private class UsersTable(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Long]("id", O.PrimaryKey)

    def firstName = column[String]("first_name")

    def lastName = column[String]("last_name")

    def email = column[String]("email")

    def * = (id, firstName, lastName, email) <> (User.tupled, User.unapply)
  }
}
