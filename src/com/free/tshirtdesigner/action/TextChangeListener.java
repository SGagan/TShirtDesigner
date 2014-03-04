package com.free.tshirtdesigner.action;

/**
 * User: anhnt
 * Date: 2/28/14
 * Time: 3:41 PM
 */
public interface TextChangeListener
{
    public void changeColor();

    public void changeText(String text);

    public void changeFont();

    public void delete();
}
