package com.fciensy.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StringConvert
{
    /**
     * Convert int array to a new string using delim and default
     * suffix and default prefix.The default suffix and default
     * prefix is single quotes(').<p>
     * @param valueList int array.
     * @param delim char separator.
     * @return a new string.
     */
    public static String convertString(int[] valueList,String delim)
    {
        return convertString(valueList,delim,"","",0,valueList.length);
    }

    /**
     * Convert int array whose position is from index1 to index2
     * to a new string using delim and default suffix and default
     * prefix.The default suffix and default prefix is single quotes(').<p>
     * @param valueList int array.
     * @param delim char separator.
     * @param prefix.
     * @param suffix.
     * @param fromIndex start position of strList.
     * @param toIndex end position of strList.
     * @return a new string.
     */
    public static String convertString(int[] valueList,String delim,
                                       String prefix,String suffix,
                                       int formIndex,int toIndex)
    {
        int i;
        String str = "";
        for (i=formIndex; i<toIndex; i++)
        {
            str += prefix + String.valueOf(valueList[i]) + suffix + delim;
        }
        if (!str.equals(""))
        {
            str = str.substring(0, str.length() - delim.length());
        }
        return str;
    }

    /**
     * Convert arraylist to a new string using delim.<p>
     * @param list data list.
     * @param delim char seperator.
     * @return a new string.
     */
    public static String convertString(List list,String delim)
    {
        return convertString(list.toArray(),delim);
    }

    /**
     * Convert HashMap to string connected with "#".
     * @param mapCode.
     * @return a new string.
     */
    public static String convertString(Map mapCode)
    {
        String str = "";
        java.util.Iterator iterator = null;
        if(mapCode==null || mapCode.size()==0) { return str; }
        else
        {
            iterator = mapCode.keySet().iterator();
            while(iterator.hasNext())
            {
               str += (String)iterator.next() + "#";
            }
            if(str.length() > 1)
            {
                str = str.substring(0,str.length() - 1);
            }
        }
        return str;
    }

    /**
     * Convert object array to a new string using delim.<p>
     * @param data object array.
     * @param delim char seperator.
     * @return a new string.
     */
    public static String convertString(Object[] data,String delim)
    {
        int i;
        String str = "";
        if (data.length < 1) {return "";}
        for (i=0; i<data.length; i++)
        {
            str += (String)data[i] + delim;
        }
        return str.substring(0, str.length() - delim.length());
    }

    /**
     * Convert string array to a new string using delim and default
     * suffix and default prefix.The default suffix and default
     * prefix is single quotes(').<p>
     * @param strList string array.
     * @param delim char separator.
     * @return a new string.
     */
    public static String convertString(String[] strList,String delim)
    {
        return convertString(strList,delim,"'","'");
    }

    /**
     * Convert string array whose position is from index1 to index2
     * to a new string using delim and default suffix and default
     * prefix.The default suffix and default prefix is single quotes(').<p>
     * @param strList string array.
     * @param delim char separator.
     * @param fromIndex start position of strList.
     * @param toIndex end position of strList.
     * @return a new string.
     */
    public static String convertString(String[] strList,String delim,
                                       int fromIndex,int toIndex)
    {
        return convertString(strList,delim,"'","'",fromIndex,toIndex);
    }

    /**
     * Convert string array to a new string using delim,perfix,suffix.<p>
     * @param strList string array.
     * @param delim char separator.
     * @param prefix.
     * @param suffix.
     * @return a new string.
     */
    public static String convertString(String[] strList,String delim,
                                       String prefix,String suffix)
    {
        return convertString(strList,delim,prefix,suffix,0,
                             strList.length);
    }

    /**
     * Convert string array whose position is from index1 to index2
     * to a new string using delim and default suffix and default
     * prefix.The default suffix and default prefix is single quotes(').<p>
     * @param strList string array.
     * @param delim char separator.
     * @param prefix.
     * @param suffix.
     * @param fromIndex start position of strList.
     * @param toIndex end position of strList.
     * @return a new string.
     */
    public static String convertString(String[] strList,String delim,
                                       String prefix,String suffix,
                                       int formIndex,int toIndex)
    {
        int i;
        String str = "";
        for (i=formIndex; i<toIndex; i++)
        {
            str += prefix + trimString(strList[i]) + suffix + delim;
        }
        if (!str.equals(""))
        {
            str = str.substring(0, str.length() - delim.length());
        }
        return str;
    }

    /**
     * Convert blank string or null string to zero.<p>
     * @param str.
     * @return zero or not null/blank string.
     */
	public static String convertToZero(String str)
	{
		if(str == null || str.equals(""))
		{
			return "0";
		}
		else
		{
			return str.trim();
		}
	}

    /**
     * Fill zero before string when length of string is less than
     * stated length.<p>
     * @param str.
     * @param len stated length.
     * @return a new string.
     */
    public static String formatString(String str,int len)
    {
        return formatString(str,"0",len);
    }

    /**
     * Fill prefix before string when length of string is less than
     * stated length.<p>
     * @param str.
     * @param prefix.
     * @param len stated length.
     * @return a new string.
     */
    public static String formatString(String str,String prefix,int len)
    {
        int i;
        int diffLen = 0;
        int strLen = str.length();
        if (trimString(prefix).length() != 1 ||
            strLen >= len) { return str; }
        else
        {
            diffLen = len - strLen;
            for(i=0; i<diffLen; i++) { str = prefix + str; }
            return str;
        }
    }

   /**
    * Return a new string using delim.The default delim is comma(,).
    * @param conn database connection.
    * @param sql.
    * @param prefix.
    * @param suffix.
    * @return a new string.
    */
    public static String getColumnList(Connection conn,String sql,
                                       String prefix,String suffix)
    {
        ResultSet rs = null;
        Statement stmt = null;
        String columnList = "";
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                columnList = columnList + prefix + rs.getString(1) +
                             suffix + ",";
            }
            if (!columnList.equals(""))
            {
                columnList = columnList.substring(0, columnList.length() - 1);
            }
            return columnList;
        }
        catch(SQLException se) { return ""; }
        catch(Exception e) { return ""; }
        finally
        {
            if (rs != null) { try {rs.close();} catch (Exception e) {} }
            if (stmt != null) { try {stmt.close();} catch (Exception e) {} }
        }
    }

    /**
     * Repeat data with number of time using delim.<p>
     * @param data.
     * @param delim char seperator.
     * @paran num number of repetition.
     * @return a new string.
     */
    public static String repeat(String data,String delim,int num)
    {
        int i;
        String str = "";
        if (num < 1) { return str; }
        for (i=0; i<num; i++)
        {
            str += data + delim;
        }
        return str.substring(0, str.length() - delim.length());
    }

    /**
     * Convert arraylist to string array.<p>
     * @param list.
     * @return string array.
     */
    public static String[] toArray(List list)
    {
        String[] result = new String[list.size()];
        for (int i=0; i<list.size(); i++)
            result[i] = list.get(i).toString();
       
        return result;
    }

    /**
     * Trim blank or convert null to blank string.<p>
     * @param str.
     * @return A string which has been trimed.
     */
    public static String trimString(String str)
    {
        if (str == null) { return ""; }
		else { return str.trim(); }
    }

    public static String trimString(String str, String def)
    {
        if (str == null) { return def; }
		else { return str.trim(); }
    }

    /**
     * Trim string array.<p>
     * @param str string array.
     * @return string array which has been trimmed.
     *         If string array is null,then return a new string array
     *         whose length equals zero.
     */
    public static String[] trimString(String[] str)
    {
        int i;
        String[] newStr = {""};
        if(str == null) { return newStr; }
        else
        {
            for(i=0; i<str.length; i++)
            {
                str[i] = trimString(str[i]);
            }
        }
        return str;
    }


    public static String makeUpZero(String srcString,int num)
	{
        int len =0;
		if(srcString == null)
		{
			return "";
		}
		else
		{
            String temp = srcString.trim();
            len = temp.length();
			StringBuffer targetString =new StringBuffer();
            for(int i=0;i<num;i++)
            {
                targetString.append("0");
            }
            targetString.append(temp);
            return (targetString.substring(len)).toString();
		}
	}
}


