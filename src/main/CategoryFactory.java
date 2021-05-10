package main;

public class CategoryFactory {

    public String createCategory(String number) {

        String category = "Other";

        if (number.equalsIgnoreCase("001")) {
            category = "Books";
        } else if (number.equalsIgnoreCase("002")) {
            category = "Music & Entertainment";
        } else if (number.equalsIgnoreCase("003")) {
            category = "Games";
        } else if (number.equalsIgnoreCase("004")) {
            category = "Education";
        } else if (number.equalsIgnoreCase("005")) {
            category = "Sports & Fitness";
        } else if (number.equalsIgnoreCase("006")) {
            category = "Social Media";
        } else if (number.equalsIgnoreCase("007")) {
            category = "Utilities";
        }
        return category;
    }
}
