# spring-boot-batch-processing
- I have around 6288 records in CSV file, I wanted to import csv file record into my database server - Mysql. I have used Batch Processing API. It took around 9 seconds to complete that job. 
- Before I inserted record one by one so it took around 1 minute to complete the same job, It was very heavey weight process and it established connection with database for every insertion operation.
- It is very usefull in excel import and export operations. We can fetch the data from ItemReader<T> objects and give to itemWriter. We can also take chunks of data from itermReader and write those chunks of data to itermWriter.
- My Job output looks like Job: [SimpleJob: [name=jobA]] completed with the following parameters: [{time=1620029545051}] and the following status: [COMPLETED] in 8s63ms
