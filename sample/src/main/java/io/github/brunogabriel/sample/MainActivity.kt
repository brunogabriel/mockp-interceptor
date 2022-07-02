package io.github.brunogabriel.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.brunogabriel.mockpinterceptor.sample.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val repository by lazy {
        CharacterRepository(RetrofitProvider.provideService(CharacterService::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        binding.mockRequestButton.setOnClickListener {
            with(binding) {
                requestLayout.hide()
                progressBar.show()
            }
            showDataFromServer { repository.getMockCharacters() }
        }

        binding.requestButton.setOnClickListener {
            with(binding) {
                requestLayout.hide()
                progressBar.show()
            }
            showDataFromServer { repository.getCharacters() }
        }
    }

    private fun showDataFromServer(action: suspend () -> String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val response = withContext(Dispatchers.IO) {
                    try {
                        action()
                    } catch (error: Exception) {
                        error.message ?: "Error"
                    }
                }

                withContext(Dispatchers.Main) {
                    with(binding) {
                        apiTextView.text = response
                        progressBar.hide()
                        nestedScrollView.show()
                    }
                }
            }
        }
    }
}