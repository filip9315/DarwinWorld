package agh.ics.oop.model;

public class RowData {
    private String column1;
    private String column2;

    public RowData(String column1, String column2) {
        this.column1 = column1;
        this.column2 = column2;
    }

    public String getColumn1() {
        return column1;
    }

    public String getColumn2() {
        return column2;
    }
}