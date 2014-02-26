package com.free.tshirtdesigner.model;

import com.free.tshirtdesigner.ViewZoomer;

/**
 * User: Admin
 * Date: 2/26/14
 * Time: 11:19 PM
 */
public class LayerModel
{
    private int id;
    private int type;
    private String name;
    private ViewZoomer viewZoomer;

    public LayerModel(int id, int type, String name, ViewZoomer viewZoomer)
    {
        this.id = id;
        this.type = type;
        this.name = name;
        this.viewZoomer = viewZoomer;
    }

    public int getId()
    {
        return id;
    }

    public int getType()
    {
        return type;
    }

    public String getName()
    {
        return name;
    }

    public ViewZoomer getViewZoomer()
    {
        return viewZoomer;
    }
}
