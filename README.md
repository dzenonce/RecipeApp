#### _RecipeApp_ - приложение для просмотра рецептов


<img src="https://stage.androidsprint.ru/files/AfGoYLU.png" height="450"> <img src="https://stage.androidsprint.ru/files/a3kBAEi.png" height="450"> <img src="https://stage.androidsprint.ru/files/yQPivlr.png" height="450">

***Стек кратко:***
MVVM, ViewModel, Coroutine, OkHttp, Retrofit, Room, Di, Fragment, Glide, RecyclerView

***Стек подробнее:***
- Архитектура
	- Реализован паттерн *MVVM*, выделено несколько слоев
		- Все фрагменты подписаны на свой стейт, *ViewModel* получает данные из репозитория
	- Реализован паттерн *Repository*
		- запросы в сеть через *Retrofit* слушатель из *OkHttp*
		- кеш + избранное *Room*
	- Зависимости в viewModel 
		- Внедряются на данный момент в ручную, создание и хранение зависимостей, плюс инициализация viewModel'и (реализовано через фабрику) выполняется в AppContainer, подвязанный к жц всего приложения
	- Навигация с помощью Compose Navigation
	- Принцип Single-Activity
		- Одна Activity с основным контейнером для фрагментов, остальные экраны через Fragment
	- Асинхронные операции (Retrofit, Room) через *Coroutine*
