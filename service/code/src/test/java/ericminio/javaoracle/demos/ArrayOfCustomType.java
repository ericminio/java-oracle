package ericminio.javaoracle.demos;

public class ArrayOfCustomType {

    private CustomType[] array;

    public static ArrayOfCustomType with(Object[] incoming) {
        CustomType[] array = new CustomType[incoming.length];
        System.arraycopy(incoming, 0, array, 0, incoming.length);
        ArrayOfCustomType arrayOfCustomType = new ArrayOfCustomType();
        arrayOfCustomType.setArray(array);
        return arrayOfCustomType;
    }

    public CustomType[] getArray() {
        return array;
    }

    public void setArray(CustomType[] array) {
        this.array = array;
    }

    public int length() {
        return array.length;
    }

    public CustomType getElement(long index) {
        return array[(int) index];
    }
}
