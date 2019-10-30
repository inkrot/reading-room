package ru.readingroom.system.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.readingroom.system.models.Author;
import ru.readingroom.system.models.DataBase;
import ru.readingroom.system.models.Reader;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminAddController {

    @FXML
    private TextField librarianNameInput, librarianLoginInput, librarianPasswordInput;

    @FXML
    private TextField readerNameInput;

    @FXML
    private TextField bookNameInput, bookIsbnInput, bookYearInput, bookQuantityInput, bookAuthorNameSearchInput;

    @FXML
    private Button bookAuthorNameClearButton;

    @FXML
    private ListView<Author> bookAuthorsList;

    @FXML
    private Label bookAuthorsListPlaceholder;

    @FXML
    private Button librarianAdd, readerAdd, bookAdd, authorAdd;

    private AdminController adminController;

    public static final int LIBRARIAN_TYPE = 1;
    public static final int READER_TYPE = 2;
    public static final int BOOK_TYPE = 3;
    public static final int AUTHOR_TYPE = 4;

    public Author selectedAuthor = null;

    public void initAddDialog(AdminController adminController, int addEntityType) {
        this.adminController = adminController;
        switch (addEntityType) {
            case LIBRARIAN_TYPE:
                librarianAdd.setOnAction((v)-> addLibrarian());
                break;
            case READER_TYPE:
                readerAdd.setOnAction((v)-> addReader());
                break;
            case BOOK_TYPE:
                bookAuthorNameSearchInput.setOnKeyReleased((v) -> searchAuthor());
                bookAuthorsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedAuthor = newValue);
                bookAuthorNameClearButton.setOnAction((e) -> {
                    bookAuthorNameSearchInput.setText("");
                    searchAuthor();
                });
                //bookAdd.setOnAction((v)-> addLibrarian());
                break;
            case AUTHOR_TYPE:
                //authorAdd.setOnAction((v)-> addLibrarian());
                break;
        }
    }

    private void addLibrarian() {
        String name = librarianNameInput.getText(), login = librarianLoginInput.getText(), password = librarianPasswordInput.getText();
        if (!name.isEmpty() && !login.isEmpty() && !password.isEmpty()) {
            DataBase.insertLibrarian(name, login, password);
            adminController.addDialogStage.getScene().getWindow().hide();
            adminController.updateLibrariansTable();
        }
    }

    private void addReader() {
        String name = readerNameInput.getText();
        if (!name.isEmpty()) {
            DataBase.insertReader(name);
            adminController.addDialogStage.getScene().getWindow().hide();
            adminController.updateReadersTable();
        }
    }

    private void searchAuthor() {
        selectedAuthor = null;
        bookAuthorsList.getItems().clear();
        String name = bookAuthorNameSearchInput.getText();
        if (!name.isEmpty()) {
            ResultSet rs = DataBase.searchAuthors(name);
            try {
                if (! rs.next())
                    bookAuthorsListPlaceholder.setText("Ничего не найдено");
                else {
                    do bookAuthorsList.getItems().add(new Author(rs.getInt("id"), rs.getString("name")));
                    while (rs.next());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else bookAuthorsListPlaceholder.setText("Список авторов");
    }

    private void addBook() {
        String name = bookNameInput.getText();
        String year = bookYearInput.getText();
        String quantityStr = bookQuantityInput.getText();
        if (!name.isEmpty()) {
            DataBase.insertReader(name);
            adminController.addDialogStage.getScene().getWindow().hide();
            adminController.updateReadersTable();
        }
    }
}
