package org.si.simple_login.vaadin.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import org.si.simple_login.domain.Car;
import org.si.simple_login.repository.CarService;
import org.si.simple_login.repository.impl.UserAuthenticationDAOSQL;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = MainView.NAME)
@UIScope
public class MainView extends CustomComponent implements View {

    public static final String NAME = "main";
    private String currentUserName;
    private Label label = new Label();
    private Grid<Car> grid = new Grid<>(Car.class);
    private TextField filterText = new TextField();

    @Autowired
    private CarService carService;

    public MainView(CarService carService){

        // Initialize layout component
        VerticalLayout mainViewLayout = new VerticalLayout(label);
        mainViewLayout.addComponent(filterText);
        mainViewLayout.addComponent(grid);

        setCompositionRoot(mainViewLayout);
        //this.carService = carService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event){

        currentUserName =
                (String) VaadinSession.getCurrent().getAttribute(UserAuthenticationDAOSQL.AUTHENTICATED_USER_NAME);
        label.setValue("You are logged in, " + currentUserName);
        configureFilter();
        configureGrid();
        updateList();
    }

    private void configureGrid() {
        //grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("car_brand", "car_yom", "description");
        grid.getColumns().forEach(col -> col.setResizable(true));
        // Render a button that deletes the data row (item)
        /*grid.addColumn(person -> "Delete",
                new ButtonRenderer<>(clickEvent -> {
                    System.out.println("etrrtety6tytu");
                    popup.setPopupVisible(true));
                    grid.setItems(people);
                }));*/
        /*VerticalLayout popupContent = new VerticalLayout();
        popupContent.addComponent(new TextField("Textfield"));
        popupContent.addComponent(new Button("Button"));
        PopupView popup = new PopupView(null, popupContent);
        ButtonRenderer ab = new ButtonRenderer();
        ab.addClickListener(event -> {
            System.out.println("etrrtety6tytu");
            popup.setPopupVisible(true);
        });
        grid.addColumn("description").setRenderer(ab);*/
    }
    private void configureFilter() {
        filterText.setPlaceholder("Filter by name...");
        //filterText.setClearButtonVisible(true);

        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private void updateList() {
        //grid.setItems(carService.findAll());
        grid.setItems(carService.findAll(filterText.getValue()));
    }

}
