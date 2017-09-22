package Controllers;

import Classes.XmlTag.Period;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class DataWindowController {
    public Label labelMeasPoint;
    public Label labelMeasChannel;
    public TableColumn<Period, String> columnTime;
    public TableColumn<Period, String> columnValue;
    public TableColumn<Period, String> columnStatus;
    public TableView dataTableView;
    // создаем контекст. меню и его элементы
    private ContextMenu contextMenu = new ContextMenu();
    private MenuItem unCommMenuItem = new MenuItem("Пометить как некоммер.");
    private MenuItem commMenuItem = new MenuItem("Убрать статус некоммер.");

    @FXML
    private void initialize(){
        // указываем возможность множест. выбора строк в таблице
        dataTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // связываем столбцы таблицы с полями Period-a
        // !!! У всех этих полей должны быть геттеры
        columnTime.setCellValueFactory(new PropertyValueFactory<>("interval"));
        columnValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        contextMenu.getItems().addAll(unCommMenuItem, commMenuItem);

        // конттекс. меню показываем только при нахождении указателя мыши в указ. пределах элемента
        // dataTableView. Это сделано для того, чтобы меню показывалось только на строках таблицы, а
        // не на ее границах и заголовках столбцов
        dataTableView.setOnContextMenuRequested(event -> {
            if (event.getX() > 0 && event.getY() > 30 && event.getX() < 323 && event.getY() < 202) {
                contextMenu.show(dataTableView.getParent().getScene().getWindow(), event.getScreenX(), event.getScreenY());
            }
        });

        // обработка события выбора пункта "Пометить как некоммер."
        unCommMenuItem.setOnAction(event ->  {
            dataTableView.getSelectionModel().getSelectedItems().forEach(object ->{
                Period period = (Period) object;
                if (period != null) {
                    period.setStatus("1");
                }
            });
            dataTableView.refresh();
        });

        // обработка события выбора пункта "Убрать статус некоммер."
        commMenuItem.setOnAction(event ->  {
            dataTableView.getSelectionModel().getSelectedItems().forEach(object ->{
                Period period = (Period) object;
                if (period != null) {
                    period.setStatus("0");
                }
            });
            dataTableView.refresh();
        });
    }
}
