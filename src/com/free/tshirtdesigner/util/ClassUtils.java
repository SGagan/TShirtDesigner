package com.free.tshirtdesigner.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Le
 * Date: 10/16/13
 */
public class ClassUtils
{
    public static List<Field> getAllFields(Class clazz)
    {
        List<Field> results = new ArrayList<Field>();

        Class superClass = clazz;
        while (superClass != Object.class)
        {
            results.addAll(Arrays.asList(superClass.getFields()));
            results.addAll(Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        }

        return results;
    }

    public static void sendMail(Context context, String email)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        sendIntent.putExtra(Intent.EXTRA_TEXT, "");
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    public static void phoneCall(Context context, String phoneNumber)
    {
        Uri phoneCall = Uri.parse("tel:" + phoneNumber);
        Intent caller = new Intent(Intent.ACTION_DIAL, phoneCall);
        context.startActivity(caller);
    }

    public static void viewWebsite(Context context, String url)
    {
        if (!url.startsWith("http://") && !url.startsWith("https://"))
        {
            url = "http://" + url;
        }
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public static void viewMaps(Context context, String addressText)
    {
        String uri = "geo:" + 0 + "," + 0 + "?q=" + addressText;
        context.startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
    }

    public static void sendMailAttachedSingleFile(Context context, String email, String subject, Uri uri)
    {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("application/pdf");
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "");
        context.startActivity(sendIntent);
    }

    public static void sendMailAttachedMultiFiles(Context context, String email, String subject, ArrayList<Uri> uriFileList)
    {
        Intent sendIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        sendIntent.setType("image/png");
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriFileList);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "");
        context.startActivity(sendIntent);
    }
}
