package ericminio.javaoracle.demos;

public class ArrayOfVarchar {

    private String[] array;

    public static ArrayOfVarchar with(Object[] incoming) {
        String[] array = new String[incoming.length];
        System.arraycopy(incoming, 0, array, 0, incoming.length);
        ArrayOfVarchar arrayOfVarchar = new ArrayOfVarchar();
        arrayOfVarchar.setArray(array);
        return arrayOfVarchar;
    }

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }

    public int length() {
        return array.length;
    }

    public String getElement(long index) {
        return array[(int) index];
    }
}
