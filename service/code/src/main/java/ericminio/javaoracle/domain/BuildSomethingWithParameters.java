package ericminio.javaoracle.domain;

public abstract class BuildSomethingWithParameters {

    public String please(Parameters parameters) {
        String output = "";
        for (int index = 0; index< parameters.count(); index++) {
            String specification = parameters.get(index);
            String[] parts = specification.trim().split("\\s");
            String name = parts[0];
            String type = parts[1];
            output = modify(output, index, name, type, index == parameters.count()-1);
        }
        return output;
    }

    protected abstract String modify(String output, int index, String name, String type, boolean isLast);
}
