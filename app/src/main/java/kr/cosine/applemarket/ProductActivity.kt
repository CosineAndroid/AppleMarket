package kr.cosine.applemarket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.cosine.applemarket.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {

    private val binding by lazy { ActivityProductBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }
}