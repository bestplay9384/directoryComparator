package pl.fc.dircomparator.util;

import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * File information
 */
public class FileDetailedEntry implements Comparable<FileDetailedEntry> {

    public String name;
    public String shortPath;
    public String path;
    public byte[] content;
    public final Type type;
    public long length;
    public final Attribute attribute;

    public enum Type {
        FILE, DIR;
    }

    public enum Attribute {
        VISIBLE, HIDDEN;
    }

    public static FileDetailedEntry createDir(String path_, String shortpath_, FileDetailedEntry.Type type_, FileDetailedEntry.Attribute attribute_) {
        return new FileDetailedEntry(path_, shortpath_, null, type_, attribute_);
    }

    public static FileDetailedEntry createFile(String path_, String shortpath_, byte[] content_, FileDetailedEntry.Type type_, FileDetailedEntry.Attribute attribute_) {
        return new FileDetailedEntry(path_, shortpath_, content_, type_, attribute_);
    }

    FileDetailedEntry(String path_, String shortpath_, byte[] content_, FileDetailedEntry.Type type_, FileDetailedEntry.Attribute attribute_) {
        this.path = path_;
        this.shortPath = shortpath_;
        int idx = this.path.replaceAll("\\\\", "/").lastIndexOf("/");
        this.name = idx >= 0 ? path.substring(idx + 1) : path;

        this.content = content_;
        this.type = type_;

        if(type_ == Type.DIR) {
            this.length = 0;
        } else {
            if(this.content != null)
                this.length = content_.length;
            else
                this.length = new File(this.path).length();
        }
        this.attribute = attribute_;
    }

    /**
     * Method gets byte content of real file.
     */
    private byte[] file2bytes(File f) {
        InputStream file = null;
        byte[] bytes = null;
        try {
            file = new FileInputStream(f);
            bytes = IOUtils.toByteArray(file);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * Check if pl.fc.dircomparator.util.FileDetailedEntry object is a directory
     */
    public boolean isDirectory() {
        return (this.type == Type.DIR) ? true : false;
    }

    /**
     * Check if pl.fc.dircomparator.util.FileDetailedEntry object is a File
     */
    public boolean isFile() {
        return !isDirectory();
    }

    /**
     * Gets absolute path of pl.fc.dircomparator.util.FileDetailedEntry object
     */
    public String getAbsolutePath() {
        return this.path;
    }

    /**
     * Gets name of pl.fc.dircomparator.util.FileDetailedEntry object
     */
    public String getName() {
        return this.name;
    }

    /**
     * Checks if pl.fc.dircomparator.util.FileDetailedEntry object is Hidden
     */
    public boolean isHidden() {
        return (this.attribute == Attribute.HIDDEN ? true : false);
    }

    /**
     * Gets byte content of pl.fc.dircomparator.util.FileDetailedEntry object
     */
    public byte[] getContent() {
        if(this.type == Type.FILE && this.content == null)
            this.content = file2bytes(new File(this.path));
        return this.content;
    }

    /**
     * Method gets size of a file
     */
    public long getSize() {
        return this.length;
    }

    /**
     * Comparer for pl.fc.dircomparator.util.FileDetailedEntry object. For sorting.
     */
    public int compareTo(FileDetailedEntry other) {
        return this.path.compareTo(other.path);
    }
}
