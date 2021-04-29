package ericminio.javaoracle.domain;

public interface TypeMapper {

    String getter();

    String setter();

    String sqlType();

    String javaType();

    String sqlInputRead();
}
