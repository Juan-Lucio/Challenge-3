package com.nao.view;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Button editor that reports clicks with the model row index.
 */
public class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

    private final JButton button = new JButton();
    private final ButtonClickListener listener;
    private int modelRow;

    public interface ButtonClickListener { void onClick(int modelRow); }

    public ButtonEditor(ButtonClickListener listener) {
        this.listener = listener;
        button.addActionListener(this);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int viewRow, int viewCol) {
        modelRow = table.convertRowIndexToModel(viewRow);
        button.setText(value == null ? "Add" : value.toString());
        return button;
    }

    @Override
    public Object getCellEditorValue() { return button.getText(); }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (listener != null) listener.onClick(modelRow);
        fireEditingStopped();
    }
}
