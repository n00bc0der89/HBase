package test;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import com.sun.tools.internal.ws.wsdl.document.jaxws.Exception;
 
public class CreateTable {
 
         private static Configuration conf = null;
        
       public static void main(String[] args) throws IOException {
              // TODO Auto-generated method stub
    	   
    	   if(args.length < 3)
    	   {
    		   System.out.println("Accepts 3 arguments. <TableName> <FirstColumnFamily> <SecondColumnFamily>");
    		  // System.in.read();
    		   System.exit(1);
    	   }
             conf = HBaseConfiguration.create();
              //conf.set("hbase.zookeeper.quorum", "172.16.210.23");
             // conf.set("hbase.zookeeper.property.clientPort", "5181");
              HBaseAdmin admin = null;
             
              try {
                    
                     admin = new HBaseAdmin(conf);
             
              } catch (MasterNotRunningException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
              } catch (ZooKeeperConnectionException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
              } catch (IOException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
              }
              String tablename =  "/user/mapr/" + args[0].toString();
              String familyname = args[1].toString();
              String familyname2= args[2].toString();
              try {
                     if(!admin.tableExists(tablename))
                     {
                     HTableDescriptor desc = new HTableDescriptor(Bytes.toBytes(tablename));
                     desc.addFamily(new HColumnDescriptor(familyname));
                     desc.addFamily(new HColumnDescriptor(familyname2));
                     admin.createTable(desc);
                     System.out.println("Table "+ tablename + " created");
                    
                     }
                     else
                     {
                    	 System.out.println("Table exist");
                    	// admin.addColumn(Bytes.toBytes(tablename), new HColumnDescriptor("newColumnFamily"));
                     }
                    
              } catch (IOException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
              }
             
       }
 
}
