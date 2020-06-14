import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController {
    private FileExplorerManager leftManager;
    private FileExplorerManager rightManager;

    public MainController() {
    }

    @FXML
    private void initialize() {
        leftManager = new FileExplorerManager(cwdl, listl);
        rightManager = new FileExplorerManager(cwdr, listr);
    }


    @FXML
    public void onRefreshL() {
        leftManager.refresh();
    }

    @FXML
    public void onRefreshR() {
        rightManager.refresh();
    }

    @FXML
    public void dirUpL() {
        leftManager.dirUp();
    }

    @FXML
    public void dirUpR() {
        rightManager.dirUp();
    }

    @FXML
    public void cwdL() {
        leftManager.refresh();
    }

    @FXML
    public void cwdR() {
        rightManager.refresh();
    }

    @FXML
    public void newL() {
        leftManager.newDirectory();
    }

    @FXML
    public void newR() {
        rightManager.newDirectory();
    }

    @FXML
    public void deleteL() {
        leftManager.deleteEntry();
    }

    @FXML
    public void deleteR() {
        rightManager.deleteEntry();
    }

    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    public void genericDelete() {
        if (listl.isFocused()) {
            leftManager.deleteEntry();
        } else if (listr.isFocused()) {
            rightManager.deleteEntry();
        } else {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setHeaderText("An error occured during operation");
            dialog.setContentText("File could not be deleted");
            dialog.showAndWait();
        }
    }

    @FXML
    public void genericNewFolder() {
        if (listr.isFocused()) {
            leftManager.newDirectory();
        } else if (listr.isFocused()) {
            rightManager.newDirectory();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occured during operation");
            alert.setContentText("Directory could not be created");
            alert.showAndWait();
        }
    }

    @FXML
    public void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Author:");
        alert.setContentText("Marcin Kielar s452666");
        alert.showAndWait();
    }

    @FXML
    private TextField cwdl;

    @FXML
    private TextField cwdr;

    @FXML
    private TableView<Entry> listl;

    @FXML
    private TableView<Entry> listr;
}
