package ru.bakhelovs.network.ui.main

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import coil.load
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi
import ru.bakhelovs.network.R
import ru.bakhelovs.network.data.remote.CatsApp
import ru.bakhelovs.network.data.remote.retrofit.response.CatImageResponse
import ru.bakhelovs.network.data.remote.retrofit.response.CatsBreedNamesResponse
import ru.bakhelovs.network.data.remote.retrofit.response.ResponseItem
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
        binding.description
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

    private suspend fun showChosenName(chosenBreed: List<ResponseItem>) =
        withContext(Dispatchers.Main) {
            if (chosenBreed.isEmpty()) {
                Toast.makeText(requireContext(), "not found", Toast.LENGTH_SHORT).show()
                return@withContext
            }

            binding.catPhotoView.load(chosenBreed[0].url) {
                crossfade(true)
                placeholder(R.drawable.ic_download_progress)
                error(R.drawable.ic_download_error)
            }

            binding.description.text = chosenBreed[0].breeds!![0].description

            binding.wikipediaBtn.setOnClickListener {
                val wikiUrl = chosenBreed[0].breeds!![0].wikipediaUrl
                if (wikiUrl != null) {
                    openWebPage(wikiUrl)
                }
            }

            val affectionStarsImages = listOf<ImageView?>(
                binding.affectionStar1Image,
                binding.affectionStar2Image,
                binding.affectionStar3Image,
                binding.affectionStar4Image,
                binding.affectionStar5Image
            )
            affectionStarsImages.forEachIndexed { index, imageView ->
                imageView?.let {
                    val colorId =
                        if (chosenBreed[0].breeds!![0].affectionLevel!! > index) R.color.pink_light else R.color.gray_dark
                    ImageViewCompat.setImageTintList(
                        imageView, ColorStateList.valueOf(
                            ContextCompat.getColor(imageView.context, colorId)
                        )
                    )
                }
            }

            val adaptabilityStarsImages = listOf<ImageView?>(
                binding.adaptabilityStar1Image,
                binding.adaptabilityStar2Image,
                binding.adaptabilityStar3Image,
                binding.adaptabilityStar4Image,
                binding.adaptabilityStar5Image
            )
            adaptabilityStarsImages.forEachIndexed { index, imageView ->
                imageView?.let {
                    val colorId =
                        if (chosenBreed[0].breeds!![0].adaptability!! > index) R.color.pink_light else R.color.gray_dark
                    ImageViewCompat.setImageTintList(
                        imageView, ColorStateList.valueOf(
                            ContextCompat.getColor(imageView.context, colorId)
                        )
                    )
                }
            }

            val childFriendlyStarsImages = listOf<ImageView?>(
                binding.childFriendlyStar1Image,
                binding.childFriendlyStar2Image,
                binding.childFriendlyStar3Image,
                binding.childFriendlyStar4Image,
                binding.childFriendlyStar5Image
            )
            childFriendlyStarsImages.forEachIndexed { index, imageView ->
                imageView?.let {
                    val colorId =
                        if (chosenBreed[0].breeds!![0].childFriendly!! > index) R.color.pink_light else R.color.gray_dark
                    ImageViewCompat.setImageTintList(
                        imageView, ColorStateList.valueOf(
                            ContextCompat.getColor(imageView.context, colorId)
                        )
                    )
                }
            }

            val dogFriendlyStarsImages = listOf<ImageView?>(
                binding.dogFriendlyStar1Image,
                binding.dogFriendlyStar2Image,
                binding.dogFriendlyStar3Image,
                binding.dogFriendlyStar4Image,
                binding.dogFriendlyStar5Image
            )
            dogFriendlyStarsImages.forEachIndexed { index, imageView ->
                imageView?.let {
                    val colorId =
                        if (chosenBreed[0].breeds!![0].dogFriendly!! > index) R.color.pink_light else R.color.gray_dark
                    ImageViewCompat.setImageTintList(
                        imageView, ColorStateList.valueOf(
                            ContextCompat.getColor(imageView.context, colorId)
                        )
                    )
                }
            }

            val energyStarsImages = listOf<ImageView?>(
                binding.energyStar1Image,
                binding.energyStar2Image,
                binding.energyStar3Image,
                binding.energyStar4Image,
                binding.energyStar5Image
            )
            energyStarsImages.forEachIndexed { index, imageView ->
                imageView?.let {
                    val colorId =
                        if (chosenBreed[0].breeds!![0].energyLevel!! > index) R.color.pink_light else R.color.gray_dark
                    ImageViewCompat.setImageTintList(
                        imageView, ColorStateList.valueOf(
                            ContextCompat.getColor(imageView.context, colorId)
                        )
                    )
                }
            }

            val groomingStarsImages = listOf<ImageView?>(
                binding.groomingStar1Image,
                binding.groomingStar2Image,
                binding.groomingStar3Image,
                binding.groomingStar4Image,
                binding.groomingStar5Image
            )
            groomingStarsImages.forEachIndexed { index, imageView ->
                imageView?.let {
                    val colorId =
                        if (chosenBreed[0].breeds!![0].grooming!! > index) R.color.pink_light else R.color.gray_dark
                    ImageViewCompat.setImageTintList(
                        imageView, ColorStateList.valueOf(
                            ContextCompat.getColor(imageView.context, colorId)
                        )
                    )
                }
            }

            val healthIssuesStarsImages = listOf<ImageView?>(
                binding.healthStar1Image,
                binding.healthStar2Image,
                binding.healthStar3Image,
                binding.healthStar4Image,
                binding.healthStar5Image
            )
            healthIssuesStarsImages.forEachIndexed { index, imageView ->
                imageView?.let {
                    val colorId =
                        if (chosenBreed[0].breeds!![0].healthIssues!! > index) R.color.pink_light else R.color.gray_dark
                    ImageViewCompat.setImageTintList(
                        imageView, ColorStateList.valueOf(
                            ContextCompat.getColor(imageView.context, colorId)
                        )
                    )
                }
            }

            val intelligenceStarsImages = listOf<ImageView?>(
                binding.intelligenceStar1Image,
                binding.intelligenceStar2Image,
                binding.intelligenceStar3Image,
                binding.intelligenceStar4Image,
                binding.intelligenceStar5Image
            )
            intelligenceStarsImages.forEachIndexed { index, imageView ->
                imageView?.let {
                    val colorId =
                        if (chosenBreed[0].breeds!![0].intelligence!! > index) R.color.pink_light else R.color.gray_dark
                    ImageViewCompat.setImageTintList(
                        imageView, ColorStateList.valueOf(
                            ContextCompat.getColor(imageView.context, colorId)
                        )
                    )
                }
            }

            val sheddingStarsImages = listOf<ImageView?>(
                binding.sheddingStar1Image,
                binding.sheddingStar2Image,
                binding.sheddingStar3Image,
                binding.sheddingStar4Image,
                binding.sheddingStar5Image
            )
            sheddingStarsImages.forEachIndexed { index, imageView ->
                imageView?.let {
                    val colorId =
                        if (chosenBreed[0].breeds!![0].sheddingLevel!! > index) R.color.pink_light else R.color.gray_dark
                    ImageViewCompat.setImageTintList(
                        imageView, ColorStateList.valueOf(
                            ContextCompat.getColor(imageView.context, colorId)
                        )
                    )
                }
            }

            val socialNeedsStarsImages = listOf<ImageView?>(
                binding.socialStar1Image,
                binding.socialStar2Image,
                binding.socialStar3Image,
                binding.socialStar4Image,
                binding.socialStar5Image
            )
            socialNeedsStarsImages.forEachIndexed { index, imageView ->
                imageView?.let {
                    val colorId =
                        if (chosenBreed[0].breeds!![0].socialNeeds!! > index) R.color.pink_light else R.color.gray_dark
                    ImageViewCompat.setImageTintList(
                        imageView, ColorStateList.valueOf(
                            ContextCompat.getColor(imageView.context, colorId)
                        )
                    )
                }
            }

            val strangerFriendlyStarsImages = listOf<ImageView?>(
                binding.strangerStar1Image,
                binding.strangerStar2Image,
                binding.strangerStar3Image,
                binding.strangerStar4Image,
                binding.strangerStar5Image
            )
            strangerFriendlyStarsImages.forEachIndexed { index, imageView ->
                imageView?.let {
                    val colorId =
                        if (chosenBreed[0].breeds!![0].strangerFriendly!! > index) R.color.pink_light else R.color.gray_dark
                    ImageViewCompat.setImageTintList(
                        imageView, ColorStateList.valueOf(
                            ContextCompat.getColor(imageView.context, colorId)
                        )
                    )
                }
            }

            val vocalisationStarsImages = listOf<ImageView?>(
                binding.vocalisationStar1Image,
                binding.vocalisationStar2Image,
                binding.vocalisationStar3Image,
                binding.vocalisationStar4Image,
                binding.vocalisationStar5Image
            )
            vocalisationStarsImages.forEachIndexed { index, imageView ->
                imageView?.let {
                    val colorId =
                        if (chosenBreed[0].breeds!![0].vocalisation!! > index) R.color.pink_light else R.color.gray_dark
                    ImageViewCompat.setImageTintList(
                        imageView, ColorStateList.valueOf(
                            ContextCompat.getColor(imageView.context, colorId)
                        )
                    )
                }
            }

        }

    private suspend fun showBreedNames(catsNamesResponse: List<CatsBreedNamesResponse>) =
        withContext(Dispatchers.Main) {
            val catsNamesList = catsNamesResponse.map { it.name }
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, catsNamesList)
            binding.autoCompleteTextView.setAdapter(arrayAdapter)
        }

    private suspend fun showRandomCat(catsImageResponses: List<CatImageResponse>) =
        withContext(Dispatchers.Main) {
            val randomIndex = Random.nextInt(catsImageResponses.size - 1)
            val randomCat = catsImageResponses[randomIndex]
            binding.catPhotoView.load(randomCat.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_download_progress)
                error(R.drawable.ic_download_error)
//            transformations(CircleCropTransformation())
            }
        }

    private fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(context?.packageManager!!) != null) {
            startActivity(intent)
        }
    }

    companion object {
        private val TAG = BreedFragment::class.java.simpleName
    }
}
