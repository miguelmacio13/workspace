package com.tem.aspire;

import com.temenos.api.TField;
import com.temenos.api.TStructure;
import com.temenos.api.TValidationResponse;
import com.temenos.t24.api.complex.aa.activityhook.ArrangementContext;
import com.temenos.t24.api.hook.arrangement.ActivityLifecycle;
import com.temenos.t24.api.records.aaaccountdetails.AaAccountDetailsRecord;
import com.temenos.t24.api.records.aaarrangement.AaArrangementRecord;
import com.temenos.t24.api.records.aaarrangementactivity.AaArrangementActivityRecord;
import com.temenos.t24.api.records.aaprddestermamount.AaPrdDesTermAmountRecord;
import com.temenos.t24.api.records.aaproductcatalog.AaProductCatalogRecord;

/**
 * TODO: Document me!
 *
 * @author miguel.macias
 *
 */
public class ValidateALC extends ActivityLifecycle{

    @Override
    public TValidationResponse validateRecord(AaAccountDetailsRecord accountDetailRecord,
            AaArrangementActivityRecord arrangementActivityRecord, ArrangementContext arrangementContext,
            AaArrangementRecord arrangementRecord, AaArrangementActivityRecord masterActivityRecord,
            TStructure productPropertyRecord, AaProductCatalogRecord productRecord, TStructure record) {
        
        AaPrdDesTermAmountRecord termAmount = new AaPrdDesTermAmountRecord(record);
        
        String amountT = termAmount.getAmount().getValue();
        int amountI = Integer.parseInt(amountT);
        
        if (amountI == 20000){
            termAmount.getLocalRefField("PROOFID").setValue("1"); 
        } else if(amountI == 10000){
            termAmount.getLocalRefField("PROOFID").setValue("2");
        } else if(amountI > 50000){
            termAmount.getLocalRefField("PROOFID").setValue("3");
        } else if (amountI < 30000){
            termAmount.getLocalRefField("PROOFID").setValue("4");
        }else{
            TField loanValue = termAmount.getAmount();
            loanValue.setError("EB-ERR-TERM");
        }
        
        return termAmount.getValidationResponse();
    }
    
}
