package com.gutscheweb.castles.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class FormatUtil {
    public static Component getFormattedList(String title, String... strings) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        StringBuilder toDeserialize = new StringBuilder("<yellow>--------</yellow><gold>" + title + "</gold><yellow>--------</yellow>");
        for (String str : strings)
            toDeserialize.append("\n").append(str);
        toDeserialize.append("\n<yellow>").append("-".repeat(title.length() + 12)).append("</yellow>");
        return miniMessage.deserialize(toDeserialize.toString());
    }
}
