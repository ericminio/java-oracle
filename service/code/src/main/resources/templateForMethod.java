    public Object methodName() throws SQLException {
        CallableStatement statement = connection.prepareCall("{ ? = call packageName.functionName(???) }");
        statement.registerOutParameter(1, Types.OTHER);
        // set/register parameters
        statement.execute();
        Object data = statement.getObject(1);
        // set out parameters

        return data;
    }