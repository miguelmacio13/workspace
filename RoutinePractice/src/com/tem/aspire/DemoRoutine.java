package com.tem.aspire;

import com.temenos.api.TField;
import com.temenos.api.TStructure;
import com.temenos.api.TValidationResponse;
import com.temenos.t24.api.complex.eb.templatehook.TransactionContext;
import com.temenos.t24.api.hook.system.RecordLifecycle;
import com.temenos.t24.api.records.fundstransfer.FundsTransferRecord;

/**
 * TODO: Document me!
 *
 * @author miguel.macias
 *
 */
public class DemoRoutine extends RecordLifecycle{

    @Override
    public void defaultFieldValues(String application, String currentRecordId, TStructure currentRecord,
            TStructure unauthorisedRecord, TStructure liveRecord, TransactionContext transactionContext) {
        // TODO Auto-generated method stub

        FundsTransferRecord fundsTransaferRecord = new FundsTransferRecord();
        fundsTransaferRecord.setTransactionType("AC");
        currentRecord.set(fundsTransaferRecord.toStructure());
    }

    @Override
    public TValidationResponse validateRecord(String application, String currentRecordId, TStructure currentRecord,
            TStructure unauthorisedRecord, TStructure liveRecord, TransactionContext transactionContext) {
        // TODO Auto-generated method stub
        FundsTransferRecord fundsTransferRecord = new FundsTransferRecord();
        TField creditcurrency = fundsTransferRecord.getCreditCurrency();
        if(creditcurrency.getValue()!="EUR"){
            creditcurrency.setError("Credit Currency should be EUR");
        }
        return fundsTransferRecord.getValidationResponse();
    }
    
    
}
