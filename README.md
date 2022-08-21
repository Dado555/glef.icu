# glef.icu
Web aplikacija koja omogućava pronalazak filma (pregled informacija, skidanje prevoda i magneta za film, gledanje filma diretno preko vlc player-a), čuvanje filmova u watchlist i wishlist, komentarisanje pogledanih filmova, prijavljivanje komentara.

# Sistemi bazirani na znanju
[Specifikacija projekta](https://docs.google.com/document/d/13aGeEoK-gUN0zRUV3aFMgi_D_Zd76NPKR3Guwiu3iQU/edit?usp=sharing) - [pdf](https://drive.google.com/file/d/1yQjLVlQDuV4WAOOct50uf6ivvDXjHu0V/view?usp=sharing)

Preporuka filma bazirana na:
  - osobinama korisnika (pol, godine, omiljena radnja)
  - istoriji pogledanih filmova
  - istoriji ocjenjenih filmova
  - listi želja
  - podataka prikupljenih sa IMDB/Rotten Tomatoes 

# Napredne tehnike programiranja
  ## Funkcionalnosti
  1. Administrator
  * Pregled i pretraga filmova, korisnika
  * Pregled i brisanje komentara i ocjena datih od strane korisnika
  * Banovanje korisnika, čiji su komentari prijavljeni kao nedolični, na određeni vremenski period
  * Dodavanje novih filmova (download link (yts.mx), link za titl (titlovi.com), imdb link)
  * Izmjena postojećih filmova
  2. Registrovani korisnik
  * Prijava
  * Pregled profila i izmjena korisničkih informacija
  * Pregled i izmjena pogledanih i ocjenjenih filmova
  * Ocjenjivanje i komentarisanje pogledanih filmova
  * Prijava neprikladnog komentara
  * Pregled i pretraga svih dostupnih filmova
  * Dobijanje preporuke za gledanje od strane sistema 
     * osnovna preporuka - [SBNZ](#sistemi-bazirani-na-znanju "Sistemi bazirani na znanju")
     * (?) napredna preporuka - mašinsko, korisnik bi unio neke od sljedećih stavki (i na osnovu opisa/unosa sistem bi izbacio listu preporuka):
       * žanr filma
       * godina snimanja
       * glumac (glavni, sporedni..)
       * radnja (pas daleko od kuće) 
  3. Neregistrovani korisnik
  * Registracija
  * Pregled i pretraga filmova
  * Opcije skidanja magneta i gledanja filmova (gledanje sa podešenim prevodom?)
  
  ## Arhitektura sistema
  * user-service (autentifikacija i autorizacija) - Go
  * scrape-movie-service (CRUD filmova, prikupljanje informacija o filmu, skidanje magneta i prevoda, gledanje filma) - Go
  * user-movies-service (manipulacija korisnikovih wishlist i watchlist) - Go
  * merge-movie-subtitle-service (namještanje prevoda za skinuti film i prevod) - Python
  * grading-service (komentari i ocjene za film, banovanje korisnika) - Rust
  * RecommendBasic service- Java
  * RecommendAdvanced service - Python (?)
  * Vue klijentska aplikacija
