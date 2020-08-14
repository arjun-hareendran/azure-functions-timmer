package com.arjun.functions;

import com.microsoft.azure.serverless.functions.annotation.*;
import com.microsoft.azure.storage.StorageException;

import java.time.LocalDateTime;

import com.arjun.azure.table.AzureTable;
import com.arjun.datastructure.AuditTable;
import com.arjun.datastructure.RuntimeDataHolder;
import com.arjun.postgre.DataExtract;
import com.arjun.utils.TimeFunctions;
import com.microsoft.azure.serverless.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */

public class Function {

	@FunctionName("Timer")
	@QueueOutput(name = "myQueueItem", queueName = "walkthrough", connection = "AzureWebJobsStorage")
	public String functionHandler(@TimerTrigger(name = "timerInfo", schedule = "0 */1 * * * *") String timerInfo,
			final ExecutionContext executionContext) throws Exception {

		executionContext.getLogger().info("Timer trigger input: " + timerInfo);

		System.out.println("Process Started");

		// Generate Key and Parition
		String tableName = "dataingestionaudit";
		LocalDateTime now = LocalDateTime.now();
		// String partitionvalue = new TimeFunctions().uniquePartition();
		RuntimeDataHolder.partitionvalue = new TimeFunctions().uniquePartition();

		AuditTable auditTable = new AuditTable(RuntimeDataHolder.partitionvalue, "1-A", "Process Started");

		AzureTable tableobj = new AzureTable();
		tableobj.setTableOperation(tableName);
		tableobj.executeInsert(auditTable);

		auditTable = new AuditTable(RuntimeDataHolder.partitionvalue, "2-A", "Connecting to Postgre DB");
		tableobj.executeInsert(auditTable);

		DataExtract dataExtract = new DataExtract();
		dataExtract.getData(tableobj);

		auditTable = new AuditTable(RuntimeDataHolder.partitionvalue, "3-A", "Disconnecting to postgre");
		tableobj.executeInsert(auditTable);

		auditTable = new AuditTable(RuntimeDataHolder.partitionvalue, "100-A", "Process Ended");
		tableobj.executeInsert(auditTable);

		System.out.println("End of Process");

		return " ";

		/*
		 * String returnvalue = ""; try { returnvalue = new
		 * DataExtract().getData();
		 * executionContext.getLogger().info("The value returned is ==>  " +
		 * returnvalue); } catch (Exception e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); return "Failed to get the data"; }
		 * 
		 * return returnvalue;
		 */
		// return "From timer: \"" + timerInfo + "\"";
	}

}
