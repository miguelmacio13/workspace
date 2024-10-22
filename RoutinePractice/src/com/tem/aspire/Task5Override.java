package com.tem.aspire;

import com.temenos.api.TField;
import com.temenos.api.TStructure;
import com.temenos.api.TValidationResponse;
import com.temenos.t24.api.complex.eb.templatehook.TransactionContext;
import com.temenos.t24.api.hook.system.RecordLifecycle;
import com.temenos.t24.api.records.customer.CustomerRecord;

/**
 * TODO: Document me!
 *
 * @author miguel.macias
 *
 */
public class Task5Override extends RecordLifecycle{
    
    
    @Override
    public TValidationResponse validateRecord(String application, String currentRecordId, TStructure currentRecord,
            TStructure unauthorisedRecord, TStructure liveRecord, TransactionContext transactionContext) {
        
       CustomerRecord customerRecord = new CustomerRecord(currentRecord);
       // Get customer's information
       TField familyName = customerRecord.getFamilyName();
       TField givenName = customerRecord.getGivenNames();
       //checking if Familyname have numeric case
       
       for(Character c : familyName.getValue().toCharArray()){
           if(Character.isDigit(c)){
               familyName.setOverride("TRG.TK5.OVERR");
               break;
           }
       }      
       //Checking if GivenName have numeric case
       for(Character c : givenName.getValue().toCharArray()){
           if(Character.isDigit(c)){
               familyName.setOverride("TRG.TK5.OVERR");
               break;
           }
       }
       return customerRecord.getValidationResponse();
    }
    
}
