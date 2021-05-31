package com.cat.generator.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import com.cat.generator.core.common.Config;
import com.cat.generator.core.db.TableBean;
import com.cat.generator.core.db.TableEntity;

public class DbUtils {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DbUtils.class);
	
	public static List<TableEntity> readDb(DataSource ds) {
		try {
			Connection conn = ds.getConnection();
			ResultSet tbRs = conn.getMetaData().getTables(Config.dbName, null, "%", new String[] { "TABLE" });
			//所有表
			List<String> tbNames = new LinkedList<>();
			while(tbRs.next()) {
				tbNames.add(tbRs.getString("TABLE_NAME"));
			}
			
			List<TableEntity> result = new ArrayList<TableEntity>();
			for (String tbName : tbNames) {//单表
				//组装表信息
				TableEntity tableEntity = new TableEntity();
				tableEntity.setTablName(tbName);
				tableEntity.setEntityName(StringUtils.underlineToUpperCamal(tbName));
				//	读取列数据
				readColumns(conn, tbName, tableEntity);
				// 主键
				readKeys(conn, result, tbName, tableEntity);
				//索引
				readIndex(conn, tbName, tableEntity);
			}
			//log.info("result:{}", result);
			return result;
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

	private static void readIndex(Connection conn, String tbName, TableEntity excelEntity) throws SQLException {
		ResultSet indexs = conn.getMetaData().getIndexInfo(Config.dbName, null, tbName, false, false);
		while (indexs.next()) {
			String indexName = indexs.getString("INDEX_NAME");
			if (org.apache.commons.lang3.StringUtils.equals(indexName, "PRIMARY")) {
				continue;
			}
			String indexValue = indexs.getString("COLUMN_NAME");
			excelEntity.addIndex(indexName, indexValue);
//			log.info("readIndex tbName:{}, indexName:{}, value:{}", tbName, indexName, indexValue);
		}
	}

	/**
	 * 读取主键数据
	 * @param conn
	 * @param tbName
	 * @param excelEntity
	 * @throws SQLException
	 */
	private static void readKeys(Connection conn, List<TableEntity> result, String tbName, TableEntity excelEntity)
			throws SQLException {
		ResultSet keys = conn.getMetaData().getPrimaryKeys(Config.dbName, null, tbName);
		List<String> primarys = new ArrayList<String>();
		while (keys.next()) {
			String keyColName = keys.getString("COLUMN_NAME");
			primarys.add(StringUtils.underlineToLowerCamal(keyColName));
		}
		//System.out.println(tbName+"====>primarys:"+primarys);
		excelEntity.setPrimarys(primarys);
		result.add(excelEntity);
//		log.info("readKeys tbName:{}, primarys:{}", tbName, primarys);
	}

	/**
	 * 读取列数据
	 * @param conn
	 * @param tbName
	 * @param excelEntity
	 * @throws SQLException
	 */
	private static void readColumns(Connection conn, String tbName, TableEntity excelEntity) throws SQLException {
		TableBean excelBean = null;
		//列
		ResultSet rs = conn.getMetaData().getColumns(Config.dbName, null, tbName, null);
		while (rs.next()) {
			String ctype = rs.getString("TYPE_NAME");
			try {
				ctype = StringUtils.sqlTypeToJavaType(ctype);
			} catch (Exception e) {
				e.printStackTrace();
			}
			excelBean = new TableBean();
			//player_id -> playerId
			excelBean.setField(StringUtils.firstCharLower(rs.getString("COLUMN_NAME")));
			excelBean.setTbField(StringUtils.firstCharUpper(rs.getString("COLUMN_NAME")));
			excelBean.setType(ctype);
			//excelBean.setWrapper(StringUtils.getWrapper(excelBean.getType()));
			excelBean.setDesc(rs.getString("REMARKS"));
			excelEntity.addEntityBeans(excelBean);
		}
	}
	
}
