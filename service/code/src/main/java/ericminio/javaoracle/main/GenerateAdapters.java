package ericminio.javaoracle.main;

import ericminio.javaoracle.domain.Incoming;
import ericminio.javaoracle.domain.FileInfo;
import ericminio.javaoracle.domain.FileSet;
import ericminio.javaoracle.domain.GenerateClasses;
import ericminio.javaoracle.support.LogSink;

import java.io.IOException;

import static ericminio.javaoracle.support.FileUtils.save;

public abstract class GenerateAdapters {

    private final GenerateClasses generateClasses;

    public GenerateAdapters() {
        generateClasses = new GenerateClasses();
    }

    public String getLog() {
        return generateClasses.getLog();
    }

    public LogSink getLogSink() {
        return generateClasses.getLogSink();
    }

    public void now(Incoming incoming, String javaPackage, String outputFolder) throws IOException {
        FileSet fileSet = generateClasses.from(incoming, javaPackage);
        for (int i=0; i<fileSet.size(); i++) {
            FileInfo fileInfo = fileSet.get(i);
            save(outputFolder, fileInfo.getFileName(), fileInfo.getContent());
        }
    }
}
