package pl.fc.dircomparator.entries;

import pl.fc.dircomparator.util.FileDetailedEntry;
import pl.fc.dircomparator.util.FileSystemEntry;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Bartek Kielak on 2015-08-24.
 * @author b.kielak.fc@gmail.com
 */
public class FileSystem implements FileSystemEntry {

    private Path path = null;
    public List<FileDetailedEntry> files;

    public FileSystem(String dir) {
        try {
            path = Paths.get(dir);
            files = new ArrayList<FileDetailedEntry>();

            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
                    if(Files.isDirectory(filePath))
                        files.add(FileDetailedEntry.createDir(filePath.toAbsolutePath().toString(), getFilePath(filePath.toAbsolutePath().toString()), FileDetailedEntry.Type.DIR, Files.isHidden(filePath) ? FileDetailedEntry.Attribute.HIDDEN : FileDetailedEntry.Attribute.VISIBLE));
                    else
                        files.add(FileDetailedEntry.createFile(filePath.toAbsolutePath().toString(), getFilePath(filePath.toAbsolutePath().toString()), null, FileDetailedEntry.Type.FILE, Files.isHidden(filePath) ? FileDetailedEntry.Attribute.HIDDEN : FileDetailedEntry.Attribute.VISIBLE));
                    return FileVisitResult.CONTINUE;
                }
            });

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
     }

    public String getFilePath(String fullPath) {
        if(fullPath.startsWith(path.toAbsolutePath().toString())) {
            return fullPath.substring(path.toAbsolutePath().toString().length()+1);
        } else {
            return fullPath;
        }
    }

    @Override
    public boolean isDirectory() {
        if(path != null && Files.exists(path) && Files.isDirectory(path))
            return true;
        else
            return false;
    }

    @Override
    public boolean isFile() {
        if(path != null && Files.exists(path) && !Files.isDirectory(path))
            return true;
        else
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
        return path.toAbsolutePath().toString();
    }

    @Override
    public List<String> getDirectoryMap() {
        List<String> paths = new ArrayList<String>();
        for(FileDetailedEntry x: files) {
            paths.add(x.shortPath);
        }
        Collections.sort(paths);
        return paths;
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
