Rejestracja Użytkownika:

POST /api/register
Parametry: Dane użytkownika (np. email, hasło)
Opis: Endpoint służący do rejestracji nowego użytkownika w systemie.
Logowanie Użytkownika:

POST /api/login
Parametry: Dane logowania (np. email, hasło)
Opis: Endpoint umożliwiający użytkownikowi zalogowanie się do systemu.
Tłumaczenie Tekstu:

POST /api/translate
Parametry: Tekst źródłowy, język źródłowy, język docelowy
Opis: Endpoint przetwarzający żądanie tłumaczenia tekstu z jednego języka na inny.
Podsumowanie Tekstu:

POST /api/summarize
Parametry: Tekst źródłowy, preferowana długość podsumowania
Opis: Endpoint generujący krótkie podsumowanie tekstu na podstawie preferencji użytkownika.
Pobieranie Ustawień Użytkownika:

GET /api/user/settings
Parametry: Token autoryzacyjny (jeśli wymagane)
Opis: Endpoint zwracający ustawienia użytkownika, takie jak preferowany język tłumaczenia czy długość podsumowania.
Aktualizacja Ustawień Użytkownika:

PUT /api/user/settings
Parametry: Token autoryzacyjny, Nowe ustawienia użytkownika
Opis: Endpoint pozwalający użytkownikowi zaktualizować swoje ustawienia w systemie.
Historia Tłumaczeń i Podsumowań:

GET /api/user/history
Parametry: Token autoryzacyjny
Opis: Endpoint zwracający historię tłumaczeń i generowania podsumowań użytkownika.
Wylogowanie Użytkownika:

POST /api/logout
Parametry: Token autoryzacyjny
Opis: Endpoint umożliwiający użytkownikowi wylogowanie się z systemu.
Przywracanie Hasła:

POST /api/forgot-password
Parametry: Email użytkownika
Opis: Endpoint inicjujący proces przywracania hasła dla użytkownika.
Zmiana Hasła:

PUT /api/change-password
Parametry: Token autoryzacyjny, Nowe hasło
Opis: Endpoint pozwalający użytkownikowi zmienić swoje hasło.
