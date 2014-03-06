package com.free.tshirtdesigner.popup;

import java.io.Serializable;

/**
 * User: AnhNT
 * Date: 2/20/14
 * Time: 10:06 PM
 */
public class PopupItem implements Serializable
{
    private int id;
    private String itemTitle;
    private String itemValue;

    public PopupItem(int id, String itemTitle, String itemValue)
    {
        this.id = id;
        this.itemTitle = itemTitle;
        this.itemValue = itemValue;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getItemTitle()
    {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle)
    {
        this.itemTitle = itemTitle;
    }

    public String getItemValue()
    {
        return itemValue;
    }

    public void setItemValue(String itemValue)
    {
        this.itemValue = itemValue;
    }
}
