package ericminio.javaoracle.domain;

import java.util.List;

public class ExtractReturnType {
    public String please(List<String> functionSpecification) {
        String statement = new JoinWith(" ").please(functionSpecification).trim();
        if (statement.endsWith(";")) {
            statement = statement.substring(0, statement.length()-1).trim();
        }
        String[] words = statement.split(" ");
        String lastWord = words[words.length - 1];

        return lastWord;
    }
}
