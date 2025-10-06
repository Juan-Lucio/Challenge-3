package com.nao.view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Renderer that uses JTextArea to allow wrapping of long text in a JTable cell.
 * Use for Summary and Keywords columns.
 */
public class TextAreaRenderer extends JTextArea implements TableCellRenderer {

    public TextAreaRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        setText(value == null ? "" : value.toString());
        setFont(table.getFont());
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
        // Adjust row height to fit content (best-effort)
        int prefHeight = getPreferredSize().height;
        if (table.getRowHeight(row) != prefHeight) {
            table.setRowHeight(row, Math.max(table.getRowHeight(row), prefHeight));
        }
        return this;
    }
}
