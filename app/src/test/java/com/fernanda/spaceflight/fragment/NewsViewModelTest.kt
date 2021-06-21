package com.fernanda.spaceflight.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fernanda.spaceflight.network.response.NewResponse
import com.fernanda.spaceflight.repository.NewRepository
import com.fernanda.spaceflight.ui.fragment.NewsListener
import com.fernanda.spaceflight.ui.fragment.NewsViewModel
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner.Silent::class)
@ExperimentalCoroutinesApi
class NewsViewModelTest {
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @get : Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private lateinit var viewModel: NewsViewModel

    @Mock
    private lateinit var repository: NewRepository

    @Mock
    private lateinit var listener: NewsListener

    @Mock
    private lateinit var newsObserver: Observer<List<NewResponse>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        viewModel = NewsViewModel(repository)
    }

    @Test
    fun initViewModel() {
        val newsResponse: List<NewResponse> = arrayListOf()
        viewModel.listener = listener
        viewModel.newList.observeForever(newsObserver)
        viewModel.initViewModel()
        verify(newsObserver).onChanged(newsResponse)
        verify(listener).onSearch()
    }

    @Test
    fun saveClick() {
        viewModel.listener = listener
        viewModel.saveClick(NewResponse(id = "1", title = "test", summary = "teste descrição", newsSite = "",
                imageUrl = "", url = "", featured = false, events = arrayListOf(), launches = arrayListOf()))
    }

    @Test
    fun getNewsPage() = TestCoroutineDispatcher().runBlockingTest {
        val newsResponse: List<NewResponse> = arrayListOf()
        Mockito.`when`(repository.getNews(1, 1)).thenReturn(Response.success(newsResponse))

        viewModel.listener = listener
        viewModel.newList.observeForever(newsObserver)
        viewModel.getNewsPage(16)

        verify(newsObserver).onChanged(newsResponse)
    }
}