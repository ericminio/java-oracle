package ericminio.javaoracle.support;

import ericminio.javaoracle.domain.FileInfo;
import ericminio.javaoracle.domain.FileSet;
import ericminio.javaoracle.http.FileFormData;
import ericminio.javaoracle.http.FormDataSet;

public class BuildFileSet {

    public FileSet from(FormDataSet input) {
        FileSet set = new FileSet();
        for (int i=0; i<input.size(); i++) {
            if (input.get(i) instanceof FileFormData) {
                FileFormData fileFormData = (FileFormData) input.get(i);
                set.add(new FileInfo(fileFormData.getFileName(), fileFormData.getContent()));
            }
        }
        return set;
    }
}
