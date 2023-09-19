package com.example.testnestedscroll

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp



fun LazyListScope.LfsResult()
{

    items(10) {
        Text("Page s", modifier = Modifier.height(200.dp))
    }

}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun pager() {
    val pagerState = rememberPagerState(initialPage = 0)


    HorizontalPager(pageCount = 3, state = pagerState) {


        val scrollState = rememberLazyListState(0,10)

        LaunchedEffect(pagerState) {

            snapshotFlow { pagerState.currentPage }.collect { page ->
                scrollState.scrollToItem(0)
                //scrollState.scrollToItem(0)
            }
        }


            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(rememberNestedScrollInteropConnection()),
                /*state = scrollState*/
            ) {
                item { Text("Page $it / 3 ", modifier = Modifier.height(200.dp)) }
                LfsResult()

                items(100) {
                    Text("Item $it / 100", modifier = Modifier.height(100.dp))
                }
            }

    }
}