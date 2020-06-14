import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
    private AnchorPane root;

    @FXML
    private Button upl;

    @FXML
    private Button upr;

    @FXML
    private TextField cwdl;

    @FXML
    private TextField cwdr;

    @FXML
    private Button refreshl;

    @FXML
    private Button refreshr;

    @FXML
    private TableView<Entry> listl;

    @FXML
    private TableView<Entry> listr;
}
