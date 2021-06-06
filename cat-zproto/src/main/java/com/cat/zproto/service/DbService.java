package com.cat.zproto.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.zproto.domain.system.SettingConfig;
import com.cat.zproto.domain.table.TableBean;
import com.cat.zproto.domain.table.TableEntity;
import com.cat.zproto.util.StringUtils;

/**
 * 读取数据库信息, 读取指定表的表结构
 * @auth Jeremy
 * @date 2021年6月6日下午6:11:36
 */
@Service
public class DbService {
	
	@Autowired private SettingConfig setting;
	
	@Autowired private DataSource database;
	
	private static final Logger log = LoggerFactory.getLogger(DbService.class);
	
	public List<TableEntity> readDb() {
		try (Connection conn = database.getConnection()){
			//Connection conn = database.getConnection();
			ResultSet tbRs = conn.getMetaData().getTables(setting.getDbInfo().getDbName(), null, "%", new String[] { "TABLE" });
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

	/**
	 * 读取索引信息
	 * @param conn
	 * @param tbName
	 * @param excelEntity
	 * @throws SQLException  
	 * @return void  
	 * @date 2021年6月6日下午6:12:18
	 */
	private void readIndex(Connection conn, String tbName, TableEntity excelEntity) throws SQLException {
		ResultSet indexs = conn.getMetaData().getIndexInfo(setting.getDbInfo().getDbName(), null, tbName, false, false);
		while (indexs.next()) {
			String indexName = indexs.getString("INDEX_NAME");
			if (org.apache.commons.lang3.StringUtils.equals(indexName, "PRIMARY")) {
				continue;
			}
			String indexValue = indexs.getString("COLUMN_NAME");
			excelEntity.addIndex(indexName, indexValue);
		}
	}

	/**
	 * 读取主键数据
	 * @param conn
	 * @param tbName
	 * @param excelEntity
	 * @throws SQLException
	 */
	private void readKeys(Connection conn, List<TableEntity> result, String tbName, TableEntity excelEntity)
			throws SQLException {
		ResultSet keys = conn.getMetaData().getPrimaryKeys(setting.getDbInfo().getDbName(), null, tbName);
		List<String> primarys = new ArrayList<String>();
		while (keys.next()) {
			String keyColName = keys.getString("COLUMN_NAME");
			primarys.add(StringUtils.underlineToLowerCamal(keyColName));
		}
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
	private void readColumns(Connection conn, String tbName, TableEntity excelEntity) throws SQLException {
		TableBean excelBean = null;
		//列
		ResultSet rs = conn.getMetaData().getColumns(setting.getDbInfo().getDbName(), null, tbName, null);
		while (rs.next()) {
			String ctype = rs.getString("TYPE_NAME");
			try {
				ctype = StringUtils.sqlTypeToJavaType(ctype);
			} catch (Exception e) {
				e.printStackTrace();
			}
			excelBean = new TableBean();
			excelBean.setField(StringUtils.firstCharLower(rs.getString("COLUMN_NAME")));
			excelBean.setTbField(StringUtils.firstCharUpper(rs.getString("COLUMN_NAME")));
			excelBean.setType(ctype);
			excelBean.setDesc(rs.getString("REMARKS"));
			excelEntity.addEntityBeans(excelBean);
		}
	}
	

}
