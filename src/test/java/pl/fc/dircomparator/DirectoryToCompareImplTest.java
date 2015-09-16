package pl.fc.dircomparator;

import org.junit.Assert;
import org.junit.Test;
import pl.fc.dircomparator.compare.CompareDirectoriesImpl;

/**
 * Created by Bartek Kielak on 2015-08-24.
 */
public class DirectoryToCompareImplTest {

    private final CompareDirectoriesImpl instance = new CompareDirectoriesImpl();

    @Test
    public void shouldTestIt() {
        String f1 = "src/test/resources/dir1";
        String f2 = "src/test/resources/dir2";
        boolean test = instance.dirEquals(f1, f2);
        Assert.assertFalse(test);
    }
}
