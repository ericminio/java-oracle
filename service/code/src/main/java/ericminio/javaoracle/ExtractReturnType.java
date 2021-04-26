package ericminio.javaoracle;

import java.util.List;

public class ExtractReturnType {
    public String please(List<String> functionSpecification) {
        String lastLine = functionSpecification.get(functionSpecification.size() - 1);
        String[] words = lastLine.split(" ");
        String lastWord = words[words.length - 1];
        lastWord = lastWord.substring(0, lastWord.indexOf(";"));

        return lastWord;
    }
}
