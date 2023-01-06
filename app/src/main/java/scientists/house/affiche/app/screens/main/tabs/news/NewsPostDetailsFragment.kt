package scientists.house.affiche.app.screens.main.tabs.news

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import scientists.house.affiche.app.R
import scientists.house.affiche.app.databinding.FragmentNewsPostDetailsBinding
import scientists.house.affiche.app.model.DataHolder
import scientists.house.affiche.app.model.news.entities.NewsDetailedPost
import scientists.house.affiche.app.screens.base.BaseFragment
import scientists.house.affiche.app.utils.viewModelCreator
import scientists.house.affiche.app.utils.visible

@AndroidEntryPoint
class NewsPostDetailsFragment : BaseFragment(R.layout.fragment_news_post_details) {

    @Inject
    lateinit var factory: NewsPostDetailsViewModel.Factory
    override val viewModel by viewModelCreator {
        factory.create(args.postLink)
    }

    private lateinit var binding: FragmentNewsPostDetailsBinding

    private val args by navArgs<NewsPostDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNewsPostDetailsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setupViews() {
        super.setupViews()
        binding.apply {
            fnpdIError.veMbTryAgain.setOnClickListener {
                viewModel.load(args.postLink)
            }
        }
    }

    override fun observeViewModel() {
        viewModel.newsDetailedPost.observe(viewLifecycleOwner) { holder ->
            when (holder) {
                DataHolder.LOADING -> {
                    binding.apply {
                        fnpdILoading.root.visible = true
                        fnpdIError.root.visible = false
                        fnewsLlContent.visible = false
                    }
                }
                is DataHolder.READY -> {
                    binding.apply {
                        fnpdILoading.root.visible = false
                        fnpdIError.root.visible = false
                        fnewsLlContent.visible = true
                    }

                    setupDetailedNews(holder.data)
                }
                is DataHolder.ERROR -> {
                    binding.apply {
                        fnpdILoading.root.visible = false
                        fnpdIError.root.visible = true
                        fnewsLlContent.visible = false
                    }
                }
            }
        }
    }

    private fun setupDetailedNews(data: NewsDetailedPost) {
        binding.apply {
            data.title?.let {
                fnewsMtvTitle.text = it
            } ?: run {
                fnewsMtvTitle.visible = false
            }

            data.about?.let {
                fnewsMtvAbout.text = it
            } ?: run {
                fnewsMtvAbout.visible = false
            }

            data.date?.let {
                fnewsMtvDate.text = it
            } ?: run {
                fnewsMtvDate.visible = false
            }

        }
    }
}