    public int methodName() throws SQLException {
        CallableStatement statement = connection.prepareCall("{? = call packageName.functionName()}");
        statement.registerOutParameter(1, Types.TTT);
        statement.execute();

        return statement.getTtt(1);
    }
