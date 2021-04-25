package ericminio.javaoracle;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Generator {

    public String generate(List<String> specification) throws IOException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("template.java");
        String code = new Stringify().inputStream(stream)
            .replace("ClassName", new ExtractClassName().please(specification));

        return code;
    }
}