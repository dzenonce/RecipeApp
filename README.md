#### _RecipeApp_ - приложение для просмотра рецептов


<img src="https://github.com/dzenonce/RecipeApp/blob/master/preview/recipeMain.png" height="450"> <img src="https://github.com/dzenonce/RecipeApp/blob/master/preview/recipeRecipesList.png" height="450"> <img src="https://github.com/dzenonce/RecipeApp/blob/master/preview/recipeFavorites.png" height="450">

***Стек кратко:***
MVVM, ViewModel, Coroutine, OkHttp, Retrofit, Room, Di, Fragment, Glide, RecyclerView

***Стек подробнее:***
- Архитектура
	- Реализован паттерн *MVVM*, выделено несколько слоев
		- Все фрагменты подписаны на свой стейт, *ViewModel* получает данные из репозитория
	- Реализован паттерн *Repository*
		- запросы в сеть через *Retrofit* слушатель из *OkHttp*
		- кеш + избранное *Room*
	- DI - Hilt
	- Навигация с помощью Compose Navigation
	- Принцип Single-Activity
		- Одна Activity с основным контейнером для фрагментов, остальные экраны через Fragment
	- Асинхронные операции (Retrofit, Room) через *Coroutine*
	- Этот репозиторий содержит 2 версии проекта
   		- Классический XML
       		- Переписанный на Compose
