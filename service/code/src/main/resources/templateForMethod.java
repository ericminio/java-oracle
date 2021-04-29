    public Object methodName() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select packageName.functionName(???) from dual");
        // set IN parameters
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return (Object) resultSet.getTtt(1);
    }