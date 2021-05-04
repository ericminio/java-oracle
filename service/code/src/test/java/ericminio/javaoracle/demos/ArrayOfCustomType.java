package ericminio.javaoracle.demos;

public class ArrayOfCustomType {

    private CustomType[] array;

    public static ArrayOfCustomType with(Object[] incoming) {
        CustomType[] array = new CustomType[incoming.length];
        System.arraycopy(incoming, 0, array, 0, incoming.length);
        ArrayOfCustomType arrayType = new ArrayOfCustomType();
        arrayType.setArray(array);
        return arrayType;
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

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof ArrayOfCustomType)) {
            return false;
        }
        ArrayOfCustomType other = (ArrayOfCustomType) o;
        if (! (this.length() == other.length() )) {
            return false;
        }
        boolean ok = true;
        for (int i=0; i<this.length(); i++) {
            ok = ok && (this.getElement(i).equals(other.getElement(i)));
            if (!ok) {
                break;
            }
        }

        return ok;
    }

    @Override
    public int hashCode() {
        int value = 0;
        for (int i=0; i<this.length(); i++) {
            value += this.getElement(i).hashCode();
        }
        return value;
    }

    @Override
    public String toString() {
        String collectionToString = "";
        for (int i=0; i<this.length(); i++) {
            collectionToString += this.getElement(i).toString();
        }
        return this.getClass().getSimpleName() + "{ " + collectionToString + " }";
    }
}
