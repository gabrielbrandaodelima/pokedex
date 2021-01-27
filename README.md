
# Pokédex App 
* Pokédex App built with Kotlin and PokéApi

# About Pokédex App 🏻‍💻

* Created by [Gabriel Lima](https://www.linkedin.com/in/gabrielbrandaodelima/)
* gabrielblimapas@gmail.com

# Project Description
* An Android Application which lists Pokémons , using [PokéApi](https://pokeapi.co/) ;
* It scrolls infinitely.
* One can search pokémons by name or pokémon *id*
* One can see the details of each pokémon :
  * Name; 
  * Id;
  * Width; 
  * Height;
  * Base exp given;
  * Base Stats;
  * Moves list names
* One can Favourite a Pokémon and see it listed in [this webhook page](https://webhook.site/#!/23328ac8-0cf2-49f7-9bb2-78d8f38bc9a6/17407e91-ebb4-45a8-9fb4-50aa1f0f2ce5/1)

# Tech stack 
- [x] Minimum SDK level 21
- [x] Kotlin based, [Coroutines + Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [x] Android JetPack 
- [x] MVVM Architecture (View - Viewbinding - ViewModel - Model)
- [x] LiveData
- [x] Navigation
- [x] ViewModel
- [x] Viewbinding
- [x] Retrofit
- [x] Koin
- [x] Flow
- [x] Coroutines
- [x] [PokéApi](https://pokeapi.co/) 


# Project Approach

I tried to follow MVVM design patterns by using clean code and clean architecture principles. [Articles](https://github.com/gabrielbrandaodelima/pokedex/blob/master/README.md#thanks)

Since PokeAPI's pokémons listing endpoint, just gives us it's name and respective details url, I had to retrieve pokemons image on it's callback, making other HTTP requests.

For handling pokémons listing, I needed to fetch this list with name/url and, for each result, request it's detail by calling get pokemon details endpoint.

After retrieving every pokemon details, the list was saved on the ViewModel and the UI is updated with the LiveData, a local repository was not created.

For paging handler, it was controlled within the ViewModel, since it's not context-aware. Firstly the *offset* param which controls paging, was been incremented manually after LiveData's posting values to UI , but I got some thread-related issue which suddenly this increment's were multiplying unnecesarily. So I changed the *offset* control by retrieving the correct value from the *next* field returned by PokéAPI , which indicates the url for the next page.  

For searching, I made use of Kotlin extension functions for filtering local data while typing. If user submits, pressing enter, it calls getPokemonDetails API for retrieving pokemon info. 

# Third party libraries used 

* [Retrofit](https://square.github.io/retrofit/), for Http communications.

* [GSON](https://github.com/google/gson), for Json serializing.

* [Koin](https://insert-koin.io/), for dependency injection.

* [Picasso](https://github.com/square/picasso), for image loading.

* [Swipe Refresh Layout](https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout), for handling API paging refresh.

# Thanks
* [Saepul Nahwan](https://dribbble.com/saepulnahwan23) for his [Pokedex App design](https://dribbble.com/shots/6545819-Pokedex-App)
* [Kotlin-Pokedex](https://github.com/mrcsxsiq/Kotlin-Pokedex) for layout design and resources
* Kotlin clean architecture articles:
  * https://proandroiddev.com/kotlin-clean-architecture-1ad42fcd97fa
  * https://antonioleiva.com/clean-architecture-android/
  * https://fernandocejas.com/blog/engineering/2019-05-08-architecting-android-reloaded/
  * https://fernandocejas.com/blog/engineering/2015-07-18-architecting-android-the-evolution/
  * https://fernandocejas.com/blog/engineering/2014-09-03-architecting-android-the-clean-way/
