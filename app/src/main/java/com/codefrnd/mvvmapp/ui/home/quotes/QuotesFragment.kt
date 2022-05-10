package com.codefrnd.mvvmapp.ui.home.quotes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codefrnd.mvvmapp.R
import com.codefrnd.mvvmapp.data.db.entities.Quote
import com.codefrnd.mvvmapp.uitl.Coroutines
import com.codefrnd.mvvmapp.uitl.hide
import com.codefrnd.mvvmapp.uitl.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.quotes_fragment.*
import org.kodein.di.android.x.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private val factory: QuotesViewModelFactory by instance()
    private lateinit var viewModel: QuotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[QuotesViewModel::class.java]

        bindUI()
    }

    private fun bindUI() = Coroutines.main {
        progress_quotes.show()
        viewModel.quotes.await().observe(viewLifecycleOwner , Observer {
            progress_quotes.hide()
            initRecyclerView(it.toQuoteItem())
        })
    }

    private fun List<Quote>.toQuoteItem() : List<QuoteItem> {
        return this.map {
            QuoteItem(it)
        }
    }

    private fun initRecyclerView(toQuoteItem: List<QuoteItem>) {
        val adp = GroupAdapter<GroupieViewHolder>().apply {
            addAll(toQuoteItem)
        }

        quote_rv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adp
        }
    }

}