    public Object methodName() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select packageName.functionName(???) from dual");
        // set IN parameters
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Object data = resultSet.getObject(1);

        return data;
    }