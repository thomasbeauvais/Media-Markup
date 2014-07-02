package org.branch.common.utils;

import org.branch.common.UncheckedException;

import java.io.File;
import java.io.IOException;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 25.05.14
 */
public class FileUtils extends org.apache.commons.io.FileUtils
{

    public static String getCanonicalPath(File file)
    {
        try
        {
            return file.getCanonicalPath();
        }
        catch (IOException e)
        {
            throw new UncheckedException(e);
        }
    }
}
