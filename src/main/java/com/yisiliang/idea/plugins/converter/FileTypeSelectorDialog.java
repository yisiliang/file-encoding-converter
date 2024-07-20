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
    private final List<JCheckBox> fileTypeCheckBoxList;
    private final JCheckBox changeCharsetInFileContentCheckBox;
    private final boolean showChangeCharsetInFileContentCheckBox;
    private final ComboBox<Charset> charsetJComboBox;

    public FileTypeSelectorDialog(Set<String> fileTypes) {
        super(true);
        changeCharsetInFileContentCheckBox = new JCheckBox(ConvertI18nUtils.getI18NValue(ConverterConstants.I18N_REPLACE_CHARSET_KEY));
        changeCharsetInFileContentCheckBox.setSelected(false);
        fileTypeCheckBoxList = new ArrayList<>();
        for (String fileType : fileTypes) {
            JCheckBox checkBox = new JCheckBox(fileType);
            checkBox.setSelected(ConverterConstants.DEFAULT_SELECTED_FILE_TYPES.contains(fileType));
            fileTypeCheckBoxList.add(checkBox);
        }
        showChangeCharsetInFileContentCheckBox = showChangeCharsetInFileContentCheckBox(fileTypes);
        charsetJComboBox = new ComboBox<>(ConverterConstants.SUPPORT_CHARSET_ARRAY);
        setTitle(ConverterConstants.MESSAGE_TITLE);
        init();

        setSize(400, 300);
        pack();
    }

    private boolean showChangeCharsetInFileContentCheckBox(Set<String> fileTypes) {
        for (String contentReplaceFileType : ConverterConstants.CONTENT_REPLACE_FILE_TYPES) {
            if (fileTypes.contains(contentReplaceFileType)) {
                return true;
            }
        }
        return false;
    }

    public Charset getSelectedCharset() {
        return (Charset) charsetJComboBox.getSelectedItem();
    }

    public boolean getChangeCharsetInFileContent() {
        return changeCharsetInFileContentCheckBox.isSelected();
    }

    public Set<String> getSelectedFileTypes() {
        Set<String> fileTypes = new HashSet<>();
        for (JCheckBox checkBox : fileTypeCheckBoxList) {
            if (checkBox.isSelected()) {
                fileTypes.add(checkBox.getText());
            }
        }
        return fileTypes;
    }


    @Override
    protected @Nullable JComponent createCenterPanel() {
        int rows = fileTypeCheckBoxList.size() + 1 + 1 + 1 + 1 + 1;
        if (showChangeCharsetInFileContentCheckBox) {
            rows = rows + 1;
        }
        JPanel panel = new JPanel(new GridLayout(rows, 3, 5, 5));
        GridBagConstraints labelConstraints = new GridBagConstraints();
        int currentRow = 0;
        labelConstraints.gridx = currentRow;
        labelConstraints.gridy = 0;
        labelConstraints.fill = GridBagConstraints.BOTH;
        panel.add(new JLabel(ConvertI18nUtils.getI18NValue(ConverterConstants.I18N_SELECT_FILE_TYPES_KEY)), labelConstraints);
        currentRow++;
        for (JCheckBox checkBox : fileTypeCheckBoxList) {
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
        panel.add(new JLabel(ConvertI18nUtils.getI18NValue(ConverterConstants.I18N_SELECT_TARGET_CHARSET_KEY)), charsetLabelConstraints);
        currentRow++;

        GridBagConstraints comboBoxConstraints = new GridBagConstraints();
        comboBoxConstraints.gridx = currentRow;
        comboBoxConstraints.gridy = 0;
        comboBoxConstraints.fill = GridBagConstraints.BOTH;
        panel.add(charsetJComboBox, comboBoxConstraints);
        currentRow++;

        if (showChangeCharsetInFileContentCheckBox) {
            GridBagConstraints changeCharsetInFileContentCheckBoxBoxConstraints = new GridBagConstraints();
            changeCharsetInFileContentCheckBoxBoxConstraints.gridx = currentRow;
            changeCharsetInFileContentCheckBoxBoxConstraints.gridy = 0;
            changeCharsetInFileContentCheckBoxBoxConstraints.fill = GridBagConstraints.BOTH;
            panel.add(changeCharsetInFileContentCheckBox, changeCharsetInFileContentCheckBoxBoxConstraints);
        }
        pack();
        return panel;
    }
}
