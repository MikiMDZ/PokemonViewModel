package com.examen.pokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.examen.pokemon.databinding.ActivityMainBinding


var listapokemons: List<Result>? = null

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ActivityMainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url = "https://pokeapi.co/api/v2/pokemon?limit=100&offset=0"
        viewModel.descargarPokemons(url)
        initObserver()
    }

    private fun initObserver() {
        viewModel.isVisible.observe(this) { isVisible ->
            if (isVisible) setVisible() else setGone()
        }

        viewModel.responseText.observe(this) { responseText ->
            showToast(responseText)
        }

        viewModel.responseList.observe(this) {
            setRecycler(it as MutableList<Result>)
        }
    }
    private fun setVisible() {
        binding.pbDownloading.visibility = View.VISIBLE
    }

    private fun setGone() {
        binding.pbDownloading.visibility = View.GONE
    }

    private fun showToast(text: String) {
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()

    }

    private fun setRecycler(lista: MutableList<Result>) {

        val adapter = RecyclerAdapter(lista)


        listapokemons = lista
        binding.recycler.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.recycler.adapter = adapter
    }
}