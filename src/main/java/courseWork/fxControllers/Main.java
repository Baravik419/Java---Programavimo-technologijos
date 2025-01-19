package courseWork.fxControllers;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import courseWork.StartGUI;
import courseWork.hibernateControllers.CustomHibernate;
import courseWork.hibernateControllers.GenericHibernate;
import courseWork.Model.*;
import courseWork.Model.Enum.PublicationStatus;


public class Main implements Initializable {

    //*--User field--*//

    @FXML
    public Tab userTab;

    @FXML
    public ListView<User> userListField;

    @FXML
    public TextField loginField;

    @FXML
    public TextField nameField;

    @FXML
    public TextField pswField;

    @FXML
    public TextField surnameField;

    @FXML
    public TextField addressField;

    @FXML
    public DatePicker bDay;

    @FXML
    public TextField phoneNumField;

    @FXML
    public TextField emailField;

    @FXML
    public RadioButton adminCheck;

    @FXML
    public RadioButton clientCheck;

    //*--User manager field--*//

    public Tab userManagerTab;

    @FXML
    public TableView<UserTableParam> userTable;

    @FXML
    public TableColumn<UserTableParam, Integer> colId;

    @FXML
    public TableColumn<UserTableParam, String> colLogin;

    @FXML
    public TableColumn<UserTableParam, String> colPass;

    @FXML
    public TableColumn<UserTableParam, String> colName;

    @FXML
    public TableColumn<UserTableParam, String> colSurname;

    @FXML
    public TableColumn<UserTableParam, String> colAddress;

    @FXML
    public TableColumn<UserTableParam, String> colPhone;

    @FXML
    public TableColumn<UserTableParam, String> colMail;

    @FXML
    public TableColumn dummyCol;

    //*--Book exchange field--*//

    @FXML
    public Tab bookExchangeTab;

    @FXML
    public ListView<Publication> availablePublicationList;

    @FXML
    public TextArea publicationBio;

    @FXML
    public ListView<Comment> messageList;

    @FXML
    public TextArea ownerBio;

    @FXML
    public TextArea messageText;

    @FXML
    public Button leaveReviewButton;

    @FXML
    public Button reserveButton;

    @FXML
    public Button addMessageButton;

    @FXML
    public TabPane allTabs;

    @FXML
    public Tab chatTab;

    //*--Inventory field--*//

    @FXML
    public Tab inventoryTab;

    @FXML
    public TableView<PublicationTableParam> inventoryTable;

    @FXML
    public TableColumn<PublicationTableParam, Integer> colPublicationID;

    @FXML
    public TableColumn<PublicationTableParam, String> colPublicationTitle;

    @FXML
    public TableColumn<PublicationTableParam, String> colUser;

    @FXML
    public TableColumn<PublicationTableParam, String> colDate;

    @FXML
    public TableColumn colPublicationStatus;

    @FXML
    public TableColumn colHistory;

    @FXML
    public ListView<Publication> borrowedBooksListView;

    //*--Chat field--*//

    @FXML
    public ComboBox<Client> userSelectionComboBox;

    @FXML
    public TreeView<Chat> chatWindow;

    @FXML
    public ContextMenu chatContextMenu;

    @FXML
    public TextArea chatBodyTextArea;

    @FXML
    public Button insertChatButton;

    @FXML
    public MenuItem deleteChatItem;

    @FXML
    public ListView<Client> userListViewChat;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bookExchange");
    GenericHibernate hibernate = new GenericHibernate(entityManagerFactory);
    CustomHibernate cusHib = new CustomHibernate(entityManagerFactory);

    private User currentUser;

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = user;
        fillUserList();
        enableVisibility();
    }

    private void enableVisibility() {
        if (currentUser instanceof Client) {
            allTabs.getTabs().remove(userTab);
            allTabs.getTabs().remove(userManagerTab);
            allTabs.getTabs().add(chatTab);
        } else {
            leaveReviewButton.setDisable(false);
            allTabs.getTabs().remove(chatTab);
        }

    }

    public void disableFields() {

        if (clientCheck.isSelected()) {
            addressField.setDisable(false);
            bDay.setDisable(false);
            emailField.setDisable(false);
            phoneNumField.setDisable(true);
            phoneNumField.clear();

        } else {
            addressField.setDisable(true);
            addressField.clear();
            bDay.setDisable(true);
            bDay.setValue(null);
            emailField.setDisable(true);
            emailField.clear();
            phoneNumField.setDisable(false);
        }

    }

    public void loadData() {
        if (userTab.isSelected()) {
            fillUserList();
        } else if (bookExchangeTab.isSelected()) {
            availablePublicationList.getItems().clear();
            availablePublicationList.getItems().addAll(cusHib.getAvailablePublications(currentUser));
        }

        else if (inventoryTab.isSelected()) {
            fillPublicationList();
            loadBorrowedPublicationlist();
        }

        else if (userManagerTab.isSelected()) {
            //loadUserData();
            fillUserTable();
        } else if (chatTab.isSelected()) {
            loadUserComboBox();
        }
    }

    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillUserList();
        disableFields();
        loadUserComboBox();
        //fillTree();

        userTable.setEditable(true);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colPass.setCellValueFactory(new PropertyValueFactory<>("password"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colLogin.setCellFactory(TextFieldTableCell.forTableColumn());

        colLogin.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setLogin(event.getNewValue());
            User user = hibernate.getEntityById(User.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            user.setLogin(event.getNewValue());
            hibernate.update(user);
        });

        colAddress.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setAddress(event.getNewValue());
            User user = hibernate.getEntityById(User.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            if (user instanceof Client client) {
                client.setAddress(event.getNewValue());
                hibernate.update(user);
            }
        });



        colPhone.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPhoneNumber(event.getNewValue());
            User user = hibernate.getEntityById(User.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            if (user instanceof Admin admin) {
                admin.setPhoneNumber(event.getNewValue());
                hibernate.update(admin);
            }
        });

        Callback<TableColumn<UserTableParam, Void>, TableCell<UserTableParam, Void>> callback = param -> {
            final TableCell<UserTableParam, Void> cell = new TableCell<>() {

                private final Button deleteButton = new Button("Delete");
                {
                    deleteButton.setOnAction(event -> {
                        UserTableParam row = getTableView().getItems().get(getIndex());
                        hibernate.delete(User.class, row.getId());
                        fillUserTable();
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            };
            return cell;
        };

        dummyCol.setCellFactory(callback);

        //*--My Inventory--*//
        availablePublicationList.setEditable(true);
        colPublicationID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPublicationTitle.setCellValueFactory(new PropertyValueFactory<>("publicationTitle"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("publicationUser"));
        Callback<TableColumn<PublicationTableParam, Void>, TableCell<PublicationTableParam, Void>> callbackBookStatus = param -> {
            final TableCell<PublicationTableParam, Void> cell = new TableCell<>() {

                private final ChoiceBox<PublicationStatus> bookStatus = new ChoiceBox<>();

                {
                    bookStatus.getItems().addAll(PublicationStatus.values());
                    bookStatus.setOnAction(event -> {
                        PublicationTableParam rowData = getTableRow().getItem();
                        if (rowData != null) {
                            rowData.setPublicationStatus(bookStatus.getValue());

                            Publication publication = hibernate.getEntityById(Publication.class, rowData.getId());
                            publication.setPublicationStatus(bookStatus.getValue());
                            hibernate.update(publication);

                            insertPublicationRecord(publication);
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        PublicationTableParam rowData = getTableRow().getItem();
                        bookStatus.setValue(rowData.getPublicationStatus());
                        setGraphic(bookStatus);
                    }
                }
            };
            return cell;
        };

        colPublicationStatus.setCellFactory(callbackBookStatus);

        Callback<TableColumn<PublicationTableParam, Void>, TableCell<PublicationTableParam, Void>> historyButton = param -> new TableCell<>() {
            private final Button viewHistoryBtn = new Button("View history");

            {
                viewHistoryBtn.setOnAction(event -> {
                    PublicationTableParam row = getTableView().getItems().get(getIndex());
                    try {
                        loadHistory(row.getId(), currentUser);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(viewHistoryBtn);
                }
            }
        };

        colHistory.setCellFactory(historyButton);
        setData(entityManagerFactory, currentUser);
        loadData();
    }

//*-----------------------------------------------------------*
    //USER TAB

    public void fillUserList() {
        userListField.getItems().clear();
        nameField.clear();
        surnameField.clear();
        addressField.clear();
        loginField.clear();
        pswField.clear();
        phoneNumField.clear();
        emailField.clear();
        List<User> userList = hibernate.getAllRecords(User.class);
        userListField.getItems().addAll(userList);
    }

    public void createNewUser() {
        if (clientCheck.isSelected()) {
            Client client = new Client(loginField.getText(), pswField.getText(), nameField.getText(), surnameField.getText(), addressField.getText(), bDay.getValue(), emailField.getText());
            if(client != null) {
                hibernate.create(client);
            }
        }
        else{
            Admin admin = new Admin(loginField.getText(), pswField.getText(), nameField.getText(), surnameField.getText(), phoneNumField.getText());
            if (admin != null) {
                hibernate.create(admin);
            }
        }
        fillUserList();
    }

    public void updateUser() {
        User selectedUser = userListField.getSelectionModel().getSelectedItem();
        User userInfoFromDB = hibernate.getEntityById(User.class, selectedUser.getId());

        userInfoFromDB.setName(nameField.getText());
        userInfoFromDB.setSurname(surnameField.getText());

        if (userInfoFromDB instanceof Client)
        {
            addressField.setText(((Client) userInfoFromDB).getAddress());
            emailField.setText(((Client) userInfoFromDB).getEmail());
            bDay.setValue(((Client) userInfoFromDB).getBirthDate());
        } else {
            phoneNumField.setText(((Admin) userInfoFromDB).getPhoneNumber());
        }

        hibernate.update(userInfoFromDB);
        fillUserList();
    }

    public void deleteUser() {
        User selectedUser = userListField.getSelectionModel().getSelectedItem();
        hibernate.delete(User.class, selectedUser.getId());
        fillUserList();
    }

    public void loadUserData()
    {
        User selectedUser = userListField.getSelectionModel().getSelectedItem();
        User userInfoFromDB = hibernate.getEntityById(User.class, selectedUser.getId());

        nameField.setText(userInfoFromDB.getName());
        surnameField.setText(userInfoFromDB.getSurname());

        if (userInfoFromDB instanceof Client)
        {
            Client client = (Client) userInfoFromDB;
            addressField.setText(client.getAddress());
            emailField.setText(client.getEmail());
            bDay.setValue(client.getBirthDate());
        } else {
            Admin admin = (Admin) userInfoFromDB;
            phoneNumField.setText(admin.getPhoneNumber());
        }
    }

    public void clearFields()
    {
        loginField.clear();
        nameField.clear();
        surnameField.clear();
        addressField.clear();
        phoneNumField.clear();
        emailField.clear();
        pswField.clear();
    }

    public void loadProductForm() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("/fxmls/productWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Book exchange platform");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
//*-----------------------------------------------------------*

//*-----------------------------------------------------------*

    //BOOK EXCHANGE TAB

    public void loadPublicationInfo() {
        Publication publication = availablePublicationList.getSelectionModel().getSelectedItem();
        Publication publicationFromDb = hibernate.getEntityById(Publication.class, publication.getId());

        publicationBio.setText(
                "Title: " + publicationFromDb.getTitle() + "\n" +
                        "Year: " + publicationFromDb.getYear());

        ownerBio.setText(publicationFromDb.getOwner().getName());
    }

    public void loadReviewWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("/fxmls/UserReview.fxml"));
        Parent parent = fxmlLoader.load();
        UserReview userReview = fxmlLoader.getController();
        userReview.setData(entityManagerFactory, currentUser, availablePublicationList.getSelectionModel().getSelectedItem().getOwner());
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle("Book exchange platform");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void chatWithOwner() {
    }

    public void reserveBook() {

        Publication publication = availablePublicationList.getSelectionModel().getSelectedItem();
        Publication publicationFromDb = hibernate.getEntityById(Publication.class, publication.getId());
        publicationFromDb.setPublicationStatus(PublicationStatus.REQUESTED);
        publicationFromDb.setClient((Client) currentUser);
        hibernate.update(publicationFromDb);

        PeriodicRecord periodicRecord = new PeriodicRecord((Client) currentUser, publicationFromDb, LocalDate.now(), PublicationStatus.REQUESTED);
        hibernate.create(periodicRecord);
        loadData();
    }

    private void fillPublicationList() {
        inventoryTable.getItems().clear();
        List<Publication> publications = cusHib.getOwnPublications(currentUser);
        for (Publication p : publications) {
            PublicationTableParam publicationTableParam = new PublicationTableParam();
            publicationTableParam.setId(p.getId());
            publicationTableParam.setPublicationTitle(p.getTitle());
            if (p.getClient() != null) {
                publicationTableParam.setPublicationUser(p.getClient().getName() + " " + p.getClient().getSurname());
            }
            publicationTableParam.setPublicationStatus(p.getPublicationStatus());

            inventoryTable.getItems().add(publicationTableParam);
        }
    }

    private void insertPublicationRecord(Publication publication) {
        PeriodicRecord periodicRecord = new PeriodicRecord(publication.getClient(), publication, LocalDate.now(), publication.getPublicationStatus());
        hibernate.create(periodicRecord);
    }

    private void loadHistory(int id, User currentUser) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("/fxmls/History.fxml"));
        Parent parent = fxmlLoader.load();
        History history = fxmlLoader.getController();
        history.setData(entityManagerFactory, currentUser, id);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle("Book exchange platform");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private void loadBorrowedPublicationlist()
    {
        borrowedBooksListView.getItems().clear();
        List<Publication> publications = cusHib.getOwnBorrowedPublications(currentUser);
        borrowedBooksListView.getItems().addAll(publications);
    }
//*-----------------------------------------------------------*


//*-----------------------------------------------------------*
    //USER MANAGER TAB

    public void fillUserTable() {
        userTable.getItems().clear();
        List<User> users = hibernate.getAllRecords(User.class);
        for (User u : users) {
            UserTableParam userTableParameters = new UserTableParam();
            userTableParameters.setId(u.getId());
            userTableParameters.setLogin(u.getLogin());
            userTableParameters.setPassword(u.getPassword());
            userTableParameters.setName(u.getName());
            userTableParameters.setSurname(u.getSurname());
            if(u instanceof Client) {
                userTableParameters.setAddress(((Client) u).getAddress());
                userTableParameters.setEmail(((Client) u).getEmail());
            }
            else
            {
                userTableParameters.setAddress(null);
                userTableParameters.setEmail(null);
                userTableParameters.setPhoneNumber(((Admin) u).getPhoneNumber());
            }
            userTable.getItems().add(userTableParameters);
        }
    }

    //*------------------------------------------------------------*
    //CHAT TAB

    public void insertChat() {
        if (currentUser instanceof Client client) {
            Client targetClient = userListViewChat.getSelectionModel().getSelectedItem();

            Chat selectedChat = chatWindow.getSelectionModel().getSelectedItem() != null ? chatWindow.getSelectionModel().getSelectedItem().getValue() : null;
            Chat chat;
            if (selectedChat != null) {
                chat = new Chat(chatBodyTextArea.getText(), selectedChat, (Client) currentUser);
            } else {

                chat = new Chat(chatBodyTextArea.getText(), targetClient, client);
            }
            hibernate.create(chat);
            fillTree();
        }
    }

    public void loadUserComboBox()
    {
        userListViewChat.getItems().clear();
        List<Client> clients = cusHib.getClients((Client)currentUser);
        userListViewChat.getItems().addAll(clients);
    }

    public void fillTree() {
        Client targetClient = userListViewChat.getSelectionModel().getSelectedItem();
        chatWindow.setRoot(new TreeItem<>());
        chatWindow.setShowRoot(false);
        chatWindow.getRoot().setExpanded(true);
        Client clientFromDb = hibernate.getEntityById(Client.class, targetClient.getId());
        clientFromDb.getChatList().forEach(c -> addTreeItem(c, chatWindow.getRoot()));
        clientFromDb.getMyChats().forEach(c -> addTreeItem(c, chatWindow.getRoot()));

    }

    public void addTreeItem(Chat chat, TreeItem<Chat> parentChat) {
        TreeItem<Chat> treeItem = new TreeItem<>(chat);
        parentChat.getChildren().add(treeItem);
        chat.getRepliedChats().forEach(sub -> addTreeItem(sub, treeItem));
    }


    public void loadChat() {
        Chat selectedChat = chatWindow.getSelectionModel().getSelectedItem().getValue();
        chatBodyTextArea.setText(selectedChat.getBody());
    }

    public void deleteChat() {
        cusHib.deleteChat(chatWindow.getSelectionModel().getSelectedItem().getValue().getId());
    }
}

