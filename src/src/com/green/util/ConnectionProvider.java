package com.green.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;


public class ConnectionProvider
{
    public static DataSource cpds;

    static
    {
        try
        {
            Context context = new InitialContext();
            cpds = (DataSource) context.lookup("java:comp/env/jdbc/vod");
        } catch (NamingException e)
        {
            e.printStackTrace();
            System.out.println("初始化数据源时出错!");
        }
    }

    /**
     * 获取当前的数据库连接,
     *
     * @return 返回数据库连接
     * @throws Exception 如果连接配置的数据源错误或者取连接错误则抛出Exception异常
     */
    public static Connection getConnection() throws Exception
    {
        Connection conn = null;
        if (cpds != null)
        {
            conn = cpds.getConnection();
        }
        else
        {
            throw new Exception("数据源错误...");
        }
        return conn;
    }
}
