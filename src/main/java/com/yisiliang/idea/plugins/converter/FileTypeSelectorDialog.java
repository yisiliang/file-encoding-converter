package com.yisiliang.idea.plugins.converter;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileTypeSelectorDialog extends DialogWrapper {
    private final List<JCheckBox> checkBoxList;
    private final ComboBox<Charset> charsetJComboBox;

    public FileTypeSelectorDialog(Set<String> fileTypes) {
        super(true);
        checkBoxList = new ArrayList<>();
        for (String fileType : fileTypes) {
            JCheckBox checkBox = new JCheckBox(fileType);
            if (ConverterConstants.DEFAULT_SELECTED_FILE_TYPES.contains(fileType)) {
                checkBox.setSelected(true);
            } else {
                checkBox.setSelected(false);
            }
            checkBoxList.add(checkBox);
        }
        charsetJComboBox = new ComboBox<>(ConverterConstants.SUPPORT_CHARSET_ARRAY);
        setTitle(ConverterConstants.MESSAGE_TITLE);
        init();

        setSize(400, 300);
        pack();
    }

    public Charset getSelectedCharset() {
        return (Charset) charsetJComboBox.getSelectedItem();
    }

    public Set<String> getSelectedFileTypes() {
        Set<String> fileTypes = new HashSet<>();
        for (JCheckBox checkBox : checkBoxList) {
            if (checkBox.isSelected()) {
                fileTypes.add(checkBox.getText());
            }
        }
        return fileTypes;
    }


    @Override
    protected @Nullable JComponent createCenterPanel() {
        int rows = checkBoxList.size() + 1 + 1 + 1 + 1;
        JPanel panel = new JPanel(new GridLayout(rows, 3, 5, 5));
        GridBagConstraints labelConstraints = new GridBagConstraints();
        int currentRow = 0;
        labelConstraints.gridx = currentRow;
        labelConstraints.gridy = 0;
        labelConstraints.fill = GridBagConstraints.BOTH;
        panel.add(new JLabel("Please select file types:"), labelConstraints);
        currentRow++;
        for (JCheckBox checkBox : checkBoxList) {
            GridBagConstraints checkBoxConstraints = new GridBagConstraints();
            checkBoxConstraints.gridx = currentRow;
            checkBoxConstraints.gridy = 0;
            labelConstraints.fill = GridBagConstraints.BOTH;
            panel.add(checkBox, checkBoxConstraints);
            currentRow++;
        }

        GridBagConstraints charsetLabelConstraints = new GridBagConstraints();
        charsetLabelConstraints.gridx = currentRow;
        charsetLabelConstraints.gridy = 0;
        charsetLabelConstraints.fill = GridBagConstraints.BOTH;
        panel.add(new JLabel("Please select target charset:"), charsetLabelConstraints);
        currentRow++;

        GridBagConstraints comboBoxConstraints = new GridBagConstraints();
        comboBoxConstraints.gridx = currentRow;
        comboBoxConstraints.gridy = 0;
        comboBoxConstraints.fill = GridBagConstraints.BOTH;
        panel.add(charsetJComboBox, comboBoxConstraints);
        pack();
        return panel;
    }
}
