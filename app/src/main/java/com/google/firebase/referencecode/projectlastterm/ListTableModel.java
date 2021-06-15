package com.google.firebase.referencecode.projectlastterm;

public class ListTableModel {
    private String nameTable;
    private String status;

    public String getNameTable() {
        return nameTable;
    }

    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ListTableModel(String status) {
        this.status = status;
    }
    public ListTableModel() {

    }
}
