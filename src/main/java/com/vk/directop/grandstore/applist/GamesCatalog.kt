package com.vk.directop.grandstore.applist

object GamesCatalog {
    val games: List<Game> = listOf(
        Game(
            id = "resident-evil-requiem",
            title = "Resident Evil Requiem",
            description = "Capcom’s ninth mainline entry in the legendary survival horror franchise stands out as one of the definitive releases of the year. Resident Evil Requiem introduces dual",
            category = "Action",
            image = "https://www.film.ru/sites/default/files/styles/epsa_260x400/public/game/covers/1d84898b3a1fe0eeb0ac0fbb83cfff3c.jpg"
        ),
        Game(
            id = "forza-horizon-6",
            title = "Forza Horizon 6",
            description = "The critically acclaimed open-world racing series returns with Forza Horizon 6, transporting players to a highly detailed and visually stunning Japan setting. Featuring a massive roster of over 550 meticulously modeled vehicles, it immediately topped Metacritic charts upon its release. ",
            category = "Action",
            image = "https://i0.wp.com/dictionaryblog.cambridge.org/wp-content/uploads/2026/09/climate.jpg?ssl=1"
        ),
        Game(
            id = "crimson-desert",
            title = "Crimson Desert",
            description = "Pearl Abyss finally unleashed its massive open-world action RPG, Crimson Desert, to critical acclaim. Players follow the gritty story of Cliff, a mercenary leader navigating a brutal but beautiful sandbox landscape. ",
            category = "Action",
            image = "https://www.film.ru/sites/default/files/styles/epsa_260x400/public/game/covers/1d84898b3a1fe0eeb0ac0fbb83cfff3c.jpg"
        ),
        Game(
            id = "gta-5",
            title = "GTA 5",
            description = "Take five stars and win.",
            category = "Action",
            image = "https://i0.wp.com/dictionaryblog.cambridge.org/wp-content/uploads/2026/09/climate.jpg?ssl=1"
        ),
        Game(
            id = "corsair-sea-wolfs",
            title = "Corsair: Sea wolfs",
            description = "Amazing game, where you can do many things^ open world.",
            category = "Action",
            image = "https://i0.wp.com/dictionaryblog.cambridge.org/wp-content/uploads/2026/09/climate.jpg?ssl=1"
        ),
        Game(
            id = "corsair-2",
            title = "Corsair 2: Sea wolfs",
            description = "Developed by IO Interactive (the masterminds behind the modern Hitman trilogy), 007 First Light serves as a high-budget prequel following a young, resourceful James Bond just starting his espiona",
            category = "Action",
            image = "https://i0.wp.com/dictionaryblog.cambridge.org/wp-content/uploads/2026/09/climate.jpg?ssl=1"
        ),
        Game(
            id = "corsair-3",
            title = "Corsair 3: Sea wolfs",
            description = "Amazing game, where you can do many things^ open world.",
            category = "Action",
            image = "https://i0.wp.com/dictionaryblog.cambridge.org/wp-content/uploads/2026/09/climate.jpg?ssl=1"
        ),
        Game(
            id = "pokemon-pokopia",
            title = "Pokémon Pokopia",
            description = "Serving as a major showcase for Nintendo's Switch 2 hardware, Pokémon Pokopia reinvents the traditional Pokémon formula into a charming, cozy social simulation sandbox.",
            category = "Action",
            image = "https://newcdn.igromania.ru/editor/images/21/b504a942-5792-4602-8bd1-99fe133639b4.jpg"
        ),
        Game(
            id = "esoteric-ebb",
            title = "Esoteric Ebb",
            description = "Disco Elysium в мире Baldur’s Gate заказывали? Тогда добро пожаловать в Esoteric Ebb — изометрическую ролевую игру, в которой игрок раскрывает политический заговор накануне первых выборов городского правительства. Приключение состоит из напряжённых схваток, исход которых определяют броски «костей», а также разветвлённых диалогов с множеством выборов. Особый маги",
            category = "RPG",
            image = "https://newcdn.igromania.ru/editor/images/46/8472e3a6-b505-4f2b-b675-32e6530ef5fc.jpg"
        ),
    )

    fun find(id: String): Game? = games.find { it.id == id }
}
