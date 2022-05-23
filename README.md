# glef.icu
Web aplikacija koja omogućava da pronađete željeni film i isti gledate sa podešenim prevodom.

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
  * Registracija (aktivacija preko mail-a)
  * Pregled i pretraga filmova
  * Opcije skidanja filmova i gledanja istih sa podešenim prevodom
  
  ## Arhitektura sistema
  * User service (autentifikacija i autorizacija) - Go
  * Movie service (CRUD filmova) - Go
  * ScrapeMovie service (prikupljanje informacija o filmu) - Go
  * DownloadMovie service (skidanje filma i namještanje prevoda) - Python
  * Grading service - Rust
  * RecommendBasic service- Java
  * RecommendAdvanced service - Python (?)
  * Vue klijentska aplikacija
