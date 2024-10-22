package com.tem.aspire;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.temenos.api.TStructure;
import com.temenos.t24.api.complex.eb.servicehook.ServiceData;
import com.temenos.t24.api.complex.eb.servicehook.TransactionData;
import com.temenos.t24.api.hook.system.ServiceLifecycle;
import com.temenos.t24.api.records.aabilldetails.AaBillDetailsRecord;
import com.temenos.t24.api.system.DataAccess;

/**
 * TODO: Document me!
 *
 * @author miguel.macias
 *
 */
public class Task6 extends ServiceLifecycle {

    @Override
    public List<String> getIds(ServiceData serviceData, List<String> controlList) {
        DataAccess data = new DataAccess(this);
        List<String> id = null;
        id = data.selectRecords("BNK", "AA.ARRANGEMENT", "", "WITH PRODUCT.LINE EQ LENDIND");
        return id;
    }

    @Override
    public void postUpdateRequest(String id, ServiceData serviceData, String controlItem,
            List<TransactionData> transactionData, List<TStructure> records) {
      DataAccess data = new DataAccess(this);
//      AaArrangementRecord aaRec = new AaArrangementRecord(data.getRecord("AA.ARRANGEMENT", id));
      
      List<String> aaBDid = null;
      aaBDid = data.selectRecords("BNK", "AA.BILL.DETAILS", "", "WITH ARRANGEMENT.ID EQ " + id);
      
     for( String c : aaBDid){
         AaBillDetailsRecord aaBD = new AaBillDetailsRecord(data.getRecord("AA.BILL.DETAILS", c));
         
         String expPaymDate = aaBD.getPaymentDate().getValue();
         
         try{
           FileWriter file = new FileWriter("D:\\R22_ALL\\practice\\miniproject.txt",true);
           file.write("Arrangement ID: " + id + ", bill ID: " + c + ", expected payment date: "
                   + expPaymDate );
           file.close();
           System.out.println("File written succesfully");
       }
       catch (IOException e){
           System.out.println("Exception: " + e);
       }
     }
       
    }

//    @Override
//    public void updateRecord(String id, ServiceData serviceData, String controlItem,
//            TransactionControl transactionControl, List<SynchronousTransactionData> transactionData,
//            List<TStructure> records) {
//        
//        DataAccess data = new DataAccess(this);
//        FundsTransferRecord ftRecord = new FundsTransferRecord(data.getRecord("FUNDS.TRANSFER", id));
//        String ftDC = ftRecord.getDebitCustomer().getValue();
//        
//        CustomerRecord cRecord = new CustomerRecord(data.getRecord("CUSTOMER", ftDC));
//        String cN = cRecord.getNationality().getValue();
//        
//        if(cN.equals("US")){
//            try{
//                FileWriter file = new FileWriter("D:\\R22_ALL\\practice\\customer.txt",true);
//                file.write("Customer Id " + ftDC + " Nationality " + cN + "\n");
//                file.close();
//                System.out.println("File written succesfully");
//            }
//            catch (IOException e){
//                System.out.println("Exception: " + e);
//            }
//            
//            AccountRecord account = new AccountRecord();
//            account.setCustomer(ftDC);
//            account.setCategory("1001");
//            account.setCurrency("USD");
//            
//            SynchronousTransactionData sync = new SynchronousTransactionData();
//            sync.setVersionId("ACCOUNT,TASK6");
//            sync.setFunction("INPUT");
//            sync.setSourceId("AA.COB");
//            
//            transactionData.add(sync);
//            records.add(account.toStructure());
//        }
//    }
    
    
    
    

    
}
