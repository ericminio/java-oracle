    private Date buildDateOrNull(java.sql.Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        else {
            return new Date(timestamp.getTime());
        }
    }
