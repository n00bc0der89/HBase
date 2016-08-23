package test;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseExample {
    public static void main(String[] args) throws IOException {
    	
    	 if(args.length < 1)
  	   {
  		   System.out.println("Accepts 1 argument. <TableName>");
  		  // System.in.read();
  		   System.exit(1);
  	   }
        Configuration config = HBaseConfiguration.create();
       // config.set("fs.default.name", "172.16.210.23");
        HBaseAdmin admin = new HBaseAdmin(config);
        
        String tableN = "/user/mapr/" + args[0].toString();
        if(!admin.tableExists(tableN))
        {
        	 HTableDescriptor desc = new HTableDescriptor(Bytes.toBytes(tableN));
             desc.addFamily(new HColumnDescriptor("cf1"));
             desc.addFamily(new HColumnDescriptor("cf2"));
             admin.createTable(desc);
        	System.out.println("Table "+ tableN + " created" );
        }
       
        HTable table = new HTable(config, tableN);
        Put p = new Put(Bytes.toBytes("row1"));
        p.add(Bytes.toBytes("cf1"), Bytes.toBytes("col2"),
                Bytes.toBytes("ABC"));
        p.add(Bytes.toBytes("cf1"), Bytes.toBytes("col2"),
                Bytes.toBytes("XYZ"));

        table.put(p);
        Get g = new Get(Bytes.toBytes("row1"));
        Result r = table.get(g);
        byte[] value = r.getValue(Bytes.toBytes("cf1"),
                Bytes.toBytes("col2"));

        String valueStr = Bytes.toString(value);
        System.out.println("Read value from table "+ tableN + " and column family cf1. Value is " + valueStr);
        Scan s = new Scan();
        s.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("col2"));
        ResultScanner scanner = table.getScanner(s);
        try {
            for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
                System.out.println("Found row: " + rr);
            }

        } finally {
            scanner.close();
        }
        
    }
}