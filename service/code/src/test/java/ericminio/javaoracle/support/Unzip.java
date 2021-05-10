package ericminio.javaoracle.support;

import ericminio.javaoracle.domain.FileInfo;
import ericminio.javaoracle.domain.FileSet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip {

    public FileSet please(byte[] bytes) throws IOException {
        FileSet fileSet = new FileSet();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ZipInputStream zipInputStream = new ZipInputStream(byteArrayInputStream, StandardCharsets.UTF_8);
        ZipEntry entry;
        while((entry = zipInputStream.getNextEntry()) != null) {
            String content = new Stringify().inputStream(zipInputStream);
            String fileName = entry.getName();
            fileSet.add(new FileInfo(fileName, content));
            zipInputStream.closeEntry();
        }
        zipInputStream.close();

        return fileSet;
    }
}
