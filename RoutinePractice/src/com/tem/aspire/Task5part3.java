package com.tem.aspire;

import java.util.List;

import com.temenos.api.TStructure;
import com.temenos.t24.api.complex.eb.servicehook.TransactionData;
import com.temenos.t24.api.complex.eb.templatehook.TransactionContext;
import com.temenos.t24.api.hook.system.RecordLifecycle;
import com.temenos.t24.api.records.fundstransfer.FundsTransferRecord;

/**
 * TODO: Document me!
 *
 * @author miguel.macias
 *
 */
public class Task5part3 extends RecordLifecycle {
    @Override
    public void postUpdateRequest(String application, String currentRecordId, TStructure currentRecord,
            List<TransactionData> transactionData, List<TStructure> currentRecords,
            TransactionContext transactionContext) {
        FundsTransferRecord fundsTransferRecord = new FundsTransferRecord();
        fundsTransferRecord.setTransactionType("AC");
        fundsTransferRecord.setDebitAcctNo("100102");
        fundsTransferRecord.setCreditAcctNo(currentRecordId);
        fundsTransferRecord.setCurrencyMktDr("1");
        fundsTransferRecord.setCurrencyMktCr("1");
        fundsTransferRecord.setDebitCurrency("USD");
        fundsTransferRecord.setCreditAmount("10000");
        fundsTransferRecord.setCreditCurrency("USD");
        fundsTransferRecord.setDebitValueDate("20220419");
        fundsTransferRecord.setCreditValueDate("20220419");
        fundsTransferRecord.setProcessingDate("20220419");
        TransactionData tData = new TransactionData(); 
        tData.setFunction("INPUT"); 
        tData.setSourceId("AA.COB"); 
        tData.setNumberOfAuthoriser("0"); 
        tData.setVersionId("FUNDS.TRANSFER,TASK5P3");
        currentRecords.add(fundsTransferRecord.toStructure()); 
        transactionData.add(tData);
    }
}
