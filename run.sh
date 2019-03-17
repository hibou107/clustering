apps/spark-2.4.0-bin-hadoop2.7/bin/spark-submit \
--class "com.github.hibou107.SparkApp" \
--master local[4] \
clustering/target/scala-2.11/clustering-assembly-0.1.jar