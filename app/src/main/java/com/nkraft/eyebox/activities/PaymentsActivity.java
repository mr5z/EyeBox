package com.nkraft.eyebox.activities;

import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.PaymentsAdapter;
import com.nkraft.eyebox.models.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentsActivity extends ListActivity {

    @Override
    BaseListAdapter getAdapter() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment() {{ setClientName("888 Pharma (Axon)"); setClientAddress("Quezon City"); }});
        payments.add(new Payment() {{ setClientName("ACJM Medic Plus Ent."); setClientAddress("Philippines"); }});
        payments.add(new Payment() {{ setClientName("AD-Drugstel"); setClientAddress("Quezon City"); }});
        return new PaymentsAdapter(payments);
    }
}
