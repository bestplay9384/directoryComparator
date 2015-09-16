package pl.fc.dircomparator;

import org.junit.Test;
import pl.fc.dircomparator.entries.AbstractFileSystem;
import pl.fc.dircomparator.util.FileDetailedEntry;
import pl.fc.dircomparator.util.FileSystemEntry;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class DirectoryToCompareMockTest {

    AbstractFileSystem config = new AbstractFileSystem();
    FileSystemEntry testee = config;

    private static final byte[] SAMPLE_BIN_CONTENT = new byte[] {1,2,3};

    @Test
    public void rootShouldBeDirectory() throws Exception {
        assertTrue(testee.isDirectory());
    }

    @Test
    public void shouldListDirContent() throws Exception {
        config.addDir("somedir", FileDetailedEntry.Attribute.VISIBLE);
        config.addFile("somefile.txt", SAMPLE_BIN_CONTENT, FileDetailedEntry.Attribute.VISIBLE);

        List<FileDetailedEntry> dirList = testee.listFiles();
        assertEquals(2, dirList.size());
        Iterator<FileDetailedEntry> iterator = dirList.iterator();
        while (iterator.hasNext()) {
            FileDetailedEntry next = iterator.next();
            if (next.isDirectory()) {
                assertEquals("somedir", next.getName());
            } else if (next.isFile()) {
                assertEquals("somefile.txt", next.getName());
            } else {
                throw new IllegalStateException("unknown type?: "+next);
            }
        }
    }

    @Test
    public void shouldAllowAddingDirectoryInRoot() throws Exception {
        config.addDir("somedir", FileDetailedEntry.Attribute.VISIBLE);

        List<FileDetailedEntry> listFiles = testee.listFiles();
        assertEquals(1, listFiles.size());
        FileDetailedEntry info = listFiles.get(0);
        assertEquals("somedir", info.getName());
        assertTrue(info.isDirectory());
    }

    @Test
    public void shouldAllowAddingFileInRoot() throws Exception {
        config.addFile("somefile.txt", SAMPLE_BIN_CONTENT, FileDetailedEntry.Attribute.VISIBLE);

        List<FileDetailedEntry> listFiles = testee.listFiles();
        assertEquals(1, listFiles.size());
        FileDetailedEntry info = listFiles.get(0);
        assertEquals("somefile.txt", info.getName());
        assertTrue(info.isFile());
    }

    @Test
    public void shouldHideHiddenFile() throws Exception {
        config.addFile("somefile.txt", SAMPLE_BIN_CONTENT, FileDetailedEntry.Attribute.HIDDEN);
        //config.setAttributes("somefile.txt", FileToCompareAttribute.HIDDEN);

        List<FileDetailedEntry> listFiles = testee.listFiles();
        assertTrue(listFiles.isEmpty());
    }

    @Test
    public void shouldReturnFileContent() throws Exception {
        config.addFile("somefile.txt", SAMPLE_BIN_CONTENT, FileDetailedEntry.Attribute.VISIBLE);

        assertFalse(testee.listFiles().isEmpty());
        FileDetailedEntry file = testee.listFiles().get(0);
        assertTrue(file.isFile());
        assertEquals(SAMPLE_BIN_CONTENT, file.getContent());
        /*assertEquals(SAMPLE_BIN_CONTENT, null);
        // There is no way to fetch content
        // CZEMU???
        */
    }

    @Test
    public void shouldGiveAbsolutePathInRoot() throws Exception {
        config.addFile("somefile.txt", SAMPLE_BIN_CONTENT, FileDetailedEntry.Attribute.VISIBLE);

        assertFalse(testee.listFiles().isEmpty());
        FileDetailedEntry file = testee.listFiles().get(0);
        assertTrue(file.isFile());
        assertEquals("somefile.txt", file.getAbsolutePath());
    }

    @Test
    public void shouldGiveAbsolutePathInSubdir() throws Exception {
        config.addFile("somedir/somefile.txt", SAMPLE_BIN_CONTENT, FileDetailedEntry.Attribute.VISIBLE);

        assertFalse(testee.listFiles().isEmpty());
        FileDetailedEntry file = testee.listFiles().get(0);
        assertTrue(file.isFile());
        assertEquals("somedir/somefile.txt", file.getAbsolutePath());
    }

}
