package ru.readingroom.system.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import ru.readingroom.system.App;
import ru.readingroom.system.Main;
import ru.readingroom.system.models.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AdminController {

    @FXML
    private TabPane adminTabPane;

    // ================================================================

    @FXML
    private Tab librariansTab;
    @FXML
    private TableView<DataBaseEntity> librariansTable;
    @FXML
    private TableColumn<Object, Object> librariansIdColumn;
    @FXML
    private TableColumn<Object, Object> librariansNameColumn;
    @FXML
    private TableColumn<Object, Object> librariansLoginColumn;
    @FXML
    private TableColumn<Object, Object> librariansPasswordColumn;

    // ================================================================

    @FXML
    private Tab readersTab;
    @FXML
    private TableView<DataBaseEntity> readersTable;
    @FXML
    private TableColumn<Object, Object> readersIdColumn;
    @FXML
    private TableColumn<Object, Object> readersNameColumn;

    // ================================================================

    @FXML
    private Tab booksTab;
    @FXML
    private TableView<DataBaseEntity> booksTable;
    @FXML
    private TableColumn<?, ?> booksIdColumn;
    @FXML
    private TableColumn<?, ?> booksNameColumn;
    @FXML
    private TableColumn<?, ?> booksAuthorColumn;
    @FXML
    private TableColumn<?, ?> booksYearColumn;
    @FXML
    private TableColumn<?, ?> booksIsbnColumn;
    @FXML
    private TableColumn<?, ?> booksQuantityColumn;

    // ================================================================

    @FXML
    private Tab authorsTab;
    @FXML
    private TableColumn<Object, Object> authorsIdColumn;
    @FXML
    private TableColumn<Object, Object> authorsNameColumn;
    @FXML
    private TableView<DataBaseEntity> authorsTable;

    // ================================================================

    @FXML
    private Button addLibrarianButton, addReaderButton, addBookButton, addAuthorButton;

    public Stage addDialogStage;

    @FXML
    void initialize() {
        adminTabPane.getSelectionModel().selectedItemProperty().addListener((ov, t, t1) -> {
            if (t1.getId().equals(librariansTab.getId()))
                updateLibrariansTable();
            else if (t1.getId().equals(readersTab.getId()))
                updateReadersTable();
            else if (t1.getId().equals(booksTab.getId()))
                updateBooksTable();
            else if (t1.getId().equals(authorsTab.getId()))
                updateAuthorsTable();
        });

        librariansTable.setRowFactory((tableViewCallback));
        readersTable.setRowFactory((tableViewCallback));
        booksTable.setRowFactory((tableViewCallback));
        authorsTable.setRowFactory((tableViewCallback));

        librariansIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        librariansNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        librariansLoginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        librariansPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        readersIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        readersNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        booksIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        booksNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        booksAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        booksYearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        booksIsbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        booksQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        authorsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        addLibrarianButton.setOnAction((event -> showAddLibrarianDialog()));
        addReaderButton.setOnAction((event -> showAddReaderDialog()));
        addBookButton.setOnAction((event -> showAddBookDialog()));
        addAuthorButton.setOnAction((event -> showAddAuthorDialog()));

        updateLibrariansTable();
    }

    Callback<TableView<DataBaseEntity>, TableRow<DataBaseEntity>> tableViewCallback = tableView -> {
        final TableRow<DataBaseEntity> row = new TableRow<>();
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem removeItem = new MenuItem("Удалить");
        removeItem.setOnAction(event -> deleteEntity(row.getTableView(), row.getItem().getId()));
        rowMenu.getItems().addAll(removeItem);
        row.contextMenuProperty().bind(
                Bindings.when(Bindings.isNotNull(row.itemProperty()))
                        .then(rowMenu)
                        .otherwise((ContextMenu)null));
        return row;
    };

    public void showAddDialog(int addEntityType, String fxmlPath, int w, int h) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        try {
            Parent parent = fxmlLoader.load();
            addDialogStage = new Stage();
            ((AdminAddController) fxmlLoader.getController()).initAddDialog(this, addEntityType);
            addDialogStage.initModality(Modality.APPLICATION_MODAL);
            addDialogStage.setScene(new Scene(parent, w, h));
            addDialogStage.setResizable(false);
            addDialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddLibrarianDialog() {
        showAddDialog(AdminAddController.LIBRARIAN_TYPE, "../views/add_librarian_dialog.fxml", 354, 282);
    }

    public void showAddReaderDialog() {
        showAddDialog(AdminAddController.READER_TYPE, "../views/add_reader_dialog.fxml", 354, 188);
    }

    public void showAddBookDialog() {
        showAddDialog(AdminAddController.BOOK_TYPE, "../views/add_book_dialog.fxml", 354, 446);
    }

    public void showAddAuthorDialog() {
        //showAddDialog(AdminAddController.AUTHOR_TYPE, "../views/add_author_dialog.fxml", 354, 188);
    }

    public void deleteEntity(TableView table, int id) {
        System.out.println(table + " | " + id);
    }


    public void updateLibrariansTable() {
        librariansTable.getItems().clear();

        ResultSet rs = DataBase.getLibrariansList();
        try {
            while(rs.next())
                librariansTable.getItems().add(
                        new Librarian(rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("login"),
                                rs.getString("password"))
                );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateReadersTable() {
        readersTable.getItems().clear();

        ResultSet rs = DataBase.getReadersList();
        try {
            while(rs.next())
                readersTable.getItems().add(new Reader(rs.getInt("id"), rs.getString("name")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBooksTable() {
        booksTable.getItems().clear();

        ResultSet rs = DataBase.getBooksList();
        try {
            while(rs.next())
                booksTable.getItems().add(
                        new Book(rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("author"),
                                rs.getString("year"),
                                rs.getString("isbn"),
                                rs.getInt("quantity"))
                );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAuthorsTable() {
        authorsTable.getItems().clear();

        ResultSet rs = DataBase.getAuthorsList();
        try {
            while(rs.next())
                authorsTable.getItems().add(new Author(rs.getInt("id"), rs.getString("name")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adminExit() {
        if (confirm("Подтвердите действие", "Выйти из учетной записи администратора?")) {
            App.stage.close();
            try {
                new Main().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean confirm(String titleString, String messageString) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titleString);
        alert.setHeaderText("");
        alert.setContentText(messageString);
        Optional<ButtonType> action = alert.showAndWait();
        return action.get() == ButtonType.OK;
    }

}
