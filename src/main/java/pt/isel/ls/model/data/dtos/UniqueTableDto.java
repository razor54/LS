package pt.isel.ls.model.data.dtos;


import pt.isel.ls.control.http.HttpStatusCode;
import pt.isel.ls.model.data.entities.Entity;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;


public class UniqueTableDto<T extends Entity> extends DTO {
    private final int numberOfColumns;
    private final String[] columnNames;
    private final int numberOfRows;
    private LinkedList<SQLRow> tableRows = null;
    private Function<? super SQLRow, T> toEntity;

    private final int skip;
    private final int top;
    private String path;

    private String userInput;

    // quais as posiçoes que devem ter referencias

    public  UniqueTableDto(ResultSet rs, int skip, int top) throws SQLException {
        super(HttpStatusCode.Ok,DTOType.Data);
        ResultSetMetaData metaData = rs.getMetaData();

        tableRows = new LinkedList<>();
        numberOfColumns = metaData.getColumnCount();
        this.columnNames = new String[numberOfColumns];
        setColumnNames(metaData);
        numberOfRows = fillTable(rs, skip, top);
        this.skip=skip;
        this.top=top;

    }

    public UniqueTableDto(ResultSet rs, int skip, int top, List<String> columnNames) throws SQLException {
        super(HttpStatusCode.Ok,DTOType.Data);

        ResultSetMetaData metaData = rs.getMetaData();

        tableRows = new LinkedList<>();
        numberOfColumns = metaData.getColumnCount();
        this.columnNames = (String[]) columnNames.toArray();
        numberOfRows = fillTable(rs, skip, top);
        this.skip=skip;
        this.top=top;
    }

    public UniqueTableDto(ResultSet rs, int skip, int top, Function<SQLRow, T> toEntity) throws SQLException {
        super(HttpStatusCode.Ok,DTOType.Data);

        ResultSetMetaData metaData = rs.getMetaData();

        tableRows = new LinkedList<>();
        numberOfColumns = metaData.getColumnCount();
        this.columnNames = new String[numberOfColumns];
        setColumnNames(metaData);
        this.toEntity = toEntity;
        numberOfRows = fillTable(rs, skip, top);

        this.skip=skip;
        this.top=top;
    }


    public UniqueTableDto(ResultSet rs, int skip, int top, Function<SQLRow, T> toEntity, String path, String userInput) throws SQLException {
        super(HttpStatusCode.Ok,DTOType.Data);

        this.path = path;
        ResultSetMetaData metaData = rs.getMetaData();
        this.userInput=userInput;
        tableRows = new LinkedList<>();
        numberOfColumns = metaData.getColumnCount();
        this.columnNames = new String[numberOfColumns];
        setColumnNames(metaData);
        this.toEntity = toEntity;
        numberOfRows = fillTable(rs, skip, top);

        this.skip=skip;
        this.top=top;
    }

    @Override
    public String toString() {
        return "UniqueTableDto{" +
                "numberOfColumns=" + numberOfColumns +
                ", columnNames=" + Arrays.toString(columnNames) +
                ", numberOfRows=" + numberOfRows +
                ", tableRows=" + tableRows +
                '}';
    }

    private int fillTable(ResultSet rs, int skip, int top) throws SQLException {
        int rows = 0;
        if (rs.last())
            length = rs.getRow();
        rs.beforeFirst();
        rs.absolute(skip);
        while (rs.next() && rows < top) {
            SQLRow row = new SQLRow(numberOfColumns);
            for (int i = 0; i < numberOfColumns; i++) {

                if (columnNames[i] != null)
                    row.setColumnValue(columnNames[i], rs.getObject(i + 1) == null ?
                            null : rs.getObject(i + 1).toString());
            }
            tableRows.add(row);
            ++rows;
        }
        return rows;
    }

    @Override
    public int maxRowNumber() {
        return length;
    }

    public String getColumnNames() {
        StringBuilder res = new StringBuilder();
        for (String s : columnNames) {
            res.append(s).append(" ");
        }
        return res.toString();
    }

    private void setColumnNames(ResultSetMetaData rsMetaData) throws SQLException {
        for (int i = 0; i < numberOfColumns; i++) {
            columnNames[i] = rsMetaData.getColumnName(i + 1).toLowerCase();
        }
    }

    public String getAllfromColumn(String column) {
        StringBuilder res = new StringBuilder();
        for (SQLRow t : tableRows) {
            res.append(t.getColumnValue(column)).append("\n");
        }
        return res.toString();
    }


    public int getRowNumber() {
        return numberOfRows;
    }


    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
         this.userInput=userInput;
    }


    public int getSkip() {
        return skip;
    }


    public int getTop() {
        return top;
    }

    public int getColumns() {
        return numberOfColumns;
    }

    public String getColumnLabel(int i) {
        return columnNames[i];
    }

    public int getLength(){
        return length;
    }
    private int length;
    public String getJSONFormatOf(int rowIdx) {
        StringBuilder result = new StringBuilder("{");
        SQLRow row = tableRows.get(rowIdx);

        for (int i = 0; i < numberOfColumns; i++) {
            if (i > 0)
                result.append(",");
            result.append("\"").append(columnNames[i]).append("\"").append(":").append("\"").append(row.getColumnValue(columnNames[i])).append("\"");
        }
        return result.append("}").toString();
    }

    @Override
    public String getViewPath() {
        return path;
    }

    public String getPlainRowAt(int i) {
        return tableRows.get(i).toString();
    }

    public String getTableAsPlainText() {
        return toString();
    }

    public Stream<T> getAsEntities() {

        return tableRows.stream().map(toEntity);
    }



    public SQLRow getRowAt(int i) {
        //TODO NO ELEMENTS EXCEPTION
        return tableRows.isEmpty() ? null : tableRows.get(i);
    }

    public String[] getColumn(String cName) {
        String[] column = new String[numberOfRows];
        for (int i = 0; i < column.length; i++) {
            column[i] = tableRows.get(i).getColumnValue(cName);
        }
        return column;
    }

    public class SQLRow {
        private final HashMap<String, String> rowMapping;

        SQLRow(int columns) {
            rowMapping = new HashMap<>(columns);
        }

        void setColumnValue(String cName, String cValue) {
            rowMapping.put(cName, cValue);
        }

        public String getColumnValue(String column) {
            return rowMapping.get(column);
        }


        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < numberOfColumns; i++) {
                if (i > 0)
                    result.append(", ");
                result.append(columnNames[i]).append("=").append(rowMapping.get(columnNames[i]));
            }
            return result.toString();
        }


    }


}
