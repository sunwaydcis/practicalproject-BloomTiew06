package ch.makery.address.view

import ch.makery.address.model.Person
import ch.makery.address.MainApp
import ch.makery.address.util.DateUtil.asString
import javafx.fxml.FXML
import javafx.scene.control.{Label, TableColumn, TableView}
import scalafx.Includes.*
import javafx.event.ActionEvent
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

@FXML
class PersonOverviewController():

  @FXML private var personTable: TableView[Person] = null
  @FXML private var firstNameColumn: TableColumn[Person, String] = null
  @FXML private var lastNameColumn: TableColumn[Person, String] = null
  @FXML private var firstNameLabel: Label = null
  @FXML private var lastNameLabel: Label = null
  @FXML private var streetLabel: Label = null
  @FXML private var postalCodeLabel: Label = null
  @FXML private var cityLabel: Label = null
  @FXML private var birthdayLabel: Label = null

  def initialize(): Unit =
    personTable.items = MainApp.personData
    firstNameColumn.cellValueFactory = {_.value.firstName}
    lastNameColumn.cellValueFactory = {_.value.lastName}

    showPersonDetails(None)

    personTable.selectionModel().selectedItem.onChange(
      (_, _, newValue) => showPersonDetails(Some(newValue))
    )

  private def showPersonDetails(person: Option[Person]): Unit =
    person match
      case Some(person) =>
        firstNameLabel.text <== person.firstName
        lastNameLabel.text <== person.lastName
        streetLabel.text <== person.street
        cityLabel.text <== person.city
        postalCodeLabel.text = person.postalCode.value.toString
        birthdayLabel.text = person.date.value.asString

      case None =>
        firstNameLabel.text = ""
        lastNameLabel.text = ""
        streetLabel.text = ""
        cityLabel.text = ""
        postalCodeLabel.text = ""
        birthdayLabel.text = ""

  def handleDeletePerson(action: ActionEvent): Unit =
    val selectedIndex =
      personTable.selectionModel().selectedIndex.value
    if (selectedIndex >= 0) then
      personTable.items().remove(selectedIndex)
    else
      val alert = new Alert(AlertType.Warning):
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Person Selected"
        contentText = "Please select a person in the table."
      alert.showAndWait()

  def handleNewPerson(action: ActionEvent) =
    val person = new Person("", "")
    val okClicked = MainApp.showPersonEditDialog(person)
    if (okClicked) then
      MainApp.personData += person

  def handleEditPerson(action: ActionEvent) =
    val selectedPerson =
      personTable.selectionModel().selectedItem.value
    if (selectedPerson != null) then
      val okClicked = MainApp.showPersonEditDialog(selectedPerson)
        if (okClicked) then
          showPersonDetails(Some(selectedPerson))
        else
          val alert = new Alert(Alert.AlertType.Warning):
            initOwner(MainApp.stage)
            title = "No Selection"
            headerText = "No Person Selected"
            contentText = "Please select a person in the table."
          alert.showAndWait()
  end handleEditPerson

