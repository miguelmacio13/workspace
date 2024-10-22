package com.tem.aspire;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
 
//import com.temenos.api.TField;
import com.temenos.api.TStructure;
//import com.temenos.services.customer.data.CustomerRecord;
import com.temenos.t24.api.complex.eb.servicehook.ServiceData;
import com.temenos.t24.api.complex.eb.servicehook.SynchronousTransactionData;
import com.temenos.t24.api.complex.eb.servicehook.TransactionControl;
//import com.temenos.t24.api.hook.contract.FundsTransfer;
import com.temenos.t24.api.hook.system.ServiceLifecycle;
import com.temenos.t24.api.records.account.AccountRecord;
//import com.temenos.t24.api.records.beneficiary.BeneficiaryRecord;
import com.temenos.t24.api.records.customer.CustomerRecord;
import com.temenos.t24.api.records.fundstransfer.FundsTransferRecord;
import com.temenos.t24.api.system.DataAccess;

/**
 * TODO: Document me!
 *
 * @author miguel.macias
 *
 */
public class MultiThread extends ServiceLifecycle {

    @Override
    public List<String> getIds(ServiceData serviceData, List<String> controlList) {
        DataAccess data = new DataAccess(this);
        //BeneficiaryRecord BRecord  = new BeneficiaryRecord();
        //controlList.add(id);
        List<String> id = null;
        id = data.selectRecords("BNK", "FUNDS.TRANSFER", "", "");
 
        return id;
    }

    @Override
    public void updateRecord(String id, ServiceData serviceData, String controlItem,
            TransactionControl transactionControl, List<SynchronousTransactionData> transactionData,
            List<TStructure> records) {
      //super.updateRecord(id, serviceData, controlItem, transactionControl, transactionData, records);
        DataAccess data = new DataAccess(this);
        FundsTransferRecord FRecord  = new FundsTransferRecord(data.getRecord("FUNDS.TRANSFER",id));
        String fRt = FRecord.getDebitCustomer().getValue();
        CustomerRecord cus = new CustomerRecord(data.getRecord("CUSTOMER",fRt));
        String natio  = cus.getNationality().getValue();
        //String newCustomer;
        if(natio.equals("US")){
            try {
                FileWriter file = new FileWriter("D:\\Pruebas2.txt",true); 
                file.write("Beni ID"+fRt+"Nationality: "+natio+'\n');
                file.close();
            } catch (IOException e) {
                System.out.println("Exeption: "+e);
            }
            AccountRecord account = new AccountRecord();
            account.setCustomer(fRt);
            account.setCategory("1001");
            account.setCurrency("USD");
            SynchronousTransactionData sync = new SynchronousTransactionData();
            sync.setVersionId("ACCOUNT,SYNC");
            sync.setFunction("INPUT");
            sync.setSourceId("AA.COB");
            transactionData.add(sync);
            records.add(account.toStructure());
        }
    }
}
