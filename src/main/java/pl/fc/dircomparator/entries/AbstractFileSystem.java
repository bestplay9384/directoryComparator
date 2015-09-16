package pl.fc.dircomparator.entries;

import pl.fc.dircomparator.util.FileDetailedEntry;
import pl.fc.dircomparator.util.FileSystemEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Bartek Kielak on 2015-08-24.
 * @author b.kielak.fc@gmail.com
 */
public class AbstractFileSystem implements FileSystemEntry {

    public List<FileDetailedEntry> files;

    public AbstractFileSystem() {
        files = new ArrayList<FileDetailedEntry>();
    }

    public void addDir(String fullPath, FileDetailedEntry.Attribute attribute_) {
        files.add(FileDetailedEntry.createDir(fullPath, fullPath, FileDetailedEntry.Type.DIR, attribute_));
    }

    public void addFile(String fullPath, byte[] binaryContent, FileDetailedEntry.Attribute attribute_) {
        files.add(FileDetailedEntry.createFile(fullPath, fullPath, binaryContent, FileDetailedEntry.Type.FILE, attribute_));
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public boolean isFile() {
        return false;
    }

    @Override
    public FileDetailedEntry getFile(String filename) {
        for(FileDetailedEntry x: files) {
            if(x.getAbsolutePath().equals(filename)) {
                return x;
            }
        }
        return null;
    }

    @Override
    public String getAbsolutePath() {
        return "";
    }

    @Override
    public List<String> getDirectoryMap() {
        List<String> map = new ArrayList<String>();
        for(FileDetailedEntry f: files) {
            if(!f.isHidden()) {
                map.add(f.getAbsolutePath());
            }
        }
        Collections.sort(map);
        return map;
    }

    @Override
    public List<String> getDirectoryFiles() {
        List<String> map = new ArrayList<String>();
        for(FileDetailedEntry f: files) {
            if(!f.isDirectory()) {
                map.add(f.getAbsolutePath());
            }
        }
        Collections.sort(map);
        return map;
    }

    @Override
    public List<FileDetailedEntry> listFiles() {
        List<FileDetailedEntry> map = new ArrayList<FileDetailedEntry>();
        for(FileDetailedEntry f: files) {
            if(!f.isHidden()) {
                map.add(f);
            }
        }
        Collections.sort(map);
        return map;
    }
}