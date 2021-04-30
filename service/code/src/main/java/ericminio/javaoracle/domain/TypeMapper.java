package ericminio.javaoracle.domain;

public interface TypeMapper {

    String sqlStatementSetter();

    String javaType();

    String sqlInputRead();

    String sqlOutputWrite();

    String methodReturnStatement();
}
