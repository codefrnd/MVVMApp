package com.codefrnd.mvvmapp.ui.home.quotes

import com.codefrnd.mvvmapp.R
import com.codefrnd.mvvmapp.data.db.entities.Quote
import com.codefrnd.mvvmapp.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem(
    private val quote: Quote
) : BindableItem<ItemQuoteBinding>() {

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)
    }

    override fun getLayout(): Int = R.layout.item_quote
}
