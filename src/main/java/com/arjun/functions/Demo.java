package com.arjun.functions;

import java.time.LocalDateTime;

import com.arjun.azure.table.AzureTable;
import com.arjun.datastructure.AuditTable;
import com.arjun.datastructure.RuntimeDataHolder;
import com.arjun.postgre.DataExtract;
import com.arjun.utils.TimeFunctions;

public class Demo {

	public static void main(String[] args) throws Exception {
		
		System.out.println("Process Started");
		
		// Generate Key and Parition
		String tableName = RuntimeDataHolder.auditTablename;
		LocalDateTime now = LocalDateTime.now();
		RuntimeDataHolder.partitionvalue = new TimeFunctions().uniquePartition();

		AuditTable auditTable = new AuditTable(RuntimeDataHolder.partitionvalue, "1-A",
				"Process Started");

		AzureTable tableobj = new AzureTable();
		tableobj.setTableOperation(tableName);
		tableobj.executeInsert(auditTable);

		auditTable = new AuditTable(RuntimeDataHolder.partitionvalue, "2-A",
				"Connecting to Postgre DB");
		tableobj.executeInsert(auditTable);


		DataExtract dataExtract = new DataExtract();
		dataExtract.getData(tableobj);

		auditTable = new AuditTable(RuntimeDataHolder.partitionvalue, "3-A",
				"Disconnecting to postgre");
		tableobj.executeInsert(auditTable);

		auditTable = new AuditTable(RuntimeDataHolder.partitionvalue, "100-A",
				"Process Ended");
		tableobj.executeInsert(auditTable);
		
		System.out.println("End of Process");
	}

}
