package org.example.bookstoreapp.springData;

import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.book.Book;
import org.example.bookstoreapp.book.Category;
import org.example.bookstoreapp.book.BookRepo;
import org.example.bookstoreapp.user.Role;
import org.example.bookstoreapp.user.User;
import org.example.bookstoreapp.user.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MockBook implements CommandLineRunner {

    private final BookRepo bookRepo;
    private final UserRepo userRepo;

    @Override
    @Transactional
    public void run(String... args) {

        loadBooks();
        loadAdmin();
    }


    private void loadAdmin(){
        if(userRepo.count()==0){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            User admin = User.builder()
                    .role(Role.ADMIN)
                    .fullName("Admin")
                    .password(bCryptPasswordEncoder.encode("1234"))
                    .email("admin@gmai.com")
                    .build();

            userRepo.save(admin);
        }
    }

    private void loadBooks() {
        if(bookRepo.count() == 0) {
            Book grokAlgo = Book.builder()
                    .title("Grokking Algorithms")
                    .author("Aditya Y. Bhargava")
                    .coverImage("https://skybooks.ir/images/productImages/Grokking-Algorithms_EB1709675048.jpg")
                    .price(28.99)
                    .discount(10)
                    .isNew(true)
                    .rating(5)
                    .publisher("Manning")
                    .language("English")
                    .category(Category.COMPUTER_SCIENCE)
                    .isbn("9781633438538")
                    .year(2024)
                    .paperback(322)
                    .description("An Illustrated Guide for Programmers and Other Curious People  A friendly, " +
                            "fully-illustrated introduction to the most important computer programming algorithms." +
                            "Master the most widely used algorithms and be fully prepared when you’re asked about them at your next job interview. " +
                            "With beautifully simple explanations, over 400 fun illustrations, and dozens of relevant examples," +
                            " you’ll actually enjoy learning about algorithms with this fun and friendly guide!" +
                            " The first edition of Grokking Algorithms proved to over 100,000 readers that learning algorithms doesn't have to be complicated or boring! " +
                            "This revised second edition contains brand new coverage of trees, including binary search trees, balanced trees, B-trees and more. " +
                            "You’ll also discover fresh insights on data structure performance that takes account of modern CPUs. Plus, the book’s fully annotated code samples have been updated to Python 3." +
                            "Foreword by Daniel Zingaro")
                    .build();
            Book grokBitcoin =  Book.builder()
                    .title("Grokking Bitcoin")
                    .author("Kalle Rosenbaum")
                    .rating(4.8)
                    .price(35.93)
                    .year(2019)
                    .language("English")
                    .publisher("Manning")
                    .category(Category.COMPUTER_SCIENCE)
                    .isbn("9781617294648")
                    .paperback(480)
                    .coverImage("https://skybooks.ir/images/productImages/GrokkingBitcoin1_Rs1657377051.jpg")
                    .description("If you think Bitcoin is just an alternative currency for geeks, it's time to think again. " +
                            "Grokking Bitcoin opens up this powerful distributed ledger system," +
                            " exploring the technology that enables applications both for Bitcoin-based financial transactions and using the blockchain for registering physical property ownership." +
                            " With this fully illustrated, easy-to-read guide, you'll finally understand how Bitcoin works, how you can use it, " +
                            "and why you can trust the blockchain.Foreword by David A. Harding,Contributor to Bitcoin documentation.About the TechnologyInflation, depressed economies, " +
                            "debased currencies ... these are just a few of the problems centralized banking has caused throughout history.")
                    .build();
            Book grokAi = Book.builder()
                    .title("Grokking Artificial Intelligence Algorithms")
                    .author("Rishal Hurbans")
                    .price(20.99)
                    .year(2020)
                    .language("English")
                    .publisher("Manning")
                    .discount(10)
                    .paperback(393)
                    .isbn("9781617296185")
                    .coverImage("https://skybooks.ir/images/productImages/GrokkingArtificialIntelligenceAlgorithms4_uI1657375595.jpg")
                    .rating(4.3)
                    .category(Category.COMPUTER_SCIENCE)
                    .description("Understand and Apply the Core Algorithms of Deep Learning and Artificial Intelligence in This Friendly Illustrated Guide Including Exercises and Examples " +
                            "Grokking Artificial Intelligence Algorithms is a fully illustrated and interactive tutorial guide to the different approaches and algorithms that underpin AI. " +
                            "Written in simple language and with lots of visual references and hands-on examples," +
                            " you'll learn the concepts, terminology," +
                            " and theory you need to effectively incorporate AI algorithms into your applications." +
                            " And to make sure you truly grok as you go, you'll use each algorithm in practice with creative coding exercises - including building a maze puzzle game, " +
                            "performing diamond data analysis, and even exploring drone material optimization.")
                    .build();
            Book cleanCode = Book.builder()
                    .title("Clean Code")
                    .author("Robert C. Martin")
                    .category(Category.COMPUTER_SCIENCE)
                    .coverImage("https://skybooks.ir/images/productImages/CleanCode_hY1655407329.jpg")
                    .rating(4.9)
                    .price(22.99)
                    .language("English")
                    .publisher("Pearson")
                    .isbn("9780132350884")
                    .year(2009)
                    .paperback(462)
                    .description("Even bad code can function. But if code isn’t clean, it can bring a development organization to its knees. Every year," +
                            " countless hours and significant resources are lost because of poorly written code. But it doesn’t have to be that way.Noted software expert Robert C. " +
                            "Martin, presents a revolutionary paradigm with Clean Code: A Handbook of Agile Software Craftsmanship." +
                            " Martin, who has helped bring agile principles from a practitioner’s point of view to tens of thousands of programmers, " +
                            "has teamed up with his colleagues from Object Mentor to distill their best agile practice of cleaning code “on the fly” into a book that will instill within you the values of software craftsman, " +
                            "and make you a better programmer―but only if you work at it.")
                    .build();
            Book rustAction = Book.builder()
                    .title("Rust in Action")
                    .author("Timothy Samuel McNamara")
                    .coverImage("https://skybooks.ir/images/productImages/Rust-in-Action_8D1662717972.jpg")
                    .rating(4.4)
                    .price(36.4)
                    .discount(20)
                    .language("English")
                    .publisher("Manning")
                    .category(Category.LANGUAGE)
                    .paperback(457)
                    .year(2021)
                    .isbn("9781617294556")
                    .description("Systems programming concepts and techniques Rust in Action is a hands-on guide to systems programming with Rust. " +
                            "Written for inquisitive programmers, it presents real-world use cases that go far beyond syntax and structure. " +
                            " Summary :Rust in Action introduces the Rust programming language by exploring numerous systems programming concepts and techniques. " +
                            "You'll be learning Rust by delving into how computers work under the hood. You'll find yourself playing with persistent storage, " +
                            "memory, networking and even tinkering with CPU instructions. The book takes you through using Rust to extend other applications and teaches you tricks to write blindingly fast code. " +
                            "You'll also discover parallel and concurrent programming. Filled to the brim with real-life use cases and scenarios, " +
                            "you'll go beyond the Rust syntax and see what Rust has to offer in real-world use cases.")
                    .build();
            Book cryptoEngineers = Book.builder()
                    .title("Cryptology For Engineers")
                    .author("Robert Schmied")
                    .price(30.21)
                    .rating(4.6)
                    .category(Category.CRYPTOGRAPHY)
                    .coverImage("https://skybooks.ir/images/productImages/Cryptology-For-Engineers_2O1715972130.jpg")
                    .isbn("9789811208041")
                    .paperback(382)
                    .year(2020)
                    .language("English")
                    .publisher("World Scientific")
                    .description("Cyptology is increasingly becoming one of the most essential topics of interest in everyday life." +
                            " Digital communication happens by transferring data between at least two participants — But do we want to disclose private information while executing a sensitive bank transfer? " +
                            "How about allowing third-party entities to eavesdrop on private calls while performing an important secret business discussion? Do we want to allow ambient communication concerning us to be manipulated while control software is driving our autonomous car along a steep slope? " +
                            "Questions like these make it clear why issues of security are a great concern in our increasingly augmented world.Cryptology for Engineers is a study of digital security in communications systems." +
                            " The book covers the cryptographical functionalities of ciphering, hash generation, digital signature generation, " +
                            "key management and random number generation, with a clear sense of the mathematical background on the one hand and engineers' requirements on the other. " +
                            "Numerous examples computable by hand or with a small additional cost in most cases are provided inside.")
                    .build();
            Book springAction = Book.builder()
                    .title("Spring in Action")
                    .author("Craig Walls")
                    .coverImage("https://skybooks.ir/images/productImages/Spring-Boot-in-Action_XD1659195012.jpg")
                    .isbn("9781617292545")
                    .price(24.99)
                    .rating(4.2)
                    .publisher("Manning")
                    .language("English")
                    .year(2016)
                    .paperback(266)
                    .category(Category.LANGUAGE)
                    .description("A developer-focused guide to writing applications using Spring Boot." +
                            " You'll learn how to bypass the tedious configuration steps so that you can concentrate on your application's behavior.  " +
                            "The Spring Framework simplifies enterprise Java development, but it does require lots of tedious configuration work. " +
                            "Spring Boot radically streamlines spinning up a Spring application. " +
                            "You get automatic configuration and a model with established conventions for build-time and runtime dependencies. " +
                            "You also get a handy command-line interface you can use to write scripts in Groovy. " +
                            "Developers who use Spring Boot often say that they can't imagine going back to hand configuring their applications.About the Book  ")
                    .build();
            Book cleanArchitecture = Book.builder()
                    .title("Clean Architecture")
                    .author("Robert C. Martin")
                    .price(29.99)
                    .coverImage("https://skybooks.ir/images/productImages/CleanArchitecture_1e1655814220.jpg")
                    .rating(4.1)
                    .year(2016)
                    .language("English")
                    .publisher("Person")
                    .paperback(429)
                    .category(Category.COMPUTER_SCIENCE)
                    .isbn("9780134494166")
                    .description("A Craftsman's Guide to Software Structure and Design  By applying universal rules of software architecture," +
                            " you can dramatically improve developer productivity throughout the life of any software system. " +
                            "Now, building upon the success of his best-selling books Clean Code and The Clean Coder, " +
                            "legendary software craftsman Robert C. Martin (“Uncle Bob”) reveals those rules and helps you apply them.Martin’s Clean Architecture doesn’t merely present options. " +
                            "Drawing on over a half-century of experience in software environments of every imaginable type, " +
                            "Martin tells you what choices to make and why they are critical to your success." +
                            " As you’ve come to expect from Uncle Bob, this book is packed with direct, " +
                            "no-nonsense solutions for the real challenges you’ll face–the ones that will make or break your projects.")
                    .build();
            Book friends = Book.builder()
                    .title("Friends, Lovers, and the Big Terrible Thing")
                    .author("Matthew Perry")
                    .coverImage("https://skybooks.ir/images/productImages/Friends-Lovers-and-the-Big-Terrible-Thing_eo1673459140.jpg")
                    .price(28.99)
                    .rating(4.3)
                    .isBestseller(true)
                    .language("English")
                    .publisher("Flatiron Books")
                    .category(Category.BIOGRAPHY)
                    .discount(5)
                    .year(2022)
                    .isbn("9781250866448")
                    .paperback(280)
                    .description("The BELOVED STAR OF FRIENDS takes us behind the scenes of the hit sitcom and his struggles with addiction in this “CANDID, DARKLY FUNNY...POIGNANT” memoir (The New York Times)A MOST ANTICIPATED BOOK by Time," +
                            " Associated Press, Goodreads, USA Today, and more!“Hi, my name is Matthew, although you may know me by another name. " +
                            "My friends call me Matty. And I should be dead.”So begins the riveting story of acclaimed actor Matthew Perry, " +
                            "taking us along on his journey from childhood ambition to fame to addiction and recovery in the aftermath of a life-threatening health scare. " +
                            "Before the frequent hospital visits and stints in rehab, there was five-year-old Matthew, who traveled from Montreal to Los Angeles, shuffling between his separated parents;" +
                            " fourteen-year-old Matthew, who was a nationally ranked tennis star in Canada; twenty-four-year-old Matthew," +
                            " who nabbed a coveted role as a lead cast member on the talked-about pilot then called Friends Like Us. . . and so much more.")
                    .build();
            Book atomic = Book.builder()
                    .title("Atomic Habitat")
                    .author("James Clear")
                    .price(19.99)
                    .rating(4.8)
                    .category(Category.MOTIVATION)
                    .isBestseller(true)
                    .language("English")
                    .publisher("Avery")
                    .isbn("9780735211292")
                    .paperback(320)
                    .year(2018)
                    .coverImage("https://skybooks.ir/images/productImages/Atomic-Habits_E71670759776.jpg")
                    .description("An Easy & Proven Way to Build Good Habits & Break Bad Ones Tiny Changes," +
                            " Remarkable Results No matter your goals, Atomic Habits offers a proven framework for improving--every day. " +
                            "James Clear, one of the world's leading experts on habit formation, reveals practical strategies that will teach you exactly how to form good habits, " +
                            "break bad ones, and master the tiny behaviors that lead to remarkable results.If you're having trouble changing your habits, the problem isn't you. " +
                            "The problem is your system. Bad habits repeat themselves again and again not because you don't want to change, but because you have the wrong system for change." +
                            " You do not rise to the level of your goals. You fall to the level of your systems. " +
                            "Here, you'll get a proven system that can take you to new heights.")
                    .build();
            Book hurt  = Book.builder()
                    .title("Can't Hurt Me")
                    .author("David Goggins")
                    .price(29.99)
                    .rating(4.5)
                    .discount(10)
                    .category(Category.MOTIVATION)
                    .isBestseller(true)
                    .language("English")
                    .publisher("Lioncrest")
                    .isbn("9781544512280")
                    .paperback(297)
                    .year(2018)
                    .coverImage("https://skybooks.ir/images/productImages/Cant-Hurt-Me_pU1691501515.jpg")
                    .description("For David Goggins, childhood was a nightmare -- poverty, prejudice, and physical abuse colored his days and haunted his nights. " +
                            "But through self-discipline, mental toughness, and hard work, " +
                            "Goggins transformed himself from a depressed, overweight young man with no future into a U.S. " +
                            "Armed Forces icon and one of the world's top endurance athletes. The only man in history to complete elite training as a Navy SEAL, Army Ranger, " +
                            "and Air Force Tactical Air Controller, he went on to set records in numerous endurance events, " +
                            "inspiring Outside magazine to name him The Fittest (Real) Man in America")
                    .build();
            Book deepWork = Book.builder()
                    .title("Deep Work")
                    .author("Cal Newport")
                    .coverImage("https://skybooks.ir/images/productImages/Deep-Work_eS1735691151.jpg")
                    .year(2016)
                    .paperback(195)
                    .category(Category.MOTIVATION)
                    .language("English")
                    .publisher("Grand Central")
                    .isBestseller(true)
                    .rating(4.2)
                    .price(19.99)
                    .isbn("9780349413686")
                    .description("Cal Newport discusses in his new book, Deep Work: Rules For Focused Success In A Distracted World, about how professionals of today have started valuing quantity over quality; " +
                            "and how this has turned young professionals of today into puppets who try to indulge in extensive multitasking, " +
                            "dealing with multiple emails and projects. This prevents them from doing 'deep work'; which is focused work free from all other distractions. " +
                            "This also means that the professionals of today should sort out their priorities. Newport uses principles of psychology and neuroscience to enhance his points. " +
                            "He elaborates how to improve a person's cognitive abilities and how employers should encourage workers to not take shortcuts for completing projects. " +
                            "He claims that the best way to break away from the corporate race is to take a break from technology and social media and use some alone-time to rewind and introspect. " +
                            "Newport enforces the beliefs of a non-technophile to deliver work that is productive and efficiently delivered.")
                    .build();
            Book theEffectiveProductDesigner = Book.builder()
                    .title("The Effective Product Designer")
                    .author("Artiom Dashinsky")
                    .price(28.99)
                    .discount(10)
                    .rating(4)
                    .isbn("9798326456779")
                    .publisher("Independently Published")
                    .coverImage("https://skybooks.ir/images/productImages/The-Effective-Product-Designer_xd1743764952.jpg")
                    .language("English")
                    .year(2024)
                    .isNew(true)
                    .category(Category.COMPUTER_SCIENCE)
                    .paperback(236)
                    .description("The best designers are effective outside of Figma." +
                            "Learn how to achieve more as a designer with 27 actionable lessons on communication, persuasion, influence, decision-making, productivity, and more." +
                            "This book will help you:" +
                            "Boost impact — Discover strategies that top designers use to influence others, sell their ideas, and generate more value." +
                            "Build a better career — Learn which companies to join to maximize wealth and to create positive change." +
                            "Attract more opportunities — Build relationships and a personal brand to maximize your career development.\n" +
                            "Learn better and faster — Acquire the right knowledge faster by knowing what and from whom to learn next.")
                    .build();
            Book webAppSecurity = Book.builder()
                    .title("Web Application Security")
                    .author("Andrew Hoffman")
                    .isNew(true)
                    .paperback(444)
                    .coverImage("https://skybooks.ir/images/productImages/Web-Application-Security_3K1706285538.jpg")
                    .category(Category.COMPUTER_SCIENCE)
                    .year(2024)
                    .price(37.25)
                    .language("English")
                    .publisher("O'Reilly")
                    .isbn("9781098143930")
                    .rating(4.5)
                    .isBestseller(true)
                    .description("Exploitation and Countermeasures for Modern Web Applications In the first edition of this critically acclaimed book," +
                            " Andrew Hoffman defined the three pillars of application security: reconnaissance, offense, and defense. " +
                            "In this revised and updated second edition, he examines dozens of related topics, " +
                            "from the latest types of attacks and mitigations to threat modeling, the secure software development lifecycle (SSDL/SDLC), and more.")
                    .build();

            Book lawOfPower = Book.builder()
                    .title("The 48 Laws of Power")
                    .author("Robert Greene ")
                    .isBestseller(true)
                    .paperback(478)
                    .category(Category.MOTIVATION)
                    .year(1998)
                    .language("English")
                    .publisher("Penguin Books")
                    .price(45.99)
                    .discount(20)
                    .rating(4.8)
                    .isbn("9780140280197")
                    .coverImage("https://skybooks.ir/images/productImages/The-48-Laws-of-Power_sh1670770669.jpg")
                    .description("Amoral, cunning, ruthless, and instructive, this multi-million-copy New York Times bestseller is the definitive manual for anyone interested in gaining, observing, or defending against ultimate control – from the author of The Laws of Human Nature." +
                            "In the book that People magazine proclaimed “beguiling” and “fascinating,”" +
                            " Robert Greene and Joost Elffers have distilled three thousand years of the history of power into 48 essential laws by drawing from the philosophies of Machiavelli, Sun Tzu," +
                            " and Carl Von Clausewitz and also from the lives of figures ranging from Henry Kissinger to P.T. Barnum.\n" +
                            "Some laws teach the need for prudence (“Law 1: Never Outshine the Master”), " +
                            "others teach the value of confidence (“Law 28: Enter Action with Boldness”), " +
                            "and many recommend absolute self-preservation (“Law 15: Crush Your Enemy Totally”). " +
                            "Every law, though, has one thing in common: an interest in total domination. In a bold and arresting two-color package, " +
                            "The 48 Laws of Power is ideal whether your aim is conquest, self-defense, or simply to understand the rules of the game.")
                    .build();
            Book junitJava = Book.builder()
                    .title("Java Unit Testing with JUnit 5")
                    .author("Shekhar Gulati, Rahul Sharma")
                    .paperback(158)
                    .category(Category.COMPUTER_SCIENCE)
                    .year(2017)
                    .language("English")
                    .publisher("Apress")
                    .price(50.91)
                    .discount(10)
                    .rating(4.5)
                    .isbn("9781484230145")
                    .coverImage("https://skybooks.ir/images/productImages/Java-Unit-Testing-with-JUnit-5_P31705682385.jpg")
                    .description("Test Driven Development with JUnit 5 Explore the new way of building and maintaining test cases with Java test driven development (TDD) using JUnit 5. This book doesn't just talk about the new concepts, it shows you ways of applying them in TDD and Java 8 to continuously deliver code that excels in all metrics." +
                            "Unit testing and test driven development have now become part of every developer's skill set. For Java developers, the most popular testing tool has been JUnit, and JUnit 5 is built using the latest features of Java.  With Java Unit Testing with JUnit 5, you'll master these new features, including method parameters, extensions, assertions and assumptions, and dynamic tests. You'll also see how to write clean tests with less code. \n" +
                            "This book is a departure from using older practices and presents new ways of performing tests, building assertions, and injecting dependencies. ")
                    .build();
            Book javaAvoid = Book.builder()
                    .title("100 Java Mistakes and How to Avoid Them")
                    .author("Tagir Valeev")
                    .paperback(353)
                    .category(Category.COMPUTER_SCIENCE)
                    .language("English")
                    .publisher("Manning")
                    .price(45.5)
                    .discount(5)
                    .rating(4.8)
                    .year(2024)
                    .isNew(true)
                    .isbn("9781633437968")
                    .coverImage("https://skybooks.ir/images/productImages/100-Java-Mistakes-and-How-to-Avoid-Them_F71713620446.jpg")
                    .description("Whenever you make a mistake writing Java, it’s almost guaranteed that someone else has made it before! " +
                            "In 100 Java Mistakes and How To Avoid Them you’ll learn about the common and the not-so-common antipatterns, errors, and tricky bits that trip up almost every Java developer."+
                            "Inside 100 Java Mistakes and How To Avoid Them you will learn how to:" +
                            "Write better Java programs\n" +
                            "Recognize common mistakes during programming\n" +
                            "Create fewer bugs and save time for debugging and testing\n" +
                            "Get help from static analyzers during programming\n" +
                            "Configure static analysis tools to reduce the number of false reports\n" +
                            "Extend static analysis tools with custom plugins")
                    .build();
            Book blockchain = Book.builder()
                    .title("Blockchain in Action")
                    .author("Bina Ramamurthy")
                    .paperback(352)
                    .category(Category.COMPUTER_SCIENCE)
                    .language("English")
                    .publisher("Manning")
                    .price(35.5)
                    .rating(4.8)
                    .isbn("9781617296086")
                    .coverImage("https://skybooks.ir/images/productImages/Blockchain-in-Action_vK1665335406.jpg")
                    .description("There’s a lot more to the blockchain than mining Bitcoin. This secure system for registering and verifying ownership and identity is perfect for supply chain logistics," +
                            " health records, and other sensitive data management tasks. Blockchain in Action unlocks the full potential of this revolutionary technology, " +
                            "showing you how to build your own decentralized apps for secure applications including digital democracy, private auctions, and electronic record management.\n" +
                            "\n" +
                            "There’s a lot more to the blockchain than mining Bitcoin. This secure system for registering and verifying ownership and identity is perfect for supply chain logistics, " +
                            "health records, and other sensitive data management tasks. Blockchain in Action unlocks the full potential of this revolutionary technology," +
                            " showing you how to build your own decentralized apps for secure applications including digital democracy, private auctions, and electronic record management." +
                            "About the technology\n" +
                            "Blockchain is more than just the tech behind Bitcoin—much more! Combining impenetrable security, decentralized transactions, and independently verifiable supply chains, blockchain applications have transformed currency, digital identity, and logistics. Platforms such as Ethereum and Hyperledger make it easy to get started by using familiar programming languages.\n" +
                            "\n" +
                            "About the book\n" +
                            "Blockchain in Action teaches you how to design and build blockchain-based decentralized apps, and is written in a clear, " +
                            "jargon-free style. First, you’ll get an overview of how blockchain works." +
                            " Next, you’ll code your first smart contract using Ethereum and Solidity," +
                            " adding a web interface, trust validation, and other features until your app is ready for deployment. " +
                            "The only thing you need to get started is standard hardware and open source software.")
                    .year(2020)
                    .build();

            Book masterBlock = Book.builder()
                    .title("Mastering Blockchain")
                    .author("Lorne Lantz, Daniel Cawrey")
                    .paperback(284)
                    .category(Category.COMPUTER_SCIENCE)
                    .language("English")
                    .publisher("O'Reilly")
                    .price(49.0)
                    .discount(10)
                    .rating(4.5)
                    .year(2021)
                    .isbn("9781492054702")
                    .coverImage("https://skybooks.ir/images/productImages/MasteringBlockchain_Rp1656858129.jpg")
                    .description("Unlocking the Power of Cryptocurrencies, Smart Contracts, and Decentralized Applications The future will be increasingly distributed. " +
                            "As the publicity surrounding Bitcoin and blockchain has shown, distributed technology and business models are gaining popularity." +
                            " Yet the disruptive potential of this technology is often obscured by hype and misconception. This detailed guide distills the complex, " +
                            "fast moving ideas behind blockchain into an easily digestible reference manual, showing what's really going on under the hood.\n" +
                            "\n" +
                            "\n" +
                            "Finance and technology pros will learn how a blockchain works as they explore the evolution and current state of the technology, " +
                            "including the functions of cryptocurrencies and smart contracts. " +
                            "This book is for anyone evaluating whether to invest time in the cryptocurrency and blockchain industry." +
                            " Go beyond buzzwords and see what the technology really has to offer.")
                    .build();

            Book llmInProduction = Book.builder()
                    .title("LLMs in Production")
                    .author("Christopher Brousseau, Matt Sharp")
                    .paperback(456)
                    .category(Category.COMPUTER_SCIENCE)
                    .language("English")
                    .publisher("Manning")
                    .price(80.99)
                    .discount(25)
                    .rating(4.7)
                    .year(2025)
                    .isNew(true)
                    .isbn("9781633437203")
                    .coverImage("https://skybooks.ir/images/productImages/LLMs-in-Production_ZE1739115231.jpg")
                    .description("LLMs in Production delivers vital insights into delivering MLOps for LLMs. You’ll learn how to operationalize these powerful AI models for chatbots, coding assistants, and more. " +
                            "Find out what makes LLMs so different from traditional software and ML, discover best practices for working with them out of the lab, " +
                            "and dodge common pitfalls with experienced advice.\n" +
                            "\n" +
                            "Purchase of the print book includes a free eBook in PDF and ePub formats from Manning Publications.\n" +
                            "\n" +
                            "About the book\n" +
                            "\n" +
                            "LLMs in Production is the comprehensive guide to LLMs you’ll need to effectively guide you to production usage." +
                            " It takes you through the entire lifecycle of an LLM, from initial concept, to creation and fine tuning, " +
                            "all the way to deployment. You’ll discover how to effectively prepare an LLM dataset, cost-efficient training techniques like LORA and RLHF," +
                            " and how to evaluate your models against industry benchmarks.\n" +
                            "\n" +
                            "Learn to properly establish deployment infrastructure and address common challenges like retraining and load testing." +
                            " Finally, you’ll go hands-on with three exciting example projects: a cloud-based LLM chatbot, a Code Completion VSCode")
                    .build();

            Book dataLLM = Book.builder()
                    .title("Data Analysis with LLMs")
                    .author("Immanuel Trummer")
                    .paperback(233)
                    .category(Category.COMPUTER_SCIENCE)
                    .language("English")
                    .publisher("Manning")
                    .price(65.99)
                    .discount(20)
                    .rating(4.7)
                    .year(2025)
                    .isNew(true)
                    .isbn("9781633437647")
                    .coverImage("https://skybooks.ir/images/productImages/Data-Analysis-with-LLMs_eI1746449146.jpg")
                    .description("Speed up common data science tasks with AI assistants like ChatGPT and Large Language Models (LLMs) from Anthropic, " +
                            "Cohere, Open AI, Google, Hugging Face, and more!\n" +
                            "Data Analysis with LLMs teaches you to use the new generation of AI assistants and Large Language Models (LLMs) to aid and accelerate common data science tasks.\n" +
                            "Learn how to use LLMs to:\n" +
                            "• Analyze text, tables, images, and audio files\n" +
                            "• Extract information from multi-modal data lakes\n" +
                            "• Classify, cluster, transform, and query multimodal data\n" +
                            "• Build natural language query interfaces over structured data sources\n" +
                            "• Use LangChain to build complex data analysis pipelines\n" +
                            "• Prompt engineering and model configuration\n" +
                            "All practical, Data Analysis with LLMs takes you from your first prompts through advanced techniques like creating LLM-based agents for data analysis and fine-tuning existing models. You’ll learn how to extract data, " +
                            "build natural language query interfaces, and much more.")
                    .build();

            Book buildOwnRobot = Book.builder()
                    .title("Build Your Own Robot")
                    .author("Marwan Alsabbagh")
                    .paperback(250)
                    .category(Category.COMPUTER_SCIENCE)
                    .language("English")
                    .publisher("Manning")
                    .price(75.99)
                    .discount(20)
                    .rating(4.8)
                    .year(2024)
                    .isbn("9781633438453")
                    .coverImage("https://skybooks.ir/images/productImages/Build-Your-Own-Robot_8d1706282248.jpg")
                    .description("Using Python, CRICKIT, and Raspberry PI  A DIY guide to bringing your first robot to life with cheap and basic components.\n" +
                            "Build Your Own Robot introduces you to the exciting world of robotics in a way that’s fun and affordable! " +
                            "You’ll build your own real robot with easy-to-find hardware and free open source software. Plus, " +
                            "all the components you need can be assembled with simple tools like a screwdriver.\n" +
                            "In Build Your Own Robot you’ll learn how to:\n" +
                            "Use cameras to capture photos and let your robot see\n" +
                            "Add cameras and basic computer vision\n" +
                            "Coordinate DC motors to move your robot\n" +
                            "Write a web app to control your robot\n" +
                            "Set up controls for joysticks\n" +
                            "Read QR codes to find and identify objects\n" +
                            "This book shows you how anyone can start building their own robot—no special soldering or electronic skills required. " +
                            "All you need is some basic Python know-how to get started. From scratch, you’ll go hands-on with DC motors, " +
                            "touch sensors, custom shell scripting, joysti ... ")
                    .build();




            bookRepo.saveAll(List.of(grokAlgo,grokBitcoin,grokAi,cleanCode,cleanArchitecture,springAction,friends,rustAction,cryptoEngineers,atomic,hurt,deepWork
            ,theEffectiveProductDesigner,webAppSecurity,lawOfPower,junitJava,javaAvoid,blockchain,masterBlock,llmInProduction,dataLLM,buildOwnRobot));
        }
    }
}
