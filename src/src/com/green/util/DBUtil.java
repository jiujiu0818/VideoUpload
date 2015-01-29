package com.green.util;

import java.sql.*;

/**
 * Created by Zhu.Wei User: Administrator Date: 2006-10-25 Time: 19:41:50
 */
public final class DBUtil
{
   private static final ThreadLocal threadLocal = new ThreadLocal();

   private static boolean isPrintln = true;

   private DBUtil()
   {
   }

   /**
    * ��ȡ��ǰ���ݿ�����
    *
    * @return
    * @throws Exception
    */
   public synchronized static Connection getCurrentConnection()
       throws Exception
   {
      Connection conn = (Connection) threadLocal.get();
      try
      {
         if (conn == null)
         {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);
            threadLocal.set(conn);
         }
         else if (conn.isClosed())
         {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);
            threadLocal.set(conn);
         }
      } catch (Exception e)
      {
         e.printStackTrace();
         throw new Exception("����Դ����...");
      }
      return conn;
   }

   public static void closeConnection() throws Exception
   {
      Connection conn = (Connection) threadLocal.get();
      if (conn != null && !conn.isClosed())
      {
         conn.close();
//			if (isPrintln) {
//				System.out.println("DBUtil closeConnection() �Ѿ��ɹ��ر�");
//			}
      }
      threadLocal.set(null);

   }

   /**
    * �ͷ����ݿ�����
    *
    * @param conn
    * @throws Exception
    */
   public static void closeConnection(Connection conn) throws Exception
   {
      try
      {
         if (conn != null)
         {
            conn.close();
         }
      } catch (SQLException e)
      {
         e.printStackTrace();
      }
   }

   /**
    * �ͷ�statement ���Բ�Ҫÿ�ζ�try
    *
    * @param stmt
    */
   public static void freeStatement(Statement stmt)
   {
      if (stmt != null)
      {
         try
         {
            stmt.close();
         } catch (SQLException e)
         {
            e.printStackTrace();
         }
      }
   }

   /**
    * �ر�resultset
    *
    * @param rs
    */
   public static void closeResultSet(ResultSet rs)
   {
      if (rs != null)
      {
         try
         {
            rs.close();
         } catch (SQLException e)
         {
            e.printStackTrace();
         }
      }
   }


   public static void commit() throws Exception
   {
      getCurrentConnection().commit();
   }


   public static void rollback() throws Exception
   {
      getCurrentConnection().rollback();
   }

   public static void setIsPrintln(boolean flag)
   {
      isPrintln = flag;
   }

   public static void close(Statement stmt)
   {
      if (stmt != null)
      {
         try
         {
            stmt.close();
         } catch (SQLException e)
         {
            e.printStackTrace();
         }
      }
   }

   public static void close(ResultSet rs)
   {
      if (rs != null)
      {
         try
         {
            rs.close();
         } catch (SQLException e)
         {
            e.printStackTrace();
         }
      }
   }

   public static void close(Connection conn)
   {
      try
      {
         if (conn != null)
         {
            conn.close();
         }
      } catch (SQLException e)
      {
			e.printStackTrace();
		}
   }

}
