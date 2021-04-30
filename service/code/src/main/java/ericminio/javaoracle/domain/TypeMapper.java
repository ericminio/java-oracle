package ericminio.javaoracle.domain;

public interface TypeMapper {

    String functionParameterSettingStatement();

    String javaType();

    String sqlInputRead();

    String sqlOutputWrite();

    String methodReturnStatement();
}
