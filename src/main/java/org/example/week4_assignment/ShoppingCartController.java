package org.example.week4_assignment;

import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.week4_assignment.db.CartService;
import org.example.week4_assignment.db.LocalizationService;
import org.example.week4_assignment.model.CartItem;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShoppingCartController {

    @FXML
    private VBox rootVBox;

    @FXML
    private Label titleLabel;

    @FXML
    private ComboBox<String> languageSelector;

    @FXML
    private Label itemsLabel;

    @FXML
    private TextField itemsField;

    @FXML
    private Button enterButton;

    @FXML
    private Label priceLabel;

    @FXML
    private TextField priceField;

    @FXML
    private Label quantityLabel;

    @FXML
    private TextField quantityField;

    @FXML
    private Button calculateButton;

    @FXML
    private Label totalLabel;

    private final LocalizationService localizationService = new LocalizationService();
    private final CartService cartService = new CartService();

    private Map<String, String> strings;
    private final List<CartItem> itemsList = new ArrayList<>();

    private int totalItems = 0;
    private int currentItem = 1;
    private double totalCost = 0.0;
    private String currentLang = "en";

    @FXML
    public void initialize() {
        languageSelector.getItems().addAll("English", "Arabic", "Finnish", "Swedish", "Japanese");
        languageSelector.getSelectionModel().select("English");

        updateLanguage("English");

        languageSelector.setOnAction(event -> {
            System.out.println("Selected from ComboBox: " + languageSelector.getValue());
            updateLanguage(languageSelector.getValue());
        });

        enterButton.setOnAction(event -> handleEnter());
        calculateButton.setOnAction(event -> calculateTotal());

        showItemFields(false);
    }

    private void updateLanguage(String language) {
        currentLang = switch (language) {
            case "Arabic" -> "ar";
            case "Finnish" -> "fi";
            case "Swedish" -> "sv";
            case "Japanese" -> "ja";
            default -> "en";
        };

        System.out.println("Current language code = " + currentLang);

        strings = localizationService.loadLanguage(currentLang);
        System.out.println("Strings map = " + strings);

        titleLabel.setText(strings.getOrDefault("title", "Shopping Cart"));
        itemsLabel.setText(strings.getOrDefault("prompt.noItems", "Enter number of items:"));
        enterButton.setText(strings.getOrDefault("button.enter", "Enter"));
        calculateButton.setText(strings.getOrDefault("button.calculate", "Calculate"));
        totalLabel.setText("");

        applyRTL();

        if (priceLabel.isVisible()) {
            updateItemLabels();
        }
    }

    private void applyRTL() {
        boolean rtl = currentLang.equals("ar");
        rootVBox.setNodeOrientation(rtl ? NodeOrientation.RIGHT_TO_LEFT : NodeOrientation.LEFT_TO_RIGHT);
        System.out.println("RTL applied: " + rtl);
    }

    @FXML
    public void handleEnter() {
        try {
            totalItems = Integer.parseInt(itemsField.getText().trim());

            if (totalItems <= 0) {
                totalLabel.setText(strings.getOrDefault("error.invalidInput", "Invalid input"));
                showItemFields(false);
                return;
            }

            currentItem = 1;
            totalCost = 0.0;
            itemsList.clear();

            showItemFields(true);
            updateItemLabels();
            clearItemFields();
            totalLabel.setText("");

        } catch (NumberFormatException e) {
            totalLabel.setText(strings.getOrDefault("error.invalidInput", "Invalid input"));
        }
    }

    @FXML
    public void calculateTotal() {
        try {
            double price = Double.parseDouble(priceField.getText().trim());
            int quantity = Integer.parseInt(quantityField.getText().trim());

            double subtotal = ShoppingCartCalculator.calculateItemCost(price, quantity);
            totalCost = ShoppingCartCalculator.addToTotal(totalCost, subtotal);

            itemsList.add(new CartItem(currentItem, price, quantity, subtotal));

            currentItem++;

            if (currentItem <= totalItems) {
                updateItemLabels();
                clearItemFields();
                return;
            }

            totalLabel.setText(strings.getOrDefault("label.total", "Total:") + " " + totalCost);

            int cartId = cartService.saveCartRecord(totalItems, totalCost, currentLang);
            if (cartId != -1) {
                cartService.saveCartItems(cartId, itemsList);
            }

        } catch (NumberFormatException e) {
            totalLabel.setText(strings.getOrDefault("error.invalidInput", "Invalid input"));
        }
    }

    private void updateItemLabels() {
        priceLabel.setText(
                MessageFormat.format(
                        strings.getOrDefault("prompt.priceItem", "Enter price for item {0}:"),
                        currentItem
                )
        );

        quantityLabel.setText(
                MessageFormat.format(
                        strings.getOrDefault("prompt.quantityItem", "Enter quantity for item {0}:"),
                        currentItem
                )
        );
    }

    private void clearItemFields() {
        priceField.clear();
        quantityField.clear();
    }

    private void showItemFields(boolean visible) {
        priceLabel.setVisible(visible);
        priceField.setVisible(visible);
        quantityLabel.setVisible(visible);
        quantityField.setVisible(visible);
        calculateButton.setVisible(visible);
    }
}