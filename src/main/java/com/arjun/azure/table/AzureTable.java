package com.arjun.azure.table;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import com.arjun.datastructure.AuditTable;
import com.arjun.datastructure.EnvironmentDataHolder;
import com.arjun.datastructure.RuntimeDataHolder;
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.table.*;

public class AzureTable {

	private CloudTable cloudTable;

	public void setTableOperation(String tablename) {
		try {	
			CloudStorageAccount storageAccount;
			storageAccount = CloudStorageAccount.parse(RuntimeDataHolder.storageConnectionString);
			CloudTableClient tableClient = storageAccount.createCloudTableClient();
			this.cloudTable = tableClient.getTableReference(tablename);
			//this.cloudTable.createIfNotExists();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void executeInsertOrReplaceQuery(TableServiceEntity obj) throws StorageException {	
			TableOperation insertCustomer1 = TableOperation.insertOrReplace(obj);
			this.cloudTable.execute(insertCustomer1);
	}

	public void executeInsert(AuditTable obj) throws StorageException {
		TableOperation insertCustomer1 = TableOperation.insert(obj);
		this.cloudTable.execute(insertCustomer1);
	}
	
	public void fetchDetails(String tablename,String partitionvalue,String key) throws InvalidKeyException, URISyntaxException, StorageException{
		
		CloudStorageAccount storageAccount = CloudStorageAccount.parse(RuntimeDataHolder.storageConnectionString);
		CloudTableClient tableClient = storageAccount.createCloudTableClient();
		CloudTable cloudTable = tableClient.getTableReference(tablename);
		TableOperation tableoperation = TableOperation.retrieve(partitionvalue,key,EnvironmentDataHolder.class);
		EnvironmentDataHolder specificEntity = cloudTable.execute(tableoperation).getResultAsType();

		if (specificEntity != null) {
			System.out.println(specificEntity.getPartitionKey() + " " + specificEntity.getRowKey() + "\t"
					+ specificEntity.getPostgreHostname());
		}
		
	}

}