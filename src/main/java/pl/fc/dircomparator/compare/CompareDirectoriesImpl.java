package pl.fc.dircomparator.compare;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.fc.dircomparator.util.FileDetailedEntry;
import pl.fc.dircomparator.util.FileSystemEntry;
import pl.fc.dircomparator.entries.FileSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Bartek Kielak on 2015-08-21.
 * @author b.kielak.fc@gmail.com
 */
public class CompareDirectoriesImpl implements CompareDirectories {

    private static final Logger LOGGER = LogManager.getLogger(CompareDirectoriesImpl.class);
    private String error;

    @Override
    public Boolean dirEquals(String dir1, String dir2) {
        return dirEqualsInternal(new FileSystem(dir1), new FileSystem(dir2));
    }

    /**
     * Method compares two dirs by its name structure, file map and files content.
     */
    public Boolean dirEqualsInternal(FileSystemEntry dirA, FileSystemEntry dirB) {
        assertValidDir(dirA);
        assertValidDir(dirB);
        List<String> alistA = dirA.getDirectoryMap();
        List<String> alistB = dirB.getDirectoryMap();
        if (areListsEqual(alistA, alistB)) {
            List<String> filesA = dirA.getDirectoryFiles();
            List<String> filesB = dirB.getDirectoryFiles();
            LOGGER.debug("Directories " + dirA.getAbsolutePath() + " and " + dirB.getAbsolutePath() + " DO have equal filemap.");
            for (int i = 0; i < filesA.size(); i++) {
                FileDetailedEntry file1 = dirA.getFile(filesA.get(i));
                FileDetailedEntry file2 = dirB.getFile(filesB.get(i));
                if(file1.getSize() != file2.getSize()) {
                    if (!Arrays.equals(dirA.getFile(filesA.get(i)).getContent(), dirB.getFile(filesB.get(i)).getContent())) {
                        LOGGER.debug("Files " + dirA.getFile(filesA.get(i)) + " and " + dirB.getFile(filesB.get(i)) + " are DIFFERENT");
                        return false;
                    }
                } else {
                    LOGGER.debug("Files " + dirA.getFile(filesA.get(i)).getAbsolutePath() + " and " + dirB.getFile(filesB.get(i)).getAbsolutePath() + " are IDENTICAL");
                }
            }
        } else {
            LOGGER.debug("Directories " + dirA.getAbsolutePath() + " and " + dirB.getAbsolutePath() + " are NOT equal!");
            return false;
        }

        return true;
    }

    /**
     * Method checks if directory path is valid, whether it exists.
     */
    private void assertValidDir(FileSystemEntry directoryToValidate) {
        if (directoryToValidate==null || !directoryToValidate.isDirectory()) {
            error = "Dirname with path " + directoryToValidate + " does not exist!";
            LOGGER.error(error);
            throw new IllegalStateException(error);
        }
    }

    /**
     * Method checks if two String lists are equal (the same size, structure, sorting).
     */
    private static boolean areListsEqual(List<String> list1, List<String> list2) {
        List<String> cp = new ArrayList<String>(list1);
        for (String o : list2) {
            if (!cp.remove(o)) {
                return false;
            }
        }
        return cp.isEmpty();
    }

}
