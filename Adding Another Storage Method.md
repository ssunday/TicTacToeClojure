#UPDATED How to Add More Storage Methods

For those that JSON, EDN, and Postgres are not enough options for saving Tic Tac Toe data, have no fear, for this application is easily extended using protocols.

1. Find a suitable keyword for it. E.G, postgres = :pg, mysql = :sql, txt = :txt and so on.
2. Implement all the methods found in the DataType protocol in the scoring_repository file in a record for each of the types in that respective file.
3. If you are using a file based system, like .txt or json, there is a file filled with some common methods that apply to file-types, such as checking if a file exists or deleting a file.
4. When implementing the read-tally method, make sure it returns the values as a vector of vectors of name and the score hash with keywords applied. Basically, the data comes out the same way it goes in. This is so the score_recording methods can correctly tally the total scores for each player.
5. Once you are done, require the file in the data_storage_factory and add another case to the cond statement seeing if the type is equal to your chosen keyword and if it is to return the creation of that data's record.
6. Done

That is all that is needed to be done to add another score recording method to the Tic Tac Toe game.


#OLD Adding More Storage Methods to Tic Tac Toe (WITH Multimethods)

For those that JSON, EDN, and Postgres are not enough options for saving Tic Tac Toe data, have no fear, for this application is easily extended.

1. Find a suitable keyword for it. E.G, postgres = :pg, mysql = :sql, txt = :txt and so on.
2. Create methods for all the defmulti's found in the scoring_repository file, using that keyword to identify it. These need to be implemented in whatever storage method you would like to add or else it will not function.
3. If you are using a file based system, like .txt or json, there is a file filled with some common methods that apply to file-types, such as checking if a file exists or deleting a file.
4. When implementing the read-tally method, make sure it returns the values as a vector of vectors of name and the score hash with keywords applied. Basically, the data comes out the same way it goes in. This is so the score_recording methods can correctly tally the total scores for each player.
5. Once you are done, require the file in the game_runner.
6. Done.

That is all that is needed to be done to add another score recording method to the Tic Tac Toe game.
