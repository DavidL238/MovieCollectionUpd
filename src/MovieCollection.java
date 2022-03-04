import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName)

  {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies()
  {
    return movies;
  }
  
  public void menu()
  {
    String menuOption = "";
    
    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");
    
    while (!menuOption.equals("q"))
    {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();
      
      if (!menuOption.equals("q"))
      {
        processOption(menuOption);
      }
    }
  }
  
  private void processOption(String option)
  {
    if (option.equals("t"))
    {
      searchTitles();
    }
    else if (option.equals("c"))
    {
      searchCast();
    }
    else if (option.equals("k"))
    {
      searchKeywords();
    }
    else if (option.equals("g"))
    {
      listGenres();
    }
    else if (option.equals("r"))
    {
      listHighestRated();
    }
    else if (option.equals("h"))
    {
      listHighestRevenue();
    }
    else
    {
      System.out.println("Invalid choice!");
    }
  }
  
  private void searchTitles()
  {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();
    
    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();
    
    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();
    
    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();
      
      if (movieTitle.indexOf(searchTerm) != -1)
      {
        //add the Movie object to the results list
        results.add(movies.get(i));
      }
    }
    
    // sort the results by title
    sortResults(results);
    
    // now, display them all to the user    
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();
      
      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;
      
      System.out.println("" + choiceNum + ". " + title);
    }
    
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    
    int choice = scanner.nextInt();
    scanner.nextLine();
    
    Movie selectedMovie = results.get(choice - 1);
    
    displayMovieInfo(selectedMovie);
    
    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void sortResults(ArrayList<Movie> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();
      
      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }
  
  private void displayMovieInfo(Movie movie)
  {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }
  
  private void searchCast()
  {
    System.out.print("Type in the name of the cast member you would like to search: ");
    String name = scanner.nextLine();
    ArrayList<String> castMembers = new ArrayList<String>();
    ArrayList<Movie> moviesWithWord = new ArrayList<Movie>();


    for (Movie movie : movies) {
      String castList = movie.getCast();
      if (castList.contains(name)) {
        moviesWithWord.add(movie);
        System.out.println(castList);
        String[] actorList = castList.split("\\|");
        ArrayList<String> actorListLow = new ArrayList<String>(Arrays.asList(actorList));
        for (int i = 0; i < actorListLow.size(); i++) {
          actorListLow.set(i, actorListLow.get(i).toLowerCase());
          System.out.println(actorListLow);
          if (actorListLow.get(i).contains(name.toLowerCase())) {
            boolean add = true;
              for (String castMember : castMembers) {
                if (castMember.equals(actorList[i])) {
                  add = false;
                  break;
                }
              }
              if (add) {
                castMembers.add(actorList[i]);
              }
            }
          }
      }
    }

    sortResults(moviesWithWord);
    sortStrings(castMembers); // Implementation in next method

    for (int i = 0; i < castMembers.size(); i++) {
      int order = i + 1;
      System.out.println(order + ". " + castMembers.get(i));
    }

    System.out.println("Which cast member would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    String selected = castMembers.get(choice - 1);
    ArrayList<Movie> list = new ArrayList<Movie>();

    for (Movie movie : moviesWithWord) {
      if (movie.getCast().contains(selected)) {
        list.add(movie);
      }
    }

    System.out.println("Which title would you like to select?");

    for (int i = 0; i < list.size(); i++) {
      int order = i + 1;
      System.out.println(order + ". " + list.get(i).getTitle());
    }

    choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = list.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void sortStrings(ArrayList<String> a) {
    String s;
    for (int i = 0; i < a.size() - 1; i++) {
      while (a.get(i).compareTo(a.get(i+1)) > 0) {
        s = a.get(i);
        a.set(i, a.get(i+1));
        a.set(i+1, s);
        if (i > 0) {
          i--;
        }
      }
    }
  }

  private void searchKeywords()
  {
    System.out.print("Type in the keyword you would like to search: ");
    String keyword = scanner.nextLine().toLowerCase();
    ArrayList<Movie> moviesWithWord = new ArrayList<Movie>();

    for (int i = 0; i < movies.size(); i++) {
      if (movies.get(i).getKeywords().contains(keyword)) {
        moviesWithWord.add(movies.get(i));
      }
    }
    sortResults(moviesWithWord);

    for (int i = 0; i < moviesWithWord.size(); i++) {
      int order = i + 1;
      System.out.println(order + ". " + moviesWithWord.get(i).getTitle());
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = moviesWithWord.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void listGenres()
  {
    ArrayList<String> genre = new ArrayList<>();
    for (Movie movie : movies) {
      boolean addGenre = true;
      String[] genres = movie.getGenres().split("\\|");
      for (String genreList : genres) {
        for (String allGenres : genre) {
          if (genreList.equals(allGenres)) {
            addGenre = false;
            break;
          }
        }
        if (addGenre) {
          genre.add(genreList);
        }
        addGenre = true;
      }
    }
    sortStrings(genre);
    for (int i = 0; i < genre.size(); i++) {
      int order = i + 1;
      System.out.println(order + ". " + genre.get(i));
    }
    System.out.println("Type the number that corresponds to the genre: ");
    int choice = scanner.nextInt();
    scanner.nextLine();
    String selectedGenre = genre.get(choice-1);
    ArrayList<Movie> moviesWithGenre = new ArrayList<Movie>();
    for (Movie movie : movies) {
      if (movie.getGenres().contains(selectedGenre)) {
        moviesWithGenre.add(movie);
      }
    }

    sortResults(moviesWithGenre);
    int count = 1;
    for (Movie moviesList : moviesWithGenre) {
      System.out.println(count + ". " + moviesList);
      count++;
    }
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = moviesWithGenre.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void listHighestRated()
  {
    ArrayList<Movie> allMovies = new ArrayList<>();
    for (Movie movie : movies) {
      allMovies.add(movie);
    }
    ArrayList<Movie> highestRated = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      double highestValue = allMovies.get(0).getUserRating();
      int indexOf = 0;
      for (int p = 0; p < allMovies.size(); p++) {
        if (allMovies.get(p).getUserRating() > highestValue) {
          highestValue = allMovies.get(p).getUserRating();
          indexOf = p;
        }
      }
      highestRated.add(allMovies.remove(indexOf));
    }

    int count = 1;
    for (Movie moviesList : highestRated) {
      System.out.println(count + ". " + moviesList.getTitle() + " (User Rating: " + moviesList.getUserRating() + ")");
      count++;
    }
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = highestRated.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void listHighestRevenue()
  {
    ArrayList<Movie> allMovies = new ArrayList<>(movies);
    ArrayList<Movie> highestRevenue = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      double highestValue = allMovies.get(0).getRevenue();
      int indexOf = 0;
      for (int p = 0; p < allMovies.size(); p++) {
        if (allMovies.get(p).getRevenue() > highestValue) {
          highestValue = allMovies.get(p).getRevenue();
          indexOf = p;
        }
      }
      highestRevenue.add(allMovies.remove(indexOf));
    }

    int count = 1;
    for (Movie moviesList : highestRevenue) {
      System.out.println(count + ". " + moviesList.getTitle() + " (Box Revenue: " + moviesList.getRevenue() + ")");
      count++;
    }
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = highestRevenue.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void importMovieList(String fileName)
  {
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();
      
      movies = new ArrayList<Movie>();
      
      while ((line = bufferedReader.readLine()) != null) 
      {
        String[] movieFromCSV = line.split(",");
     
        String title = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        double userRating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);
        
        Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
        movies.add(nextMovie);  
      }
      bufferedReader.close();
    }
    catch(IOException exception)
    {
      // Print out the exception that occurred
      System.out.println("Unable to access " + exception.getMessage());              
    }
  }
}