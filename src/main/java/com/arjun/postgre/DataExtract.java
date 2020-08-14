package com.arjun.postgre;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.arjun.azure.table.AzureTable;
import com.arjun.datastructure.AuditTable;
import com.arjun.datastructure.RuntimeDataHolder;
import com.microsoft.azure.serverless.functions.ExecutionContext;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.BlobOutputStream;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.table.TableServiceEntity;

public class DataExtract {

	public static final String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=dfldevhbase01;AccountKey=P0O65zvUpokgTFzADOViConG5Nf02H01zN7fgH0LgoB2HppTdJNuKIHtPfc2TF/KZBKK5FPpW6qf3/df48Easg==;EndpointSuffix=core.windows.net";

	public String getData(AzureTable tableobject) throws Exception {
		Connection c = null;
		Statement stmt = null;

		AuditTable auditTable = new AuditTable(RuntimeDataHolder.partitionvalue, "2-B", "Connecting to Postgre");
		tableobject.executeInsert(auditTable);

		Class.forName("org.postgresql.Driver");
		Properties props = new Properties();

		props.setProperty("user", RuntimeDataHolder.user);
		props.setProperty("password", RuntimeDataHolder.password);
		props.setProperty("ssl", RuntimeDataHolder.ssl);
		c = DriverManager.getConnection(RuntimeDataHolder.postgreConnection, props);

		auditTable = new AuditTable(RuntimeDataHolder.partitionvalue, "2-C", "Connection Succesfull");
		tableobject.executeInsert(auditTable);

		auditTable = new AuditTable(RuntimeDataHolder.partitionvalue, "2-D", "Querying the table");
		tableobject.executeInsert(auditTable);

		stmt = c.createStatement();
		String sql = "";
		ResultSet rs = stmt.executeQuery(sql);

		auditTable = new AuditTable(RuntimeDataHolder.partitionvalue, "2-E", "Query Completed");
		tableobject.executeInsert(auditTable);

		String vehicle_id = "";
		while (rs.next()) {
			vehicle_id = rs.getString(1);
		}

		auditTable = new AuditTable(RuntimeDataHolder.partitionvalue, "2-F", "Returning Value");
		tableobject.executeInsert(auditTable);

		auditTable = new AuditTable(RuntimeDataHolder.partitionvalue, "2-G", "Stats");
		auditTable.setKey("Vin");
		auditTable.setValue(vehicle_id);
		tableobject.executeInsert(auditTable);

		return vehicle_id;
	}

	public void createBlobFile() throws Exception {

		File sourceFile = null;

		LocalDateTime now = LocalDateTime.now();
		NumberFormat f = new DecimalFormat("00");

		List<String> dirname = new ArrayList<String>();
		List<String> filename = new ArrayList<String>();

		dirname.add(String.valueOf(now.getYear()));
		dirname.add(String.valueOf(f.format(now.getMonthValue())));
		dirname.add(String.valueOf(f.format(now.getDayOfMonth())));

		filename.add(String.valueOf(f.format(now.getHour())));
		filename.add(String.valueOf(f.format(now.getMinute())));
		filename.add(String.valueOf(f.format(now.getSecond())));

		String fullpath = String.join("-", dirname) + "-" + String.join("-", filename);

		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;

		storageAccount = CloudStorageAccount.parse(storageConnectionString);

		blobClient = storageAccount.createCloudBlobClient();
		container = blobClient.getContainerReference(RuntimeDataHolder.blobContainer);

		CloudBlockBlob blockBlob = container.getBlockBlobReference("Sampe-file.txt");

		BlobOutputStream blobOutputStream = blockBlob.openOutputStream();

		byte[] buffer = "Hello Azure ! Ping from Arjun".getBytes();
		// byte[] buffer = getData().getBytes();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer);

		int next = inputStream.read();
		while (next != -1) {
			blobOutputStream.write(next);
			next = inputStream.read();
		}

		blobOutputStream.close();

	}
}
