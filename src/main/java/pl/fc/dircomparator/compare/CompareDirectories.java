package pl.fc.dircomparator.compare;

/**
 * Umiejętność porównywania zawartość dwóch folderów.
 */
public interface CompareDirectories {
    public Boolean dirEquals(String dir1, String dir2);
}
