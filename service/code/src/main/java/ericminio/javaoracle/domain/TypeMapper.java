package ericminio.javaoracle.domain;

public interface TypeMapper {

    String resultSetGetter();

    String sqlStatementSetter();

    String javaType();

    String sqlInputRead();

    String sqlOutputWrite();
}
