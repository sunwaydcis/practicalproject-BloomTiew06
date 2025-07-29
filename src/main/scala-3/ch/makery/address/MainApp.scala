package ch.makery.address

import ch.makery.address.model.Person
import ch.makery.address.view.PersonEditDialogController
import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.*
import scalafx.scene as sfxs
import javafx.scene as jfxs
import scalafx.collections.ObservableBuffer
import scalafx.stage.{Modality, Stage}

object MainApp extends JFXApp3:

  var roots: Option[sfxs.layout.BorderPane] = None

  val personData = new ObservableBuffer[Person]()

  personData += new Person("Hans", "Muster")
  personData += new Person("Ruth", "Mueller")
  personData += new Person("Heinz", "Kurz")
  personData += new Person("Cornelia", "Meier")
  personData += new Person("Werner", "Meyer")
  personData += new Person("Lydia", "Kunz")
  personData += new Person("Anna", "Best")
  personData += new Person("Stefan", "Meier")
  personData += new Person("Martin", "Mueller")

  override def start(): Unit =
    val rootResource = getClass.getResource("/ch.makery.address.view/RootLayout.fxml")
    val loader = new FXMLLoader(rootResource)
    loader.load()

    roots = Option(loader.getRoot[jfxs.layout.BorderPane])

    stage = new PrimaryStage():
      title = "AddressApp"
      scene = new Scene():
        root = roots.get

    showPersonOverview()

  def showPersonOverview(): Unit =
    val resource = getClass.getResource("/ch.makery.address.view/PersonOverview.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.get.center = roots
  end showPersonOverview

  def showPersonEditDialog(person: Person): Boolean =
    val resource = getClass.getResource("view/PersonEditDialog.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val roots2 = loader.getRoot[jfxs.Parent]
    val control = loader.getController[PersonEditDialogController]
    val dialog = new Stage():
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      scene = new Scene:
        root = roots2
    control.dialogStage = dialog
    control.person = person
    dialog.showAndWait()
    control.okClicked
  end showPersonEditDialog
