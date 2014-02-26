package com.free.tshirtdesigner;

/**
 * User: Dell
 * Date: 2/26/14
 * Time: 10:21 PM
 */
public class ViewDetail
{
    int height;
    int width;
    int[] coordinate;
    int imageUrl;
    String text;

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int[] getCoordinate()
    {
        return coordinate;
    }

    public void setCoordinate(int[] coordinate)
    {
        this.coordinate = coordinate;
    }

    public int getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
