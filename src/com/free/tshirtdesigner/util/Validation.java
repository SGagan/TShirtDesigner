package com.free.tshirtdesigner.util;

import java.util.regex.Pattern;

/**
 * User: Dell
 * Date: 2/16/14
 * Time: 12:31 AM
 */
public class Validation
{
    private static final Pattern EMAIL_PATTERN = Pattern
            .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                    + "[a-zA-Z][a-zA-Z\\-]{0,25}" + ")+");

    /**
     * @param target target
     * @return boolean
     */
    public static final boolean isValidEmail(CharSequence target)
    {
        return EMAIL_PATTERN.matcher(target).matches();
    }

}
