package com.arjun.datastructure;

import java.time.LocalDateTime;

import com.microsoft.azure.storage.table.TableServiceEntity;

public class AuditTable extends TableServiceEntity {

	String recordinsertdate;
	String recordupdatedate;
	String status;
	String key;
	String value;

	public AuditTable(String partitionKey,String rowKey,  String Status) {

		// LocalDateTime now = LocalDateTime.now();

		this.partitionKey = partitionKey;
		this.rowKey = rowKey;
		this.recordinsertdate = LocalDateTime.now().toString();
		this.recordupdatedate = LocalDateTime.now().toString();
		this.status = Status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRecordinsertdate() {
		return recordinsertdate;
	}

	public void setRecordinsertdate(String recordinsertdate) {
		this.recordinsertdate = recordinsertdate;
	}

	public String getRecordupdatedate() {
		return recordupdatedate;
	}

	public void setRecordupdatedate(String recordupdatedate) {
		this.recordupdatedate = recordupdatedate;
	}

	public String getStepname() {
		return status;
	}

	public void setStepname(String stepname) {
		this.status = stepname;
	}

}
