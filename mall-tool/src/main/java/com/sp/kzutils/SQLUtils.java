package com.kuaizi.etl.common.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.google.common.base.Preconditions;

public final class SQLUtils {

	static class MapRowProcessor extends BasicRowProcessor {
		public MapRowProcessor() {
			super();
		}

		@Override
		public Map<String, Object> toMap(ResultSet rs) throws SQLException {
			Map<String, Object> result = new HashMap<String, Object>();
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			for (int i = 1; i <= cols; i++) {
				result.put(rsmd.getColumnLabel(i), rs.getObject(i));
			}
			return result;
		}
	}

	/**
	 * @param connection
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static List<Map<String, Object>> query(Connection connection, String sql, Object[] params)
	    throws SQLException {
		Preconditions.checkNotNull(connection, "数据库连接为空");
		List<Map<String, Object>> result = null;
		try {
			QueryRunner qRunner = new QueryRunner();
			result = qRunner.query(connection, sql, new MapListHandler(new MapRowProcessor()), params);
		} finally {
			DBUtils.closeConn(connection);
		}
		return result;
	}

	public static <E> List<E> query(Connection connection, String sql, Object[] params, Class<E> clazz)
	    throws SQLException {
		Preconditions.checkNotNull(connection, "数据库连接为空");
		List<E> result = null;
		try {
			QueryRunner qRunner = new QueryRunner();
			result = qRunner.query(connection, sql, new BeanListHandler<E>(clazz), params);
		} finally {
			DBUtils.closeConn(connection);
		}
		return result;
	}

	/**
	 * @param connection
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static int execute(Connection connection, String sql, Object[] params) throws SQLException {
		Preconditions.checkNotNull(connection, "数据库连接为空");
		int num = 0;
		try {
			QueryRunner qRunner = new QueryRunner();
			if (params == null) {
				num = qRunner.update(connection, sql);
			} else {
				num = qRunner.update(connection, sql, params);
			}
		} finally {
			DBUtils.closeConn(connection);
		}
		return num;
	}

	/**
	 * 默认300条记录一批
	 * 
	 * @param connection
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static int executeBatch(Connection connection, String sql, Object[][] params) throws SQLException {
		return batch(connection, sql, params, 300);
	}

	/**
	 * @param connection
	 * @param sql
	 * @param params
	 * @param batchSize
	 * @return
	 * @throws SQLException
	 */
	public static int executeBatch(Connection connection, String sql, Object[][] params, int batchSize)
	    throws SQLException {
		return batch(connection, sql, params, batchSize);
	}

	/**
	 * @param connection
	 * @param sql
	 * @param params
	 * @param sliptSize
	 * @return
	 * @throws SQLException
	 */
	private static int batch(Connection connection, String sql, Object[][] params, int sliptSize) throws SQLException {
		Preconditions.checkNotNull(connection, "数据库连接为空");
		int n = 0;
		int size = params.length;
		try {
			QueryRunner qRunner = new QueryRunner();
			int offset = 0;
			int index = (size > sliptSize) ? sliptSize : size;
			while (offset < index) {
				Object[][] _params = Arrays.copyOfRange(params, offset, index);
				n = n + qRunner.batch(connection, sql, _params).length;
				offset = offset + sliptSize;
				index = ((index + sliptSize) > size) ? size : index + sliptSize;
				_params = null;
			}
		} finally {
			DBUtils.closeConn(connection);
		}
		return n;
	}

}
