package pl.fc.dircomparator.compare;

public class CompareDirectoriesFactory {
    public static CompareDirectories createCompareFile() {
        return new CompareDirectoriesImpl();
    }
}
