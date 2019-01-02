package org.walleth.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import org.walleth.R
import org.walleth.data.AppDatabase
import org.walleth.data.config.Settings
import org.walleth.data.exchangerate.ExchangeRateProvider
import org.walleth.data.networks.NetworkDefinitionProvider
import org.walleth.data.transactions.TransactionEntity

enum class TransactionAdapterDirection {
    INCOMING, OUTGOING
}

class TransactionRecyclerAdapter(private val transactionList: List<TransactionEntity>,
                                 val appDatabase: AppDatabase,
                                 private val direction: TransactionAdapterDirection,
                                 val networkDefinitionProvider: NetworkDefinitionProvider,
                                 val exchangeRateProvider: ExchangeRateProvider,
                                 val settings: Settings
) : RecyclerView.Adapter<TransactionViewHolder>() {

    override fun getItemCount() = transactionList.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) = holder.bind(transactionList[position], appDatabase)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return TransactionViewHolder(itemView, direction, networkDefinitionProvider, exchangeRateProvider, settings)
    }

}