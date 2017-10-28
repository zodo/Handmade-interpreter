package gui

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

object Main {
  def main(args: Array[String]) {
    System.out.println("testing")
    Application.launch(classOf[Main], args: _*)
  }


}

class Main extends Application {
  def start(primaryStage: Stage) {
    val root: Parent = FXMLLoader.load(getClass.getResource("sample.fxml"))
    primaryStage.setTitle("Интерпретатор")
    val scene: Scene = new Scene(root, 600, 700)
    primaryStage.setScene(scene)
    primaryStage.show()
    scene.getStylesheets.add(getClass.getResource("style.css").toExternalForm)
  }
}