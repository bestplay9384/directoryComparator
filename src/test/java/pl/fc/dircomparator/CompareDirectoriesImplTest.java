package pl.fc.dircomparator;

import org.junit.Test;
import pl.fc.dircomparator.compare.CompareDirectoriesImpl;
import pl.fc.dircomparator.entries.AbstractFileSystem;
import pl.fc.dircomparator.util.FileDetailedEntry;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CompareDirectoriesImplTest {

    private final CompareDirectoriesImpl instance = new CompareDirectoriesImpl();
    byte[] trescPrzykladowa1 = new byte[] {1, 2, 3};
    byte[] trescPrzykladowa2 = new byte[] {1, 2, 3, 4};

    @Test
    public void shouldDetectDirsAreNotEqual() throws Exception {

        AbstractFileSystem f1 = new AbstractFileSystem();
        f1.addDir("testk", FileDetailedEntry.Attribute.VISIBLE);
        f1.addFile("testk/plik.txt", trescPrzykladowa1, FileDetailedEntry.Attribute.VISIBLE);
        f1.addFile("testk/plik2.txt", trescPrzykladowa1, FileDetailedEntry.Attribute.VISIBLE);
        f1.addDir("dwa", FileDetailedEntry.Attribute.VISIBLE);
        f1.addFile("dwa/plik.txt", trescPrzykladowa1, FileDetailedEntry.Attribute.VISIBLE);

        AbstractFileSystem f2 = new AbstractFileSystem();
        f2.addDir("testk", FileDetailedEntry.Attribute.VISIBLE);
        f2.addFile("testk/plik.txt", trescPrzykladowa1, FileDetailedEntry.Attribute.VISIBLE);
        f2.addFile("testk/plik2.txt", trescPrzykladowa1, FileDetailedEntry.Attribute.VISIBLE);
        f2.addDir("dwa", FileDetailedEntry.Attribute.VISIBLE);
        f2.addFile("dwa/plik.txt", trescPrzykladowa1, FileDetailedEntry.Attribute.VISIBLE);
        f2.addDir("dwa/trzy", FileDetailedEntry.Attribute.VISIBLE);

        boolean test = instance.dirEqualsInternal(f1, f2);
        assertFalse(test);
    }

    @Test
    public void shouldDetectDirsAreEqual() {

        AbstractFileSystem f1 = new AbstractFileSystem();
        f1.addDir("testk", FileDetailedEntry.Attribute.VISIBLE);
        f1.addFile("testk/plik.txt", trescPrzykladowa1, FileDetailedEntry.Attribute.VISIBLE);
        f1.addFile("testk/plik2.txt", trescPrzykladowa1, FileDetailedEntry.Attribute.VISIBLE);
        f1.addDir("dwa", FileDetailedEntry.Attribute.VISIBLE);
        f1.addFile("dwa/plik.txt", trescPrzykladowa2, FileDetailedEntry.Attribute.VISIBLE);

        AbstractFileSystem f2 = new AbstractFileSystem();
        f2.addDir("testk", FileDetailedEntry.Attribute.VISIBLE);
        f2.addFile("testk/plik.txt", trescPrzykladowa1, FileDetailedEntry.Attribute.VISIBLE);
        f2.addFile("testk/plik2.txt", trescPrzykladowa1, FileDetailedEntry.Attribute.VISIBLE);
        f2.addDir("dwa", FileDetailedEntry.Attribute.VISIBLE);
        f2.addFile("dwa/plik.txt", trescPrzykladowa2, FileDetailedEntry.Attribute.VISIBLE);

        boolean test = instance.dirEqualsInternal(f1, f2);
        assertTrue(test);
    }

    @Test
    public void shouldIgnoreInvisibleFiles() throws Exception {
        AbstractFileSystem f1 = new AbstractFileSystem();
        f1.addFile("plik.txt", trescPrzykladowa2, FileDetailedEntry.Attribute.VISIBLE);

        AbstractFileSystem f2 = new AbstractFileSystem();
        f2.addFile("plik.txt", trescPrzykladowa2, FileDetailedEntry.Attribute.HIDDEN);

        assertFalse(instance.dirEqualsInternal(f1, f2));
    }

    @Test
    public void shouldIgnoreInvisibleFilesInSubdirs() throws Exception {
        AbstractFileSystem f1 = new AbstractFileSystem();
        f1.addFile("abc/plik.txt", trescPrzykladowa2, FileDetailedEntry.Attribute.VISIBLE);

        AbstractFileSystem f2 = new AbstractFileSystem();
        f2.addFile("abc/plik.txt", trescPrzykladowa2, FileDetailedEntry.Attribute.HIDDEN);

        assertFalse(instance.dirEqualsInternal(f1, f2));
    }

    @Test
    public void shouldIgnoreInvisibleDirs() throws Exception {
        AbstractFileSystem f1 = new AbstractFileSystem();
        f1.addFile("abc/plik.txt", trescPrzykladowa2, FileDetailedEntry.Attribute.VISIBLE);
        f1.addDir("testk", FileDetailedEntry.Attribute.VISIBLE);

        AbstractFileSystem f2 = new AbstractFileSystem();
        f2.addDir("testk", FileDetailedEntry.Attribute.HIDDEN);
        f2.addFile("abc/plik.txt", trescPrzykladowa2, FileDetailedEntry.Attribute.VISIBLE);

        assertFalse(instance.dirEqualsInternal(f1, f2));
    }

    @Test
    public void shouldCompareFileContent() throws Exception {
        AbstractFileSystem f1 = new AbstractFileSystem();
        f1.addFile("plik.txt", trescPrzykladowa1, FileDetailedEntry.Attribute.VISIBLE);

        AbstractFileSystem f2 = new AbstractFileSystem();
        f2.addFile("plik.txt", trescPrzykladowa2, FileDetailedEntry.Attribute.VISIBLE);

        assertFalse(instance.dirEqualsInternal(f1, f2));
    }


}
