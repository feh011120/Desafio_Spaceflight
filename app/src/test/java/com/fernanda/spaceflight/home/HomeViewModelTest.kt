package com.fernanda.spaceflight.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fernanda.spaceflight.network.response.NewResponse
import com.fernanda.spaceflight.repository.NewRepository
import com.fernanda.spaceflight.ui.home.HomeListener
import com.fernanda.spaceflight.ui.home.HomeViewModel
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase
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
class HomeViewModelTest : TestCase() {
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @get : Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var repository: NewRepository

    @Mock
    private lateinit var listener: HomeListener

    @Mock
    private lateinit var newsObserver: Observer<List<NewResponse>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun getNewsError() = TestCoroutineDispatcher().runBlockingTest {
        val newsResponse: List<NewResponse> = arrayListOf()

        Mockito.`when`(repository.getNews(1, 1)).thenReturn(Response.success(newsResponse))

        viewModel.listener = listener
        viewModel.newList.observeForever(newsObserver)
        viewModel.getNews()
        verify(newsObserver).onChanged(newsResponse)
    }
}