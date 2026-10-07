package com.vk.directop.grandstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.vk.directop.grandstore.applist.AppListScreen
import com.vk.directop.grandstore.applist.Game
import com.vk.directop.grandstore.ui.theme.GrandStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GrandStoreTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppListScreen(
                        games = gamesList(),
                        modifier = Modifier.padding(innerPadding)
                    )
//                    DetailCardScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

fun gamesList(): List<Game> {
    return listOf(
        Game(
            "Resident Evil Requiem",
            "Capcom’s ninth mainline entry in the legendary survival horror franchise stands out as one of the definitive releases of the year. Resident Evil Requiem introduces dual",
            "Action",
            "https://www.film.ru/sites/default/files/styles/epsa_260x400/public/game/covers/1d84898b3a1fe0eeb0ac0fbb83cfff3c.jpg"
        ),
        Game(
            "Forza Horizon 6",
            "The critically acclaimed open-world racing series returns with Forza Horizon 6, transporting players to a highly detailed and visually stunning Japan setting. Featuring a massive roster of over 550 meticulously modeled vehicles, it immediately topped Metacritic charts upon its release. ",
            "Action",
            "https://i0.wp.com/dictionaryblog.cambridge.org/wp-content/uploads/2026/09/climate.jpg?ssl=1"
        ),
        Game(
            "Crimson Desert",
            "Pearl Abyss finally unleashed its massive open-world action RPG, Crimson Desert, to critical acclaim. Players follow the gritty story of Cliff, a mercenary leader navigating a brutal but beautiful sandbox landscape. ",
            "Action",
            "https://www.film.ru/sites/default/files/styles/epsa_260x400/public/game/covers/1d84898b3a1fe0eeb0ac0fbb83cfff3c.jpg"
        ),
        Game(
            "GTA 5",
            "Take five stars and win.",
            "Action",
            "https://i0.wp.com/dictionaryblog.cambridge.org/wp-content/uploads/2026/09/climate.jpg?ssl=1"
        ),
        Game(
            "Corsair: Sea wolfs",
            "Amazing game, where you can do many things^ open world.",
            "Action",
            "https://i0.wp.com/dictionaryblog.cambridge.org/wp-content/uploads/2026/09/climate.jpg?ssl=1"
        ),
        Game(
            "Corsair 2: Sea wolfs",
            "Developed by IO Interactive (the masterminds behind the modern Hitman trilogy), 007 First Light serves as a high-budget prequel following a young, resourceful James Bond just starting his espiona",
            "Action",
            "https://i0.wp.com/dictionaryblog.cambridge.org/wp-content/uploads/2026/09/climate.jpg?ssl=1"
        ),
        Game(
            "Corsair 3: Sea wolfs",
            "Amazing game, where you can do many things^ open world.",
            "Action",
            "https://i0.wp.com/dictionaryblog.cambridge.org/wp-content/uploads/2026/09/climate.jpg?ssl=1"
        ),
        Game(
            "Pokémon Pokopia",
            "Serving as a major showcase for Nintendo's Switch 2 hardware, Pokémon Pokopia reinvents the traditional Pokémon formula into a charming, cozy social simulation sandbox.",
            "Action",
            "https://newcdn.igromania.ru/editor/images/21/b504a942-5792-4602-8bd1-99fe133639b4.jpg"
        ),
        Game(
            "Esoteric Ebb",
            "Disco Elysium в мире Baldur’s Gate заказывали? Тогда добро пожаловать в Esoteric Ebb — изометрическую ролевую игру, в которой игрок раскрывает политический заговор накануне первых выборов городского правительства. Приключение состоит из напряжённых схваток, исход которых определяют броски «костей», а также разветвлённых диалогов с множеством выборов. Особый маги",
            "RPG",
            "https://newcdn.igromania.ru/editor/images/46/8472e3a6-b505-4f2b-b675-32e6530ef5fc.jpg"
        ),
    )
}
