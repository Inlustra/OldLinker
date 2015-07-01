/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thenairn.linker.ui.config;

import com.thenairn.linker.entity.KeyData;
import com.thenairn.linker.entity.Shortcut;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Tom
 */
public class ShortcutTableModel extends AbstractTableModel implements TableCellRenderer {

    private final List<Shortcut> shortcuts = new ArrayList<>();

    private final ShortcutFieldCellEditor editor;

    public ShortcutTableModel() {
        editor = new ShortcutFieldCellEditor();
    }

    public ShortcutFieldCellEditor getEditor() {
        return editor;
    }

    public void addShortcut(Shortcut shortcut) {
        shortcuts.add(shortcut);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return shortcuts.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 1:
                return "Shortcut";
            case 2:
                return "Default";
            case 0:
            default:
                return "Name";
        }//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 1:
                return true;
            case 0:
            case 2:
            default:
                return false;
        }  //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Shortcut shortcut = shortcuts.get(rowIndex);
        switch (columnIndex) {
            case 1:
                return shortcut.getData();
            case 2:
                return shortcut.getDefaultData();
            case 0:
            default:
                return shortcut.getName();
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Shortcut shortcut = (Shortcut) value;
        switch (column) {
            case 1:
                return new ShortcutField(shortcut.getData());
            case 2:
                return new ShortcutField(shortcut.getDefaultData());
            case 0:
            default:
                return new JLabel(shortcut.getName());
        }
    }

    public class ShortcutFieldCellEditor extends AbstractCellEditor implements TableCellEditor {

        ShortcutField field = new ShortcutField(); 
        int rowSelected;

        @Override
        public Object getCellEditorValue() {
            return field.getData();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            KeyData shortcut = (KeyData) value;
            rowSelected = row;
            return field = new ShortcutField(shortcut);
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
            if (field.getData() == null) {
                return;
            }
            if (!field.getData().isValidShortcut()) {
                return;
            }
            System.out.println("SET");
            shortcuts.get(rowSelected).setData(field.getData());
        }
        
        
        

    }
}
