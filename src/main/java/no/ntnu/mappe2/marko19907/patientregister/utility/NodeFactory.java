package no.ntnu.mappe2.marko19907.patientregister.utility;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.mappe2.marko19907.patientregister.model.Patient;

/**
 * The class NodeFactory represents a factory that is responsible for creating various Node objects.
 *
 * @author Marko
 * @version 29-04-2021
 */
public class NodeFactory
{

    /**
     * NodeFactory constructor
     */
    public NodeFactory()
    {
    }

    /**
     * Returns a new TextField
     * @return A new TextField
     */
    public Node createTextField()
    {
        return new TextField();
    }

    /**
     * Returns a new GridPane
     * @return A new GridPane
     */
    public Node createGridPane()
    {
        return new GridPane();
    }

    /**
     * Returns a new Label
     * @return A new Label
     */
    public Node createLabel()
    {
        return new Label();
    }

    /**
     * Returns a new Button
     * @return A new Button
     */
    public Node createButton()
    {
        return new Button();
    }

    /**
     * Returns a new MenuBar
     * @return A new MenuBar
     */
    public Node createMenuBar()
    {
        return new MenuBar();
    }

    /**
     * Returns a new ToolBar
     * @return A new ToolBar
     */
    public Node createToolBar()
    {
        return new ToolBar();
    }

    /**
     * Returns a new BorderPane
     * @return A new BorderPane
     */
    public Node createBorderPane()
    {
        return new BorderPane();
    }

    /**
     * Returns a new TableView
     * @return A new TableView
     */
    public Node createTableView()
    {
        return new TableView<Patient>();
    }

    /**
     * Returns a new VBox
     * @return A new VBox
     */
    public Node createVBox()
    {
        return new VBox();
    }

    /**
     * Returns a new HBox
     * @return A new HBox
     */
    public Node createHBox()
    {
        return new HBox();
    }
}
