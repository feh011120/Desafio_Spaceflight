package com.fernanda.spaceflight.dialog

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fernanda.spaceflight.network.response.NewResponse
import com.fernanda.spaceflight.repository.NewRepository
import com.fernanda.spaceflight.ui.dialog.DialogDetailViewModel
import com.fernanda.spaceflight.ui.dialog.DialogListener
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
@ExperimentalCoroutinesApi
class DialogDetailViewModelTest {

    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @get : Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private lateinit var viewModel: DialogDetailViewModel

    @Mock
    private lateinit var repository: NewRepository

    @Mock
    private lateinit var listener: DialogListener

    @Mock
    private lateinit var newsObserver: Observer<List<NewResponse>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        viewModel = DialogDetailViewModel(repository)
    }

    @Test
    fun getClick() {
        val newsResponse: List<NewResponse> = arrayListOf()
        viewModel.listener = listener
        viewModel.getClick()
    }

    @Test
    fun onNavigate(view: View) {
        viewModel.listener = listener
        verify(listener).onNavigate("https://www.spaceflightnewsapi.net/documentation#/Article/get_api_v2_articles")

    }

    @Test
    fun onClose(view: View) {
        viewModel.listener = listener
        viewModel.news.observeForever(newsObserver)
        viewModel.onClose(view)
        verify(listener).onClose()
    }

}


