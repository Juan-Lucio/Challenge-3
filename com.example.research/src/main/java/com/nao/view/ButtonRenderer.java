package com.nao.view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Simple button renderer.
 */
public class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() { setOpaque(true); }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        setText(value == null ? "Add" : value.toString());
        return this;
    }
}
