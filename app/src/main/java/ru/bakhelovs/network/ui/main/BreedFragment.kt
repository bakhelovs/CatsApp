package ru.bakhelovs.network.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import coil.load
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi
import ru.bakhelovs.network.data.remote.retrofit.response.CatImageResponse
import ru.bakhelovs.network.data.remote.retrofit.response.CatsBreedNamesResponse
import ru.bakhelovs.network.data.remote.retrofit.response.ResponseItem
import ru.bakhelovs.network.R
import ru.bakhelovs.network.databinding.FragmentBreedBinding
import ru.bakhelovs.network.model.*
import kotlin.random.Random

@ExperimentalSerializationApi
class BreedFragment : BaseFragment() {

    private var _binding: FragmentBreedBinding? = null
    private val binding get() = _binding!!
    private lateinit var breeds: List<CatsBreedNamesResponse>

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Coroutine exception, scope active:${coroutineScope.isActive}", throwable)
        coroutineScope = createCoroutineScope()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.catPhotoView
        binding.autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedBreed = breeds[position]
                loadChosenBreed(selectedBreed.id)

            }

        binding.loadCatView.setOnClickListener { loadRandomCat() }

        loadBreedNames()
    }

    // рандомная картинка
    private fun loadRandomCat() {
        coroutineScope.launch(exceptionHandler) {
            val cats = CatsApp.RetrofitModule.catsApi.getRandomImage()
            showRandomCat(cats)
        }
    }

    // список имен для адаптера ACTV
    private fun loadBreedNames() {
        coroutineScope.launch(exceptionHandler) {
            breeds = CatsApp.RetrofitModule.catsApi.getBreedNames()
            showBreedNames(breeds)
        }
    }

    // выбранная порода
    private fun loadChosenBreed(id: String) {
        coroutineScope.launch(exceptionHandler) {
            val chosenName = CatsApp.RetrofitModule.catsApi.getChosenBreedDetails(id)
            showChosenName(chosenName)
        }

    }

    private suspend fun showChosenName(chosenBreed: List<ResponseItem>) = withContext(Dispatchers.Main) {
        if (chosenBreed.isEmpty()) {
            Toast.makeText(requireContext(), "not found", Toast.LENGTH_SHORT).show()
            return@withContext
        }

        binding.catPhotoView.load(chosenBreed[0].url) {
            crossfade(true)
            placeholder(R.drawable.ic_download_progress)
            error(R.drawable.ic_download_error)
        }
    }

    private suspend fun showBreedNames(catsNamesResponse: List<CatsBreedNamesResponse>) =
        withContext(Dispatchers.Main){
            val catsNamesList = catsNamesResponse.map { it.name }
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, catsNamesList)
            binding.autoCompleteTextView.setAdapter(arrayAdapter)
        }

    private suspend fun showRandomCat(catsImageResponses: List<CatImageResponse>) = withContext(Dispatchers.Main) {
        val randomIndex = Random.nextInt(catsImageResponses.size - 1)
        val randomCat = catsImageResponses[randomIndex]
        binding.catPhotoView.load(randomCat.imageUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_download_progress)
            error(R.drawable.ic_download_error)
//            transformations(CircleCropTransformation())
        }
    }

    companion object {
        private val TAG = BreedFragment::class.java.simpleName
    }
}
