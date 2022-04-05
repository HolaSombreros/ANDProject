package com.group2.foodie.util;

public class DailyFormatter {

    public static String formatIngredients(String ingredients) {
        String output = ingredients.replace("<ol><li>", "");
        output = output.replace("</ol>", "");
        output = output.replace("</li><li>", "\n\n");
        output = output.replace("</li>", "");
        output = output.replace("<p>", "");
        output = output.replace("</p>", "");
        output = output.replace("<span>", "");
        output = output.replace("</span>", "");
        output = output.replace("<b>", "");
        output = output.replace("</b>", "");
        output = output.replace("<i>", "");
        output = output.replace("</i>", "");
        output = output.replace("<u>", "");
        output = output.replace("</u>", "");
        return output;
    }

}
