package ericminio.javaoracle.domain;

public class TypeMapperNumber implements TypeMapper {

    @Override
    public String functionParameterSettingStatement() {
        return "statement.setBigDecimal(index, field);";
    }

    @Override
    public String javaType() {
        return "BigDecimal";
    }

    @Override
    public String sqlInputRead() {
        return "stream.readBigDecimal()";
    }

    @Override
    public String sqlOutputWrite() {
        return "stream.writeBigDecimal(this.getField());";
    }

    @Override
    public String methodReturnStatement() {
        return "return (BigDecimal) data;";
    }
}
