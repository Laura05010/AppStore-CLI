## Before you run this program, you must have two files at hand:
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