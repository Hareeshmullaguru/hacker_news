# Hacker News

This project provides three operations of Hacker News

  1. /top-stories  - Fetch the top stories
  2. /past-stories - Serve the previous past stories.
  3. /story/{id}/comments - Return the top 10 comments of story.
  
 This system is eventually consistent after every 10 minutes.For this, created a job to run every 10 minutes will fetch the top 10 stories and top 10 comments of top 3 stories store into Database.
 
Future Work 
    a. Add REDIS cache
    b. Improve code coverage
 
