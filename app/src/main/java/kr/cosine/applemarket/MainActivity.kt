package kr.cosine.applemarket

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kr.cosine.applemarket.adapter.ProductAdapter
import kr.cosine.applemarket.databinding.ActivityMainBinding
import kr.cosine.applemarket.registry.ProductRegistry

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val productRegistry by lazy { ProductRegistry.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView() = with(binding.productRecyclerView) {
        val dividerItemDecoration = DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL)
        addItemDecoration(dividerItemDecoration)
        adapter = ProductAdapter(productRegistry.products)
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}