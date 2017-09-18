package ch.internettechnik.anouman.tests.unit;

import ch.internettechnik.anouman.util.EsrTool;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by michzuerch on 06.10.15.
 */
public class EsrToolTestIsPlzSwitzerlandTest {
    EsrTool tool = new EsrTool();

    @Test
    public void testIsPlzSwitzerland() {
        assertTrue("testIsPlzSwitzerland", tool.isPlzSwitzerland("8008"));
        assertTrue("testIsPlzSwitzerland", tool.isPlzSwitzerland("1000"));
        assertFalse("testIsPlzSwitzerland", tool.isPlzSwitzerland("A-8008"));
        assertFalse("testIsPlzSwitzerland", tool.isPlzSwitzerland("GAS7"));
        assertFalse("testIsPlzSwitzerland", tool.isPlzSwitzerland("23451"));
    }


}
