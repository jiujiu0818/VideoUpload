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
            System.out.println("��ʼ������Դʱ����!");
        }
    }

    /**
     * ��ȡ��ǰ�����ݿ�����,
     *
     * @return �������ݿ�����
     * @throws Exception ����������õ�����Դ�������ȡ���Ӵ������׳�Exception�쳣
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
            throw new Exception("����Դ����...");
        }
        return conn;
    }
}
