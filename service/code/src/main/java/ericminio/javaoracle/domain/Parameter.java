package ericminio.javaoracle.domain;

public class Parameter {

    private String parameterSpecification;
    private String name;
    private String type;
    private boolean out;

    public Parameter(String parameterSpecification) {
        this.parameterSpecification = parameterSpecification.toLowerCase().trim();
        if (this.parameterSpecification.indexOf(" in ") != -1) {
            this.parameterSpecification = this.parameterSpecification
                    .replace(" in ", " ").trim();
        }
        if (this.parameterSpecification.indexOf(" out ") != -1) {
            this.out = true;
            this.parameterSpecification = this.parameterSpecification
                    .replace(" out ", " ").trim();
        }
        this.parameterSpecification = this.parameterSpecification
                .replaceAll("\\s\\s+", " ").trim();
        String[] parts = this.parameterSpecification.split("\\s");
        setName(parts[0]);
        setType(parts[1]);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name.trim();
    }

    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type.trim();
    }

    public boolean isOut() {
        return out;
    }
}
