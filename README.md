_________________
# Welcome to the *AppStore-CLI* 
![alt text](src/AppStoreLogos.jpeg)
## This program showcases the basic structure of an App Store.
_________________

## <u> What you need to use the system  </u>
#### Before you run this program, you must have two files at hand:
> #### 1. A *database.txt file* containing the database you want to use to feed the program:
>> ### **Database feeder file:**
>> - Before the beginning of the day, a database text file must be fed to the program so that it is ready to perform the transactions that the daily text file contains, as a database is required to run this program.
>> - The Database file contains variable-length text lines with the following formats:
>>> 1,UUUUUUUUUUUUUUU,TT,CCCCCCCCC    
>>> Example: 1,Sunnia,DD,001965.00  
>>> Where:
>>> -   1
        >>>     -   represents a user to be populated in the user database
>>> -   UUUUUUUUUUUUUUU
        >>>    -   is the username
>>> -   TT
        >>>    -   is the user type (AA=admin, BD=buyer & developer(seller), BS=buy-standard, DD=Developer(seller))
>>> -   CCCCCCCCC
>>>   - is the available credit
>>>
>>> 2,IIIIIIIIIIIIIIIIIIIIIIIIIIIIII,PPPPPP,SSSSSSSSSSSSSSS,GGGGGGGGGGGGGGG+
>>> Example: Duolingo                      ,00.99,200.00,DeveloperInc.,Education  
>>> Where:
>>> -   2
        >>>     -   represents a app to be populated into the app database
>>> -   IIIIIIIIIIIIIIIIIIIIIIIIIIIIII
        >>>    -   is the name of the app
>>> -   PPPPPP
        >>>    -   is the price of the app
>>> -   SSSSSSSSSSSSSSS
        >>>    -   is the developer's username
>>> -   GGGGGGGGGGGGGGG+
        >>> -   is the app's category
>>>
>>> 3,UUUUUUUUUUUUUUU,IIIIIIIIIIIIIIIIIIIIIIIIIIIIII,SSSSSSSSSSSSSSS  
>>> Where:
>>> Example: 3,Eren,Freedom,Zeke
>>> -   3
        >>>     -   represents a app to be populated into a user's inventory and the seller's inventory
>>> -   UUUUUUUUUUUUUUU
        >>>    -   is the username of the app owner
>>> -   IIIIIIIIIIIIIIIIIIIIIIIIIIIIII
        >>>    -   is the name of the app
>>> -   SSSSSSSSSSSSSSS
        >>>    -   is the username of the app developer(seller) *(might be a blank space if the seller deleted the app or doesn't exist)*
>>>
>> *** Note that once the program is finished running, database.txt will be overwritten so that it is ready for the next day
>
> 2. A *daily.txt file* containing the daily transactions for the system to perform which follows the following format:
>> ### **Daily feeder file:**
>>> XX UUUUUUUUUUUUUUU TT CCCCCCCCC    
>>> Example: 01 A_USER SS 002222.00  
>>> Where:
>>> -   XX
        >>>     -   is a two-digit transaction code: 00-login, 01-create, 02-delete, 06-addcredit, 08-logout
>>> -   UUUUUUUUUUUUUUU
        >>>    -   is the username
>>> -   TT
        >>>     -   is the user type (AA=admin, BD=buyer & developer(seller), BS=buy-standard, DD=Developer(seller))
>>> -   CCCCCCCCC
        >>>     - is the available credit
>>>
>>> XX UUUUUUUUUUUUUUU SSSSSSSSSSSSSSS CCCCCCCCC    
>>> Example: 05 Junia Malawi 002000.00  
>>> Where:
>>> -   XX
        >>>     -   is a two-digit transaction code: 05-refund
>>> -   UUUUUUUUUUUUUUU
        >>>    -   is the buyer's username
>>> -   SSSSSSSSSSSSSSS
        >>>    -   is the seller's username
>>> -   CCCCCCCCC
        >>>     - is the refund credit
>>>
>>> XX IIIIIIIIIIIIIIIIIIIIIIIIIIIIII SSSSSSSSSSSSSSS CCCCC GGG
>>> Example: 03 Youtube                       YoutubeInc. 00.00 006
>>> Where:
>>> -   XX
        >>>     -   is a two-digit transaction code: 03-sell
>>> -   IIIIIIIIIIIIIIIIIIIIIIIIIIIIII
        >>>    -   is the app name
>>> -   SSSSSSSSSSSSSSS
        >>>    -   is the seller's username
>>> -   CCCCC
        >>>     - is the price of the app
>>> -   GGG
        >>>     - is the 3-digit app category code (001 - Books, 002 - Music & Entertainment, 003 - Games, 004 - Education, 005 - Sports & Fitness, 006 - Social Media,  007 - Utilities )
>>>
>>> XX IIIIIIIIIIIIIIIIIIIIIIIIIIIIII SSSSSSSSSSSSSSS UUUUUUUUUUUUUUU    
>>> Example: 04 Duolingo                      Amie Leonard  
>>> Where:
>>> -   XX
        >>>     -   is a two-digit transaction code: 04-buy
>>> -   IIIIIIIIIIIIIIIIIIIIIIIIIIIIII
        >>>    -   is the app name
>>> -   SSSSSSSSSSSSSSS
        >>>    -   is the seller's username
>>> -   UUUUUUUUUUUUUUU
        >>>     - is the buyer's username
>>>
>>> XX IIIIIIIIIIIIIIIIIIIIIIIIIIIIII UUUUUUUUUUUUUUU SSSSSSSSSSSSSSS  
>>> Example: 07 Youtube                       Palutena Villager  
>>> Where:
>>> -   XX
        >>>     -   is a two-digit transaction code: 07-removeapp
>>> -   IIIIIIIIIIIIIIIIIIIIIIIIIIIIII
        >>>    -   is the app name
>>> -   UUUUUUUUUUUUUUU
        >>>    -   is the owner's username (optional)
>>> -   SSSSSSSSSSSSSSS
        >>>     - is the receiver's username (optional)

### Notes about these files
> - The daily text file format as given in the README.md does not need to be modified, of course.
> - Please make sure you followed the above instructions for the database format
> - The files mentioned above must be ***inside the repo folder*** for the system to use them
> - For testing purposes we have provided a *database.txt* file that you may use and another *originalDatabaseCopy.txt* file **so that you have this starting database once *database.txt* is overwritten.**
> - This is in case that you want to start the program from the beginning
_________________
## <u> How to use the system  </u>
![alt text](src/CLI-Interaction.png)
> 1. Before you run this program, *you must have the two text files inside the project folder* (mentioned in the previous section)
> 2. To run this program, you need to run Controller.java, located in the src/main folder
>> It will prompt you to do step 3 &  step 4
> 3. The system will ask you if you have the two required files (one database text file and one daily text file) with you at hand. Then, you enter your answer (Yes or No), in which case the program branches off:
>> * If you enter "Yes", then proceed to step 4
>> * If you enter "No", the system will remind you to get the files for the next time you run the program and will terminate. Please get the required two text files and run the program again (go to step 1).
> 4. Please write the file name of the database.txt file that you will be providing as such :
>> *databaseFileName.txt*
>
> - If the file is valid and the inputted database text file has been read successfully, a message will appear saying that your database text file has been processed, which implies the system's database is populated and you're good to go!
> - If the controller is unable to find this file it will ask you to provide the name of a database file that it can access.
> 4. Please write the file name of the daily.txt file that you will be providing as such :
>> *dailyFileName.txt*
>
> - If the controller is unable to find this file it will ask you to provide the name of a daily file that it can access.
> 5. After feeding the Controller.java those two files, the terminal will output Strings entailing what was fed onto the database and all the transactions that occurred that day, along with any error messages if there were any
> 6. Once the day is over, you will notice the production of a new file called *transactionReceipt.txt*. This is a new file that stores the codes of all
     the **successful** transactions that occurred in that day.
> - **NOTE:** the *transactionsReceipt.txt* file follows the **SAME** format as the daily text file
> 7. The database file provided is then overwritten with the current state of the database:
>> i.e. if a user puts money in their account, then the database will have an updated account balance
> - **NOTE:** if you want to run the program with the same database text file, you **must** change the database text file to its original self before you ran it (i.e, copy and paste your database text file from before you ran the program.)
>- If you do not make this change, then if you run the program again, it will not give you the desired output.
   > This is why an *originalDatabaseCopy.txt* file is provided for you
> 8. The program will tell you that the day is over, as all the files have been processed. Now, it will prompt you with what you want to do next (i.e. the program branches off again). It will ask if you would you like to continue to the next day or finish?
>> * If you enter "Yes", the program will run again and proceed to the next day, asking for a database text file and daily text file
>> * If you enter "No", the program will thank you for using it and terminate. If you select "No" but you wanted to continue, simply run the program again and follow steps 1-9!
> 9. Congratulations! You just ran our program! We encourage you to play around with different database text files and daily transaction text files
> 

## <u>Design Decisions </u>
>### Inheritance
>>#### Where we used it:
>> We used Inheritance for the <b>user classes </b> in our system. All four of our user classes extend from the parent
> User.java. We also used Inheritance for the <b> transaction classes </b> in our system. The transactions
> they have overlapping attributes and methods, so we found it best for all four user classes to inherit from one parent
> user class with the common attributes and methods. Similarly, we designed our transactions classes in a way where they
> inherited from a parent Transaction.java.
>#### Why we used it:
>>We used Inheritance for the <b> user classes </b> because although we have four different types of users in our systems,
> they have overlapping attributes (i.e. userName) and methods. We found it best for all four user classes to inherit from one parent
> user class with the common attributes and methods. Similarly, we used Inheritance for the <b> transaction classes </b>
> because we found common methods between them all (i.e. execute()). This also helped us in the long run when there were
> two new transactions, removeGameTransaction and giftTransaction, introduced. We simply had to create two new classes
> and have them extend from the parent Transaction.java class.

## <u> Design Patterns </u>
>#### MVC
>> #### Why we used it:
>> We used the MVC design pattern because we wanted to write code that was more organized and maintainable.
> We believe that it is important to differentiate the frontend and backend of our system. Please refer to our UML diagram (in features)
> to see these clear differentiations.
>>
>#### Factory
>> #### Why we used it:
>> We used the Factory design pattern when it came to creating our users, as seen in UserFactory.java. We created this
> so that it would be easy to create users, based on their usertype code (i.e. BS for a Buyer). This makes it more maintainable
> for future users to come.
>#### Observer
>> #### Why we used it:
>> We used the Observer design pattern because we wanted a way to notify the Controller when something changed in our
> file reading and writing classes. Not only does this help us interact with the user (almost like the frontend), it also
> made testing more manageable.
>