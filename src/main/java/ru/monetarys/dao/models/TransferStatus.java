package ru.monetarys.dao.models;

public enum TransferStatus {
    NEW;

    public static TransferStatus byName(String value) {
        try {
            return TransferStatus.valueOf(value);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}
