package controllers

import dao.UserDao
import play.api.libs.json._
import play.api.mvc._
import models.User

import javax.inject.Inject
import scala.concurrent.Future
import scala.concurrent.ExecutionContext

class UsersController @Inject()(userDao: UserDao, cc: ControllerComponents, ec: ExecutionContext)
  extends AbstractController(cc) {

  def hello(): Action[AnyContent] = Action(Ok("Hello World"))

  def get() = Action.async {
    implicit val userWrites: OWrites[User] = Json.writes[User]
    val futureUsers = userDao.all();
    futureUsers.map(users => Ok(Json.toJson(users)))(ec)
  }

  def find(id: Long) = Action.async {
    implicit val userWrites: OWrites[User] = Json.writes[User]
    val futureUsers = userDao.findById(id)
    futureUsers.map(users => Ok(Json.toJson(users)))(ec)
  }
}
