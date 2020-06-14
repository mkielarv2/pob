import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileExplorerManager {
    private final int THRESHOLD = 300;
    private long clickTimestamp = 0;

    private final TextField cwd;
    private final TableView<Entry> table;

    public FileExplorerManager(TextField cwd, TableView<Entry> table) {
        this.cwd = cwd;
        this.table = table;

        setDefaultDirectory(cwd);

        initColumns(table);
        setOnClickListener(table, cwd);

        addKeyEventHandlers(table);

        table.setOnDragDetected(event -> {
            Entry selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Dragboard dragboard = table.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();

                List<File> files = new LinkedList<>();
                files.add(new File(table.getSelectionModel().getSelectedItem().getPath()));
                content.putFiles(files);
                content.putString(Integer.toString(this.hashCode()));

                dragboard.setContent(content);
                event.consume();
            }
        });

        table.setOnDragOver(event -> {
            if (Integer.parseInt(event.getDragboard().getString()) != this.hashCode()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        table.setOnDragDropped(event -> {
            List<File> files = event.getDragboard().getFiles();
            System.out.println("wolololo");
            try {
                Files.copy(files.get(0).toPath(), (new File(cwd.getText() + "\\" + files.get(0).getName())).toPath());
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error occured during operation");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });

        refresh();
    }

    private void setDefaultDirectory(TextField cwd) {
        cwd.setText("C:\\");
    }

    private void initColumns(TableView<Entry> table) {
        TableColumn<Entry, Boolean> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item ? "D" : "F");
                } else {
                    setText("");
                }
            }
        });
        table.getColumns().add(typeColumn);

        TableColumn<Entry, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        table.getColumns().add(nameColumn);
        table.getSortOrder().add(nameColumn);

        TableColumn<Entry, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setCellFactory(column -> new TableCell<>() {
            private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : format.format(item));
            }
        });
        table.getColumns().add(dateColumn);
    }

    public void refresh() {
        List<Entry> entries = getEntries(cwd.getText());
        table.getItems().clear();
        table.getItems().addAll(entries);
    }

    private List<Entry> getEntries(String path) {
        File folder = new File(path);
        File[] files = folder.listFiles();
        List<Entry> entries = new LinkedList<>();
        if (files != null) {
            Arrays.stream(files)
                    .forEach(file ->
                            entries.add(
                                    new Entry(
                                            file.getName(),
                                            new Date(file.lastModified()),
                                            file.isDirectory(),
                                            file.getAbsolutePath()
                                    )
                            )
                    );
        }
        return entries;
    }

    public void dirUp() {
        File parent = getParentFile();
        if (parent != null) {
            cwd.setText(parent.getAbsolutePath());
            refresh();
        }
    }

    private File getParentFile() {
        return (new File(cwd.getText())).getParentFile();
    }

    public void deleteEntry() {
        var item = table.getSelectionModel().getSelectedItem();
        if (item != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete '" + item.getName() + "' from current folder?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK &&
                    (new File(item.getPath())).delete()) {
                refresh();
            } else {
                Alert dialog = new Alert(Alert.AlertType.ERROR);
                dialog.setTitle("Error");
                dialog.setHeaderText("An error occured during operation");
                dialog.setContentText("File could not be deleted");
                dialog.showAndWait();
            }
        }
    }

    public void newDirectory() {
        TextInputDialog inputDialog = new TextInputDialog("New Folder");
        inputDialog.setTitle("Insert directory name");
        inputDialog.setHeaderText(null);
        Optional<String> optional = inputDialog.showAndWait();
        optional.ifPresent(s -> {
            File directory = new File(cwd.getText() + "\\" + s);
            if (directory.mkdir()) {
                refresh();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error occured during operation");
                alert.setContentText("Directory could not be created");
                alert.showAndWait();
            }
        });
    }

    private void setOnClickListener(TableView<Entry> table, TextField cwd) {
        table.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (System.currentTimeMillis() - clickTimestamp < THRESHOLD) {
                    int index = table.getSelectionModel().getSelectedIndex();
                    Entry entry = table.getItems().get(index);
                    if (entry.getType()) {
                        cwd.setText(entry.getPath());
                        refresh();
                    }
                }
                clickTimestamp = System.currentTimeMillis();
            }
        });
    }

    private void addKeyEventHandlers(TableView<Entry> table) {
        table.addEventHandler(InputEvent.ANY, event -> {
            if (event instanceof KeyEvent) {
                KeyEvent keyEvent = (KeyEvent) event;
                if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                    if (keyEvent.getCode() == KeyCode.F7) {
                        deleteEntry();
                    } else if (keyEvent.getCode() == KeyCode.F8) {
                        newDirectory();
                    }
                }
            }
        });
    }

    @Override
    public int hashCode() {
        return Objects.hash(THRESHOLD, clickTimestamp, cwd, table);
    }
}
