package pl.fc.dircomparator.util;

import java.util.List;

/**
 * Operations in filesystem used to compare files
 */
public interface FileSystemEntry {
    boolean isDirectory();
    boolean isFile();
    FileDetailedEntry getFile(String filename);
    String getAbsolutePath();
    List<String> getDirectoryMap();
    List<String> getDirectoryFiles();
    List<FileDetailedEntry> listFiles();
}
