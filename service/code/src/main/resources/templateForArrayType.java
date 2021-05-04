public class ArrayType {

    private RecordType[] array;

    public static ArrayType with(Object[] incoming) {
        RecordType[] array = new RecordType[incoming.length];
        System.arraycopy(incoming, 0, array, 0, incoming.length);
        ArrayType arrayType = new ArrayType();
        arrayType.setArray(array);
        return arrayType;
    }

    public RecordType[] getArray() {
        return array;
    }

    public void setArray(RecordType[] array) {
        this.array = array;
    }

    public int length() {
        return array.length;
    }

    public RecordType getElement(long index) {
        return array[(int) index];
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof ArrayType)) {
            return false;
        }
        ArrayType other = (ArrayType) o;
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
