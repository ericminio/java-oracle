    public int methodName() throws SQLException {
        CallableStatement statement = connection.prepareCall("{? = call packageName.functionName()}");
        statement.registerOutParameter(1, Types.INTEGER);
        statement.executeUpdate();

        return statement.getInt(1);
    }
