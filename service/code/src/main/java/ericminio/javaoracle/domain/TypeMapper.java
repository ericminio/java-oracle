package ericminio.javaoracle.domain;

public interface TypeMapper {

    String javaType();

    String functionParameterOutType();

    String functionParameterSettingStatement();

    String sqlInputRead();

    String sqlOutputWrite();

    String methodReturnStatement();

    String cast();
}
