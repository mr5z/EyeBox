package com.nkraft.eyebox.activities;

import android.content.Intent;

import com.nkraft.eyebox.adapters.BaseListAdapter;
import com.nkraft.eyebox.adapters.ClientsAdapter;
import com.nkraft.eyebox.models.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientsActivity extends ListActivity implements ClientsAdapter.ItemClickListener {

    @Override
    BaseListAdapter getAdapter() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client() {{ setClientName("888 Pharma (Axon)"); setClientAddress("Quezon City"); }});
        clients.add(new Client() {{ setClientName("ACJM Medic Plus Ent."); setClientAddress("Philippines"); }});
        clients.add(new Client() {{ setClientName("AD-Drugstel"); setClientAddress("Quezon City"); }});
        ClientsAdapter adapter = new ClientsAdapter(clients);
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    public void onItemClick(Client data) {
        startActivity(new Intent(this, ProductsActivity.class));
    }
}
