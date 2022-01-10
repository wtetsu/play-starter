package controllers

import play.api.libs.json._
import play.api.mvc._
import models.User

import javax.inject.Inject

class UsersController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) {

  def hello(): Action[AnyContent] = Action(Ok("Hello World"))

  def get(): Action[AnyContent] = Action {
    implicit val userWrites: OWrites[User] = Json.writes[User]
    val users = List(User("Taro", 25), User("Jiro", 80), User("Saburo", 95))
    val json = Json.toJson(users)
    Ok(json)
  }
}
