package kr.ac.kumoh.prof.w1502prelazylist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import kr.ac.kumoh.prof.w1502prelazylist.SongViewModel.Song
import kr.ac.kumoh.prof.w1502prelazylist.ui.theme.W1502PreLazyListTheme

class MainActivity : ComponentActivity() {
    //data class Song(var title: String, var singer: String)
    //private val songs = mutableStateListOf<Song>()
    private lateinit var model: SongViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this)[SongViewModel::class.java]

        setContent {
            MyApp()
        }
    }

    @Composable
    fun MyApp() {
        W1502PreLazyListTheme {
            Column {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    onClick = {
                        //songs.add(Song("애인있어요", "이은미"))
                        model.requestSong()
                    }
                ) {
                    Text("로드")
                }
                SongList()
            }
        }
    }

    @Composable
    fun SongItem(index: Int, song: Song) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(255, 210, 210))
                .padding(8.dp)
                .clickable {
                    Toast
                        .makeText(application, song.title, Toast.LENGTH_LONG)
                        .show()
                },
        ) {
            AsyncImage(
                //model = "https://picsum.photos/300/300?random=$index",
                //model = SongViewModel.SERVER_URL + "/image/" + song.image,
                model = "${SongViewModel.SERVER_URL}/image/${song.image}",
                contentDescription = "노래 앨범 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    //.clip(CircleShape),
                    .clip(RoundedCornerShape(percent = 10)),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = song.title, fontSize = 30.sp)
                Text(text = song.singer, fontSize = 20.sp)
            }
        }
    }

    @Composable
    fun SongList() {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(4.dp),
        ) {
            itemsIndexed(model.songs)  { index, song ->
                SongItem(index, song)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    W1502PreLazyListTheme {
//        MyApp()
//    }
}