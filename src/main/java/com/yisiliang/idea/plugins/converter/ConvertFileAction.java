package com.yisiliang.idea.plugins.converter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

public class ConvertFileAction extends AnAction {
    private static final Logger LOGGER = Logger.getInstance(ConvertFileAction.class);

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        VirtualFile virtualFile = event.getData(CommonDataKeys.VIRTUAL_FILE);
        if (virtualFile == null) {
            LOGGER.warn("current virtualFile is null, return.");
            return;
        }
        Project project = event.getProject();
        if (project == null) {
            LOGGER.warn("current project is null, return.");
            return;
        }

        if (virtualFile.isWritable() && virtualFile.isInLocalFileSystem() && virtualFile.getCanonicalPath() != null) {
            Set<String> fileTypes = new HashSet<>();
            loopForFileType(virtualFile, fileTypes);
            if (fileTypes.isEmpty()) {
                LOGGER.warn("file types in " + virtualFile + " is empty.");
                Messages.showWarningDialog("There is no valid file to convert.", ConverterConstants.MESSAGE_TITLE);
                return;
            }
            FileTypeSelectorDialog fileTypeSelectorDialog = new FileTypeSelectorDialog(fileTypes);
            boolean ok = fileTypeSelectorDialog.showAndGet();
            if (ok) {
                final Charset charset = fileTypeSelectorDialog.getSelectedCharset();
                final Set<String> convertedFileTypes = fileTypeSelectorDialog.getSelectedFileTypes();
                if (charset == null || convertedFileTypes.isEmpty()) {
                    Messages.showWarningDialog("Please selected at least one file type.", ConverterConstants.MESSAGE_TITLE);
                    return;
                }
                Application application = ApplicationManager.getApplication();
                FileEditorManager editorManager = FileEditorManager.getInstance(project);
                application.runWriteAction(() -> convertFile(editorManager, virtualFile, charset, convertedFileTypes));
            }
        } else {
            Messages.showWarningDialog("Current file [" + virtualFile.getName() + "] is not writable.", ConverterConstants.MESSAGE_TITLE);
        }
    }

    private static void loopForFileType(VirtualFile virtualFile, Set<String> fileTypes) {
        if (virtualFile.isDirectory()) {
            VirtualFile[] children = virtualFile.getChildren();
            for (VirtualFile child : children) {
                loopForFileType(child, fileTypes);
            }
        } else if (virtualFile.isWritable()) {
            String extension = virtualFile.getExtension();
            if (extension != null && !extension.isBlank()) {
                fileTypes.add(extension.toLowerCase());
            }
        }
    }

    private static void convertFile(FileEditorManager editorManager, VirtualFile virtualFile, Charset charset, Set<String> convertFileTypes) {
        if (virtualFile.isDirectory()) {
            VirtualFile[] children = virtualFile.getChildren();
            for (VirtualFile child : children) {
                convertFile(editorManager, child, charset, convertFileTypes);
            }
        } else {
            BytesEncodingDetect bytesEncodingDetect = new BytesEncodingDetect();
            if (virtualFile.isWritable()
                    && virtualFile.getExtension() != null
                    && convertFileTypes.contains(virtualFile.getExtension().toLowerCase())) {
                try {
                    byte[] oriBytes = virtualFile.contentsToByteArray(false);
                    Charset detectEncoding = bytesEncodingDetect.detectEncoding(oriBytes);
                    if (!charset.equals(detectEncoding)) {
                        LOGGER.info("convert " + virtualFile + " from " + detectEncoding + " to " + charset);
                        byte[] convertBytes = ConvertUtils.convert(detectEncoding, charset, oriBytes);
                        virtualFile.setCharset(charset);
                        virtualFile.setBinaryContent(convertBytes);
                        VfsUtil.markDirtyAndRefresh(false, false, false, virtualFile);
                        editorManager.updateFilePresentation(virtualFile);
                    }
                } catch (IOException e) {
                    LOGGER.error("convert " + virtualFile + " failed", e);
                }
            }
        }
    }
}
