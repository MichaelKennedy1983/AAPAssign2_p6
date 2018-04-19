package edu.udc;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class HolderCellEditor extends DefaultCellEditor {
    private Border padding;

    public HolderCellEditor (final JTextField textField, int horizontalPadding) {
        super(textField);
        padding = BorderFactory.createEmptyBorder(0, horizontalPadding, 0, horizontalPadding);

        editorComponent.setAlignmentX(JTextField.CENTER_ALIGNMENT);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        super.getTableCellEditorComponent(table, value, isSelected, row, column);

        editorComponent.setBorder(BorderFactory
                .createCompoundBorder(editorComponent.getBorder(), padding));

        return editorComponent;
    }
}
