package reflection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

public class TableUtil {
    /**
     * Populates a JTable with data from a list of objects.
     * Each object in the list corresponds to a row in the table.
     * Each field in the object corresponds to a column in the table.
     * @param table The JTable to populate.
     * @param list The list of objects containing the data.
     * @param <T> The type of the objects in the list.
     */
    public static <T> void populateTable(JTable table, List<T> list) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        if (!list.isEmpty()) {
            Class<?> clazz = list.get(0).getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                model.addColumn(fieldName);
            }

            for (T t : list) {
                Object[] rowData = new Object[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    field.setAccessible(true);
                    try {
                        rowData[i] = field.get(t);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                model.addRow(rowData);
            }
        }
    }
}