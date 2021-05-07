 package ru.bakhelovs.network.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.*
import ru.bakhelovs.network.*
import ru.bakhelovs.network.data.remote.retrofit.response.CatImageResponse
import ru.bakhelovs.network.model.CatsApp
import kotlin.random.Random

 class MainFragment : Fragment(R.layout.main_fragment) {

    private var catPhotoView: ImageView? = null
    private lateinit var viewModel: MainViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        catPhotoView = view.findViewById(R.id.catPhotoView)

        view.findViewById<Button>(R.id.loadCatView).setOnClickListener {
            loadCats()
        }
    }

     private var coroutineScope = createCoroutineScope()


     private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
         Log.e(TAG, "Coroutine exception, scope active:${coroutineScope.isActive}", throwable)
         coroutineScope = createCoroutineScope()
     }

     private fun loadCats() {
         coroutineScope.launch(exceptionHandler) {
             val cats = CatsApp.RetrofitModule.catsApi.getRandomImage()
             showRandomCat(cats)
         }
     }

     private suspend fun showRandomCat(catsImageResponses: List<CatImageResponse>) = withContext(Dispatchers.Main) {
         val randomIndex = Random.nextInt(catsImageResponses.size - 1)
         val randomCat = catsImageResponses[randomIndex]
         catPhotoView?.load(randomCat.imageUrl) {
             crossfade(true)
             placeholder(R.drawable.ic_download_progress)
             error(R.drawable.ic_download_error)
             transformations(CircleCropTransformation())
         }
     }


     private fun createCoroutineScope() = CoroutineScope(Job() + Dispatchers.IO)

    companion object {
        private val TAG = MainFragment::class.java.simpleName
    }

}