package com.cat.zproto.domain.table;

import java.io.IOException;

import com.cat.zproto.domain.table.po.TableEntity;

public class DBDomain implements IDBSource {

	private IDBSource source;

	public DBDomain(IDBSource source) {
		this.source = source;
	}

	public IDBSource getSource() {
		return source;
	}

	public void setSource(IDBSource source) {
		this.source = source;
	}

	@Override
	public TableEntity getTableEntity(String tableEntityName) {
		return source.getTableEntity(tableEntityName);
	}

	@Override
	public void init() throws IOException {
		source.init();
	}

	@Override
	public int dbType() {
		return source.dbType();
	}

}
