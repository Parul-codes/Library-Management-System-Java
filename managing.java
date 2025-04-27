import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.io.BufferedReader;
import java.io.FileReader;

public class managing {
    public class Book {
        String name, author;
        boolean isIssued;

        Book(String name, String author) {
            this.name = name;
            this.author = author;
            this.isIssued = false;
        }

        public void displayInfo() {
            System.out.println("Title : "+name+ " Author : "+author+" Issued : "+ (isIssued ? "Yes":"No"));
        }
    }

    public class Library {
        ArrayList<Book> books = new ArrayList<>();

        public void addBook(String name, String author) {
            Book newBook = new Book(name, author);
            books.add(newBook);
            System.out.println("Book added successfully!");
        }

        public void loadBooks() {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("Books.txt"));
                String line;
                while((line = reader.readLine()) != null) {
                    String[] parts = line.split(", ");
                    if(parts.length == 3) {
                        String name = parts[0];
                        String author = parts[1];
                        boolean isIssued = Boolean.parseBoolean(parts[2]);
                        Book book = new Book(name, author);
                        book.isIssued = isIssued;
                        books.add(book);
                    }
                }
                reader.close();
                System.out.println("Books loaded successfully.");
            } catch (IOException e) {
                System.out.println("Error loading books: " + e.getMessage());
            }
        }

        public void displayBooks() {
            if(books.isEmpty()) {
                System.out.println("There are no books in the library");
                return;
            }
            Collections.sort(books, new Comparator<Book>() {
                @Override
                public int compare(Book b1, Book b2) {
                    return b1.name.compareToIgnoreCase(b2.name);
                }
            });
            for(Book book : books) {
                book.displayInfo();
            }
        }

        public void issueBook(String name) {
            for(Book book : books) {
                if(book.name.equalsIgnoreCase(name.trim())) {
                    if(!book.isIssued) {
                        book.isIssued = true;
                        System.out.println("Book has been issued!");
                        return;
                    } else {
                        System.out.println("Book is already issued");
                        return;
                    }
                }
            }
            System.out.println("Book not found");
        }

        public void returnBook(String name) {
            for(Book book : books) {
                if(book.name.equalsIgnoreCase(name)) {
                    if(book.isIssued) {
                        book.isIssued = false;
                        System.out.println("Book has been returned!");
                        return;
                    } else {
                        System.out.println("Book was never issued");
                        return;
                    }
                }
            }
            System.out.println("Book not found");
        }

        public void searchByAuthor(String author) {
            boolean found = false;
            System.out.println("Books found by "+author+" : ");
            for(Book book : books) {
                if(book.author.equalsIgnoreCase(author.trim())) {
                    System.out.print(book.name+" ");
                    found = true;
                } 
            }
            if(!found) {
                System.out.println("No books by this author.");
            }
        }

        public void issued() {
            boolean found = false;
            for(Book book : books) {
                if(book.isIssued) {
                    book.displayInfo();
                    found = true;
                }
            }
            if(!found) {
                System.out.println("No book has been issued yet.");
            }
        }

        public void saveBooks() {
            try {
                FileWriter writer = new FileWriter("Books.txt");
                for(Book book : books) {
                    writer.write(book.name+", "+book.author+", "+(book.isIssued ? "yes":"no"+"\n"));
                }
                writer.close();
                System.out.println("Books saved to file succesfully.");
            } catch(IOException e) {
                System.out.println("Error saving books "+e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        managing m = new managing();
        Library lib = m.new Library();
        lib.loadBooks();
        System.out.println("Welcome to Parul's Library!");
        int choice;
        do {
            System.out.println("1. Add a book");
            System.out.println("2. Display Books");
            System.out.println("3. Issue a book");
            System.out.println("4. Return a book");
            System.out.println("5. Search By Author");
            System.out.println("6. See Issued Books");
            System.out.println("7. Exit Library");
            System.out.print("Enter your choice : ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter book title : ");
                    String name = sc.nextLine();
                    System.out.print("Enter book author : ");
                    String author = sc.nextLine();
                    if(name.isEmpty() || author.isEmpty()) {
                        System.err.println("Title or author cannot be empty.");
                    } else {
                        lib.addBook(name, author);
                    }
                    break;
                case 2:
                    lib.displayBooks();
                    break;
                case 3:
                    System.out.print("Enter book title : ");
                    String n = sc.nextLine().trim();
                    lib.issueBook(n);
                    break;
                case 4: 
                    System.out.print("Enter book title : ");
                    String o = sc.nextLine();
                    lib.returnBook(o);
                    break;
                case 5:
                    System.out.print("Enter the author's name : ");
                    String authorSearch = sc.nextLine().trim();
                    lib.searchByAuthor(authorSearch);
                    System.out.println();
                    break;
                case 6:
                    lib.issued();
                    break;
                case 7:
                    lib.saveBooks();
                    System.out.println("Exiting Library...");
                    break;
                default:
                    System.out.println("Wrong option. Please try again");
                    break;
            }
        } while(choice!=7);
        sc.close();
    }
}
