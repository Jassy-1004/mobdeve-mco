package com.mobdeve.s13.Group17.MCO2

class DataHelper {
    companion object {
        fun initializeData(): ArrayList<Books> {
            val data = ArrayList<Books>()
            data.add(
                Books(
                    "9780439023528", "The Hunger Games",
                    "Suzanne Collins",
                    "In the ruins of a place once known as North America lies the nation of Panem, a shining Capitol surrounded by twelve outlying districts. The Capitol is harsh and cruel and keeps the districts in line by forcing them all to send one boy and one girl between the ages of twelve and eighteen to participate in the annual Hunger Games, a fight to the death on live TV.\n" +
                            "\n" +
                            "Sixteen-year-old Katniss Everdeen, who lives alone with her " +
                            "mother and younger sister, regards it as a death sentence when " +
                            "she steps forward to take her sister's place in the Games. " +
                            "But Katniss has been close to dead before—and survival, for her, " +
                            "is second nature. Without really meaning to, she becomes a contender. " +
                            "But if she is to win, she will have to start making choices that " +
                            "weight survival against humanity and life against love.",
                    4.33F,
                    "September 14, 2008",
                    R.drawable.hunger_games
                )

            )
            data.add(
                Books(
                    "9780439358071", "Harry Potter and the Order of the Phoenix (Harry Potter, #5)",
                    "J.K. Rowling",
                    "Harry Potter is about to start his fifth year at Hogwarts School of Witchcraft and Wizardry. Unlike most schoolboys, " +
                            "Harry never enjoys his summer holidays, but this summer is even worse than usual." +
                            " The Dursleys, of course, are making his life a misery, but even his best friends, " +
                            "Ron and Hermione, seem to be neglecting him.",
                    4.50F,
                    "June 21, 2003",
                    R.drawable.harry
                )

            )
            data.add(
                Books(
                    "9780684801223", "The Old Man and the Sea",
                    "Ernest Hemingway",
                    "This short novel, already a modern classic, is the superbly " +
                            "told, tragic story of a Cuban fisherman in the Gulf Stream " +
                            "and the giant Marlin he kills and loses—specifically referred " +
                            "to in the citation " +
                            "accompanying the author's Nobel Prize for literature in 1954.",
                    3.5F,
                    "September 1, 1952",
                    R.drawable.the_oldman
                )

            )
            data.add(
                Books(
                    "9780062387240", "Divergent",
                    "Veronica Roth",
                    "In Beatrice Prior's dystopian Chicago world, " +
                            "society is divided into five factions, each dedicated to " +
                            "the cultivation of a particular virtue—Candor (the honest), " +
                            "Abnegation (the selfless), Dauntless (the brave), Amity (the peaceful), " +
                            "and Erudite (the intelligent). On an appointed day of every year, all sixteen-year-olds must select the faction to which they will devote the rest of their lives. For Beatrice, the decision is between staying with her family and being who she really is—she can't have both. " +
                            "So she makes a choice that surprises everyone, " +
                            "including herself.",
                    4F,
                    "April 25, 2011",
                    R.drawable.divergent
                )

            )
            data.add(
                Books(
                    "9780316327336", "Twilight",
                    "Stephenie Meyer",
                    "About three things I was absolutely positive.\n" +
                            "\n" +
                            "First, Edward was a vampire.\n" +
                            "\n" +
                            "Second, there was a part of him—and I didn't know how dominant that part might be—that thirsted for my blood.\n" +
                            "\n" +
                            "And third, I was unconditionally and irrevocably in love with him.\n" +
                            "\n" +
                            "Deeply seductive and extraordinarily suspenseful, Twilight is a love story with bite.\n",
                    2F,
                    "October 5, 2005",
                    R.drawable.twilight
                )

            )
            data.add(
                Books(
                    "9781405288194", "The Little Prince",
                    "Antoine de Saint-Exupéry",
                    "A pilot stranded in the desert awakes one morning to see, " +
                            "standing before him, the most extraordinary little fellow. " +
                            "\"Please,\" asks the stranger, \"draw me a sheep.\" " +
                            "And the pilot realizes that when life's events are too difficult " +
                            "to understand, there is no choice but to succumb to their mysteries. " +
                            "He pulls out pencil and paper... And thus begins this wise and enchanting " +
                            "fable that, in teaching the secret of what is really important in life, " +
                            "has changed forever the world for its readers.",
                    4.5F,
                    "April 6, 1943",
                    R.drawable.little_prince
                )

            )
            data.add(
                Books(
                    "9780066238500", "The Chronicles of Narnia",
                    "C.S. Lewis",
                    "Journeys to the end of the world, fantastic creatures, and epic" +
                            " battles between good and evil—what more could any " +
                            "reader ask for in one book? The book that has it all is The Lion, the" +
                            " Witch and the Wardrobe, written in 1949 by Clive Staples Lewis. But Lewis did not stop there. Six more books followed," +
                            " and together they became known as The Chronicles of Narnia.",
                    4.1F,
                    "January 1, 1956",
                    R.drawable.narnia
                )
            )

            return data;
        }

        fun initializesData(): ArrayList<Profile> {
            val data = ArrayList<Profile>()
            data.add(
                Profile(10001,"kayeshi120", "Kaye", "Shi", "kayeshi120@gmail.com",
                    "12345", "Female", "May 10, 2001", "Hi! Welcome to my page."
                )
            )
            data.add(
                Profile(10002,"jasminchua1004", "Jasmin", "Chua", "jasminchua1004@gmail.com",
                    "12345", "Female", "August 24, 2002", "Hi! Welcome to my page."
                )
            )
            data.add(
                Profile(10003,"hailytan016","Haily","Tan","hailytan016@gmail.com",
                    "761892","Female", "December 5, 2000","Hi! Welcome to my page.")
            )

            return data;
        }

        fun initializedData(): ArrayList<Comments>{
            val data = ArrayList<Comments>()
            data.add(
                Comments("kayeshi120", "This is one of the best books that I have read!")
            )
            data.add(
                Comments("hailytan016", "Would recommend this book to my friends")
            )
            data.add(
                Comments("jasminchua123", "will surely read this again!!!")
            )

            return data
        }

        fun initializesDatas(): ArrayList<BookReview>{
            val data = ArrayList<BookReview>()
            data.add(
                BookReview("Divergent", "Veronica Roth","Divergent follows the story of Beatrice (Tris), who has a " +
                        "choice to make among the 5 factions of her nation, which will map out certain actions in her life: Abnegation (the selfless)," +
                        " Amity (the peaceful), Candor (the honest), Dauntless (the brave), and Erudite (the intellectual).",
                        3.0F,"This book is so interesting. It didn't disappoint me", R.drawable.divergent)
            )
            data.add(
                BookReview("Harry Potter", "J.K. Rowling","The novels follow Harry Potter, " +
                        "an 11-year-old boy who discovers he is the son of famous wizards " +
                        "and will attend Hogwarts School of Witchcraft and Wizardry. Harry " +
                        "learns of an entire society of wizards and witches.",4.5F,"I would love to read more " +
                        "books with the same plot. I totally love it. Would recommend it to my friends",R.drawable.harry)
            )
            data.add(
                BookReview("Hunger Games", "Suzanne Collins","The Hunger Games is an annual event in which one boy and one girl aged " +
                        "12–18 from each of the twelve districts surrounding the Capitol are selected by lottery " +
                        "to compete in a televised battle royale to the death.", 4.8F,"Would love to read it again!",
                        R.drawable.hunger_games)
            )

            return data
        }
    }
}