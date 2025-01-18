package courseWork.fxControllers;

import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import courseWork.Model.Client;
import courseWork.Model.Comment;
import courseWork.Model.User;
import courseWork.hibernateControllers.CustomHibernate;
import courseWork.hibernateControllers.GenericHibernate;

public class UserReview {
    @FXML
    public TreeView<Comment> userReview;
    @FXML
    public ContextMenu commentContextMenu;
    @FXML
    public MenuItem deleteItem;
    @FXML
    public TextArea commentBody;
    @FXML
    public TextField commentTitle;
    @FXML
    public Button updateButton;



    private CustomHibernate customHibernate;
    private GenericHibernate genericHibernate;
    private User currentUser;
    private Client targetClient;

    public void setData(EntityManagerFactory entityManagerFactory, User user, Client client) {
        this.customHibernate = new CustomHibernate(entityManagerFactory);
        this.genericHibernate = new GenericHibernate(entityManagerFactory);
        this.currentUser = user;
        this.targetClient = client;
        fillTree();

        if (currentUser instanceof Client) {
            updateButton.setDisable(true);
            commentContextMenu.hide();
            //deleteItem.setDisable(true);
        }
    }

    private void fillTree() {
        userReview.setRoot(new TreeItem<>());
        userReview.setShowRoot(false);
        userReview.getRoot().setExpanded(true);
        Client clientFromDb = genericHibernate.getEntityById(Client.class, targetClient.getId());
        clientFromDb.getCommentList().forEach(c -> addTreeItem(c, userReview.getRoot()));
    }

    public void addTreeItem(Comment comment, TreeItem<Comment> parentComment) {
        TreeItem<Comment> treeItem = new TreeItem<>(comment);
        parentComment.getChildren().add(treeItem);
        comment.getReplies().forEach(sub -> addTreeItem(sub, treeItem));
    }

    public void insertComment() {

        if (currentUser instanceof Client client) {
            Comment selectedComment = userReview.getSelectionModel().getSelectedItem() != null ? userReview.getSelectionModel().getSelectedItem().getValue() : null;
            Comment comment;
            if (selectedComment != null) {
                comment = new Comment(commentTitle.getText(), commentBody.getText(), selectedComment, client);
            } else {
                comment = new Comment(commentTitle.getText(), commentBody.getText(), targetClient, client);
            }
            genericHibernate.create(comment);
            fillTree();
        }
    }

    public void loadComment() {
        Comment selectedComment = userReview.getSelectionModel().getSelectedItem().getValue();
        commentTitle.setText(selectedComment.getTitle());
        commentBody.setText(selectedComment.getBody());
    }

    public void updateComment() {
        Comment selectedComment = userReview.getSelectionModel().getSelectedItem().getValue();
        selectedComment.setTitle(commentTitle.getText());
        selectedComment.setBody(commentBody.getText());
        genericHibernate.update(selectedComment);
        fillTree();
    }

    public void deleteComment() {
        customHibernate.deleteComment(userReview.getSelectionModel().getSelectedItem().getValue().getId());
    }
}
