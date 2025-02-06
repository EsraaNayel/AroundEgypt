package com.esraa.aroundegypt.ui.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.esraa.aroundegypt.domain.models.Experience
import com.esraa.aroundegypt.ui.list.viewmodel.ExperienceViewModel


class ExperienceActivity : ComponentActivity() {
    private val viewModel by viewModels<ExperienceViewModel>(factoryProducer = {
        ExperienceViewModel.Factory
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Column(
                Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
            ) {
                HomeScreen(viewModel)
            }
        }
    }
}

@Composable
fun ExperienceCard(experience: Experience, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Box {
            Image(
                painter = rememberImagePainter(experience.cover_photo),
                contentDescription = experience.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )

            // Icons and Tags
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .background(Color.Blue.copy(alpha = 0.7f), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            ) {
                Text(text = "RECOMMENDED", color = Color.White, fontSize = 12.sp)
            }

            // Views and Likes
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Visibility, contentDescription = "Views", tint = Color.White
                )
                Text(text = experience.views_no.toString(), color = Color.White)
            }
        }
        Text(
            text = experience.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }


}

@Composable
fun RecommendedExperiences(
    recommendedExperiences: List<Experience>,
    onItemClick: (Experience) -> Unit
) {
    Text(text = "Recommended Experiences", fontSize = 20.sp, fontWeight = FontWeight.Bold)

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = rememberLazyListState()
        ) {
            items(recommendedExperiences) { experience ->
                ExperienceCard(experience, Modifier
                    .width(maxWidth - (maxWidth / 10))
                    .padding(8.dp)
                    .clickable { onItemClick(experience) })
            }
        }
    }
}

@Composable
fun MostRecentExperiences(recentExperiences: List<Experience>, onItemClick: (Experience) -> Unit) {
    Text(text = "Most Recent", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    LazyColumn {
        items(recentExperiences) { experience ->
            ExperienceCard(experience, Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onItemClick(experience) })


        }
    }

}

@Composable
fun HomeScreen(viewModel: ExperienceViewModel) {

    val recentExperiences by
    viewModel.recentExperiences.collectAsState()
    val recommendedExperiences by
    viewModel.recommendedExperiences.collectAsState()
    val searchResult by viewModel.searchResult.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var searchText by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedExperience: Experience? by remember { mutableStateOf(null) }

    Scaffold(

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    viewModel.searchExperiences(it)
                },
                placeholder = { Text("Try \"Luxor\"") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                Column() {

                    if (searchText.isEmpty()) {
                        Text(text = "Welcome!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = "Now you can explore any experience in 360 degrees and get all the details about it all in one place.",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }

                }
                // Show either Recommended/Recent OR Search Results
                val itemsToShow = if (searchText.isEmpty()) {
                    Column {
                        RecommendedExperiences(recommendedExperiences) { experience ->
                            selectedExperience = experience
                            showBottomSheet = true
                        }
                        MostRecentExperiences(recentExperiences) { experience ->
                            selectedExperience = experience
                            showBottomSheet = true
                        }
                    }

                } else {
                    LazyColumn {
                        items(searchResult) { experience ->
                            ExperienceCard(experience, Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    selectedExperience = experience
                                    showBottomSheet = true
                                })
                        }
                    }
                }
            }
        }

        // Bottom Sheet
        if (showBottomSheet && selectedExperience != null) {
            ExperienceDetails(selectedExperience!!) {
                showBottomSheet = false
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExperienceDetails(experience: Experience, onClose: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = onClose,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(experience.title, style = MaterialTheme.typography.headlineSmall)
            Image(
                painter = rememberAsyncImagePainter(model = experience.cover_photo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp)
            )
            Text(experience.title, style = MaterialTheme.typography.bodyMedium)

            // Like Button
            Button(onClick = { /*TODO*/ }) {
                Text("Like")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    HomeScreen(
        viewModel = TODO()
    )
}