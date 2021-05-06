package ericminio.javaoracle.domain;

public class AddPackageStatement {
    private String packageName;

    public AddPackageStatement(String packageName) {
        this.packageName = packageName;
    }

    public String to(String code) {
        return "package " + this.packageName + ";\n\n" + code;
    }
}
